-- ============================================================
-- AVANCE 3 - ESQUEMA NORMALIZADO (3FN) - TALLER DE REPARACIÓN
-- ============================================================
DROP DATABASE IF EXISTS taller;
CREATE DATABASE taller CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE taller;

-- ---------- Catálogos ----------
CREATE TABLE MARCA (
    id_marca       INT AUTO_INCREMENT PRIMARY KEY,
    nombre_marca   VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE TIPO_EQUIPO (
    id_tipo_equipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre_tipo    VARCHAR(50) NOT NULL UNIQUE
);

-- ---------- Entidades independientes ----------
CREATE TABLE CLIENTE (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL,
    apellido   VARCHAR(100) NOT NULL,
    telefono   VARCHAR(20),
    correo     VARCHAR(150),
    direccion  VARCHAR(255)
);

CREATE TABLE TECNICO (
    id_tecnico   INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL,
    apellido     VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100),
    telefono     VARCHAR(20)
);

CREATE TABLE PIEZA (
    id_pieza        INT AUTO_INCREMENT PRIMARY KEY,
    nombre_pieza    VARCHAR(100) NOT NULL,
    descripcion     VARCHAR(255),
    precio_unitario DECIMAL(10,2) NOT NULL CHECK (precio_unitario >= 0)
);

-- ---------- EQUIPO (depende de CLIENTE, MARCA, TIPO_EQUIPO) ----------
CREATE TABLE EQUIPO (
    id_equipo               INT AUTO_INCREMENT PRIMARY KEY,
    modelo                  VARCHAR(100) NOT NULL,
    color                   VARCHAR(30),
    numero_serie            VARCHAR(50) NOT NULL UNIQUE,
    descripcion_condiciones VARCHAR(255),
    id_cliente              INT NOT NULL,
    id_tipo_equipo          INT NOT NULL,
    id_marca                INT NOT NULL,
    CONSTRAINT fk_equipo_cliente
        FOREIGN KEY (id_cliente)     REFERENCES CLIENTE(id_cliente)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_equipo_tipo
        FOREIGN KEY (id_tipo_equipo) REFERENCES TIPO_EQUIPO(id_tipo_equipo)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_equipo_marca
        FOREIGN KEY (id_marca)       REFERENCES MARCA(id_marca)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ---------- ORDEN_SERVICIO ----------
CREATE TABLE ORDEN_SERVICIO (
    id_orden                INT AUTO_INCREMENT PRIMARY KEY,
    fecha_recepcion         DATE NOT NULL,
    fecha_entrega_estimada  DATE,
    fecha_entrega_real      DATE,
    estado                  VARCHAR(30) NOT NULL DEFAULT 'Pendiente',
    id_equipo               INT NOT NULL,
    id_tecnico              INT NOT NULL,
    CONSTRAINT fk_orden_equipo
        FOREIGN KEY (id_equipo)  REFERENCES EQUIPO(id_equipo)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_orden_tecnico
        FOREIGN KEY (id_tecnico) REFERENCES TECNICO(id_tecnico)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ---------- REPARACION (sin id_tecnico: se hereda de ORDEN_SERVICIO) ----------
CREATE TABLE REPARACION (
    id_reparacion       INT AUTO_INCREMENT PRIMARY KEY,
    diagnostico         TEXT,
    descripcion_trabajo TEXT,
    fecha_reparacion    DATE,
    costo_mano_obra     DECIMAL(10,2) NOT NULL DEFAULT 0.00
                        CHECK (costo_mano_obra >= 0),
    id_orden            INT NOT NULL,
    CONSTRAINT fk_reparacion_orden
        FOREIGN KEY (id_orden) REFERENCES ORDEN_SERVICIO(id_orden)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ---------- PAGO ----------
CREATE TABLE PAGO (
    id_pago     INT AUTO_INCREMENT PRIMARY KEY,
    fecha_pago  DATE NOT NULL,
    monto       DECIMAL(10,2) NOT NULL CHECK (monto > 0),
    metodo_pago VARCHAR(50) NOT NULL,
    id_orden    INT NOT NULL,
    CONSTRAINT fk_pago_orden
        FOREIGN KEY (id_orden) REFERENCES ORDEN_SERVICIO(id_orden)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ---------- REPARACION_PIEZA (sin subtotal: atributo derivado eliminado) ----------
CREATE TABLE REPARACION_PIEZA (
    id_reparacion             INT NOT NULL,
    id_pieza                  INT NOT NULL,
    cantidad                  INT NOT NULL DEFAULT 1 CHECK (cantidad > 0),
    precio_unitario_aplicado  DECIMAL(10,2) NOT NULL DEFAULT 0.00
                              CHECK (precio_unitario_aplicado >= 0),
    PRIMARY KEY (id_reparacion, id_pieza),
    CONSTRAINT fk_rep_pieza_reparacion
        FOREIGN KEY (id_reparacion) REFERENCES REPARACION(id_reparacion)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_rep_pieza_pieza
        FOREIGN KEY (id_pieza) REFERENCES PIEZA(id_pieza)
        ON DELETE RESTRICT ON UPDATE CASCADE
);