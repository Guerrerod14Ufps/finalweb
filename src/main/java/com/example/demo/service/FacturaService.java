package com.example.demo.service;

import com.example.demo.dto.FacturaDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public void crearFactura(String tiendaUuid, FacturaDTO facturaDTO) throws Exception {
        // 1. Verificar la tienda
        Tienda tienda = tiendaRepository.findByUuid(tiendaUuid)
                .orElseThrow(() -> new Exception("Tienda no encontrada"));

        // 2. Validar y registrar el cliente
        Cliente cliente = clienteRepository.findByDocumento(facturaDTO.getCliente().getDocumento())
                .orElseGet(() -> {
                    Cliente nuevoCliente = new Cliente();
                    nuevoCliente.setDocumento(facturaDTO.getCliente().getDocumento());
                    nuevoCliente.setNombre(facturaDTO.getCliente().getNombre());
                    // Aquí se busca el tipo de documento
                    TipoDocumento tipoDocumento = new TipoDocumento();
                    tipoDocumento.setNombre(facturaDTO.getCliente().getTipoDocumento());
                    nuevoCliente.setTipoDocumento(tipoDocumento);
                    return clienteRepository.save(nuevoCliente);
                });

        // 3. Verificar el vendedor
        Vendedor vendedor = vendedorRepository.findByDocumento(facturaDTO.getVendedor().getDocumento())
                .orElseThrow(() -> new Exception("Vendedor no encontrado"));

        // 4. Verificar el cajero
        Cajero cajero = cajeroRepository.findByToken(facturaDTO.getCajero().getToken())
                .orElseThrow(() -> new Exception("Cajero no encontrado"));

        // 5. Crear la compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setImpuestos(facturaDTO.getImpuesto());
        compra.setFecha(LocalDateTime.now());
        compra.setTotal(0.0); // Se calculará después
        compra = compraRepository.save(compra);

        // 6. Agregar los productos
        Double total = 0.0;
        for (FacturaDTO.ProductoDTO productoDTO : facturaDTO.getProductos()) {
            Producto producto = productoRepository.findByReferencia(productoDTO.getReferencia())
                    .orElseThrow(() -> new Exception("Producto no encontrado: " + productoDTO.getReferencia()));

            DetallesCompra detalles = new DetallesCompra();
            detalles.setCompra(compra);
            detalles.setProducto(producto);
            detalles.setCantidad(productoDTO.getCantidad());
            detalles.setDescuento(productoDTO.getDescuento());
            detalles.setPrecio(producto.getPrecio() * productoDTO.getCantidad() * (1 - productoDTO.getDescuento() / 100));
            total += detalles.getPrecio();
            detallesCompraRepository.save(detalles);
        }

        // 7. Registrar los medios de pago
        for (FacturaDTO.MedioPagoDTO medioPagoDTO : facturaDTO.getMediosPago()) {
            TipoPago tipoPago = new TipoPago();
            tipoPago.setNombre(medioPagoDTO.getTipoPago());

            Pago pago = new Pago();
            pago.setCompra(compra);
            pago.setTipoPago(tipoPago);
            pago.setTarjetaTipo(medioPagoDTO.getTipoTarjeta());
            pago.setValor(medioPagoDTO.getValor());
            pago.setCuotas(medioPagoDTO.getCuotas());
            pagoRepository.save(pago);
        }

        // 8. Actualizar el total de la compra
        compra.setTotal(total);
        compraRepository.save(compra);
    }
}
