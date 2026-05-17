# Plan de Desarrollo Unificado: Simulación de Cobro Concurrente
**Caso de Estudio - Tecnología en Desarrollo de Software**

Este documento es la hoja de ruta para el equipo. Explica detalladamente cómo funciona la lógica del problema, cómo interactúan los componentes y qué debe desarrollar cada integrante paso a paso en nuestra sesión sincrónica.

---

## 1. Comprensión del Problema y Enfoque Lógico

El desafío académico nos pide simular el comportamiento de un supermercado. Para entender la concurrencia, debemos mapear el mundo real al código:
* **Los Clientes con sus carritos** son los datos de entrada (la carga de trabajo).
* **Las Cajeras** son los hilos de ejecución (las trabajadoras en paralelo).

### El Flujo de Trabajo del Sistema
1. **Punto de Entrada (API REST):** El sistema recibe mediante Postman un único listado que contiene a todos los clientes y los productos que lleva cada uno.
2. **El Orquestador:** Una clase central recibe este listado, mide el tiempo exacto en el que inicia el proceso y genera dinámicamente **una Cajera (Hilo) por cada cliente**. Si el JSON trae 3 clientes, el código crea 3 cajeras simultáneamente.
3. **Procesamiento en Paralelo (El núcleo):** Cada cajera atiende a su cliente asignado de forma independiente. Lee los productos uno a uno y, para simular que está "escaneando" físicamente el artículo, el hilo se duerme (`Thread.sleep`) los segundos que tenga configurados ese producto.
4. **Sincronización:** El programa principal espera a que la última cajera termine de cobrar el carrito más pesado.
5. **Cierre y Persistencia:** El sistema calcula el tiempo total absoluto que tomó atender a todo el supermercado, guarda ese registro en la base de datos como historial y responde a Postman con el resultado final.

---

## 2. Mapa de la Arquitectura de Capas

Para implementar buenas prácticas de programación, dividiremos el software en cuatro capas bien definidas:

* **Capa de Controladores (Controller):** Es la puerta de entrada. Recibe los datos de la petición web, valida que la información sea correcta y segura (que no vengan carritos vacíos o valores negativos) y se la pasa al servicio.
* **Capa de Servicios (Service):** Es el cerebro o el "orquestador". No sabe cómo cobra una cajera individualmente, pero es el encargado de crearlas, iniciar los hilos, esperar a que terminen y guardar el reporte en la base de datos.
* **Capa del Motor Concurrente (Engine / Runnable):** Es donde vive la lógica de los hilos. Aquí se define qué hace una cajera cuando se activa su hilo (recorrer los productos, dormir el hilo para simular tiempo y reportar en consola).
* **Capa de Modelos y Datos (Model / Entity / Repository):** Define la estructura de los objetos (`Cliente`, `Producto`, `Historial`) y las interfaces para conectarse a la base de datos H2.

---

## 3. Plan de Trabajo Paso a Paso por Integrante

Para trabajar de forma sincrónica, nos dividiremos las tareas de la siguiente manera:

### 👤 Integrante 1: Especialista en Modelado de Datos y Persistencia
*Tu objetivo es construir los cimientos del proyecto, definir qué datos guardamos y cómo se estructuran las entidades.*

* [ ] **Paso 1 (Configuración Inicial):** Crear el proyecto base en Spring Boot incluyendo las dependencias de Web, JPA (Base de datos), Validación y la base de datos. Configurar el archivo `application.properties` para que la base de datos funcione.
* [ ] **Paso 2 (Estructura de Intercambio - DTOs):** Diseñar las clases de transferencia de datos. Debes crear la clase `ProductoDTO` (con atributos como nombre, precio y tiempo de procesamiento en segundos) y la clase `ClienteDTO` (que contiene el nombre del cliente y una lista de sus productos).
* [ ] **Paso 3 (Estructura de Persistencia):** Crear la entidad de base de datos llamada `HistorialSimulacion` para almacenar la fecha de la prueba, cuántos clientes se atendieron y el tiempo total calculado. Adicionalmente, debes crear su interfaz `Repository` heredando de `JpaRepository` para poder guardar los datos con una sola línea de código.

---

### 👤 Integrante 2: Especialista en el Motor Concurrente (Hilos)
*Tu objetivo es desarrollar el eje central del trabajo: el comportamiento autónomo y paralelo de las cajeras.*

* [ ] **Paso 1 (Contrato de Concurrencia):** Crear la clase `Cajera` y hacer que implemente la interfaz nativa `Runnable` de Java. Esto la capacita legalmente para convertirse en un hilo de ejecución.
* [ ] **Paso 2 (Estado del Hilo):** Configurar el constructor de la clase para que, al momento de nacer, reciba tres datos obligatorios: su propio nombre (ej. "Cajera 1"), el `ClienteDTO` que le toca atender y el tiempo inicial del sistema (para saber cuánto tiempo ha pasado).
* [ ] **Paso 3 (Lógica de Ejecución - El método `run`):** Sobrescribir el método `run()`. Dentro, debes programar un bucle (un ciclo `for`) que revise uno a uno los productos del carrito del cliente. 
* [ ] **Paso 4 (Simulación de Tiempo y Registro):** Dentro del bucle, extraer los segundos de procesamiento del producto y usar `Thread.sleep()` (multiplicando los segundos por 1000 para pasarlos a milisegundos) dentro de un bloque `try-catch` para simular la demora del cobro. Usar la herramienta de Logs (`Logger`) para imprimir en consola exactamente qué producto está procesando y qué cajera lo está haciendo.

---

### 👤 Integrante 3: Orquestador del Sistema y Seguridad de la API
*Tu objetivo es conectar el trabajo de tus dos compañeros, gestionar el ciclo de vida de los hilos y asegurar la aplicación.*

* [ ] **Paso 1 (Construcción del Endpoint):** Crear la clase `CheckoutController` y exponer un método `POST`. Agregar anotaciones de seguridad como `@Valid` para interceptar la petición de Postman y asegurar que los datos estén limpios antes de procesar nada.
* [ ] **Paso 2 (El Servicio Orquestador):** Crear la clase `SimulacionService` e inyectar el repositorio creado por el Integrante 1. Aquí diseñarás el método principal que recibe la lista de clientes que llegó desde el controlador.
* [ ] **Paso 3 (Ciclo de Vida de los Hilos - Arranque):** Capturar el tiempo inicial en milisegundos con `System.currentTimeMillis()`. Crear un bucle para recorrer los clientes entrantes. Por cada cliente, instanciar la clase `Cajera` (desarrollada por el Integrante 2), envolverla en un objeto `Thread` nuevo y ejecutar el método `.start()`. Debes guardar estos hilos en una lista dinámica.
* [ ] **Paso 4 (Sincronización y Cierre):** Crear un segundo bucle que recorra la lista de hilos activos y aplicar el método `.join()` a cada uno. Esto obliga al programa a quedarse congelado esperando hasta que el último hilo de cajera termine de trabajar. Al terminar la espera, restar el tiempo final con el inicial, mandar a guardar el registro en la base de datos H2 y retornar la respuesta con el cálculo total.

---

## 4. Estrategia de Pruebas (¿Cómo saber si lo hicimos bien?)

Se realizará la prueba enviando el JSON masivo desde Postman y observarán detenidamente la terminal del entorno de desarrollo.

* **Resultado Incorrecto (Secuencial):** Si los logs muestran que la Cajera 1 procesa todos los productos del Cliente A, se despide, y hasta ese momento la Cajera 2 empieza con el Cliente B, el trabajo está mal implementado (es secuencial).
* **Resultado Correcto (Concurrente):** Si en la consola los mensajes aparecen mezclados (la Cajera 1 procesa un producto, en la línea de abajo la Cajera 2 procesa otro, luego vuelve la Cajera 1), significa que los hilos están corriendo en paralelo de forma exitosa. El tiempo total devuelto en la respuesta debe ser muy cercano al tiempo del cliente que más se demoró en su carrito, no la suma de todos.