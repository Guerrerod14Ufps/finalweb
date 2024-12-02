package com.example.demo.controller;

import com.example.demo.dto.FacturaDTO;
import com.example.demo.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crear")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/{tiendaUuid}")
    public String crearFactura(@PathVariable String tiendaUuid, @RequestBody FacturaDTO facturaDTO) {
        try {
            facturaService.crearFactura(tiendaUuid, facturaDTO);
            return "Factura creada exitosamente";
        } catch (Exception e) {
            return "Error al crear la factura: " + e.getMessage();
        }
    }
}
