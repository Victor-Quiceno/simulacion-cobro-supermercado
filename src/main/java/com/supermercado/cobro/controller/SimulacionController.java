package com.supermercado.cobro.controller;

import com.supermercado.cobro.dto.ClienteDTO;
import com.supermercado.cobro.entity.HistorialSimulacion;
import com.supermercado.cobro.service.SimulacionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/simulacion")
@Validated // Activa la validación en colecciones como List<>
public class SimulacionController {

    private final SimulacionService simulacionService;

    public SimulacionController(SimulacionService simulacionService) {
        this.simulacionService = simulacionService;
    }

    @PostMapping
    public ResponseEntity<HistorialSimulacion> iniciarSimulacion(
            @RequestBody @NotEmpty(message = "La fila de clientes no puede estar vacía") List<@Valid ClienteDTO> clientes) {

        HistorialSimulacion resultado = simulacionService.ejecutarSimulacion(clientes);

        // Retorna HTTP 200 OK y el reporte final en formato JSON
        return ResponseEntity.ok(resultado);
    }
}
