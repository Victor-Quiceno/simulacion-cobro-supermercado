package com.supermercado.cobro;

/**
 * Clase que representa un producto del supermercado.
 * Contiene información básica como el nombre, el precio y el tiempo
 * que tarda una cajera en procesarlo (simulado).
 * 
 * @author Estudiante de Desarrollo de Software
 */
public class Producto {
    // Atributos privados para cumplir con el encapsulamiento
    private String nombre;
    private double precio;
    private int tiempoProcesamiento; // Tiempo en segundos para simular el cobro

    /**
     * Constructor vacío por buenas prácticas.
     */
    public Producto() {
    }

    /**
     * Constructor con todos los parámetros para inicializar el producto.
     * 
     * @param nombre              Nombre del producto (ej: Leche)
     * @param precio              Precio de venta del producto (ej: 5000)
     * @param tiempoProcesamiento Tiempo que tarda la cajera en cobrarlo (en segundos)
     */
    public Producto(String nombre, double precio, int tiempoProcesamiento) {
        this.nombre = nombre;
        this.precio = precio;
        this.tiempoProcesamiento = tiempoProcesamiento;
    }

    // --- MÉTODOS GETTERS Y SETTERS PARA EL ENCAPSULAMIENTO ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getTiempoProcesamiento() {
        return tiempoProcesamiento;
    }

    public void setTiempoProcesamiento(int tiempoProcesamiento) {
        this.tiempoProcesamiento = tiempoProcesamiento;
    }
}
