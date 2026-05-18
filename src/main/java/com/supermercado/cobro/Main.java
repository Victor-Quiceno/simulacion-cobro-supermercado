package com.supermercado.cobro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Clase Principal (Main) que representa la Ventana Gráfica interactiva de la aplicación.
 * Hereda de JFrame para abrir un panel de control tipo Dashboard con 3 columnas,
 * una para cada cajera.
 * 
 * Permite visualizar de forma interactiva y simultánea (concurrencia pura) el 
 * avance de cobro de los clientes mediante barras de progreso animadas en tiempo real.
 * 
 * @author Estudiante de Desarrollo de Software
 */
public class Main extends JFrame {

    // Componentes visuales globales
    private JButton btnIniciar;
    private JLabel lblEstado;
    private JLabel lblTiempoGlobal;

    // Componentes visuales para la Caja 1: Ana (Azul)
    private JLabel lblClientAna = new JLabel();
    private JLabel lblProductAna = new JLabel();
    private JProgressBar progressAna = new JProgressBar();
    private JTextArea txtHistoryAna = new JTextArea();
    private JLabel lblTotalAna = new JLabel();

    // Componentes visuales para la Caja 2: Marta (Morado)
    private JLabel lblClientMarta = new JLabel();
    private JLabel lblProductMarta = new JLabel();
    private JProgressBar progressMarta = new JProgressBar();
    private JTextArea txtHistoryMarta = new JTextArea();
    private JLabel lblTotalMarta = new JLabel();

    // Componentes visuales para la Caja 3: Sofia (Naranja)
    private JLabel lblClientSofia = new JLabel();
    private JLabel lblProductSofia = new JLabel();
    private JProgressBar progressSofia = new JProgressBar();
    private JTextArea txtHistorySofia = new JTextArea();
    private JLabel lblTotalSofia = new JLabel();

    /**
     * Constructor que dibuja y configura todo el panel de control gráfico.
     */
    public Main() {
        // 1. Configuración básica de la ventana
        setTitle("EA2: Simulador de Cobro en Supermercado - Dashboard Concurrente");
        setSize(980, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(240, 243, 244)); // Fondo gris claro moderno

        // 2. ENCABEZADO (Panel Norte)
        JPanel panelHeader = new JPanel(new GridLayout(2, 1, 5, 5));
        panelHeader.setBackground(new Color(33, 47, 60)); // Fondo azul oscuro
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("DASHBOARD DE COBRO CONCURRENTE EN SUPERMERCADO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Visualización del procesamiento simultáneo de cajeras con hilos de ejecución en Java", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 13));
        lblSubtitulo.setForeground(new Color(174, 214, 241));

        panelHeader.add(lblTitulo);
        panelHeader.add(lblSubtitulo);
        add(panelHeader, BorderLayout.NORTH);

        // 3. CUERPO DE CAJAS REGISTRADORAS (Panel Central - 3 Columnas)
        JPanel panelCajas = new JPanel(new GridLayout(1, 3, 20, 0));
        panelCajas.setBackground(new Color(240, 243, 244));
        panelCajas.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Caja 1: Ana (Color Azul)
        JPanel cajaAna = crearPanelCaja("Caja 1: Ana", new Color(46, 134, 193), 
                lblClientAna, lblProductAna, progressAna, txtHistoryAna, lblTotalAna);
        
        // Caja 2: Marta (Color Morado)
        JPanel cajaMarta = crearPanelCaja("Caja 2: Marta", new Color(142, 68, 173), 
                lblClientMarta, lblProductMarta, progressMarta, txtHistoryMarta, lblTotalMarta);

        // Caja 3: Sofia (Color Naranja)
        JPanel cajaSofia = crearPanelCaja("Caja 3: Sofia", new Color(211, 84, 0), 
                lblClientSofia, lblProductSofia, progressSofia, txtHistorySofia, lblTotalSofia);

        panelCajas.add(cajaAna);
        panelCajas.add(cajaMarta);
        panelCajas.add(cajaSofia);
        add(panelCajas, BorderLayout.CENTER);

        // 4. CONTROLES Y TIEMPO (Panel Sur)
        JPanel panelControles = new JPanel(new BorderLayout(10, 5));
        panelControles.setBackground(new Color(240, 243, 244));
        panelControles.setBorder(BorderFactory.createEmptyBorder(5, 20, 20, 20));

        // Botón Iniciar
        btnIniciar = new JButton("Iniciar Proceso de Cobro en Paralelo");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 15));
        btnIniciar.setBackground(new Color(39, 174, 96)); // Verde éxito
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 132, 73), 1),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));
        btnIniciar.addActionListener(e -> iniciarProcesoDeCobro());

        // Panel de textos inferiores
        JPanel panelTextos = new JPanel(new GridLayout(2, 1, 2, 2));
        panelTextos.setBackground(new Color(240, 243, 244));

        lblEstado = new JLabel("Listo para iniciar la simulación visual.");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 13));
        lblEstado.setForeground(new Color(86, 101, 115));

        lblTiempoGlobal = new JLabel("Tiempo total de ejecución del sistema: 0.00 segundos");
        lblTiempoGlobal.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTiempoGlobal.setForeground(new Color(120, 120, 120));

        panelTextos.add(lblEstado);
        panelTextos.add(lblTiempoGlobal);

        panelControles.add(btnIniciar, BorderLayout.EAST);
        panelControles.add(panelTextos, BorderLayout.WEST);
        add(panelControles, BorderLayout.SOUTH);
    }

    /**
     * Helper para construir y estilizar de forma idéntica el panel de cada caja registradora.
     */
    private JPanel crearPanelCaja(String tituloCaja, Color colorTema, 
                                  JLabel lblClient, JLabel lblProduct, 
                                  JProgressBar progress, JTextArea txtHistory, 
                                  JLabel lblTotal) {
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        // Borde redondeado y sombreado simulado con márgenes
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(218, 223, 225), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Título del encabezado de la caja
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(colorTema);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        JLabel lblTitulo = new JLabel(tituloCaja.toUpperCase(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        panel.add(panelTitulo, BorderLayout.NORTH);

        // Cuerpo dinámico
        JPanel panelCuerpo = new JPanel(new GridLayout(3, 1, 5, 5));
        panelCuerpo.setBackground(Color.WHITE);

        lblClient.setText("Esperando cliente...");
        lblClient.setFont(new Font("Arial", Font.BOLD, 13));
        lblClient.setForeground(new Color(44, 62, 80));

        lblProduct.setText("Caja inactiva.");
        lblProduct.setFont(new Font("Arial", Font.PLAIN, 12));
        lblProduct.setForeground(new Color(127, 140, 141));

        progress.setValue(0);
        progress.setStringPainted(true);
        progress.setForeground(colorTema);
        progress.setBackground(new Color(242, 244, 244));
        progress.setBorder(BorderFactory.createLineBorder(new Color(229, 232, 232), 1));

        panelCuerpo.add(lblClient);
        panelCuerpo.add(lblProduct);
        panelCuerpo.add(progress);

        // Visor de productos cobrados
        txtHistory.setEditable(false);
        txtHistory.setBackground(new Color(250, 252, 252));
        txtHistory.setForeground(new Color(52, 73, 94));
        txtHistory.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtHistory.setMargin(new Insets(8, 8, 8, 8));
        
        JScrollPane scroll = new JScrollPane(txtHistory);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(235, 237, 239)));

        JPanel panelCentro = new JPanel(new BorderLayout(5, 10));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.add(panelCuerpo, BorderLayout.NORTH);
        panelCentro.add(scroll, BorderLayout.CENTER);
        panel.add(panelCentro, BorderLayout.CENTER);

        // Precio total inferior
        lblTotal.setText("Total: $0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 17));
        lblTotal.setForeground(new Color(39, 174, 96)); // Verde precios
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        panel.add(lblTotal, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Orquesta el proceso de simulación. Lanza los hilos en background para evitar
     * el congelamiento de la ventana y sincroniza su fin.
     */
    private void iniciarProcesoDeCobro() {
        btnIniciar.setEnabled(false);
        lblEstado.setText("Proceso iniciado de forma paralela. Ejecutando hilos de cajeras...");
        lblTiempoGlobal.setText("Midiendo tiempo del sistema...");

        // Lanzamos el hilo orquestador
        new Thread(() -> {
            // 1. Creación de una variedad de productos con sus precios y tiempos de procesamiento
            Producto leche = new Producto("Leche", 5000, 2);
            Producto pan = new Producto("Pan", 3000, 1);
            Producto arroz = new Producto("Arroz", 2500, 1);
            Producto cafe = new Producto("Cafe", 8000, 3);
            Producto huevos = new Producto("Huevos", 6000, 2);

            // 2. Creación de carritos de compras
            List<Producto> productosCliente1 = new ArrayList<>();
            productosCliente1.add(leche);
            productosCliente1.add(pan);
            Cliente cliente1 = new Cliente("Cliente 1 (Juan)", productosCliente1);

            List<Producto> productosCliente2 = new ArrayList<>();
            productosCliente2.add(arroz);
            productosCliente2.add(cafe);
            Cliente cliente2 = new Cliente("Cliente 2 (Maria)", productosCliente2);

            List<Producto> productosCliente3 = new ArrayList<>();
            productosCliente3.add(huevos);
            productosCliente3.add(leche);
            productosCliente3.add(pan);
            Cliente cliente3 = new Cliente("Cliente 3 (Pedro)", productosCliente3);

            // 3. Capturamos la marca de tiempo inicial global del sistema
            long tiempoInicialGlobal = System.currentTimeMillis();

            // 4. Instanciamos y vinculamos las cajeras concurrentes con sus respectivas columnas gráficas
            Cajera cajera1 = new Cajera("Ana", cliente1, tiempoInicialGlobal, 
                    lblClientAna, lblProductAna, progressAna, txtHistoryAna, lblTotalAna);
            
            Cajera cajera2 = new Cajera("Marta", cliente2, tiempoInicialGlobal, 
                    lblClientMarta, lblProductMarta, progressMarta, txtHistoryMarta, lblTotalMarta);
            
            Cajera cajera3 = new Cajera("Sofia", cliente3, tiempoInicialGlobal, 
                    lblClientSofia, lblProductSofia, progressSofia, txtHistorySofia, lblTotalSofia);

            // 5. Lanzamos la ejecución en hilos separados paralelos
            cajera1.start();
            cajera2.start();
            cajera3.start();

            // 6. Esperamos la finalización de todas las cajas
            try {
                cajera1.join();
                cajera2.join();
                cajera3.join();
            } catch (InterruptedException ex) {
                System.out.println("Error: Simulación interrumpida.");
            }

            // 7. Calculamos el tiempo total general de la operación paralela
            long tiempoTotalSistema = System.currentTimeMillis() - tiempoInicialGlobal;

            // 8. Actualizamos los controles en la interfaz en el hilo EDT de Swing
            SwingUtilities.invokeLater(() -> {
                btnIniciar.setEnabled(true);
                lblEstado.setText("¡Simulación completada con éxito!");
                lblTiempoGlobal.setText("Tiempo total de ejecución del sistema: " 
                        + tiempoTotalSistema + " ms (" + (tiempoTotalSistema / 1000.0) + " segundos)");
            });

        }).start();
    }

    /**
     * Punto de entrada principal para iniciar la interfaz gráfica.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
