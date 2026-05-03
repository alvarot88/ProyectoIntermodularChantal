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

Para desplegar la aplicación desde cero, siga este orden:

1. **Java Runtime Environment (JRE):** Instalar OpenJDK 17 o superior.
2. **Servidor de Datos:** Instalar MySQL Server 8.0 con autenticación.
3. **Base de Datos:** Importar el script SQL para crear la estructura de tablas (`gestion_hoteles.sql`).
4. **Despliegue de App:** Copiar el archivo `gestion_hoteles.jar` en la ruta `C:\GestionHoteles\`.
5. **Driver:** Asegurarse de que el conector `mysql-connector-j-x.x.x.jar` esté incluido en la carpeta `/lib`.

---

### 5. Usuarios, Permisos y Estructura

* **Usuarios del Sistema:** Se recomienda un usuario de Windows estándar para los recepcionistas (sin permisos de instalación) y un usuario administrador para el mantenimiento.
* **Permisos de BD:** La aplicación conecta mediante un usuario específico en MySQL que solo tiene permisos de lectura/escritura en la base de datos del hotel, no permisos de borrado de tablas.
* **Estructura de Carpetas:**
    * `/bin`: Archivos ejecutables.
    * `/backups`: Copias de seguridad automáticas (se generan cada 24h).
    * `/logs`: Archivos de registro de errores del sistema.

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

![Evidencia de uso de la memoria RAM y recursos de la JVM](docs/capturas/Evidencia_RAM_SSII.png)

* **Validación del Esquema de Datos:**
    * Se han realizado pruebas de inserción y consulta en el entorno MySQL Workbench, verificando la integridad referencial de las tablas de `habitacion`, `hotel` y `reserva` sin presentar bloqueos de concurrencia.

![Validación correcta del esquema de datos](docs/capturas/Validacion_XML_OK.png)

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

