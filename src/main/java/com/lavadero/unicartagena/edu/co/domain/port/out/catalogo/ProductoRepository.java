package com.lavadero.unicartagena.edu.co.domain.port.out.catalogo;

import com.lavadero.unicartagena.edu.co.domain.model.catalogo.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (Repository) para operaciones de persistencia de Productos.
 * 
 * <p>Define el contrato para acceso a datos de productos sin depender de la
 * implementación específica (JDBC, JPA, etc.).</p>
 * 
 * <p>Implementación sugerida: JdbcProductoRepository en infrastructure/adapter/out/persistence</p>
 */
public interface ProductoRepository {
    
    /**
     * Busca un producto por su ID.
     * 
     * @param id ID del producto
     * @return Optional con el producto si existe, vacío si no
     */
    Optional<Producto> buscarPorId(Long id);
    
    /**
     * Busca un producto por su código único.
     * 
     * @param codigo Código del producto
     * @return Optional con el producto si existe, vacío si no
     */
    Optional<Producto> buscarPorCodigo(String codigo);
    
    /**
     * Lista todos los productos disponibles.
     * 
     * @return Lista de productos
     */
    List<Producto> listarTodos();
    
    /**
     * Lista productos filtrados por marca.
     * 
     * @param marcaId ID de la marca del producto
     * @return Lista de productos de esa marca
     */
    List<Producto> listarPorMarca(Long marcaId);
    
    /**
     * Lista productos con stock bajo (próximos a agotarse).
     * Útil para generar cotizaciones automáticas.
     * 
     * @param umbralMinimo Stock mínimo para considerar "por agotarse"
     * @return Lista de productos con bajo stock
     */
    List<Producto> listarPorAgotarse(int umbralMinimo);
    
    /**
     * Guarda un nuevo producto o actualiza uno existente.
     * 
     * @param producto Producto a guardar
     * @return Producto guardado con ID generado si es nuevo
     */
    Producto guardar(Producto producto);
    
    /**
     * Actualiza el precio de venta de un producto.
     * Regla de negocio: aumenta 35% sobre diferencia de precios de compra.
     * 
     * @param id ID del producto
     * @param nuevoPrecioVenta Nuevo precio de venta calculado
     * @return true si se actualizó, false si no existe
     */
    boolean actualizarPrecioVenta(Long id, java.math.BigDecimal nuevoPrecioVenta);
    
    /**
     * Elimina un producto por su ID.
     * 
     * @param id ID del producto a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean eliminar(Long id);
}
