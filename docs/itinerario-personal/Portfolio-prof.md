# Portfolio Profesional - Proyecto de Gestión de Hoteles

## 1. El Proyecto: Gestión de Hoteles (Prometeo)
* **Descripción del sistema:** Sistema de gestión de escritorio diseñado para resolver la ineficiencia en reservas y clientes en el sector hotelero, centralizando la información y garantizando la seguridad de los datos.
* **Problemática resuelta:** Eliminación de los errores generados por la gestión en papel y planillas de cálculo, automatizando el control de estancias, clientes y hoteles.
* **Tecnologías utilizadas:** * Java / JavaFX (Interfaz de usuario y lógica de negocio).
    * MySQL y JDBC (Base de datos relacional y capa de conectividad).
    * XML / XSD (Intercambio y validación de datos).

---

## 2. Capturas del Sistema

* **Interfaz de Usuario (JavaFX):**
  *(Podés reemplazar esta ruta por la de tu archivo)*
  `![Interfaz de la Aplicación](docs/capturas/Evidencia_RAM_SSII.png)`

* **Estructura de la Base de Datos:**
  `![Diagrama EER](base-de-datos/docs/diagrama_relacional_gestion_hoteles.png)`

---

## 3. Explicación del Sistema

### Arquitectura y Funcionamiento
El sistema está construido bajo una arquitectura cliente-servidor para entornos locales, optimizado para el uso de recepcionistas en el sector hotelero.
* **Capa Cliente:** Aplicación de escritorio Java que se ejecuta con un bajo consumo de memoria RAM (inferior a 200 MB), siendo apta para equipos estándar.
* **Capa de Conectividad:** Uso de JDBC para la conexión segura mediante el puerto estándar 3306.
* **Validación de Datos:** El módulo de lenguajes de marcas valida la integridad de los datos estructurados mediante un esquema XSD.

---

## 4. Enlace al Repositorio

* **Repositorio oficial en GitHub:** [https://github.com/alvarot88/ProyectoIntermodularChantal](https://github.com/alvarot88/ProyectoIntermodularChantal)

---

## 5. Breve Descripción de lo que he Aprendido

| Módulo | Aprendizaje y Aplicación en el Proyecto |
| :--- | :--- |
| **Programación** | Diseño e implementación de arquitectura en capas (DAO, Model, View, Controller) y desarrollo de interfaces con JavaFX. |
| **Sistemas Informáticos** | Configuración de entornos de ejecución, gestión de servidores MySQL y control del rendimiento (JVM). |
| **Lenguajes de Marcas** | Estandarización de información con XML y validación de restricciones técnicas mediante esquema XSD. |
| **Mantenimiento (MPO)** | Optimización del código para garantizar el bajo consumo de recursos y el mantenimiento a largo plazo. |