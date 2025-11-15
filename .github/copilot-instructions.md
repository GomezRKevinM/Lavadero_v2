# Copilot Instructions - Sistema de Lavadero ABC Ltda.

## Contexto del Negocio
Sistema de gestión para lavadero de automóviles operado por el grupo empresarial ABC Ltda. en Universidad de Cartagena. Maneja todo el ciclo: catálogo de productos/servicios, gestión de inventario, proveedores, órdenes de venta, colas de atención y administración de empleados.

## Arquitectura: Hexagonal (Ports & Adapters)

### Estructura de Packages

```
com.lavadero.unicartagena.edu.co
├── domain/                          # Núcleo del negocio (sin dependencias externas)
│   ├── model/                       # Entidades: Cliente, Producto, Servicio, OrdenVenta, etc.
│   ├── port/
│   │   ├── in/                      # Puertos de entrada (casos de uso)
│   │   └── out/                     # Puertos de salida (interfaces para persistencia)
│   └── exception/                   # Excepciones de dominio
├── application/                     # Lógica de aplicación
│   ├── service/                     # Implementación de casos de uso (puertos IN)
│   └── usecase/                     # Interfaces de casos de uso
├── infrastructure/                  # Adaptadores externos
│   ├── adapter/
│   │   ├── in/
│   │   │   ├── web/                # Controllers REST/Web
│   │   │   └── cli/                # Interfaz de línea de comandos
│   │   └── out/
│   │       ├── persistence/        # JPA/JDBC repositories
│   │       └── external/           # APIs externas, sistemas de pago
│   └── config/                     # Configuración de Spring, beans
└── shared/                          # Código compartido
    ├── util/                        # Utilidades generales
    └── constant/                    # Constantes del sistema
```

### Reglas de Dependencia
- **domain** NO depende de nadie (completamente independiente)
- **application** solo depende de **domain**
- **infrastructure** depende de **domain** y **application**
- **shared** puede ser usado por cualquier capa

## Entidades del Dominio (basadas en esquema PostgreSQL)

### Contexto: Catálogo (productos/servicios)
- **Producto**: id, codigo, nombre, precioCompra, precioVenta, marcaProducto, imagen, modificado
- **Servicio**: id, codigo, nombre, descripcion, precio, imagen, modificado
- **Categoria**: id, nombre, empresaId (relación N:N con Producto y Servicio)
- **ProductoMarca**: id, marcaProducto
- **ProductoProveedor**: tabla de unión producto-proveedor (N:N)

### Contexto: Compras e Inventario
- **Cotizacion**: id, emitido, expira, empleadoRealiza, estadoId
- **CotizacionDetalles**: id, productoId, cotizacionId, cantidad, precioCompra, precioUnitario, precioTotal, descuento, iva
- **CotizacionProveedor**: id, cotizacionId, proveedorId, estadoId
- **CotizacionCheck**: id, cotizacionProveedorId, empleadoId, fechaRevision
- **Comentarios**: id, cotizacionCheckId, comentario
- **Pedido**: id, codigo, estadoId, emitido, fechaEntrega, valorPagado, iva, cotizacionId, proveedorId
- **PedidoDetalles**: id, pedidoId, productoId, cantidad, precioUnitario, precioTotal, descuento, iva
- **NotaCorreccion**: id, idPedido, descripcion, empleadoId, fecha, fechaMaximaRespuesta, estadoId
- **NotaCorreccionDetalles**: id, notaCorreccionId, pedidoDetalleId, cantidadRecibida, precioUnitarioRecibido, ivaRecibido
- **Bodega**: id, codigo, nombre, ubicacion, empresaId
- **ProductoBodega**: tabla de unión producto-bodega (N:N)

### Contexto: Proveedores y Clientes
- **Proveedor**: id, idCompanyInfo
- **Cliente**: id, infoPersonalId, empresaId
- **CompanyInfo**: id, nombre, direccion, telefono, email, nit, webpage
- **InfoPersonal**: id, tipoIdentificacionId, nombre, apellidos, identificacion, telefono, correo, direccion
- **TipoIdentificacion**: id, tipo
- **InfoPago**: id, bancoId, tipo (enum tipo_cuenta), numeroCuenta, titular, modificado, idCompany
- **Banco**: id, banco

### Contexto: Empleados y Contratos
- **Empleado**: id, infoPersonalId, usuarioId, modificado
- **Contrato**: id, fechaInicio, fechaVencimiento, salarioBase, cargoId, empleadoId
- **Cargo**: id, nombre
- **Clausula**: id, descripcion, contratoId
- **Horario**: id, contratoId, horaInicio, horaReceso, horaReanudacion, horaFinal
- **DiasLaborados**: id, horarioId, dia (enum)
- **Usuario**: id, username, password, questionSecurity, answerSecurity, empresaId, creado, modificado

### Contexto: Operaciones y Servicios
- **AreaTrabajo**: id, nombre, descripcion, empresaId (equivalente a "Cubículo")
- **AreaTrabajoProductos**: tabla de unión area-producto (N:N)
- **AreaTrabajoServicios**: tabla de unión area-servicio (N:N)
- **ColaEspera**: id, areaTr abajoId
- **ColaCliente**: colaId, clienteId, estadoId, fechaIngreso, turno
- **ServicioEmpleados**: tabla de unión servicio-empleado (N:N)

### Contexto: Vehículos
- **Vehiculo**: id, marcaId, placa, clienteId
- **MarcaVehiculo**: id, marca
- **Color**: id, color, hexcode
- **VehiculoColor**: tabla de unión vehiculo-color (N:N)

### Entidad Transversal
- **Estado**: id, entidad (varchar), nombre - maneja estados de múltiples entidades
- **Empresa**: id, idCompanyInfo

## Reglas de Negocio Críticas

### Inventario y Compras
1. **Cálculo de precio de venta**: Aumentar 35% sobre la diferencia entre precio de compra actual y anterior
2. **Stock mínimo**: Cuando productos están por agotarse, generar cotizaciones con múltiples proveedores
3. **Verificación de pedido**: Si hay errores, generar NotaCorreccionPedido con diferencias detalladas
4. **Pago a proveedores**: Solo por productos correctamente recibidos

### Órdenes de Venta
1. **Tres tipos posibles**: solo productos, solo servicios, o productos + servicios (separados)
2. **Productos**: ItemProducto con código, marca, precio, IVA, unidades, descuento
3. **Servicios**: ItemServicio incluye detalles del automóvil, empleado aplicador, tiempos de ejecución
4. **Totales**: Calcular precios, cantidades y tiempos al final del documento

### Gestión de Servicios
1. **Cola de atención**: Vehículos organizados por orden de llegada
2. **Asignación de cubículos**: Verificar disponibilidad antes de iniciar servicio
3. **Múltiples servicios**: Un automóvil puede recibir varios servicios en el mismo cubículo simultáneamente
4. **Actualización**: Supervisor actualiza cola cuando un servicio termina

## Queries Requeridas (50 consultas del README.md)
Ver `src/main/java/com/lavadero/unicartagena/edu/co/README.md` para la lista completa de consultas que el sistema debe soportar. Incluyen:
- Listados de clientes, empleados, productos, servicios, categorías
- Estados de cotizaciones, pedidos, notas de corrección
- Detalles de órdenes de venta (productos, servicios, mixtas)
- Gestión de cola de vehículos y cubículos
- Información de empleados, contratos, cargos

## Convenciones de Código

### Naming
- **Ports IN**: `{Accion}{Entidad}UseCase` (ej: `CrearOrdenVentaUseCase`)
- **Ports OUT**: `{Entidad}Repository` (ej: `ProductoRepository`)
- **Services**: `{Entidad}Service` (ej: `ProductoService`) - implementan puertos IN
- **Adapters**: `Jpa{Entidad}Repository`, `{Entidad}Controller`
- **Entities**: Nombres sustantivos en singular

### Packages por Entidad
Agrupar código relacionado:
```
domain/model/producto/
    - Producto.java
    - Categoria.java
domain/port/in/producto/
    - CrearProductoUseCase.java
    - BuscarProductosPorCategoriaUseCase.java
```

### Testing
- Tests unitarios en `src/test/java/` siguiendo estructura de packages
- Naming: `{ClaseATestear}Test.java`
- Métodos: `should{ComportamientoEsperado}_when{Condicion}()`
- Usar JUnit 4.11

## Tecnología Stack
- **Build**: Maven 3.x
- **Java**: 1.7 (considerar actualizar a 8+ para lambdas y streams)
- **Testing**: JUnit 4.11
- **Base de Datos**: PostgreSQL (schema: `Lavadero_28_v2`)
- **Persistencia**: JPA/Hibernate recomendado (mapear entidades a tablas existentes)

## Desarrollo de Features

### Flujo típico para nueva funcionalidad:
1. **Definir modelo de dominio** en `domain/model/`
2. **Crear puerto de entrada** (caso de uso) en `domain/port/in/`
3. **Crear puerto de salida** (repository) en `domain/port/out/`
4. **Implementar servicio** en `application/service/` que use los puertos
5. **Crear adaptadores** en `infrastructure/adapter/`:
   - Persistence adapter implementa puerto OUT
   - Web/CLI adapter llama puerto IN
6. **Configurar beans** en `infrastructure/config/`

### Ejemplo: Crear Orden de Venta
```java
// 1. domain/model/venta/OrdenVenta.java
// 2. domain/port/in/venta/CrearOrdenVentaUseCase.java
// 3. domain/port/out/venta/OrdenVentaRepository.java
// 4. application/service/OrdenVentaService.java (implementa CrearOrdenVentaUseCase)
// 5. infrastructure/adapter/out/persistence/JpaOrdenVentaRepository.java
// 6. infrastructure/adapter/in/web/OrdenVentaController.java
```

## Comandos Maven
```bash
mvn clean compile          # Compilar
mvn test                   # Ejecutar tests
mvn package               # Generar JAR
mvn clean install         # Instalar en repositorio local
```

## Notas Importantes
- Proyecto universitario: Documentar exhaustivamente con Javadoc
- Sistema complejo con 50+ queries: Diseñar índices de BD cuidadosamente
- Manejar concurrencia en cola de vehículos y cubículos
- Validaciones de negocio en la capa de dominio (NO en controllers)
- Excepciones de dominio para errores de negocio (ej: `StockInsuficienteException`)
