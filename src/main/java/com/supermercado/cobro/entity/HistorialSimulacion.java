package com.supermercado.cobro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_simulacion")
public class HistorialSimulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaSimulacion;

    private Integer cantidadClientes;

    private Long tiempoTotalMilisegundos;

    private Double tiempoTotalSegundos;

    public HistorialSimulacion() {
    }

    public HistorialSimulacion(Integer cantidadClientes, Long tiempoTotalMilisegundos, Double tiempoTotalSegundos) {
        this.fechaSimulacion = LocalDateTime.now();
        this.cantidadClientes = cantidadClientes;
        this.tiempoTotalMilisegundos = tiempoTotalMilisegundos;
        this.tiempoTotalSegundos = tiempoTotalSegundos;
    }

    @PrePersist
    public void asignarFechaAntesDeGuardar() {
        if (this.fechaSimulacion == null) {
            this.fechaSimulacion = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaSimulacion() {
        return fechaSimulacion;
    }

    public void setFechaSimulacion(LocalDateTime fechaSimulacion) {
        this.fechaSimulacion = fechaSimulacion;
    }

    public Integer getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(Integer cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public Long getTiempoTotalMilisegundos() {
        return tiempoTotalMilisegundos;
    }

    public void setTiempoTotalMilisegundos(Long tiempoTotalMilisegundos) {
        this.tiempoTotalMilisegundos = tiempoTotalMilisegundos;
    }

    public Double getTiempoTotalSegundos() {
        return tiempoTotalSegundos;
    }

    public void setTiempoTotalSegundos(Double tiempoTotalSegundos) {
        this.tiempoTotalSegundos = tiempoTotalSegundos;
    }
}