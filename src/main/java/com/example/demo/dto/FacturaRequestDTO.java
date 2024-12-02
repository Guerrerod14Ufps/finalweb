package com.example.demo.dto;

import lombok.Data;

@Data
public class FacturaRequestDTO {
    private String token;
    private String cliente;
    private Integer factura;

}