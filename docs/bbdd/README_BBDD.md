#  Base de Datos - Sistema de Gestión Hotelera

Este directorio contiene la documentación, el esquema relacional y los scripts necesarios para la creación y gestión de la base de datos del proyecto de Gestión de Hoteles.

---

## Diseño y Modelo de Datos

El diseño de la base de datos se ha estructurado utilizando un modelo relacional en **MySQL**, asegurando la integridad referencial y evitando la redundancia de datos.

### 1. Modelo Entidad-Relación (EER)

El esquema central del sistema se compone de las siguientes tablas principales:

* `hotel`: Almacena la información de los establecimientos.
* `habitacion`: Depende del hotel y define el tipo y capacidad de cada habitación.
* `cliente`: Registra los datos personales y de contacto de los huéspedes.
* `reserva`: Almacena el número de reserva, fechas y el cliente asociado.
* `reserva_habitacion` *(Tabla intermedia)*: Resuelve la relación de muchos a muchos entre reservas y habitaciones, permitiendo registrar la cantidad de huéspedes y el `precio_total` de la estancia.

> **Nota:** Puedes encontrar el diagrama visual del modelo en el archivo (docs/bbdd/diagrams/gestion_hoteles_er.png)

---

## Estructura del Directorio

### `/schema/`
Contiene la estructura (DDL) y los datos iniciales (DML) para levantar el sistema:
* `gestion_hoteles_schema.sql`: Script de creación del esquema, tablas y restricciones (Foreign Keys).
* `insert_hoteles.sql`: Inserción de datos de hoteles.
* `insert_habitaciones.sql`: Inserción de habitaciones iniciales.
* `insert_clientes.sql`: Inserción de clientes.

### `/querys/`
Contiene los scripts de consultas solicitadas en el ciclo:
1. `clientes_españa.sql`
2. `clientes_nacionalidad.sql`
3. `reservas_clientes_hotel.sql`
4. `tipo_habitacion_por_hotel.sql`

---

## Decisiones de Diseño

1. **Integridad Referencial:** Uso de claves foráneas (`FOREIGN KEY`) entre `reserva_habitacion`, `reserva`, `habitacion` e `hotel` para garantizar que no existan registros huérfanos.
2. **Tipado de Datos Eficiente:** Elección de tipos de datos adaptados al dominio (ej. `DECIMAL(18,2)` para los importes monetarios como el precio total, y `DATE` para los rangos de fechas).
3. **Optimización:** Las claves principales (`PRIMARY KEY`) y secundarias (`KEY`) están indexadas en la base de datos para permitir un acceso rápido a las estancias por cliente o por hotel.