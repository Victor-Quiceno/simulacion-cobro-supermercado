package com.supermercado.cobro;

import java.util.List;

/**
 * Clase que representa un cliente en el supermercado.
 * Cada cliente tiene un nombre y una lista de productos en su carrito de compras
 * que serán procesados por una cajera en un hilo independiente.
 * 
 * @author Estudiante de Desarrollo de Software
 */
public class Cliente {
    // Atributos privados para cumplir con el encapsulamiento
    private String nombre;
    private List<Producto> productos; // Lista de productos que lleva el cliente

    /**
     * Constructor vacío por buenas prácticas.
     */
    public Cliente() {
    }

    /**
     * Constructor con todos los parámetros para inicializar el cliente.
     * 
     * @param nombre    Nombre del cliente (ej: Cliente 1)
     * @param productos Lista de productos en su carrito de compras
     */
    public Cliente(String nombre, List<Producto> productos) {
        this.nombre = nombre;
        this.productos = productos;
    }

    // --- MÉTODOS GETTERS Y SETTERS PARA EL ENCAPSULAMIENTO ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}
