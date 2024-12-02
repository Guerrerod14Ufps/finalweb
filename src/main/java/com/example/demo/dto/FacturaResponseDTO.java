package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class FacturaResponseDTO {
    private Double total;
    private Double impuestos;
    private ClienteDTO cliente;
    private List<ProductoDTO> productos;
    private CajeroDTO cajero;

    @Data
    public static class ClienteDTO {
        private String documento;
        private String nombre;
        private String tipo_documento;

    }

    @Data
    public static class ProductoDTO {
        private String referencia;
        private String nombre;
        private Integer cantidad;
        private Double precio;
        private Double descuento;
        private Double subtotal;
    }

    @Data
    public static class CajeroDTO {
        private String documento;
        private String nombre;
    }
}
