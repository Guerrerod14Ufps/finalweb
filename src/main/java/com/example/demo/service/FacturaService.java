package com.example.demo.service;

import com.example.demo.dto.FacturaDTO;
import com.example.demo.dto.FacturaRequestDTO;
import com.example.demo.dto.FacturaResponseDTO;
import com.example.demo.entity.*;
import com.example.demo.exception.CustomException;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FacturaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private CajeroRepository cajeroRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private DetallesCompraRepository detallesCompraRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Transactional
    public String crearFactura(String tiendaUuid, FacturaDTO facturaDTO) {
        // Validaciones iniciales
        if (facturaDTO.getCliente() == null)
            throw new CustomException("No hay información del cliente", HttpStatus.NOT_FOUND);

        if (facturaDTO.getVendedor() == null)
            throw new CustomException("No hay información del vendedor", HttpStatus.NOT_FOUND);

        if (facturaDTO.getCajero() == null)
            throw new CustomException("No hay información del cajero", HttpStatus.NOT_FOUND);

        if (facturaDTO.getProductos() == null || facturaDTO.getProductos().isEmpty())
            throw new CustomException("No hay productos asignados para esta compra", HttpStatus.NOT_FOUND);

        if (facturaDTO.getMediosPago() == null || facturaDTO.getMediosPago().isEmpty())
            throw new CustomException("No hay medios de pagos asignados para esta compra", HttpStatus.NOT_FOUND);

        // Verificar la tienda
        Tienda tienda = tiendaRepository.findByUuid(tiendaUuid)
                .orElseThrow(() -> new CustomException("Tienda no encontrada", HttpStatus.NOT_FOUND));

        // Verificar el vendedor
        Vendedor vendedor = vendedorRepository.findByDocumento(facturaDTO.getVendedor().getDocumento())
                .orElseThrow(() -> new CustomException("El vendedor no existe en la tienda", HttpStatus.NOT_FOUND));

        // Verificar el cajero
        Cajero cajero = cajeroRepository.findByToken(facturaDTO.getCajero().getToken())
                .orElseThrow(() -> new CustomException("El token no corresponde a ningún cajero en la tienda", HttpStatus.NOT_FOUND));

        if (!cajero.getTienda().equals(tienda)) {
            throw new CustomException("El cajero no está asignado a esta tienda", HttpStatus.FORBIDDEN);
        }

        // Validar y registrar el cliente
        Cliente cliente = clienteRepository.findByDocumentoAndTipoDocumento(
                        facturaDTO.getCliente().getDocumento(),
                        facturaDTO.getCliente().getTipoDocumento())
                .orElseGet(() -> {
                    Cliente nuevoCliente = new Cliente();
                    nuevoCliente.setDocumento(facturaDTO.getCliente().getDocumento());
                    nuevoCliente.setNombre(facturaDTO.getCliente().getNombre());
                    TipoDocumento tipoDocumento = new TipoDocumento();
                    tipoDocumento.setNombre(facturaDTO.getCliente().getTipoDocumento());
                    nuevoCliente.setTipoDocumento(tipoDocumento);
                    return clienteRepository.save(nuevoCliente);
                });

        // Crear la compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setImpuestos(facturaDTO.getImpuesto());
        compra.setFecha(LocalDateTime.now());
        compra.setTotal(0.0); // Calculado después
        compra = compraRepository.save(compra);

        // Agregar los productos
        Double total = 0.0;
        for (FacturaDTO.ProductoDTO productoDTO : facturaDTO.getProductos()) {
            Producto producto = productoRepository.findByReferencia(productoDTO.getReferencia())
                    .orElseThrow(() -> new CustomException(
                            "La referencia del producto " + productoDTO.getReferencia() + " no existe, por favor revisar los datos",
                            HttpStatus.NOT_FOUND));

            if (productoDTO.getCantidad() > producto.getCantidad()) {
                throw new CustomException("La cantidad a comprar supera el máximo del producto en tienda", HttpStatus.FORBIDDEN);
            }

            DetallesCompra detalles = new DetallesCompra();
            detalles.setCompra(compra);
            detalles.setProducto(producto);
            detalles.setCantidad(productoDTO.getCantidad());
            detalles.setDescuento(productoDTO.getDescuento());
            detalles.setPrecio(producto.getPrecio() * productoDTO.getCantidad() * (1 - productoDTO.getDescuento() / 100));
            total += detalles.getPrecio();
            detallesCompraRepository.save(detalles);
        }

        // Registrar los medios de pago
        Double totalPagos = 0.0;
        for (FacturaDTO.MedioPagoDTO medioPagoDTO : facturaDTO.getMediosPago()) {
            TipoPago tipoPago = new TipoPago();
            tipoPago.setNombre(medioPagoDTO.getTipoPago());

            Pago pago = new Pago();
            pago.setCompra(compra);
            pago.setTipoPago(tipoPago);
            pago.setTarjetaTipo(medioPagoDTO.getTipoTarjeta());
            pago.setValor(medioPagoDTO.getValor());
            pago.setCuotas(medioPagoDTO.getCuotas());
            totalPagos += pago.getValor();
            pagoRepository.save(pago);
        }

        // Validar total de pagos
        if (!total.equals(totalPagos)) {
            throw new CustomException("El valor de la factura no coincide con el valor total de los pagos", HttpStatus.FORBIDDEN);
        }

        // Actualizar el total de la compra
        compra.setTotal(total);
        compraRepository.save(compra);

        // Respuesta formateada
        return String.format(
                "{\"status\": \"success\", \"message\": \"La factura se ha creado correctamente con el número: %d\", \"data\": {\"numero\": \"%d\", \"total\": \"%.2f\", \"fecha\": \"%s\"}}",
                compra.getId(), compra.getId(), compra.getTotal(), compra.getFecha().toLocalDate());
    }
    
    
    public FacturaResponseDTO consultarFactura(FacturaRequestDTO request) {
        // Validar el token del cajero
        Cajero cajero = cajeroRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("El token no corresponde a ningún cajero"));

        // Verificar si el cajero está asociado a la tienda
        Compra compra = compraRepository.findById(request.getFactura())
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        // Validar que la factura pertenece a la tienda del cajero
        if (!compra.getTienda().getId().equals(cajero.getTienda().getId())) {
            throw new RuntimeException("El cajero no está asignado a esta tienda");
        }

        // Obtener cliente
        Cliente cliente = clienteRepository.findById(Integer.parseInt(request.getCliente()))
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Obtener detalles de la compra
        List<DetallesCompra> detalles = detallesCompraRepository.findByCompraId(request.getFactura());

        // Crear el DTO de respuesta
        FacturaResponseDTO response = new FacturaResponseDTO();
        response.setTotal(compra.getTotal());
        response.setImpuestos(compra.getImpuestos());

        // Llenar la información del cliente
        FacturaResponseDTO.ClienteDTO clienteDTO = new FacturaResponseDTO.ClienteDTO();
        clienteDTO.setDocumento(cliente.getDocumento());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setTipo_documento(cliente.getTipoDocumento().getNombre());
        response.setCliente(clienteDTO);

        // Llenar los detalles de los productos
        List<FacturaResponseDTO.ProductoDTO> productos = detalles.stream().map(detalle -> {
            FacturaResponseDTO.ProductoDTO productoDTO = new FacturaResponseDTO.ProductoDTO();
            productoDTO.setReferencia(detalle.getProducto().getReferencia());
            productoDTO.setNombre(detalle.getProducto().getNombre());
            productoDTO.setCantidad(detalle.getCantidad());
            productoDTO.setPrecio(detalle.getPrecio());
            productoDTO.setDescuento(detalle.getDescuento());
            productoDTO.setSubtotal(detalle.getCantidad() * detalle.getPrecio() - detalle.getDescuento());
            return productoDTO;
        }).collect(Collectors.toList());

        response.setProductos(productos);

        // Llenar la información del cajero
        FacturaResponseDTO.CajeroDTO cajeroDTO = new FacturaResponseDTO.CajeroDTO();
        cajeroDTO.setDocumento(cajero.getDocumento());
        cajeroDTO.setNombre(cajero.getNombre());
        response.setCajero(cajeroDTO);

        return response;
    }
    
}
