# 🏨 Sistema de Gestión Hotelera - Proyecto Intermodular

> 🌐 **Portfolio y Presentación Web:** [Hacé clic acá para ver el Portfolio](TU_LINK_AQUI)

## 📌 Descripción del Proyecto

El **Sistema de Gestión Hotelera** es un software integral de escritorio diseñado para resolver el desorden y la ineficiencia en la gestión de reservas y clientes en el sector hotelero. Permite controlar la ocupación de habitaciones, registrar clientes, procesar estancias y mantener un control de los datos sin riesgo de errores.

---

## 🛠️ Tecnologías Utilizadas

* **Java & JavaFX:** Motor lógico y diseño de interfaz gráfica amigable, ligera y moderna.
* **MySQL:** Base de datos relacional centralizada para el almacenamiento seguro de la información.
* **JDBC:** Capa de conectividad y gestión transaccional (con control de errores).
* **XML y XSD:** Intercambio de datos estructurados y validados.

---

## 📂 Estructura del Repositorio

El proyecto se encuentra estructurado para cumplir con las directrices de los módulos:

## 🚀 Mejoras de Mantenimiento, Rendimiento y Optimización (MPO)
Con el fin de garantizar un sistema robusto y preparado para el entorno profesional, se implementaron las siguientes mejoras:

Modularidad en Capas: Organización estricta del código en paquetes (dao, model, view, controller) para facilitar el mantenimiento y reducir el impacto ante cambios futuros.

Validación y Manejo de Errores: Control de integridad mediante esquema XSD. El sistema rechaza datos con formatos incorrectos (por ejemplo, textos en campos numéricos o fechas inválidas) antes de enviarlos a la base de datos.

Optimización de Recursos: La aplicación compilada opera con un consumo de memoria RAM inferior a 200 MB, garantizando fluidez en equipos básicos de recepción.

Seguridad en la Conexión: Restricciones de red mediante Firewall local en el puerto 3306, aislando los datos críticos del acceso externo.

## 📋 Justificación de Módulo de Intercambio de Datos
Se ha diseñado un XML estructurado y anidado bajo la raíz <gestion_hoteles> para agrupar las reservas según el cliente y evitar la redundancia de información. Cumple con los siguientes controles:

Metadatos de auditoría (origen, fecha y conteo de registros).

Tipos de datos estrictos y control de atributos requeridos (moneda, formato ISO de fechas).

## 👨‍💻 Presentación Profesional
Problema resuelto: Elimina el overbooking, la pérdida de registros en papel y el uso de planillas de cálculo manuales.

Público objetivo: Pequeños y medianos hoteles que buscan digitalizar su operativa de forma económica y segura.

Aprendizajes clave: Conexión de componentes de software (Java a base de datos mediante JDBC), validación de esquemas e integración de sistemas.