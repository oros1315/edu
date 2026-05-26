# EduObservador v2.0
### Sistema de Gestión de Convivencia Escolar

---

## ¿Qué hay de nuevo en v2.0?

La versión 2.0 evoluciona el prototipo de consola hacia una aplicación de escritorio completa con una interfaz gráfica moderna (Java Swing) y persistencia de datos segura mediante archivos de texto plano.

El sistema se rediseñó bajo el patrón de arquitectura **MVC estricto** (Modelo-Vista-Controlador), separando por completo la lógica de negocio de la interfaz visual, garantizando encapsulamiento, polimorfismo y manejo robusto de excepciones (flujos de datos I/O).

---

## Requisitos

| Requisito | Versión mínima |
|-----------|----------------|
| Java JDK  | 8 o superior |
| Entorno IDE | IntelliJ IDEA (Recomendado), Eclipse o NetBeans |
| Sistema Operativo | Windows / Linux / macOS |

---

## Librerías y Dependencias

**Cero dependencias externas.** El proyecto fue desarrollado 100% con bibliotecas nativas de Java (`javax.swing.*`, `java.io.*`, `java.util.*`), por lo que no requiere importar archivos `.jar` adicionales ni gestores como Maven/Gradle para compilar.

---

## Instalación y Ejecución

### Paso 1 — Extraer el proyecto
Haz clic derecho sobre el archivo ZIP del proyecto y selecciona **Extraer todo**.

### Paso 2 — Abrir en el IDE (Ej. IntelliJ IDEA)
1. Abre tu IDE.
2. Selecciona `File > Open...` y busca la carpeta extraída.
3. Espera a que el IDE indexe los archivos de Java.

### Paso 3 — Verificación del directorio de datos
Asegúrate de que la carpeta `data/` se encuentre en la **raíz del proyecto** (al mismo nivel que la carpeta `src`). Si la carpeta no existe, el sistema `FileManager` la creará automáticamente al iniciar.

### Paso 4 — Ejecutar
1. Navega en el explorador de archivos del IDE hasta `src/Main.java`.
2. Haz clic derecho sobre el archivo `Main.java` y selecciona **Run 'Main.main()'**.
3. Se abrirá la ventana de inicio de sesión de EduObservador v2.0.

---

## Usuarios de prueba

El sistema inicializa la clase `DataLoader` con los siguientes usuarios (semillas) si los archivos de texto están vacíos:

| Código    | Contraseña | Rol          |
|-----------|------------|--------------|
| COORD001  | coord123   | Coordinador  |
| DOC001    | doc123     | Docente      |
| EST001    | est123     | Estudiante   |
| EST002    | est456     | Estudiante   |

---

## Cómo usar el sistema

El guardado de datos (persistencia) se ejecuta de manera automática cada vez que se cierra una sesión o se cierra la aplicación mediante el evento `WindowClosing`.

**Flujo típico:**
1. Ingresar como **Docente** (`DOC001`) → registrar una observación sobre un estudiante.
2. Cerrar sesión → ingresar como **Estudiante** (`EST001`).
3. Consultar el historial y ver la nueva observación registrada.
4. Cerrar sesión → ingresar como **Coordinador** (`COORD001`).
5. Generar reporte del estudiante o anular la observación indicando una justificación.

---

## Persistencia de datos (.txt)

Se implementó un gestor de archivos (`FileManager`) que utiliza `try-with-resources` para garantizar el cierre seguro de flujos (`BufferedReader`, `PrintWriter`). Los datos se guardan usando el delimitador `|` con métodos de escape de caracteres para evitar corrupción de datos si un usuario usa ese símbolo.

Estructura de almacenamiento:
```text
data/
├── usuarios.txt       ← Credenciales, roles y estado activo/inactivo
├── observaciones.txt  ← Historial completo (incluye anuladas)
├── reclamos.txt       ← Solicitudes del estudiante
└── actividades.txt    ← Actividades de seguimiento