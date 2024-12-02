package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class FacturaDTO {
    private Double impuesto;
    private ClienteDTO cliente;
    private List<ProductoDTO> productos;
    private List<MedioPagoDTO> mediosPago;
    private VendedorDTO vendedor;
    private CajeroDTO cajero;

    @Data
    public static class ClienteDTO {
        private String documento;
        private String nombre;
        private String tipoDocumento;
    }

    @Data
    public static class ProductoDTO {
        private String referencia;
        private Integer cantidad;
        private Double descuento;
    }

    @Data
    public static class MedioPagoDTO {
        private String tipoPago;
        private String tipoTarjeta;
        private Integer cuotas;
        private Double valor;
    }

    @Data
    public static class VendedorDTO {
        private String documento;
    }

    @Data
    public static class CajeroDTO {
        private String token;
    }
}
