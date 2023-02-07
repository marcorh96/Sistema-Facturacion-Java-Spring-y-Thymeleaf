/* Poputale tables*/

INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (1, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (2, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (3, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (4, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (5, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (6, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (7, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (8, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (9, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (10, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (11, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (12, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (13, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (14, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (15, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (16, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (17, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (18, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (19, 'Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-28', '');
INSERT INTO clientes (id, nombre, apellido, email, create_at, foto) VALUES (20, 'Jhon', 'Doe', 'jhon.doe@gmail.com', '2017-08-08', '');


/*Populate tables productos*/
INSERT INTO productos (nombre, precio, create_at) VALUES ('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Camara Digital DSC-W320B', 123490, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Apple Ipod shuffle', 1499990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Sony Notebook Z110', 37990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES ('Mica Comoda 5 cajones', 299990, NOW());

/*Creamos algunas facturas*/
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Facturas de equipos de Oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES ('Facturas Bicicleta', 'Alguna nota', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES (3, 2, 6);
