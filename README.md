# 🏨 Sistema de Gestión Hotelera - Proyecto Intermodular

> 🌐 **Portfolio Profesional:** [Enlace a Portfolio ](docs/itinerario-personal/Portfolio-prof.md)

---

## 📌 Descripción del Proyecto

El **Sistema de Gestión Hotelera** es un software integral de escritorio diseñado para resolver la ineficiencia, el uso de papel y los errores en el registro de reservas y clientes en el sector hotelero. Permite controlar la ocupación de habitaciones, gestionar huéspedes y mantener un flujo de trabajo seguro y rápido.

---

## 🛠️ Tecnologías Utilizadas

* **Java & JavaFX:** Motor lógico y desarrollo de la interfaz gráfica de usuario.
* **MySQL & JDBC:** Capa de almacenamiento centralizado y control de transacciones mediante base de datos relacional.
* **Lenguajes de Marcas (XML / XSD):** Intercambio de datos estructurado y validado.
* **Entorno de Desarrollo:** Implementado y ejecutado desde **IntelliJ IDEA**.

---

## 📂 Estructura y Contenidos del Repositorio

Para localizar cada archivo del proyecto, consulta el siguiente índice según la estructura de carpetas:

### 1. Base de Datos (`/base-de-datos/`)
Contiene los esquemas y consultas de la base de datos MySQL.
* **`/schema/`**: Scripts de creación de tablas e inserción de datos iniciales.
    * `gestion_hoteles_schema.sql`
    * `insert_clientes.sql`, `insert_habitaciones.sql`, `insert_hoteles.sql`
* **`/querys/`**: Consultas solicitadas en el ciclo (ej. clientes por nacionalidad, reservas por hotel).
* **`/docs/`**: Modelos, diagramas de relación (ficheros `.mwb` y `.png`).

### 2. Documentación ITP e Informe Técnico (`/docs/`)
Contiene los entregables del itinerario profesional y de sistemas informáticos.
* **`/itinerario-personal/`**: Exploración del sector, perfiles, reflexiones y portfolio.
* **`/sistemas/`**: Informe Técnico de implantación y despliegue (`Informe_Tecnico.md`).
* **`/capturas/`**: Evidencias visuales de rendimiento y uso de la JVM.

### 3. Código Fuente (`/programacion/src/`)
Contiene las clases del proyecto estructuradas por capas (Patrón DAO):
* **`dao/`**: Conexión a base de datos y operaciones CRUD (`ClienteDAO`, `HotelDAO`, etc.).
* **`model/`**: Clases de dominio (Entidades: `Cliente`, `Habitacion`, `Reserva`).
* **`view/`**: Controladores de la interfaz gráfica en JavaFX (`AppHotel.java`, `PanelClientes.java`).
* **`main/`**: Clase principal de arranque.

### 4. Lenguajes de Marcas (`/xml/`)
Contiene los archivos relacionados con el módulo de lenguajes de marcas.
* `esquema.xsd`
* `reservas.xml`
* `Justificacion.md`

---

## 🚀 Mejoras de Mantenimiento y Rendimiento (MPO)

1. **Modularidad en Capas:** Organización estricta del código en paquetes para facilitar el mantenimiento.
2. **Validación de Errores:** Control de integridad mediante esquema XSD.
3. **Optimización de Recursos:** La aplicación compilada opera con un consumo de memoria RAM inferior a 200 MB, garantizando fluidez en equipos básicos.
4. **Seguridad en la Conexión:** Restricciones de red mediante Firewall local en el puerto 3306, aislando los datos críticos del acceso externo.

---

## ⚙️ Guía de Ejecución (Desde el IDE)

Para ejecutar el proyecto, siga estos pasos en su entorno de desarrollo:

1. **Requisito Previo:** Tener instalado **IntelliJ IDEA** y OpenJDK 17 o superior.
2. **Servidor de Datos:** Instalar y ejecutar **MySQL Server 8.0**. Importar el script `gestion_hoteles_schema.sql`.
3. **Configuración:** Abrir el proyecto y asegurarse de que el driver `mysql-connector-j` esté añadido a las librerías del proyecto.
4. **Ejecución:** Ejecutar la clase principal de la interfaz gráfica (`AppHotel.java` dentro del paquete `view`).