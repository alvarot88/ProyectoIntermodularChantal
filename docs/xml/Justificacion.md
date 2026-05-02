# Modulo de intercambio de datos (XML/XSD) #

En este directorio (/docs/xml/) tienes la estructura documental para la exportación y validación de datos del sistema gestion_hoteles.
Se utiliza para que la información extraida de la bbdd cumpla con la calidad necesaria para ser procesada por aplicacionex externas o informes.

## **Contenido de la carpeta "xml"** ##

reservas.xml: archivo que contiene los datos de las reservas exportadas (clientes, hoteles, fechas e importes).
esquema.xsd: esquema de validación que define las reglas tecnicas y restricciones de los datos.

## **Contenido de la carpeta "capturas"** ##
validacion_cap.pdf : contiene las capturas de la validación.

## Descripción de los datos ##

El archivo XML representa un reporte de exportación del sistema con la siguiente jerarquía:

1. **Metadatos**: Incluye el sistema de origen (`gestion_hoteles`), la versión del software, la fecha de exportación y el conteo total de registros.
2. **Listado de Reservas**: Cada bloque `<reserva>` detalla:
    * **Identificadores**: Atributo `id` único.
    * **Información del Huésped**: Nombre completo del cliente.
    * **Ubicación**: Nombre del hotel reservado.
    * **Estancia**: Fechas de entrada y salida.
    * **Finanzas**: Precio total de la estancia con el atributo de moneda (EUR).

## Reglas de Validación (XSD)
Para garantizar que la información sea íntegra, el archivo `esquema.xsd` valida:
* **Tipos de datos**: Obliga a que las fechas sean válidas y el precio sea un valor decimal (`xs:decimal`).
* **Atributos obligatorios**: El sistema no permite exportar reservas sin su ID o sin especificar la moneda.
* **Integridad de estructura**: El documento debe seguir estrictamente el orden: Metadatos -> Reservas.

## Evidencia de Validación
El archivo `reservas.xml` ha sido validado satisfactoriamente contra `esquema.xsd`.

> **Nota:** En el entorno de desarrollo IntelliJ, la validación se confirma mediante el indicador verde en la esquina superior derecha del editor. Se ha incluido un archivo PDF (`validacion_cap.pdf`) en la carpeta de capturas que documenta tanto la validación correcta como el control de errores (detección de datos inválidos).