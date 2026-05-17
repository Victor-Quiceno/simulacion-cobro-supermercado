# Plan de Acción Actualizado: Simulación de Cobro en Supermercado

Este documento ha sido unificado con el plan original del equipo (`Plan.md`) y las decisiones recientes para implementar la simulación del proceso de cobro, utilizando concurrencia en Java con Spring Boot y H2.

## Validación del Trabajo Actual (Integrante 1)
He revisado el código implementado hasta ahora por el **Integrante 1** en la carpeta `src/main/java` y puedo confirmar que **cumple perfectamente con los requerimientos**:
- La configuración de `application.properties` para H2 en memoria y JPA está correcta.
- La entidad `HistorialSimulacion` fue creada con todos los atributos necesarios (fecha, cantidad de clientes, tiempo total) y usa el ciclo de vida de JPA (`@PrePersist`) correctamente para asignar la fecha.
- La interfaz `HistorialSimulacionRepository` hereda correctamente de `JpaRepository`.
- Los DTOs (`ClienteDTO` y `ProductoDTO`) están bien estructurados e incluyen las anotaciones de validación (`@Valid`, `@NotBlank`, `@Positive`, `@Min`, etc.) tal como se planeó.

**Estado de la fase 1:** ¡Aprobado y listo! ✅

## Decisiones Técnicas Confirmadas
De acuerdo con las respuestas obtenidas, el sistema operará bajo estas reglas:
1. **Persistencia (H2):** Se guardará el historial global de cada ejecución de simulación en la base de datos a través de la entidad `HistorialSimulacion`.
2. **Visualización:** El seguimiento paso a paso de cada cajera (qué producto está cobrando y su costo) se registrará mediante **logs impresos en la consola** de Spring Boot. No hace falta responder un JSON gigante con todos los detalles.
3. **Escala de Tiempo:** Se utilizará el tiempo real. Si un producto dice que tarda 3 segundos en procesarse, el hilo de la cajera se suspenderá exactamente 3 segundos (`Thread.sleep(3000)`). Esto permitirá ver la concurrencia de forma natural.

## Arquitectura y Mapa de Capas

### 1. Capa de Modelos y Datos (Completada por Integrante 1 ✅)
Contiene las entidades JPA y objetos de transferencia (DTOs) que aseguran la integridad de los datos.

### 2. Capa del Motor Concurrente (Próximos pasos - Integrante 2 🚧)
- **Clase `Cajera` (Implementa `Runnable`)**:
  - Será el núcleo concurrente.
  - Recibirá por constructor su nombre, el `ClienteDTO` asignado y el tiempo inicial del sistema.
  - Su método `run()` iterará sobre los productos, aplicando el `Thread.sleep()` basado en el tiempo de procesamiento de cada ítem.
  - Emitirá logs a la consola demostrando qué cliente y producto atiende.

### 3. Capa de Servicios y Controladores (Próximos pasos - Integrante 3 🚧)
- **`SimulacionService` (Orquestador)**:
  - Recibirá la lista de `ClienteDTO`.
  - Registrará el tiempo de inicio absoluto (`System.currentTimeMillis()`).
  - Creará una nueva instancia de `Cajera` por cada cliente y la lanzará en un nuevo `Thread`.
  - Mantendrá la referencia de los hilos activos y usará `.join()` para esperar a que el supermercado quede vacío.
  - Calculará el tiempo total que tomó procesar toda la fila y lo guardará en la base de datos usando el `HistorialSimulacionRepository`.
- **`SimulacionController`**:
  - Proveerá el endpoint `POST` protegido con validaciones, que será el detonante de toda la operación al ser llamado desde Postman.

## Plan de Verificación Final
Una vez que terminemos el código del Integrante 2 y 3:
1. Enviaremos un JSON desde Postman con al menos 3 clientes y varios productos.
2. Observaremos la terminal; los logs de diferentes cajeras deben aparecer **intercalados** (ej. Cajera 1 cobra, luego Cajera 3 cobra, luego Cajera 2), confirmando el paralelismo.
3. Comprobaremos en la consola de H2 (`/h2-console`) que el tiempo total almacenado es cercano al tiempo del cliente con el carrito más pesado, no la suma matemática de todos.
