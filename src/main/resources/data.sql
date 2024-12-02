INSERT INTO tienda (id, nombre, direccion, uuid)
VALUES(1, 'ULTRA ALEJANDRIA', 'CALLE 14 N 4-22 CENTRO', '123e4567-e89b-12d3-a456-
426614174000');
INSERT INTO tienda (id, nombre, direccion, uuid)
VALUES(2, 'LA SUPERBODEGA', 'CARRERA 20 N 12-23', '987e6543-a12b-34d3-c789-426614174001');
INSERT INTO tienda (id, nombre, direccion, uuid)
VALUES(3, 'LA NEBULOSA COMERCIAL', 'AVENIDA 0 N 17-12', '12345678-1234-5678-1234-
123456789012');
INSERT INTO tipo_producto (id, nombre) VALUES(1, 'DISPOSITIVOS ELECTRÓNICOs');
INSERT INTO tipo_producto (id, nombre) VALUES(2, 'ROPA');
INSERT INTO tipo_producto (id, nombre) VALUES(3, 'COMIDA');
INSERT INTO tipo_producto (id, nombre) VALUES(4, 'JUGUETES');
INSERT INTO tipo_producto (id, nombre) VALUES(5, 'LIBROS');
INSERT INTO tipo_pago (id, nombre) VALUES(1, 'PSE');
INSERT INTO tipo_pago (id, nombre) VALUES(2, 'TARJETA CREDITO');
INSERT INTO tipo_pago (id, nombre) VALUES(3, 'TARJETA DEBITO');
INSERT INTO tipo_pago (id, nombre) VALUES(4, 'EFECTIVO');
INSERT INTO tipo_pago (id, nombre) VALUES(5, 'BITCOIN');
INSERT INTO tipo_pago (id, nombre) VALUES(6, 'TRANSFERENCIA');
INSERT INTO tipo_documento (id, nombre) VALUES(1, 'CC');
INSERT INTO tipo_documento (id, nombre) VALUES(2, 'CE');
INSERT INTO tipo_documento (id, nombre) VALUES(3, 'TI');
INSERT INTO tipo_documento (id, nombre) VALUES(4, 'NIT');
INSERT INTO tipo_documento (id, nombre) VALUES(5, 'RC');
INSERT INTO cliente (nombre, documento, tipo_documento_id) VALUES
('Juan Pérez', '10000001', 1),
('María Gómez', '10000002', 1),
('Carlos López', '20000001', 2),
('Laura Martínez', '10000001', 3),
('Andrés Gutiérrez', '40000001', 1),
('Sofía Ramírez', '50000001', 2),
('Luis Fernández', '60000001', 1),
('Paula Torres', '70000001', 3),
('Miguel Vega', '80000001', 2),
('Camila Sánchez', '90000001', 1),
('Jorge Rodríguez', '10000003', 2),
('Lucía Morales', '20000002', 1),
('Fernando Castillo', '30000002', 3),
('Valentina Ruiz', '40000002', 1),
('Sebastián López', '50000002', 2),
('Isabella Herrera', '60000002', 3),
('Mateo Vargas', '70000002', 1),
('Daniela Ríos', '80000002', 2),
('Diego Peña', '90000002', 3),
('Sara Muñoz', '10000004', 1);
INSERT INTO vendedor (nombre, documento, email) VALUES
('Pedro Sánchez', '10101010', 'pedro.sanchez@empresa.com'),
('Ana Ramírez', '20202020', 'ana.ramirez@empresa.com'),
('Javier Torres', '30303030', 'javier.torres@empresa.com'),
('Luisa Gómez', '40404040', 'luisa.gomez@empresa.com'),
('Carolina Ruiz', '50505050', 'carolina.ruiz@empresa.com');
INSERT INTO cajero (nombre, documento, tienda_id, email, token) VALUES
('Luis Herrera', '60606060', 1, 'luis.herrera@tienda.com', 'token123'),
('Elena Díaz', '70707070', 2, 'elena.diaz@tienda.com', 'token456'),
('Santiago Pérez', '80808080', 1, 'santiago.perez@tienda.com', 'token789'),
('Claudia López', '90909090', 2, 'claudia.lopez@tienda.com', 'token012'),
('Natalia Ríos', '10101011', 1, 'natalia.rios@tienda.com', 'token345');
INSERT INTO producto (nombre,descripcion,precio,tipo_producto_id,cantidad,referencia) VALUES
('Smartphone','Teléfono inteligente de última generación',1200.00,1,15,'ELEC001'),
('Laptop','Laptop con procesador de alta velocidad',2500.00,1,10,'ELEC002'),
('Smartwatch','Reloj inteligente con monitoreo de salud',300.00,1,25,'ELEC003'),
('Tablet','Tableta con pantalla táctil de 10 pulgadas',600.00,1,20,'ELEC004'),
('Auriculares Bluetooth','Auriculares inalámbricos con cancelación de ruido',150.00,1,30,'ELEC005'),
('Camiseta básica','Camiseta de algodón unisex',15.00,2,50,'ROPA001'),
('Jeans','Pantalón de mezclilla clásico',40.00,2,35,'ROPA002'),
('Chaqueta impermeable','Chaqueta para lluvia con capucha',80.00,2,20,'ROPA003'),
('Vestido de verano','Vestido casual con estampado floral',50.00,2,25,'ROPA004'),
('Zapatos deportivos','Zapatos cómodos para correr',60.00,2,30,'ROPA005');
INSERT INTO producto (nombre,descripcion,precio,tipo_producto_id,cantidad,referencia) VALUES
('Manzana','Manzana roja fresca',0.50,3,100,'COMI001'),
('Pan integral','Pan integral en rodajas',2.50,3,80,'COMI002'),
('Leche','Leche entera pasteurizada',1.20,3,50,'COMI003'),
('Cereal','Cereal de avena con miel',3.00,3,40,'COMI004'),
('Chocolate','Tableta de chocolate oscuro',2.00,3,70,'COMI005'),
('Osito de peluche','Pelucha suave y abrazable',25.00,4,15,'JUGU001'),
('Bloques de construcción','Set de bloques para armar estructuras',30.00,4,20,'JUGU002'),
('Muñeca','Muñeca con accesorios incluidos',40.00,4,10,'JUGU003'),
('Carrito de juguete','Carro de juguete a escala',20.00,4,25,'JUGU004'),
('Juego de mesa','Juego de mesa para toda la familia',35.00,4,12,'JUGU005');
INSERT INTO producto (nombre,descripcion,precio,tipo_producto_id,cantidad,referencia) VALUES
('El arte de la guerra','Clásico libro de estrategia militar',12.00,5,30,'LIBR001'),
('Cien años de soledad','Obra maestra de Gabriel García Márquez',15.00,5,20,'LIBR002'),
('Harry Potter y la piedra filosofal','Primera entrega de la saga de Harry Potter',18.00,5,25,'LIBR003'),
('El principito','Libro clásico de Antoine de Saint-Exupéry',10.00,5,50,'LIBR004'),
('Aprendiendo Python','Guía completa para aprender Python',40.00,5,10,'LIBR005');
INSERT INTO compra
(id, cliente_id, tienda_id, vendedor_id, cajero_id, total, impuestos, fecha, observaciones)
VALUES(1, 1, 1, 1, 1, 7728.00, 368.00, '2024-01-01 00:00:00', 'Ninguna');
INSERT INTO detalles_compra
(id, compra_id, producto_id, cantidad, precio, descuento)
VALUES(1, 1, 1, 2, 1200.00, 10.00);
INSERT INTO detalles_compra
(id, compra_id, producto_id, cantidad, precio, descuento)
VALUES(2, 1, 2, 1, 2500.00, 0.00);
INSERT INTO detalles_compra
(id, compra_id, producto_id, cantidad, precio, descuento)
VALUES(3, 1, 3, 10, 300.00, 10.00);
INSERT INTO pago
(id, compra_id, tipo_pago_id, tarjeta_tipo, cuotas, valor)
VALUES(1, 1, 1, 'VISA', 1, 7360.00);
INSERT INTO pago
(id, compra_id, tipo_pago_id, tarjeta_tipo, cuotas, valor)
VALUES(2, 1, 4, NULL, NULL, 368.00);