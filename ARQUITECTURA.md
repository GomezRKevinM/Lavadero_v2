# Arquitectura Hexagonal - Sistema Lavadero

## Estructura de Packages Completa

```
com.lavadero.unicartagena.edu.co/
│
├── domain/                                    # CAPA DE DOMINIO (Núcleo del negocio)
│   ├── model/                                # Entidades del dominio
│   │   ├── catalogo/                        # Productos, Servicios, Categorías, Marcas
│   │   ├── compras/                         # Cotizaciones, Pedidos, NotasCorreccion, Bodegas
│   │   ├── ventas/                          # Órdenes de venta (a implementar)
│   │   ├── personas/                        # Cliente, Proveedor, InfoPersonal, CompanyInfo
│   │   ├── empleados/                       # Empleado, Contrato, Cargo, Usuario, Horario
│   │   ├── operaciones/                     # AreaTrabajo (Cubículo), ColaEspera, ColaCliente
│   │   ├── vehiculos/                       # Vehiculo, MarcaVehiculo, Color
│   │   └── shared/                          # Estado, Empresa, TipoIdentificacion, Banco
│   │
│   ├── port/                                # Puertos (Interfaces)
│   │   ├── in/                              # Casos de uso (entrada)
│   │   │   ├── catalogo/                   # Ej: CrearProductoUseCase, BuscarServiciosPorCategoriaUseCase
│   │   │   ├── compras/                    # Ej: GenerarCotizacionUseCase, VerificarPedidoUseCase
│   │   │   ├── ventas/                     # Ej: CrearOrdenVentaUseCase, CalcularTotalUseCase
│   │   │   └── operaciones/                # Ej: AsignarCubiculoUseCase, ActualizarColaUseCase
│   │   │
│   │   └── out/                             # Repositorios (salida)
│   │       ├── catalogo/                   # ProductoRepository, ServicioRepository
│   │       ├── compras/                    # CotizacionRepository, PedidoRepository
│   │       ├── ventas/                     # OrdenVentaRepository
│   │       ├── operaciones/                # AreaTrabajoRepository, ColaEsperaRepository
│   │       ├── personas/                   # ClienteRepository, ProveedorRepository
│   │       └── empleados/                  # EmpleadoRepository, ContratoRepository
│   │
│   └── exception/                           # Excepciones de dominio
│       └── (Ej: StockInsuficienteException, CubiculoNoDisponibleException)
│
├── application/                             # CAPA DE APLICACIÓN
│   ├── service/                            # Implementación de casos de uso
│   │   └── (Servicios organizados por contexto)
│   └── usecase/                            # Interfaces de casos de uso (alternativa)
│
├── infrastructure/                          # CAPA DE INFRAESTRUCTURA
│   ├── adapter/
│   │   ├── in/                             # Adaptadores de entrada
│   │   │   ├── web/                       # Controllers REST (Spring MVC)
│   │   │   └── cli/                       # Interfaz de línea de comandos
│   │   │
│   │   └── out/                            # Adaptadores de salida
│   │       ├── persistence/               # Implementaciones JPA/JDBC
│   │       └── external/                  # APIs externas, sistemas de pago
│   │
│   └── config/                             # Configuración Spring, Beans
│
└── shared/                                  # CÓDIGO COMPARTIDO
    ├── util/                               # Utilidades generales
    └── constant/                           # Constantes del sistema
```

## Mapeo de Contextos a Entidades PostgreSQL

### 📦 Contexto: **catalogo**
- `productos` → Producto
- `servicios` → Servicio
- `categorias` → Categoria
- `producto_marca` → ProductoMarca
- `producto_categoria` → (relación N:N)
- `servicio_categoria` → (relación N:N)
- `producto_proveedor` → (relación N:N)

### 🛒 Contexto: **compras**
- `cotizacion` → Cotizacion
- `cotizacion_detalles` → CotizacionDetalles
- `cotizacion_proveedor` → CotizacionProveedor
- `cotizacion_check` → CotizacionCheck
- `comentarios` → Comentarios
- `pedido` → Pedido
- `pedido_detalles` → PedidoDetalles
- `nota_correcion` → NotaCorreccion
- `nota_correccion_detalles` → NotaCorreccionDetalles
- `bodegas` → Bodega
- `producto_bodega` → (relación N:N)

### 💰 Contexto: **ventas**
- `orden_venta` → OrdenVenta (pendiente: no existe en BD actual)
- `orden_venta_productos` → ItemProducto (pendiente)
- `orden_venta_servicios` → ItemServicio (pendiente)
- **Nota**: Las tablas de ventas deben ser creadas según los requerimientos del README.md

### 👥 Contexto: **personas**
- `clientes` → Cliente
- `proveedores` → Proveedor
- `company_info` → CompanyInfo
- `info_personal` → InfoPersonal
- `info_pago` → InfoPago

### 👷 Contexto: **empleados**
- `empleados` → Empleado
- `contratos` → Contrato
- `cargo` → Cargo
- `clausula` → Clausula
- `horarios` → Horario
- `dias_laborados` → DiasLaborados
- `usuarios` → Usuario
- `servicio_empleados` → (relación N:N)

### 🚗 Contexto: **vehiculos**
- `vehiculos` → Vehiculo
- `marca_vehiculos` → MarcaVehiculo
- `colores` → Color
- `vehiculo_color` → (relación N:N)

### ⚙️ Contexto: **operaciones**
- `area_trabajo` → AreaTrabajo (Cubículo)
- `area_trabajo_productos` → (relación N:N)
- `area_trabajo_servicios` → (relación N:N)
- `cola_espera` → ColaEspera
- `cola_cliente` → ColaCliente

### 🔄 Contexto: **shared** (transversal)
- `estados` → Estado
- `empresas` → Empresa
- `tipo_identificacion` → TipoIdentificacion
- `bancos` → Banco

## Flujo de Trabajo con Arquitectura Hexagonal

### Ejemplo: Crear un Producto

1. **Controller** (Infrastructure - IN)
   ```
   infrastructure/adapter/in/web/ProductoController.java
   ```
   - Recibe HTTP Request
   - Valida datos de entrada
   - Llama al caso de uso

2. **Use Case** (Domain - Port IN)
   ```
   domain/port/in/catalogo/CrearProductoUseCase.java
   ```
   - Define el contrato (interface)
   - Declara método: `Producto crear(DatosProducto datos)`

3. **Service** (Application)
   ```
   application/service/ProductoService.java
   ```
   - Implementa `CrearProductoUseCase`
   - Ejecuta lógica de negocio
   - Llama al repository (puerto OUT)

4. **Repository** (Domain - Port OUT)
   ```
   domain/port/out/catalogo/ProductoRepository.java
   ```
   - Define contrato para persistencia
   - Método: `Producto guardar(Producto producto)`

5. **JPA Adapter** (Infrastructure - OUT)
   ```
   infrastructure/adapter/out/persistence/JpaProductoRepository.java
   ```
   - Implementa `ProductoRepository`
   - Usa JPA/Hibernate para persistir

### Dependencias
```
Controller → UseCase ← Service → Repository ← JPA Adapter
  (IN)        (IN)     (APP)      (OUT)         (OUT)
```

## Reglas de Oro

1. **Domain** es independiente - NO tiene dependencias externas
2. **Application** solo conoce **Domain**
3. **Infrastructure** conoce **Domain** y **Application**
4. Las interfaces (ports) están en **Domain**
5. Las implementaciones están en **Application** o **Infrastructure**

## Tipos Enumerados PostgreSQL

Según el esquema, se deben crear enums Java para:

```java
// shared/constant/
public enum Dia {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
}

public enum TipoCuenta {
    AHORROS, CORRIENTE
}
```

## Próximos Pasos

1. Crear entidades del dominio en sus respectivos packages
2. Definir interfaces de repositorios (port/out)
3. Definir casos de uso (port/in)
4. Implementar servicios de aplicación
5. Crear adaptadores JPA
6. Crear controllers REST
7. Configurar conexión a PostgreSQL
