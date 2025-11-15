# 🚀 Sistema de Lavadero ABC Ltda.

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![Java](https://img.shields.io/badge/Java-1.8-blue)]()
[![Maven](https://img.shields.io/badge/Maven-3.x-red)]()
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-9.x+-blue)]()
[![License](https://img.shields.io/badge/license-Academic-orange)]()

Sistema de gestión completo para lavadero de automóviles desarrollado por el **Grupo Empresarial ABC Ltda.** para la **Universidad de Cartagena**. 

## 📋 Descripción del Proyecto

Sistema empresarial que gestiona todo el ciclo operativo de un lavadero de automóviles, incluyendo:

- 🛒 **Catálogo de Productos y Servicios**
- 📦 **Gestión de Inventario y Bodegas**
- 🤝 **Administración de Proveedores y Compras**
- 📝 **Órdenes de Venta (Productos, Servicios y Mixtas)**
- ⏰ **Cola de Atención y Cubículos de Trabajo**
- 👥 **Gestión de Empleados y Contratos**
- 🚗 **Registro de Clientes y Vehículos**

## 🏗️ Arquitectura

El sistema implementa **Arquitectura Hexagonal (Ports & Adapters)** con separación clara de responsabilidades:

```
┌─────────────────────────────────────────────────────┐
│              Infrastructure Layer                    │
│  (Adapters: JDBC, Web, CLI, External APIs)         │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────┴────────────────────────────────────┐
│              Application Layer                       │
│       (Services, Use Cases Implementation)          │
└────────────────┬────────────────────────────────────┘
                 │
┌────────────────┴────────────────────────────────────┐
│                Domain Layer                         │
│  (Entities, Ports, Business Rules - NO DEPS)       │
└─────────────────────────────────────────────────────┘
```

### Reglas de Dependencia
- **Domain**: Núcleo puro sin dependencias externas
- **Application**: Solo depende de Domain
- **Infrastructure**: Depende de Domain y Application
- **Shared**: Utilidades cross-cutting

## 🛠️ Tecnologías

| Categoría | Tecnología | Versión |
|-----------|-----------|---------|
| **Lenguaje** | Java | 1.8 |
| **Build Tool** | Maven | 3.x |
| **Base de Datos** | PostgreSQL | 9.x+ |
| **Persistencia** | JDBC + HikariCP | Direct SQL |
| **Testing** | JUnit | 4.11 |
| **Pool de Conexiones** | HikariCP | 3.4.5 |

## 📁 Estructura del Proyecto

```
lavadero/
├── .github/
│   └── copilot-instructions.md     # Guía para AI coding agents
├── database/
│   ├── schema.sql                  # Script DDL completo
│   └── test_data.sql              # Datos de prueba
├── src/
│   ├── main/java/com/lavadero/unicartagena/edu/co/
│   │   ├── domain/
│   │   │   ├── model/             # 39 Entidades del dominio
│   │   │   │   ├── shared/        # Estado, Empresa, Banco
│   │   │   │   ├── personas/      # Cliente, Proveedor
│   │   │   │   ├── empleados/     # Empleado, Contrato
│   │   │   │   ├── catalogo/      # Producto, Servicio
│   │   │   │   ├── compras/       # Cotización, Pedido
│   │   │   │   ├── vehiculos/     # Vehiculo, Marca, Color
│   │   │   │   └── operaciones/   # AreaTrabajo, Cola
│   │   │   ├── port/
│   │   │   │   ├── in/            # Use Case interfaces
│   │   │   │   └── out/           # Repository interfaces
│   │   │   └── exception/         # Excepciones de dominio
│   │   ├── application/
│   │   │   ├── service/           # Implementación de casos de uso
│   │   │   └── usecase/           # Interfaces de use cases
│   │   ├── infrastructure/
│   │   │   ├── adapter/
│   │   │   │   ├── in/
│   │   │   │   │   ├── web/       # REST Controllers
│   │   │   │   │   └── cli/       # CLI Interface
│   │   │   │   └── out/
│   │   │   │       └── persistence/ # JDBC Repositories
│   │   │   └── config/            # DatabaseConfig (HikariCP)
│   │   ├── shared/
│   │   │   ├── util/              # Utilidades generales
│   │   │   └── constant/          # Constantes del sistema
│   │   └── App.java               # Main application
│   └── test/java/                 # Tests unitarios e integración
├── ARQUITECTURA.md                # Documentación de arquitectura
├── STRUCTURE.md                   # Estructura de packages
├── ENTIDADES_CREADAS.md          # Inventario de entidades
├── ESTADO_PROYECTO.md            # Estado actual del desarrollo
├── COMO_EJECUTAR.md              # Guía de instalación y ejecución
└── pom.xml                       # Maven configuration
```

## 🚀 Quick Start

### Prerrequisitos

```bash
# Verificar Java 8+
java -version

# Verificar Maven
mvn -version

# Verificar PostgreSQL
psql --version
```

### Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/tu-org/lavadero.git
cd lavadero
```

2. **Configurar PostgreSQL**
```sql
-- Crear base de datos
CREATE DATABASE "Lavadero_28_v2";

-- Ejecutar schema
\c "Lavadero_28_v2"
\i database/schema.sql

-- Insertar datos de prueba
\i database/test_data.sql
```

3. **Configurar conexión a BD**
```bash
# Linux/Mac
export DB_URL="jdbc:postgresql://localhost:5432/Lavadero_28_v2"
export DB_USER="postgres"
export DB_PASSWORD="tu_password"

# Windows
set DB_URL=jdbc:postgresql://localhost:5432/Lavadero_28_v2
set DB_USER=postgres
set DB_PASSWORD=tu_password
```

4. **Compilar y ejecutar**
```bash
# Compilar
mvn clean package

# Ejecutar aplicación de ejemplo
mvn exec:java -Dexec.mainClass="com.lavadero.unicartagena.edu.co.App"
```

### Salida Esperada

```
=== Sistema de Lavadero ABC Ltda. ===
Ejemplo de uso con JDBC y Arquitectura Hexagonal

1. Probando conexión a PostgreSQL...
   ✓ Conexión exitosa!

2. Listando todos los productos...
   Total de productos: 5
   - PROD-001: Shampoo para Autos - $12000
   ...

3. Creando un nuevo producto...
   ✓ Producto creado con ID: 6
   ...
```

## 📊 Estado del Proyecto

| Componente | Completado | Pendiente | Progreso |
|------------|-----------|-----------|----------|
| **Entidades** | 39/39 | 0/39 | ![100%](https://progress-bar.dev/100) |
| **Repositories** | 1/39 | 38/39 | ![3%](https://progress-bar.dev/3) |
| **Use Cases** | 0/50 | 50/50 | ![0%](https://progress-bar.dev/0) |
| **Services** | 0/20 | 20/20 | ![0%](https://progress-bar.dev/0) |
| **Controllers** | 0/10 | 10/10 | ![0%](https://progress-bar.dev/0) |
| **Tests** | 1/100 | 99/100 | ![1%](https://progress-bar.dev/1) |

**Progreso Global: ~15%** 🟠

Ver [`ESTADO_PROYECTO.md`](ESTADO_PROYECTO.md) para detalles completos.

## 📖 Documentación

| Documento | Descripción |
|-----------|-------------|
| [`README.md`](README.md) | Este archivo - Overview del proyecto |
| [`COMO_EJECUTAR.md`](COMO_EJECUTAR.md) | Guía completa de instalación y ejecución |
| [`ARQUITECTURA.md`](ARQUITECTURA.md) | Mapeo de tablas PostgreSQL a entidades Java |
| [`STRUCTURE.md`](STRUCTURE.md) | Estructura de packages y convenciones |
| [`ENTIDADES_CREADAS.md`](ENTIDADES_CREADAS.md) | Inventario de 39 entidades creadas |
| [`ESTADO_PROYECTO.md`](ESTADO_PROYECTO.md) | Estado actual y roadmap |
| [`.github/copilot-instructions.md`](.github/copilot-instructions.md) | Guía para AI coding agents |

## 🧪 Testing

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar tests con coverage
mvn test jacoco:report

# Tests específicos
mvn test -Dtest=AppTest
```

## 🔑 Reglas de Negocio Críticas

### 1. Cálculo de Precio de Venta
```
nuevo_precio_venta = precio_venta_actual + 
                     ((precio_compra_nuevo - precio_compra_anterior) * 0.35)
```

### 2. Stock Mínimo
Cuando productos están por agotarse → Generar cotizaciones con múltiples proveedores

### 3. Verificación de Pedidos
Si hay discrepancias en recepción → Crear Nota de Corrección con detalles

### 4. Órdenes de Venta
- **Tres tipos**: Solo productos, Solo servicios, Mixta (separadas)
- **Cálculo**: Precios, IVA, descuentos, cantidades, tiempos

### 5. Cola de Atención
- Vehículos organizados por orden de llegada
- Verificar disponibilidad de cubículos
- Múltiples servicios simultáneos en mismo cubículo

## 🤝 Contribución

### Para Desarrolladores

1. **Fork el repositorio**
2. **Crear branch**: `git checkout -b feature/mi-feature`
3. **Seguir convenciones**:
   - Naming: `{Accion}{Entidad}UseCase`, `Jpa{Entidad}Repository`
   - Documentar con Javadoc
   - Tests unitarios obligatorios
4. **Commit**: `git commit -m "feat: descripción clara"`
5. **Push**: `git push origin feature/mi-feature`
6. **Pull Request** con descripción detallada

### Para Estudiantes

1. Leer documentación en orden: README → ARQUITECTURA → COMO_EJECUTAR
2. Configurar entorno local
3. Ejecutar `App.java` para ver ejemplo
4. Escoger entidad simple (ej: Color, Banco) para implementar repository
5. Pedir ayuda si te atoras en issues

## 📈 Roadmap

### Fase 1: Fundación (4-5 semanas) - En Progreso 🟠
- [x] Crear 39 entidades de dominio
- [x] Configurar arquitectura hexagonal
- [x] Ejemplo de repository JDBC
- [ ] Completar 38 repositories restantes
- [ ] Definir 50 use cases
- [ ] Implementar 15 services prioritarios

### Fase 2: Interfaz (2-3 semanas) - Pendiente ⏳
- [ ] Crear CLI o REST Controllers
- [ ] Implementar excepciones de dominio
- [ ] Validaciones de entrada

### Fase 3: Calidad (2 semanas) - Pendiente ⏳
- [ ] Tests unitarios completos
- [ ] Tests de integración JDBC
- [ ] Tests end-to-end

### Fase 4: Producción (1 semana) - Pendiente ⏳
- [ ] Documentación de despliegue
- [ ] Manual de usuario
- [ ] Capacitación

**Tiempo estimado total**: 10-11 semanas

## 🐛 Troubleshooting

### Error: "Connection refused"
```bash
# Verificar PostgreSQL corriendo
sudo service postgresql status

# Iniciar si está detenido
sudo service postgresql start
```

### Error: "database does not exist"
```sql
CREATE DATABASE "Lavadero_28_v2";
```

### Error: "relation does not exist"
```bash
psql -U postgres -d "Lavadero_28_v2" -f database/schema.sql
```

Ver [`COMO_EJECUTAR.md`](COMO_EJECUTAR.md) para troubleshooting completo.

## 👥 Equipo

- **Grupo Empresarial ABC Ltda.**
- **Universidad de Cartagena**
- Proyecto Académico - Ingeniería de Software

## 📄 Licencia

Este es un proyecto académico desarrollado para la Universidad de Cartagena.

## 📞 Contacto y Soporte

Para dudas o problemas:
- 📧 Email: soporte@abc.com
- 🐛 Issues: GitHub Issues
- 📖 Docs: Ver carpeta de documentación

---

<div align="center">

**Desarrollado con ❤️ por ABC Ltda. para Universidad de Cartagena**

[Documentación](COMO_EJECUTAR.md) • [Arquitectura](ARQUITECTURA.md) • [Estado](ESTADO_PROYECTO.md)

</div>
