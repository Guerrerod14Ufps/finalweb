package com.example.demo.controller;

import com.example.demo.dto.FacturaDTO;
import com.example.demo.dto.FacturaRequestDTO;
import com.example.demo.dto.FacturaResponseDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/crear/{tiendaUuid}")
    public ResponseEntity<String> crearFactura(@PathVariable String tiendaUuid, @RequestBody FacturaDTO facturaDTO) {
        try {
            String respuesta = facturaService.crearFactura(tiendaUuid, facturaDTO);
            return ResponseEntity.ok(respuesta);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatus()).body(String.format("{\"status\": \"error\", \"message\": \"%s\", \"data\": null}", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"status\": \"error\", \"message\": \"Error interno del servidor\", \"data\": null}");
        }
    }
    

    @PostMapping("/consultar/{tiendaUuid}")
    public ResponseEntity<FacturaResponseDTO> consultarFactura(@PathVariable String tiendaUuid, @RequestBody FacturaRequestDTO request) {
        try {
            FacturaResponseDTO facturaResponse = facturaService.consultarFactura(request);
            return ResponseEntity.ok(facturaResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new FacturaResponseDTO()); 
        }
    }
}
