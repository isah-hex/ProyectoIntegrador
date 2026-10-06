USE taller;

-- Catálogos
INSERT INTO MARCA (nombre_marca) VALUES
('Asus'), ('Apple'), ('Dell'), ('Lenovo'), ('HP'), ('Acer');

INSERT INTO TIPO_EQUIPO (nombre_tipo) VALUES
('Laptop'), ('Desktop'), ('Smartphone'), ('Tablet'), ('Impresora');

-- Clientes
INSERT INTO CLIENTE (nombre, apellido, telefono, correo, direccion) VALUES
('Angel',  'Fuentes',   '9981234567', 'angel.fuentes@correo.com',  'Col. Centro, Cancún'),
('Diego',  'Lizarraga', '9999876543', 'diego.lizarraga@correo.com','Col. Itzimná, Mérida'),
('Henry',  'Llama',     '9991112223', 'henry.llama@correo.com',    'Col. García Ginerés, Mérida');

-- Técnicos
INSERT INTO TECNICO (nombre, apellido, especialidad, telefono) VALUES
('Carlos', 'Pérez',    'Sistemas y Mantenimiento Preventivo', '9996446560'),
('María',  'Gómez',    'Microelectrónica y Placas Base',      '9991234567'),
('Luis',   'Canul',    'Redes y Software',                    '9998765432');

-- Piezas
INSERT INTO PIEZA (nombre_pieza, descripcion, precio_unitario) VALUES
('Pantalla LED 15.6"',  'Panel de reemplazo Asus ZenBook',          1850.00),
('Pasta Térmica',       'Jeringa de 10 g',                           180.00),
('Memoria RAM DDR4 8GB','Kingston Fury 3200MHz',                     680.00),
('Disco SSD 480GB',     'Kingston A400 SATA III',                    950.00),
('Batería Laptop',      'Compatible HP Pavilion',                    1200.00);

-- Equipos (id_cliente, id_tipo_equipo, id_marca con FK correctas)
INSERT INTO EQUIPO (modelo, color, numero_serie, descripcion_condiciones,
                    id_cliente, id_tipo_equipo, id_marca) VALUES
('ZenBook UX425',  'Gris',    'SN-ASUS-9921', 'Pantalla con rayón leve',   1, 1, 1),
('Inspiron 3501',  'Negro',   'SN-DELL-1102', 'Teclado con teclas flojas', 2, 1, 3),
('MacBook Air M2', 'Plateado','SN-APPLE-441','Perfecto estado',            3, 1, 2),
('ThinkPad T14',   'Negro',   'SN-LEN-7788',  'Bisagra rota',              1, 1, 4),
('HP LaserJet M15','Blanco',  'SN-HP-3321',   'Rodillo desgastado',        2, 5, 5);

-- Órdenes de servicio
INSERT INTO ORDEN_SERVICIO (fecha_recepcion, fecha_entrega_estimada, fecha_entrega_real,
                            estado, id_equipo, id_tecnico) VALUES
('2026-09-01', '2026-09-10', '2026-09-08', 'Entregado',   1, 1),
('2026-09-03', '2026-09-12', NULL,         'En proceso',  2, 2),
('2026-09-05', '2026-09-15', '2026-09-14', 'Entregado',   3, 1),
('2026-09-07', '2026-09-18', NULL,         'En proceso',  4, 3),
('2026-09-10', '2026-09-20', NULL,         'Pendiente',   5, 1);

-- Reparaciones
INSERT INTO REPARACION (diagnostico, descripcion_trabajo, fecha_reparacion,
                        costo_mano_obra, id_orden) VALUES
('Pantalla dañada',     'Cambio de panel LED',        '2026-09-08', 350.00, 1),
('Fallo de RAM',        'Sustitución de módulo RAM',  '2026-09-10', 250.00, 2),
('Batería agotada',     'Cambio de batería',          '2026-09-13', 300.00, 3),
('Bisagra rota',        'Reparación de chasis',       '2026-09-15', 400.00, 4),
('Rodillo desgastado',  'Cambio de rodillo y limpieza','2026-09-18', 200.00, 5);

-- Pagos
INSERT INTO PAGO (fecha_pago, monto, metodo_pago, id_orden) VALUES
('2026-09-08', 2200.00, 'Efectivo',      1),
('2026-09-13', 1500.00, 'Tarjeta',       3),
('2026-09-14',  600.00, 'Transferencia', 5);

-- Detalle de reparación (sin subtotal, se calcula en consulta)
INSERT INTO REPARACION_PIEZA (id_reparacion, id_pieza, cantidad, precio_unitario_aplicado) VALUES
(1, 1, 1, 1850.00),
(2, 3, 1,  680.00),
(3, 5, 1, 1200.00),
(4, 2, 1,  180.00),
(5, 4, 1,  950.00);