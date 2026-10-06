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
Iniciar la Base de Datos (Docker):
  ```bash
   docker-compose up -d