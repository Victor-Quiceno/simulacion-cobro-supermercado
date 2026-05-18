# EA2: Optimización del Proceso de Cobro en un Supermercado mediante Hilos y Concurrencia

Este proyecto es una aplicación de simulación en Java diseñada para demostrar los conceptos fundamentales de la **programación concurrente y el uso de hilos (Threads)** en un escenario cotidiano: el proceso de facturación y cobro en las cajas de un supermercado.

En esta versión mejorada, la simulación **no se ejecuta únicamente en la consola**, sino que abre una **ventana gráfica interactiva (GUI)** construida con **Java Swing** (la biblioteca nativa de Java, lo que garantiza compatibilidad sin requerir dependencias externas). 

---

## 📂 Estructura del Proyecto

El código está organizado de forma modular, donde cada clase reside en su propio archivo dentro del paquete `com.supermercado.cobro` bajo la estructura estándar de proyectos Java/Maven:

```text
simulacion-cobro-supermercado/
│
├── pom.xml                               # Configuración de dependencias de Maven
├── README.md                             # Documentación del proyecto (Este archivo)
├── ejecutar.bat                          # Acceso directo para ejecutar en Windows con doble clic
│
└── src/
    └── main/
        └── java/
            └── com/
                └── supermercado/
                    └── cobro/
                        ├── Producto.java  # Clase entidad para los productos
                        ├── Cliente.java   # Clase entidad para los clientes y sus carritos
                        ├── Cajera.java    # Clase hilo (Thread) que procesa las compras y actualiza la GUI
                        └── Main.java      # Ventana gráfica principal (JFrame) y punto de entrada
```

---

## ⚙️ Explicación de la Concurrencia Utilizada

La concurrencia en este proyecto se implementó mediante la herencia directa de la clase base `java.lang.Thread` en nuestra clase `Cajera`. A continuación, se detallan los conceptos clave utilizados:

1. **Herencia de `Thread`:** La clase `Cajera` extiende `Thread`. Esto la convierte en una unidad de ejecución independiente que la Máquina Virtual de Java (JVM) puede gestionar en paralelo con otros hilos.
2. **Método `run()`:** Es el método obligatorio que sobreescribimos de `Thread`. Define el comportamiento del hilo cuando se activa. Dentro de este método se itera el carrito de compras del cliente asignado, se calcula el total y se simula el procesamiento.
3. **Lanzamiento con `start()`:** En la clase `Main`, los hilos se inician llamando al método `.start()`. Es fundamental usar `.start()` y **no** `.run()`, ya que `.start()` le indica a la JVM que cree un nuevo contexto de hilo y ejecute `.run()` de forma asíncrona. Si se llamara a `.run()` directamente, el código se ejecutaría de forma secuencial en el hilo principal.
4. **Simulación de tiempo con `Thread.sleep()`:** Para simular que la cajera pasa físicamente el producto por el escáner, se utiliza `Thread.sleep(segundos * 1000)`. Esto suspende el hilo actual durante el tiempo de procesamiento de cada producto (en segundos), permitiendo que otros hilos activos sigan ejecutándose.
5. **Orquestación en Hilo de Fondo (Evitar GUI Freeze):** En aplicaciones con interfaz gráfica (como Swing), si ejecutamos operaciones que tardan tiempo o bloquean (como `.join()` o `Thread.sleep()`) directamente en el hilo principal de la interfaz (Event Dispatch Thread - EDT), la ventana de la aplicación se congelaría, se mostraría como "No responde" y no dibujaría los textos. Para evitar esto, al pulsar "Iniciar Simulación", creamos un **hilo orquestador de fondo**. Este hilo secundario lanza a las cajeras, espera que terminen mediante `.join()` y realiza los cálculos finales, manteniendo la ventana 100% fluida y activa.
6. **Actualización segura de la Interfaz con `SwingUtilities.invokeLater()`:** Los componentes gráficos de Swing no son seguros para hilos (`Thread-safe`). Si múltiples cajeras que corren en hilos diferentes intentan escribir directamente en la misma caja de texto de la ventana al mismo tiempo, el programa puede fallar o corromperse. Para solucionarlo, cada cajera envía sus mensajes a través de `SwingUtilities.invokeLater()`, asegurando que el hilo del dibujo de Swing los agregue de forma ordenada y segura.

---

## 💻 Instrucciones de Ejecución en IDEs

Este proyecto está construido con la estructura estándar de Maven, por lo que es compatible con cualquier entorno de desarrollo moderno de Java.

### ☕ Opción A: NetBeans
1. Abre NetBeans.
2. Selecciona **File -> Open Project** (Archivo -> Abrir Proyecto).
3. Navega hasta la carpeta raíz del proyecto (`simulacion-cobro-supermercado`) y selecciónala.
4. Una vez que el proyecto se cargue, despliega las carpetas en el panel izquierdo: `Source Packages -> com.supermercado.cobro`.
5. Haz clic derecho sobre el archivo `Main.java` y selecciona **Run File** (Ejecutar Archivo), o simplemente presiona `Shift + F6`.
6. Se abrirá una hermosa **Ventana Gráfica**. Haz clic en **"Iniciar Simulación en Paralelo"** para ver las cajeras cobrando en tiempo real en la pantalla.

### 🛠️ Opción B: IntelliJ IDEA
1. Abre IntelliJ IDEA.
2. Selecciona **Open** (Abrir) en la pantalla de bienvenida.
3. Selecciona la carpeta raíz del proyecto (`simulacion-cobro-supermercado`).
4. Espera unos segundos a que IntelliJ indexe los archivos y configure el SDK de Java (se recomienda Java 17 o superior).
5. Navega a `src/main/java/com/supermercado/cobro/Main.java` y abre el archivo.
6. Haz clic en el botón de **Play verde** (`Run 'Main.main()'`) ubicado a la izquierda de la declaración de la clase o del método `main`, o presiona `Ctrl + Shift + F10` (Windows).
7. Se abrirá la **Ventana Gráfica** interactiva del supermercado.

### 🖥️ Opción C: Ejecución Directa en Windows (Doble Clic)
Si estás en Windows y tienes Java instalado, puedes ejecutar el proyecto sin siquiera abrir un editor de código:
1. Abre la carpeta del proyecto en tu Explorador de Archivos.
2. Busca el archivo llamado **`ejecutar.bat`** en la raíz.
3. Haz **doble clic** sobre él.
4. Se abrirá una ventana de comandos de Windows que compilará el código automáticamente y, de inmediato, lanzará la **Ventana Gráfica de la Simulación**. ¡Listo!

---

## 📝 Captura Virtual del Funcionamiento (Salida en la Ventana)

Al pulsar el botón **"Iniciar Simulación en Paralelo"**, los logs de las cajeras comenzarán a poblar de forma fluida y en tiempo real el visor oscuro de la ventana:

```text
=====================================================================
  INICIANDO SIMULACION DE COBRO EN EL SUPERMERCADO (CON HILOS)
=====================================================================

Cajera Sofia comienza a atender a Cliente 3 (Pedro) (Tiempo transcurrido: 1 ms)
Cajera Ana comienza a atender a Cliente 1 (Juan) (Tiempo transcurrido: 1 ms)
Cajera Marta comienza a atender a Cliente 2 (Maria) (Tiempo transcurrido: 1 ms)

[Cajera Marta] Procesando producto: Arroz
[Cajera Marta] Precio: $2500.0
[Cajera Marta] Tiempo: 1 segundos
[Cajera Marta] Tiempo transcurrido: 1039 ms

[Cajera Ana] Procesando producto: Leche

[Cajera Sofia] Procesando producto: Huevos
[Cajera Sofia] Precio: $6000.0
[Cajera Sofia] Tiempo: 2 segundos
[Cajera Sofia] Tiempo transcurrido: 2029 ms

[Cajera Ana] Precio: $5000.0
[Cajera Ana] Tiempo: 2 segundos
[Cajera Ana] Tiempo transcurrido: 2029 ms

[Cajera Ana] Procesando producto: Pan
[Cajera Ana] Precio: $3000.0
[Cajera Ana] Tiempo: 1 segundos
[Cajera Ana] Tiempo transcurrido: 3036 ms

Total compra Cliente 1 (Juan): $8000.0
Cajera Ana termino de atender a Cliente 1 (Juan) (Tiempo de compra: 3036 ms)

[Cajera Sofia] Procesando producto: Leche
[Cajera Sofia] Precio: $5000.0
[Cajera Sofia] Tiempo: 2 segundos
[Cajera Sofia] Tiempo transcurrido: 4030 ms

[Cajera Marta] Procesando producto: Cafe
[Cajera Marta] Precio: $8000.0
[Cajera Marta] Tiempo: 3 segundos
[Cajera Marta] Tiempo transcurrido: 4045 ms

Total compra Cliente 2 (Maria): $10500.0
Cajera Marta termino de atender a Cliente 2 (Maria) (Tiempo de compra: 4046 ms)

[Cajera Sofia] Procesando producto: Pan
[Cajera Sofia] Precio: $3000.0
[Cajera Sofia] Tiempo: 1 segundos
[Cajera Sofia] Tiempo transcurrido: 5032 ms

Total compra Cliente 3 (Pedro): $14000.0
Cajera Sofia termino de atender a Cliente 3 (Pedro) (Tiempo de compra: 5032 ms)

=====================================================================
  SIMULACION COMPLETADA CON EXITO
  Tiempo total del sistema: 5032 ms (5.032 segundos)
=====================================================================
```
