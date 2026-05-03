# Sistema de Gestión Hotelera - Proyecto Intermodular

> **Portfolio Profesional:** [Enlace a Portfolio ](docs/itinerario-personal/Portfolio-prof.md)

---

## Descripción del Proyecto

El **Sistema de Gestión Hotelera** es un software integral de escritorio diseñado para resolver la ineficiencia, el uso de papel y los errores en el registro de reservas y clientes en el sector hotelero. Permite controlar la ocupación de habitaciones, gestionar huéspedes y mantener un flujo de trabajo seguro y rápido.

---

## Tecnologías Utilizadas

* **Java & JavaFX:** Motor lógico y desarrollo de la interfaz gráfica de usuario.
* **MySQL & JDBC:** Capa de almacenamiento centralizado y control de transacciones mediante base de datos relacional.
* **Lenguajes de Marcas (XML / XSD):** Intercambio de datos estructurado y validado.
* **Entorno de Desarrollo:** Implementado y ejecutado desde **IntelliJ IDEA**.

---

## Estructura y Contenidos del Repositorio

Para localizar cada archivo del proyecto, consulta el siguiente índice según la estructura de carpetas:

### 1. Base de Datos (`/bbdd/`)
Contiene los esquemas y consultas de la base de datos MySQL.
* **`/scripts/`**: Scripts de creación de tablas e inserción de datos iniciales.
    * `create_gestion_hoteles_schema.sql`
    * `insert_clientes.sql`, `insert_habitaciones.sql`, `insert_hoteles.sql`
* **`/querys/`**: Consultas solicitadas (ej. clientes por nacionalidad, reservas por hotel).
* **`/diagrams/`**: Modelos, diagramas de relación.

### 2. Capturas y demos (`/capturas/`)
Contiene videos y gif de demostración.


### 3. Itinerario Personal (`/itinerario-personal/`)
Contiene los entregables de Itinerario Personal para la Empleabilidad I
* **`Exploracion del sector profesional`**
* **`Perfil Profesional`**
* **`Portfolio profesional`**
* **`Presentación del proyecto`**
* **`Reflexión final`**


### 4. Sistemas Informaticos (`/sistemas/`)
* **`Informe técnico`**: Informe Técnico de implantación y despliegue (`Informe_Tecnico.md`).
* **`Capturas`**: (`/docs/capturas`) :Capturas y videos de funcionamiento y rendimiento de la APP.
* **`Esquema del sistema`**: Dentro del informe técnico.

### 5. Lenguajes de Marcas (`/xml/`)
Contiene los archivos relacionados con el módulo de lenguajes de marcas.
* **`/capturas/`**: Contiene captura en video de la validación.
* `esquema.xsd`
* `reservas.xml`
* `Justificacion.md`

### 6. Programación (`/programacion/src/`)
Contiene las clases del proyecto estructuradas por capas:
* **`dao/`**: Conexión a base de datos y operaciones CRUD (`ClienteDAO`, `HotelDAO`, etc.).
* **`model/`**: Clases de dominio (Entidades: `Cliente`, `Habitacion`, `Reserva`, etc.).
* **`view/`**: Controladores de la interfaz gráfica en JavaFX (`AppHotel.java`, `PanelClientes.java`, etc.).
* **`main/`**: No se utiliza ya que el arranque se realiza desde AppHotel.

---

## Mejoras de Mantenimiento y Rendimiento (MPO)

1. **Modularidad en Capas:** Organización estricta del código en paquetes para facilitar el mantenimiento.
2. **Validación de Errores:** Control de integridad mediante esquema XSD.
3. **Optimización de Recursos:** La aplicación compilada opera con un consumo de memoria RAM inferior a 200 MB, garantizando fluidez en equipos básicos.
4. **Seguridad en la Conexión:** Restricciones de red mediante Firewall local en el puerto 3306, aislando los datos críticos del acceso externo.

---

## Guía de Ejecución (Desde el IDE)

Para ejecutar el proyecto, siga estos pasos en su entorno de desarrollo:

1. **Requisito Previo:** Tener instalado **IntelliJ IDEA** y OpenJDK 17 o superior.
2. **Servidor de Datos:** Instalar y ejecutar **MySQL Server 8.0**. Importar el script `gestion_hoteles_schema.sql`.
3. **Configuración:** Abrir el proyecto y asegurarse de que el driver `mysql-connector-j` esté añadido a las librerías del proyecto.
4. **Ejecución:** Ejecutar la clase principal de la interfaz gráfica (`AppHotel.java` dentro del paquete `view`).