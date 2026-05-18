package com.supermercado.cobro.service;

import com.supermercado.cobro.concurrency.CajeraRunnable;
import com.supermercado.cobro.dto.ClienteDTO;
import com.supermercado.cobro.entity.HistorialSimulacion;
import com.supermercado.cobro.repository.HistorialSimulacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulacionService {

    private static final Logger log = LoggerFactory.getLogger(SimulacionService.class);

    private final HistorialSimulacionRepository historialRepository;

    public SimulacionService(HistorialSimulacionRepository historialRepository) {
        this.historialRepository = historialRepository;
    }

    public HistorialSimulacion ejecutarSimulacion(List<ClienteDTO> clientes) {
        // Capturamos el tiempo en el que inicia toda la simulación
        long timeStampInicial = System.currentTimeMillis();

        // Lista para guardar y vigilar los hilos que vamos a crear
        List<Thread> hilosCajeras = new ArrayList<>();

        int numeroCajera = 1;

        // Iteramos por la lista de clientes recibida
        for (ClienteDTO cliente : clientes) {
            // Instanciamos el trabajo de la cajera
            CajeraRunnable cajeraRunnable = new CajeraRunnable("Cajera " + numeroCajera, cliente, timeStampInicial);

            // Envolvemos el trabajo en un Hilo (Thread) real
            Thread hilo = new Thread(cajeraRunnable);

            // Le damos la orden de inicio para que trabaje en paralelo
            hilo.start();

            // Guardamos el hilo en la lista
            hilosCajeras.add(hilo);

            numeroCajera++;
        }

        // Sincronizar hilos con .join()
        for (Thread hilo : hilosCajeras) {
            try {
                // Congelamos el orquestador hasta que este hilo específico termine
                hilo.join();
            } catch (InterruptedException e) {
                log.error("Error al esperar a que terminara una cajera", e);
                Thread.currentThread().interrupt();
            }
        }

        // Cuando este bucle de .join() termina, tenemos la certeza absoluta
        // de que todas las cajeras terminaron. Calculamos el tiempo total.
        long tiempoTotalMilisegundos = System.currentTimeMillis() - timeStampInicial;
        double tiempoTotalSegundos = tiempoTotalMilisegundos / 1000.0;

        log.info(">>>> SIMULACIÓN FINALIZADA <<<<");
        log.info("Tiempo total absoluto para atender a {} clientes: {} segundos", clientes.size(), tiempoTotalSegundos);

        // Instanciamos el objeto de base de datos y lo guardamos
        HistorialSimulacion historial = new HistorialSimulacion(clientes.size(), tiempoTotalMilisegundos,
                tiempoTotalSegundos);

        // Retornamos el objeto guardado para que el controlador pueda usarlo
        return historialRepository.save(historial);
    }
}
