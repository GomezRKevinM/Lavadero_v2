package com.lavadero.unicartagena.edu.co;

import com.lavadero.unicartagena.edu.co.domain.model.catalogo.Producto;
import com.lavadero.unicartagena.edu.co.domain.port.out.catalogo.ProductoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence.JdbcProductoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Aplicación de ejemplo que demuestra el uso de JDBC con arquitectura hexagonal.
 * 
 * <p>Este ejemplo muestra:</p>
 * <ul>
 *   <li>Conexión a PostgreSQL usando HikariCP</li>
 *   <li>CRUD completo de productos</li>
 *   <li>Patrón Repository con JDBC</li>
 *   <li>Arquitectura Hexagonal (Ports & Adapters)</li>
 * </ul>
 * 
 * <p><strong>ANTES DE EJECUTAR:</strong></p>
 * <ol>
 *   <li>Configurar PostgreSQL con base de datos "Lavadero_28_v2"</li>
 *   <li>Ejecutar script SQL para crear schema y tablas</li>
 *   <li>Configurar variables de entorno DB_URL, DB_USER, DB_PASSWORD (opcional)</li>
 * </ol>
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Sistema de Lavadero ABC Ltda. ===");
        System.out.println("Ejemplo de uso con JDBC y Arquitectura Hexagonal\n");
        
        // Probar conexión a la base de datos
        System.out.println("1. Probando conexión a PostgreSQL...");
        if (DatabaseConfig.testConnection()) {
            System.out.println("   ✓ Conexión exitosa!\n");
        } else {
            System.err.println("   ✗ Error de conexión. Verifica configuración de BD.");
            return;
        }
        
        // Crear instancia del repository (adaptador JDBC)
        ProductoRepository productoRepo = new JdbcProductoRepository();
        
        // === EJEMPLO 1: Listar todos los productos ===
        System.out.println("2. Listando todos los productos...");
        try {
            List<Producto> productos = productoRepo.listarTodos();
            if (productos.isEmpty()) {
                System.out.println("   No hay productos registrados.\n");
            } else {
                System.out.println("   Total de productos: " + productos.size());
                for (Producto p : productos) {
                    System.out.println("   - " + p.getCodigo() + ": " + p.getNombre() + 
                                     " - $" + p.getPrecioVenta());
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("   Error al listar productos: " + e.getMessage());
        }
        
        // === EJEMPLO 2: Crear un nuevo producto ===
        System.out.println("3. Creando un nuevo producto...");
        try {
            Producto nuevoProducto = new Producto();
            nuevoProducto.setCodigo("PROD-EJEMPLO-001");
            nuevoProducto.setNombre("Cera para Autos Premium");
            nuevoProducto.setPrecioCompra(new BigDecimal("15000.00"));
            nuevoProducto.setPrecioVenta(new BigDecimal("25000.00"));
            nuevoProducto.setMarcaProducto(1L); // Debe existir en tabla producto_marca
            nuevoProducto.setImagen("cera_premium.png");
            nuevoProducto.setModificado(new Timestamp(System.currentTimeMillis()));
            
            Producto productoGuardado = productoRepo.guardar(nuevoProducto);
            System.out.println("   ✓ Producto creado con ID: " + productoGuardado.getId());
            System.out.println("   " + productoGuardado + "\n");
        } catch (Exception e) {
            System.err.println("   Error al crear producto: " + e.getMessage());
            System.err.println("   (Verifica que exista una marca con ID=1 en producto_marca)\n");
        }
        
        // === EJEMPLO 3: Buscar producto por código ===
        System.out.println("4. Buscando producto por código...");
        try {
            Optional<Producto> productoOpt = productoRepo.buscarPorCodigo("PROD-EJEMPLO-001");
            if (productoOpt.isPresent()) {
                Producto p = productoOpt.get();
                System.out.println("   ✓ Producto encontrado:");
                System.out.println("   ID: " + p.getId());
                System.out.println("   Código: " + p.getCodigo());
                System.out.println("   Nombre: " + p.getNombre());
                System.out.println("   Precio Compra: $" + p.getPrecioCompra());
                System.out.println("   Precio Venta: $" + p.getPrecioVenta() + "\n");
            } else {
                System.out.println("   Producto no encontrado.\n");
            }
        } catch (Exception e) {
            System.err.println("   Error al buscar producto: " + e.getMessage() + "\n");
        }
        
        // === EJEMPLO 4: Actualizar precio de venta (Regla de Negocio) ===
        System.out.println("5. Aplicando regla de negocio: +35% sobre diferencia de precios...");
        try {
            Optional<Producto> productoOpt = productoRepo.buscarPorCodigo("PROD-EJEMPLO-001");
            if (productoOpt.isPresent()) {
                Producto p = productoOpt.get();
                
                // Regla de negocio: precio de venta aumenta 35% sobre la diferencia
                // entre precio de compra actual y anterior
                BigDecimal precioCompraActual = new BigDecimal("18000.00");
                BigDecimal precioCompraAnterior = p.getPrecioCompra();
                BigDecimal diferencia = precioCompraActual.subtract(precioCompraAnterior);
                BigDecimal aumento = diferencia.multiply(new BigDecimal("0.35"));
                BigDecimal nuevoPrecioVenta = p.getPrecioVenta().add(aumento);
                
                System.out.println("   Precio compra anterior: $" + precioCompraAnterior);
                System.out.println("   Precio compra actual: $" + precioCompraActual);
                System.out.println("   Diferencia: $" + diferencia);
                System.out.println("   Aumento (35%): $" + aumento);
                System.out.println("   Nuevo precio venta: $" + nuevoPrecioVenta);
                
                boolean actualizado = productoRepo.actualizarPrecioVenta(p.getId(), nuevoPrecioVenta);
                if (actualizado) {
                    System.out.println("   ✓ Precio actualizado exitosamente!\n");
                }
            }
        } catch (Exception e) {
            System.err.println("   Error al actualizar precio: " + e.getMessage() + "\n");
        }
        
        // === EJEMPLO 5: Listar productos por marca ===
        System.out.println("6. Listando productos por marca (ID=1)...");
        try {
            List<Producto> productosPorMarca = productoRepo.listarPorMarca(1L);
            System.out.println("   Total de productos de esa marca: " + productosPorMarca.size());
            for (Producto p : productosPorMarca) {
                System.out.println("   - " + p.getCodigo() + ": " + p.getNombre());
            }
            System.out.println();
        } catch (Exception e) {
            System.err.println("   Error al listar por marca: " + e.getMessage() + "\n");
        }
        
        // === LIMPIAR: Eliminar producto de ejemplo (opcional) ===
        System.out.println("7. Limpiando: eliminando producto de ejemplo...");
        try {
            Optional<Producto> productoOpt = productoRepo.buscarPorCodigo("PROD-EJEMPLO-001");
            if (productoOpt.isPresent()) {
                boolean eliminado = productoRepo.eliminar(productoOpt.get().getId());
                if (eliminado) {
                    System.out.println("   ✓ Producto eliminado.\n");
                }
            }
        } catch (Exception e) {
            System.err.println("   Error al eliminar: " + e.getMessage() + "\n");
        }
        
        // Cerrar pool de conexiones al finalizar
        System.out.println("8. Cerrando conexiones...");
        DatabaseConfig.closeDataSource();
        System.out.println("   ✓ Aplicación finalizada correctamente.\n");
        
        System.out.println("===========================================");
        System.out.println("Próximos pasos:");
        System.out.println("1. Implementar más repositories (Cliente, Servicio, Pedido, etc.)");
        System.out.println("2. Crear casos de uso (UseCases) en domain/port/in/");
        System.out.println("3. Implementar servicios en application/service/");
        System.out.println("4. Crear controllers para interfaz CLI o Web");
        System.out.println("5. Implementar las 50 queries del README.md");
        System.out.println("===========================================");
    }
}
