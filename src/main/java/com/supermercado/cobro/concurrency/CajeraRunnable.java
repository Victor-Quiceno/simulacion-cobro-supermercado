package com.supermercado.cobro.concurrency;

import com.supermercado.cobro.dto.ClienteDTO;
import com.supermercado.cobro.dto.ProductoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CajeraRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CajeraRunnable.class);

    private String nombreCajera;
    private ClienteDTO cliente;
    private long timeStampInicial;

    public CajeraRunnable(String nombreCajera, ClienteDTO cliente, long timeStampInicial) {
        this.nombreCajera = nombreCajera;
        this.cliente = cliente;
        this.timeStampInicial = timeStampInicial;
    }

    @Override
    public void run() {
        log.info("La cajera {} COMIENZA A PROCESAR LA COMPRA DEL CLIENTE {} EN EL TIEMPO: {} seg",
                this.nombreCajera,
                this.cliente.getNombreCliente(),
                (System.currentTimeMillis() - this.timeStampInicial) / 1000.0);

        for (ProductoDTO producto : this.cliente.getProductos()) {
            try {
                // Simular el tiempo que tarda la cajera en pasar el producto por la caja
                Thread.sleep(producto.getTiempoProcesamientoSegundos() * 1000L);
            } catch (InterruptedException e) {
                log.error("La cajera {} fue interrumpida al procesar el producto {}",
                        this.nombreCajera, producto.getNombre());
                Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
            }

            log.info("Cajera {} procesó producto: {} | Costo: ${} | Tardó: {} seg | Tiempo transcurrido: {} seg",
                    this.nombreCajera,
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getTiempoProcesamientoSegundos(),
                    (System.currentTimeMillis() - this.timeStampInicial) / 1000.0);
        }

        // Calcular y mostrar el tiempo final al terminar el carrito
        double tiempoFinalCalculado = (System.currentTimeMillis() - this.timeStampInicial) / 1000.0;
        log.info("====> La cajera {} HA TERMINADO DE PROCESAR AL CLIENTE {} EN UN TIEMPO TOTAL DE: {} seg <====",
                this.nombreCajera,
                this.cliente.getNombreCliente(),
                tiempoFinalCalculado);
    }
}
