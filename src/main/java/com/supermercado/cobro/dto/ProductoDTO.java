package com.supermercado.cobro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ProductoDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotNull(message = "El precio del producto es obligatorio")
    @Positive(message = "El precio del producto debe ser mayor que cero")
    private BigDecimal precio;

    @NotNull(message = "El tiempo de procesamiento es obligatorio")
    @Min(value = 1, message = "El tiempo de procesamiento debe ser mínimo de 1 segundo")
    private Integer tiempoProcesamientoSegundos;

    public ProductoDTO() {
    }

    public ProductoDTO(String nombre, BigDecimal precio, Integer tiempoProcesamientoSegundos) {
        this.nombre = nombre;
        this.precio = precio;
        this.tiempoProcesamientoSegundos = tiempoProcesamientoSegundos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getTiempoProcesamientoSegundos() {
        return tiempoProcesamientoSegundos;
    }

    public void setTiempoProcesamientoSegundos(Integer tiempoProcesamientoSegundos) {
        this.tiempoProcesamientoSegundos = tiempoProcesamientoSegundos;
    }
}