# proyecto-dsbd

Proyecto Integrador - Diseño de Base de Datos  
Facultad de Matemáticas - UADY

## Integrantes
- Angel Zared Fuentes Lopez
- Diego Isaiah Lizarraga Ramírez
- Henry Javier Llama Dzib

## Docente
Erika Rossana Llanes Castro

## Estructura
- `src/`   → código Java (Swing + JDBC)
- `sql/`   → script DDL normalizado (3FN) + datos de prueba
- `lib/`   → conector MySQL (`mysql-connector-j-26.7.0.jar`)
- `.vscode/` → configuración de VS Code

## Requisitos
- JDK 17 o superior
- Docker Desktop
- VS Code con Extension Pack for Java

## Cómo ejecutar
Sistema en Java desarrollado para la administración de clientes, equipos, técnicos, órdenes de servicio, refacciones y cobros, conectado a una base de datos MySQL en Docker.

## Cómo ejecutar el proyecto

* **Paso 1 (Base de Datos):** Abrir la terminal en la raíz del proyecto e iniciar Docker con el comando: `docker-compose up -d`
* **Paso 2 (Aplicación):** Entrar a la carpeta `src/` y ejecutar la clase principal **`MenuPrincipal.java`**.
