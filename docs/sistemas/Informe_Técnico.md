# Informe Técnico: Entorno de Ejecución y Despliegue
## Módulo: Sistemas Informáticos - gestion_hoteles

Este documento detalla los requisitos, configuración y mantenimiento necesarios para el correcto funcionamiento de la aplicación de gestión hotelera.

---

### 1. Tipo de Sistema de Ejecución

Se ha determinado que la aplicación se ejecutará bajo una arquitectura de **Servidor Local con Clientes en Red**.

* **Justificación:** Al manejar datos críticos (reservas, clientes, facturación), se requiere una base de datos centralizada (MySQL) que resida en un servidor local para garantizar la seguridad de los datos y la velocidad de respuesta.
* **Equipos:** La base de datos estará en un equipo servidor, mientras que los terminales de recepción ejecutarán el cliente Java (`.jar`).

---

### 2. Requisitos de Hardware

Para asegurar la fluidez de la Máquina Virtual de Java (JVM) y el motor de base de datos, se definen los siguientes requisitos:

| Componente | Mínimo | Recomendado |
| :--- | :--- | :--- |
| **CPU** | Dual Core 2.4 GHz | Quad Core 3.0 GHz o superior |
| **RAM** | 4 GB | 8 GB |
| **Almacenamiento** | 2 GB HDD | 10 GB SSD (Mejora velocidad de lectura/escritura) |
| **Red** | Ethernet 100 Mbps | Ethernet 1 Gbps |

---

### 3. Sistema Operativo Recomendado

* **Sistema:** Windows 10 o Windows 11 (Versiones Pro para gestión de redes).
* **Justificación:** La mayoría de periféricos de hostelería (impresoras de tickets, lectores de DNI) tienen drivers optimizados para Windows. No obstante, al ser una aplicación Java, es compatible con Linux si fuera necesario.

---

### 4. Instalación del Entorno (Paso a Paso)

Para ejecutar y probar la aplicación desde el entorno de desarrollo (IntelliJ IDEA), siga este orden:

1. **Requisito Previo:** Tener instalado **IntelliJ IDEA** (o su IDE de Java favorito) y OpenJDK 17 o superior.
2. **Servidor de Datos:** Instalar y ejecutar **MySQL Server 8.0** con autenticación.
3. **Base de Datos:** Importar el script SQL para crear la estructura de tablas y datos iniciales (`gestion_hoteles.sql`) usando MySQL Workbench.
4. **Configuración del Proyecto:** Abrir el proyecto en el IDE y asegurarse de que el driver `mysql-connector-j-x.x.x.jar` esté añadido a las librerías o dependencias del proyecto.
   Ejecutar la clase principal de la interfaz gráfica (`AppHotel.java` o la clase contenedora de la vista principal en JavaFX).
---

### 5. Usuarios, Permisos y Estructura

* **Usuarios del Sistema (SO):** Se recomienda utilizar un usuario de Windows estándar para los recepcionistas (restringiendo la instalación de software ajeno al sistema) y un usuario administrador para las labores de mantenimiento y despliegue.
* **Permisos de Base de Datos:** La aplicación se conecta al servidor MySQL mediante un usuario técnico con privilegios restringidos (DML: `SELECT`, `INSERT`, `UPDATE`), protegiendo la integridad de la información al no disponer de permisos de borrado de esquemas o tablas (DDL: `DROP`).
* **Estructura de Carpetas:**
    * `/bin`: Directorio contenedor del código fuente y ejecutables.
    * `/backups`: Directorio de almacenamiento de copias de seguridad de la base de datos.
    * `/logs`: Archivos de registro (*logs*) de la actividad de la máquina virtual de Java.
---

### 6. Mantenimiento y Seguridad Mínima

* **Actualizaciones:** Revisión trimestral de la versión de Java para parches de seguridad.
* **Copias de Seguridad:** Se deben realizar copias de seguridad de la base de datos MySQL diariamente.
* **Protección:** Uso de Firewall para cerrar el puerto 3306 (MySQL) a conexiones externas fuera de la red local.
* **Plan de Contingencia:** En caso de fallo del sistema, se dispone de un log de errores para diagnóstico y los backups de las últimas 24 horas para restauración inmediata.

---

### 7. Evidencias de Funcionamiento

* **Uso de Recursos (Consola / Administrador de Tareas):**
    * La aplicación ejecutándose sobre la Máquina Virtual de Java (JVM) tiene un consumo de memoria RAM inferior a 200 MB, lo que garantiza su correcto funcionamiento incluso en equipos con los 4 GB de hardware mínimo estipulados.

![Rendimiento](docs/capturas/demo.git/Rendimiento.gif)

* **Validación del Esquema de Datos:**
    * Se han realizado pruebas de inserción y consulta en el entorno MySQL Workbench, verificando la integridad referencial de las tablas de `habitacion`, `hotel` y `reserva` sin presentar bloqueos de concurrencia.

![Validación correcta del esquema de datos](docs/capturas/demo.git/exportacionxls.gif)

---

### 💡 Esquema de Arquitectura del Sistema

```text
 ┌──────────────────────┐
 │     Cliente JavaFX   │ (Aplicación de escritorio)
 └──────────┬───────────┘
            │ JDBC
            ▼
 ┌──────────────────────┐
 │    Puerto MySQL 3306 │ (Aislado mediante Firewall)
 └──────────┬───────────┘
            ▼
 ┌──────────────────────┐
 │    Servidor MySQL    │ (Base de datos centralizada)
 └──────────────────────┘

