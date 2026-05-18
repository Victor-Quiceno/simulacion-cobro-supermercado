package com.supermercado.cobro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase de entrada de Spring Boot.
 * Implementa CommandLineRunner para que, al ejecutar el proyecto completo
 * como una aplicación Spring Boot (por ejemplo, al dar click en "Run Project" en NetBeans o IntelliJ),
 * la simulación de consola se ejecute automáticamente y se cierre al terminar.
 */
@SpringBootApplication
public class SimulacionCobroSupermercadoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimulacionCobroSupermercadoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Ejecutamos la simulación principal
        Main.main(args);
        // Cerramos la aplicación de Spring para evitar que se quede activa en segundo plano
        System.exit(0);
    }
}