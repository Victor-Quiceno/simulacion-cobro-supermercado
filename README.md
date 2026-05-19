# Simulacion de Cobro Supermercado

## Objetivo del Proyecto
Este proyecto es una API REST desarrollada en Spring Boot que simula el proceso de cobro de un supermercado. Su objetivo principal es demostrar la programacion concurrente mediante el uso de hilos (Threads) en Java. 

El sistema permite procesar multiples clientes de forma simultanea, donde cada "cajera" (hilo) atiende a un cliente de forma paralela. El tiempo de procesamiento se simula pausando el hilo la cantidad de segundos que requiere cada producto. Al finalizar la simulacion completa, los resultados del tiempo total de ejecucion se persisten en una base de datos en memoria.

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- H2 Database (Base de datos en memoria)
- Jakarta Validation (Para validacion de datos)

## Como Inicializar el Proyecto
1. Clona o descarga el repositorio en tu maquina local.
2. Abre el proyecto en tu IDE de preferencia (IntelliJ IDEA, Eclipse, VS Code).
3. Ejecuta la clase principal `SimulacionCobroSupermercadoApplication.java` o utiliza el siguiente comando de Maven en la terminal desde la raiz del proyecto:
   `mvn spring-boot:run`
4. El servidor se iniciara en el puerto 8080.

## Como Usar la API
El sistema expone un unico endpoint para iniciar la simulacion.

- **URL:** `http://localhost:8080/api/simulacion`
- **Metodo:** `POST`
- **Headers:** `Content-Type: application/json`

### Ejemplo de Peticion (Body)
Debes enviar una lista de clientes con sus respectivos productos y el tiempo que toma procesar cada uno:

```json
[
  {
    "nombreCliente": "Cliente1",
    "productos": [
      {
        "nombre": "Leche",
        "precio": 5000,
        "tiempoProcesamientoSegundos": 2
      },
      {
        "nombre": "Pan",
        "precio": 3000,
        "tiempoProcesamientoSegundos": 1
      }
    ]
  },
  {
    "nombreCliente": "Cliente2",
    "productos": [
      {
        "nombre": "Carne",
        "precio": 15000,
        "tiempoProcesamientoSegundos": 4
      }
    ]
  }
]
```

## Base de Datos
El proyecto utiliza H2 Database para almacenar el historial de las simulaciones realizadas.

Para acceder a la base de datos:
1. Con la aplicacion en ejecucion, ingresa desde tu navegador a: `http://localhost:8080/h2-console`
2. En la pantalla de inicio de sesion, utiliza los siguientes datos:
   - **JDBC URL:** `jdbc:h2:mem:supermercado_db`
   - **User Name:** `sa`
   - **Password:** (dejar en blanco)
3. Haz clic en "Connect".
4. Podras realizar consultas a la tabla `historial_simulacion`.
