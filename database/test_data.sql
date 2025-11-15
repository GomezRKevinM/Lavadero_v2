-- ============================================================
-- DATOS DE PRUEBA PARA SISTEMA DE LAVADERO ABC LTDA.
-- ============================================================
-- Este script inserta datos de prueba mínimos para poder
-- ejecutar y probar el sistema.
-- ============================================================

-- Usar el schema correcto
SET search_path TO "Lavadero_28_v2";

-- ============================================================
-- 1. EMPRESA (Entidad base del sistema)
-- ============================================================

-- Primero insertar company_info para la empresa
INSERT INTO company_info (id, nombre, direccion, telefono, email, nit, webpage) VALUES
(1, 'ABC Ltda.', 'Calle 30 #49-43, Cartagena', '3001234567', 'info@abc.com', '900123456-7', 'www.abc.com');

-- Luego la empresa
INSERT INTO empresa (id, id_company_info) VALUES
(1, 1);

-- ============================================================
-- 2. TIPOS Y CATÁLOGOS BASE
-- ============================================================

-- Tipos de identificación
INSERT INTO tipo_identificacion (id, tipo) VALUES
(1, 'Cédula de Ciudadanía'),
(2, 'NIT'),
(3, 'Pasaporte'),
(4, 'Cédula de Extranjería');

-- Bancos
INSERT INTO banco (id, banco) VALUES
(1, 'Bancolombia'),
(2, 'Banco de Bogotá'),
(3, 'Davivienda'),
(4, 'Banco Popular');

-- Marcas de productos
INSERT INTO producto_marca (id, marca_producto) VALUES
(1, 'Turtle Wax'),
(2, 'Meguiar''s'),
(3, '3M'),
(4, 'Sonax'),
(5, 'Sin Marca');

-- Marcas de vehículos
INSERT INTO marca_vehiculo (id, marca) VALUES
(1, 'Toyota'),
(2, 'Chevrolet'),
(3, 'Mazda'),
(4, 'Renault'),
(5, 'Hyundai');

-- Colores
INSERT INTO color (id, color, hexcode) VALUES
(1, 'Blanco', '#FFFFFF'),
(2, 'Negro', '#000000'),
(3, 'Gris', '#808080'),
(4, 'Rojo', '#FF0000'),
(5, 'Azul', '#0000FF');

-- ============================================================
-- 3. ESTADOS (Para múltiples entidades)
-- ============================================================

-- Estados de Cotización
INSERT INTO estado (id, entidad, nombre) VALUES
(1, 'cotizacion', 'Pendiente'),
(2, 'cotizacion', 'Aprobada'),
(3, 'cotizacion', 'Rechazada'),
(4, 'cotizacion', 'En Revisión');

-- Estados de Pedido
INSERT INTO estado (id, entidad, nombre) VALUES
(5, 'pedido', 'Pendiente'),
(6, 'pedido', 'En Tránsito'),
(7, 'pedido', 'Recibido'),
(8, 'pedido', 'Con Problemas');

-- Estados de Nota de Corrección
INSERT INTO estado (id, entidad, nombre) VALUES
(9, 'nota_correccion', 'Abierta'),
(10, 'nota_correccion', 'En Revisión'),
(11, 'nota_correccion', 'Resuelta');

-- Estados de Cola de Clientes
INSERT INTO estado (id, entidad, nombre) VALUES
(12, 'cola_cliente', 'Esperando'),
(13, 'cola_cliente', 'En Atención'),
(14, 'cola_cliente', 'Atendido');

-- ============================================================
-- 4. CATEGORÍAS DE PRODUCTOS Y SERVICIOS
-- ============================================================

INSERT INTO categoria (id, nombre, empresa_id) VALUES
(1, 'Productos de Limpieza', 1),
(2, 'Encerado y Pulido', 1),
(3, 'Accesorios', 1),
(4, 'Lavado Básico', 1),
(5, 'Lavado Premium', 1),
(6, 'Detailing', 1);

-- ============================================================
-- 5. PRODUCTOS
-- ============================================================

INSERT INTO producto (id, codigo, nombre, precio_compra, precio_venta, marca_producto, imagen, modificado) VALUES
(1, 'PROD-001', 'Shampoo para Autos 1L', 8000.00, 12000.00, 1, 'shampoo.png', CURRENT_TIMESTAMP),
(2, 'PROD-002', 'Cera Líquida 500ml', 15000.00, 22500.00, 2, 'cera.png', CURRENT_TIMESTAMP),
(3, 'PROD-003', 'Esponja de Microfibra', 3000.00, 5000.00, 3, 'esponja.png', CURRENT_TIMESTAMP),
(4, 'PROD-004', 'Limpiador de Llantas 750ml', 12000.00, 18000.00, 4, 'limpiador_llantas.png', CURRENT_TIMESTAMP),
(5, 'PROD-005', 'Toalla de Secado Grande', 8000.00, 12000.00, 5, 'toalla.png', CURRENT_TIMESTAMP);

-- Relación producto-categoría (N:N)
INSERT INTO producto_categoria (producto_id, categoria_id) VALUES
(1, 1), -- Shampoo → Productos de Limpieza
(2, 2), -- Cera → Encerado y Pulido
(3, 3), -- Esponja → Accesorios
(4, 1), -- Limpiador llantas → Productos de Limpieza
(5, 3); -- Toalla → Accesorios

-- ============================================================
-- 6. SERVICIOS
-- ============================================================

INSERT INTO servicio (id, codigo, nombre, descripcion, precio, imagen, modificado) VALUES
(1, 'SERV-001', 'Lavado Básico', 'Lavado exterior con shampoo y secado', 15000.00, 'lavado_basico.png', CURRENT_TIMESTAMP),
(2, 'SERV-002', 'Lavado Premium', 'Lavado completo exterior e interior', 30000.00, 'lavado_premium.png', CURRENT_TIMESTAMP),
(3, 'SERV-003', 'Encerado', 'Aplicación de cera protectora', 25000.00, 'encerado.png', CURRENT_TIMESTAMP),
(4, 'SERV-004', 'Pulido', 'Pulido de pintura y eliminación de rayones leves', 45000.00, 'pulido.png', CURRENT_TIMESTAMP),
(5, 'SERV-005', 'Lavado de Motor', 'Limpieza profunda del compartimento del motor', 20000.00, 'lavado_motor.png', CURRENT_TIMESTAMP);

-- Relación servicio-categoría (N:N)
INSERT INTO servicio_categoria (servicio_id, categoria_id) VALUES
(1, 4), -- Lavado Básico → Lavado Básico
(2, 5), -- Lavado Premium → Lavado Premium
(3, 2), -- Encerado → Encerado y Pulido
(4, 6), -- Pulido → Detailing
(5, 6); -- Lavado Motor → Detailing

-- ============================================================
-- 7. PROVEEDORES
-- ============================================================

INSERT INTO company_info (id, nombre, direccion, telefono, email, nit, webpage) VALUES
(2, 'Distribuidora Automotriz S.A.S.', 'Av. Pedro de Heredia #45-23', '3051234567', 'ventas@distrib.com', '900987654-3', 'www.distrib.com'),
(3, 'Importadora de Químicos Ltda.', 'Calle 70 #52-12', '3009876543', 'info@quimicos.com', '900555444-1', 'www.quimicos.com');

INSERT INTO proveedor (id, id_company_info) VALUES
(1, 2),
(2, 3);

-- Relación producto-proveedor (N:N)
INSERT INTO producto_proveedor (producto_id, proveedor_id) VALUES
(1, 1), -- Shampoo de Proveedor 1
(1, 2), -- Shampoo también de Proveedor 2
(2, 1), -- Cera de Proveedor 1
(3, 2), -- Esponja de Proveedor 2
(4, 1), -- Limpiador de Proveedor 1
(5, 2); -- Toalla de Proveedor 2

-- ============================================================
-- 8. BODEGAS
-- ============================================================

INSERT INTO bodega (id, codigo, nombre, ubicacion, empresa_id) VALUES
(1, 'BOD-001', 'Bodega Principal', 'Piso 1 - Área de Almacén', 1),
(2, 'BOD-002', 'Bodega Secundaria', 'Piso 2 - Área de Reserva', 1);

-- Relación producto-bodega (N:N)
INSERT INTO producto_bodega (producto_id, bodega_id) VALUES
(1, 1), -- Shampoo en Bodega Principal
(2, 1), -- Cera en Bodega Principal
(3, 1), -- Esponja en Bodega Principal
(4, 2), -- Limpiador en Bodega Secundaria
(5, 2); -- Toalla en Bodega Secundaria

-- ============================================================
-- 9. CLIENTES
-- ============================================================

INSERT INTO info_personal (id, tipo_identificacion_id, nombre, apellidos, identificacion, telefono, correo, direccion) VALUES
(1, 1, 'Juan Carlos', 'Pérez Gómez', '1234567890', '3201234567', 'juan.perez@email.com', 'Calle 10 #20-30'),
(2, 1, 'María Fernanda', 'López Martínez', '0987654321', '3109876543', 'maria.lopez@email.com', 'Carrera 15 #25-35'),
(3, 1, 'Carlos Andrés', 'Rodríguez Silva', '1122334455', '3156789012', 'carlos.rodriguez@email.com', 'Avenida 8 #12-45');

INSERT INTO cliente (id, info_personal_id, empresa_id) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1);

-- ============================================================
-- 10. VEHÍCULOS
-- ============================================================

INSERT INTO vehiculo (id, marca_id, placa, cliente_id) VALUES
(1, 1, 'ABC123', 1), -- Toyota de Juan Carlos
(2, 3, 'XYZ789', 2), -- Mazda de María Fernanda
(3, 2, 'DEF456', 3); -- Chevrolet de Carlos Andrés

-- Relación vehículo-color (N:N)
INSERT INTO vehiculo_color (vehiculo_id, color_id) VALUES
(1, 1), -- Toyota Blanco
(2, 4), -- Mazda Rojo
(3, 3); -- Chevrolet Gris

-- ============================================================
-- 11. EMPLEADOS Y USUARIOS
-- ============================================================

INSERT INTO info_personal (id, tipo_identificacion_id, nombre, apellidos, identificacion, telefono, correo, direccion) VALUES
(4, 1, 'Pedro Antonio', 'García Ruiz', '5566778899', '3187654321', 'pedro.garcia@abc.com', 'Calle 5 #10-15'),
(5, 1, 'Ana Isabel', 'Martínez Torres', '6677889900', '3198765432', 'ana.martinez@abc.com', 'Carrera 20 #30-40');

INSERT INTO usuario (id, username, password, question_security, answer_security, empresa_id, creado, modificado) VALUES
(1, 'admin', 'admin123', '¿Color favorito?', 'azul', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'pedro.garcia', 'pedro123', '¿Mascota?', 'perro', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO empleado (id, info_personal_id, usuario_id, modificado) VALUES
(1, 4, 2, CURRENT_TIMESTAMP),
(2, 5, 1, CURRENT_TIMESTAMP);

-- Cargos
INSERT INTO cargo (id, nombre) VALUES
(1, 'Lavador'),
(2, 'Supervisor'),
(3, 'Administrador'),
(4, 'Cajero');

-- Contratos
INSERT INTO contrato (id, fecha_inicio, fecha_vencimiento, salario_base, cargo_id, empleado_id) VALUES
(1, '2024-01-01', '2025-12-31', 1300000.00, 1, 1), -- Pedro lavador
(2, '2024-01-01', '2025-12-31', 1500000.00, 3, 2); -- Ana administradora

-- Horarios
INSERT INTO horario (id, contrato_id, hora_inicio, hora_receso, hora_reanudacion, hora_final) VALUES
(1, 1, '08:00:00', '12:00:00', '13:00:00', '17:00:00'),
(2, 2, '07:00:00', '12:00:00', '13:00:00', '18:00:00');

-- Días laborados
INSERT INTO dias_laborados (id, horario_id, dia) VALUES
(1, 1, 'LUNES'),
(2, 1, 'MARTES'),
(3, 1, 'MIERCOLES'),
(4, 1, 'JUEVES'),
(5, 1, 'VIERNES'),
(6, 2, 'LUNES'),
(7, 2, 'MARTES'),
(8, 2, 'MIERCOLES'),
(9, 2, 'JUEVES'),
(10, 2, 'VIERNES');

-- Relación servicio-empleado (N:N)
INSERT INTO servicio_empleados (servicio_id, empleado_id) VALUES
(1, 1), -- Pedro puede hacer Lavado Básico
(2, 1), -- Pedro puede hacer Lavado Premium
(3, 1), -- Pedro puede hacer Encerado
(5, 1); -- Pedro puede hacer Lavado de Motor

-- ============================================================
-- 12. ÁREAS DE TRABAJO (CUBÍCULOS)
-- ============================================================

INSERT INTO area_trabajo (id, nombre, descripcion, empresa_id) VALUES
(1, 'Cubículo A', 'Zona de lavado básico - 2 vehículos', 1),
(2, 'Cubículo B', 'Zona de lavado premium - 1 vehículo', 1),
(3, 'Cubículo C', 'Zona de detailing - 1 vehículo', 1);

-- Productos disponibles en cada área
INSERT INTO area_trabajo_productos (area_trabajo_id, producto_id) VALUES
(1, 1), -- Cubículo A tiene Shampoo
(1, 3), -- Cubículo A tiene Esponja
(1, 5), -- Cubículo A tiene Toalla
(2, 1), -- Cubículo B tiene Shampoo
(2, 2), -- Cubículo B tiene Cera
(2, 3), -- Cubículo B tiene Esponja
(2, 5), -- Cubículo B tiene Toalla
(3, 2), -- Cubículo C tiene Cera
(3, 4); -- Cubículo C tiene Limpiador de Llantas

-- Servicios que se pueden realizar en cada área
INSERT INTO area_trabajo_servicios (area_trabajo_id, servicio_id) VALUES
(1, 1), -- Cubículo A → Lavado Básico
(2, 2), -- Cubículo B → Lavado Premium
(2, 3), -- Cubículo B → Encerado
(3, 4), -- Cubículo C → Pulido
(3, 5); -- Cubículo C → Lavado Motor

-- ============================================================
-- 13. COLA DE ESPERA
-- ============================================================

INSERT INTO cola_espera (id, area_trabajo_id) VALUES
(1, 1), -- Cola para Cubículo A
(2, 2), -- Cola para Cubículo B
(3, 3); -- Cola para Cubículo C

-- Clientes en cola
INSERT INTO cola_cliente (cola_id, cliente_id, estado_id, fecha_ingreso, turno) VALUES
(1, 1, 12, CURRENT_TIMESTAMP, 1), -- Juan Carlos esperando en Cubículo A
(1, 2, 12, CURRENT_TIMESTAMP, 2), -- María Fernanda esperando en Cubículo A
(2, 3, 13, CURRENT_TIMESTAMP, 1); -- Carlos Andrés siendo atendido en Cubículo B

-- ============================================================
-- FINALIZADO
-- ============================================================

SELECT 'Datos de prueba insertados exitosamente!' AS mensaje;

-- Verificar conteos
SELECT 'Empresas: ' || COUNT(*) FROM empresa;
SELECT 'Productos: ' || COUNT(*) FROM producto;
SELECT 'Servicios: ' || COUNT(*) FROM servicio;
SELECT 'Clientes: ' || COUNT(*) FROM cliente;
SELECT 'Empleados: ' || COUNT(*) FROM empleado;
SELECT 'Vehículos: ' || COUNT(*) FROM vehiculo;
SELECT 'Proveedores: ' || COUNT(*) FROM proveedor;
SELECT 'Áreas de Trabajo: ' || COUNT(*) FROM area_trabajo;
