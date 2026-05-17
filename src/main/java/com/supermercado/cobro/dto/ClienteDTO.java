package com.supermercado.cobro.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ClienteDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;

    @Valid
    @NotEmpty(message = "El cliente debe tener al menos un producto")
    private List<ProductoDTO> productos;

    public ClienteDTO() {
    }

    public ClienteDTO(String nombreCliente, List<ProductoDTO> productos) {
        this.nombreCliente = nombreCliente;
        this.productos = productos;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }
}