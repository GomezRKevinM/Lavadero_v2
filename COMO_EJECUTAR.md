# Cómo Ejecutar el Sistema de Lavadero

Este documento explica paso a paso cómo configurar y ejecutar el sistema.

## Prerrequisitos

1. **Java 8 o superior**
   ```bash
   java -version  # Debe mostrar 1.8 o superior
   ```

2. **Maven 3.x**
   ```bash
   mvn -version
   ```

3. **PostgreSQL 9.x o superior**
   ```bash
   psql --version
   ```

## Paso 1: Configurar PostgreSQL

### 1.1 Crear la base de datos

```sql
-- Conectarse como usuario postgres
psql -U postgres

-- Crear la base de datos
CREATE DATABASE "Lavadero_28_v2";

-- Conectarse a la nueva base de datos
\c "Lavadero_28_v2"
```

### 1.2 Ejecutar el script SQL

Ejecuta el archivo `database/schema.sql` que contiene todas las tablas del sistema:

```bash
psql -U postgres -d "Lavadero_28_v2" -f database/schema.sql
```

O desde psql:
```sql
\c "Lavadero_28_v2"
\i 'ruta/al/archivo/database/schema.sql'
```

### 1.3 Insertar datos de prueba (opcional)

```bash
psql -U postgres -d "Lavadero_28_v2" -f database/test_data.sql
```

## Paso 2: Configurar Conexión a BD

Tienes dos opciones:

### Opción A: Variables de Entorno (Recomendado para producción)

**Windows:**
```bash
set DB_URL=jdbc:postgresql://localhost:5432/Lavadero_28_v2
set DB_USER=postgres
set DB_PASSWORD=tu_password
```

**Linux/Mac:**
```bash
export DB_URL="jdbc:postgresql://localhost:5432/Lavadero_28_v2"
export DB_USER="postgres"
export DB_PASSWORD="tu_password"
```

### Opción B: Modificar DatabaseConfig.java directamente

Edita `infrastructure/config/DatabaseConfig.java`:

```java
private static final String DB_URL = "jdbc:postgresql://localhost:5432/Lavadero_28_v2";
private static final String DB_USER = "postgres";
private static final String DB_PASSWORD = "tu_password";
```

⚠️ **ADVERTENCIA:** No commitear contraseñas al repositorio. Agrega `DatabaseConfig.java` al `.gitignore` si usas esta opción.

## Paso 3: Compilar el Proyecto

```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar tests
mvn test

# Generar JAR ejecutable
mvn package
```

## Paso 4: Ejecutar el Programa

### Opción A: Desde Maven

```bash
mvn exec:java -Dexec.mainClass="com.lavadero.unicartagena.edu.co.App"
```

### Opción B: Desde JAR

```bash
# Primero generar el JAR
mvn package

# Ejecutar
java -cp target/lavadero-1.0-SNAPSHOT.jar com.lavadero.unicartagena.edu.co.App
```

### Opción C: Desde IDE (Eclipse/IntelliJ/VSCode)

1. Importar el proyecto como Maven project
2. Configurar las variables de entorno en Run Configuration
3. Ejecutar `App.java` como Java Application

## Ejemplo de Salida Esperada

```
=== Sistema de Lavadero ABC Ltda. ===
Ejemplo de uso con JDBC y Arquitectura Hexagonal

1. Probando conexión a PostgreSQL...
   ✓ Conexión exitosa!

2. Listando todos los productos...
   Total de productos: 5
   - PROD-001: Shampoo para Autos - $15000
   - PROD-002: Cera Premium - $25000
   ...

3. Creando un nuevo producto...
   ✓ Producto creado con ID: 6
   ...

===========================================
Próximos pasos:
1. Implementar más repositories (Cliente, Servicio, Pedido, etc.)
2. Crear casos de uso (UseCases) en domain/port/in/
3. Implementar servicios en application/service/
4. Crear controllers para interfaz CLI o Web
5. Implementar las 50 queries del README.md
===========================================
```

## Troubleshooting

### Error: "Connection refused"

**Causa:** PostgreSQL no está corriendo o no acepta conexiones.

**Solución:**
```bash
# Verificar estado de PostgreSQL
sudo service postgresql status   # Linux
brew services list               # Mac
# Windows: Buscar "Services" y verificar PostgreSQL

# Iniciar PostgreSQL si está detenido
sudo service postgresql start    # Linux
brew services start postgresql   # Mac
```

### Error: "database does not exist"

**Causa:** La base de datos "Lavadero_28_v2" no fue creada.

**Solución:**
```sql
CREATE DATABASE "Lavadero_28_v2";
```

### Error: "relation does not exist"

**Causa:** Las tablas no fueron creadas correctamente.

**Solución:**
```bash
psql -U postgres -d "Lavadero_28_v2" -f database/schema.sql
```

### Error: "password authentication failed"

**Causa:** Credenciales incorrectas en la configuración.

**Solución:**
1. Verificar usuario y contraseña de PostgreSQL
2. Actualizar variables de entorno o `DatabaseConfig.java`
3. Verificar `pg_hba.conf` para métodos de autenticación

### Error: "HikariPool-1 - Exception during pool initialization"

**Causa:** Error general de conexión (puede ser cualquiera de los anteriores).

**Solución:**
1. Verificar que PostgreSQL está corriendo
2. Verificar URL de conexión (puerto, nombre de BD)
3. Verificar usuario/password
4. Verificar firewall (puerto 5432 debe estar abierto)

## Arquitectura del Proyecto

```
App.java (main)
    ↓
JdbcProductoRepository (Infrastructure - Adapter OUT)
    ↓
ProductoRepository (Domain - Port OUT)
    ↓
Producto (Domain - Entity)
    ↓
PostgreSQL Database
```

El flujo inverso sería:
```
PostgreSQL → JDBC → Repository → Service (futuro) → UseCase → Controller
```

## Próximos Pasos para Desarrollo

### 1. Completar Repositories (38 restantes)

```bash
# Crear interfaz para cada entidad
domain/port/out/
    - personas/ClienteRepository.java
    - empleados/EmpleadoRepository.java
    - compras/PedidoRepository.java
    - operaciones/ColaEsperaRepository.java
    # ... etc

# Crear implementación JDBC para cada una
infrastructure/adapter/out/persistence/
    - JdbcClienteRepository.java
    - JdbcEmpleadoRepository.java
    - JdbcPedidoRepository.java
    # ... etc
```

### 2. Crear Use Cases (Casos de Uso)

```bash
domain/port/in/
    - catalogo/CrearProductoUseCase.java
    - catalogo/BuscarProductosPorCategoriaUseCase.java
    - ventas/CrearOrdenVentaUseCase.java
    - compras/GenerarCotizacionUseCase.java
    # ... etc (50 queries del README.md)
```

### 3. Implementar Application Services

```bash
application/service/
    - ProductoService.java (implementa use cases de producto)
    - ClienteService.java
    - VentaService.java
    # ... etc
```

### 4. Crear Controllers

```bash
infrastructure/adapter/in/web/
    - ProductoController.java (REST API)
    - ClienteController.java

# O para interfaz de línea de comandos:
infrastructure/adapter/in/cli/
    - MenuPrincipalCLI.java
    - ProductoCLI.java
```

### 5. Implementar las 50 Queries del README.md

Ver `README.md` sección "Consultas del Sistema" para lista completa.

## Recursos Adicionales

- **Documentación de Arquitectura:** `ARQUITECTURA.md`
- **Estructura de Packages:** `STRUCTURE.md`
- **Lista de Entidades:** `ENTIDADES_CREADAS.md`
- **Instrucciones para AI Agents:** `.github/copilot-instructions.md`
- **Schema de Base de Datos:** `database/schema.sql`

## Contacto y Soporte

Para dudas o problemas, contactar al equipo de desarrollo de ABC Ltda.
o abrir un issue en el repositorio del proyecto.
