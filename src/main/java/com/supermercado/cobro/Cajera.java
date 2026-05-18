package com.supermercado.cobro;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Clase que representa a una cajera del supermercado.
 * Hereda de Thread para ejecutarse en paralelo de forma concurrente.
 * 
 * Se comunica directamente con los componentes visuales de su columna
 * en la interfaz gráfica para actualizar etiquetas, el historial de compra,
 * el total acumulado y animar la barra de progreso en tiempo real de forma suave.
 * 
 * @author Estudiante de Desarrollo de Software
 */
public class Cajera extends Thread {
    // Atributos privados para el encapsulamiento
    private String nombre;
    private Cliente cliente;
    private long tiempoInicial;

    // Componentes de la interfaz gráfica asignados a esta cajera
    private JLabel lblCliente;
    private JLabel lblProducto;
    private JProgressBar progressBar;
    private JTextArea txtHistorial;
    private JLabel lblTotal;

    /**
     * Constructor vacío por buenas prácticas.
     */
    public Cajera() {
    }

    /**
     * Constructor completo para inicializar la cajera y vincular sus componentes visuales.
     * 
     * @param nombre        Nombre de la cajera (ej: Ana)
     * @param cliente       Cliente asignado para atender
     * @param tiempoInicial Tiempo inicial de la simulación para medir transcurridos
     * @param lblCliente    Etiqueta que muestra el cliente activo
     * @param lblProducto   Etiqueta que muestra el producto que se está cobrando
     * @param progressBar   Barra de progreso de cobro del producto actual
     * @param txtHistorial  Área de texto donde se listan los productos ya cobrados
     * @param lblTotal      Etiqueta que muestra el total acumulado de la compra
     */
    public Cajera(String nombre, Cliente cliente, long tiempoInicial, 
                  JLabel lblCliente, JLabel lblProducto, JProgressBar progressBar, 
                  JTextArea txtHistorial, JLabel lblTotal) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.tiempoInicial = tiempoInicial;
        this.lblCliente = lblCliente;
        this.lblProducto = lblProducto;
        this.progressBar = progressBar;
        this.txtHistorial = txtHistorial;
        this.lblTotal = lblTotal;
    }

    /**
     * Método principal que se ejecuta al iniciar el hilo de la cajera (.start()).
     * Recorre cada producto del cliente, actualiza las etiquetas, anima la barra de progreso
     * en pequeños pasos y añade los ítems cobrados al historial.
     */
    @Override
    public void run() {
        // 1. Logs iniciales en la terminal tradicional para depuración
        System.out.println("Cajera " + this.nombre + " comienza a atender a " + this.cliente.getNombre() 
                + " (Tiempo: " + (System.currentTimeMillis() - this.tiempoInicial) + " ms)");

        // 2. Actualizamos el estado del cliente en la ventana gráfica
        SwingUtilities.invokeLater(() -> {
            lblCliente.setText("Atendiendo a: " + this.cliente.getNombre());
            txtHistorial.setText(""); // Limpiamos el historial de la caja
            lblTotal.setText("Total: $0");
            progressBar.setValue(0);
        });

        double totalCompra = 0; // Acumulador del total de la compra

        // 3. Iteramos por cada producto que lleva el cliente en su carrito
        for (Producto producto : this.cliente.getProductos()) {
            try {
                // Mostramos en la ventana qué producto se está cobrando
                SwingUtilities.invokeLater(() -> {
                    lblProducto.setText("Cobrando: " + producto.getNombre() + " ($" + producto.getPrecio() + ")");
                });

                System.out.println("[Cajera " + this.nombre + "] Iniciando cobro de " + producto.getNombre() 
                        + " (" + producto.getTiempoProcesamiento() + "s)");

                // ANIMACIÓN SUAVE DE LA BARRA DE PROGRESO:
                // Si el producto tarda T segundos, dividimos la espera en 100 pasos (de 0% a 100%).
                // Cada paso dormirá T * 10 milisegundos.
                // Ejemplo: si T = 2s (2000ms), cada paso duerme 2 * 10 = 20ms. Total: 100 * 20 = 2000ms.
                int tiempoPorPaso = producto.getTiempoProcesamiento() * 10;
                
                for (int p = 0; p <= 100; p++) {
                    final int porcentaje = p;
                    SwingUtilities.invokeLater(() -> progressBar.setValue(porcentaje));
                    Thread.sleep(tiempoPorPaso);
                }

                // Sumamos el precio al total del cliente
                totalCompra += producto.getPrecio();
                final double totalAcumulado = totalCompra;

                // Añadimos el producto procesado al historial gráfico y actualizamos el total acumulado
                SwingUtilities.invokeLater(() -> {
                    txtHistorial.append("✔ " + producto.getNombre() + " - $" + producto.getPrecio() 
                            + " (" + producto.getTiempoProcesamiento() + "s)\n");
                    lblTotal.setText("Total: $" + totalAcumulado);
                });

                System.out.println("[Cajera " + this.nombre + "] Termino cobro de " + producto.getNombre() 
                        + " | Tiempo: " + (System.currentTimeMillis() - this.tiempoInicial) + " ms");

            } catch (InterruptedException e) {
                System.out.println("ERROR: El hilo de la cajera " + this.nombre + " fue interrumpido.");
                SwingUtilities.invokeLater(() -> {
                    lblProducto.setText("ERROR: Interrumpido");
                });
                Thread.currentThread().interrupt(); // Restablece el estado de interrupción del hilo
                return;
            }
        }

        // 4. Mostramos al finalizar todos los productos que se terminó de atender al cliente
        SwingUtilities.invokeLater(() -> {
            lblCliente.setText("Atendido ✔");
            lblProducto.setText("Compra finalizada.");
            progressBar.setValue(100);
        });

        System.out.println("Cajera " + this.nombre + " termino de atender a " + this.cliente.getNombre() 
                + " (Tiempo de compra: " + (System.currentTimeMillis() - this.tiempoInicial) + " ms)");
    }

    // --- MÉTODOS GETTERS Y SETTERS PARA EL ENCAPSULAMIENTO ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public long getTiempoInicial() {
        return tiempoInicial;
    }

    public void setTiempoInicial(long tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }
}
