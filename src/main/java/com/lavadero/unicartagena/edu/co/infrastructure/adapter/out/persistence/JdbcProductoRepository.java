package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.catalogo.Producto;
import com.lavadero.unicartagena.edu.co.domain.port.out.catalogo.ProductoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación JDBC del repositorio de Productos.
 * 
 * <p>Adaptador de salida que conecta el dominio con PostgreSQL usando JDBC puro.</p>
 * 
 * <p>Ejemplo de uso:</p>
 * <pre>
 * ProductoRepository repo = new JdbcProductoRepository();
 * Optional&lt;Producto&gt; producto = repo.buscarPorCodigo("PROD-001");
 * </pre>
 */
public class JdbcProductoRepository implements ProductoRepository {
    
    private static final String SCHEMA = "\"Lavadero_28_v2\".";
    private static final String TABLE = SCHEMA + "productos";
    
    @Override
    public Optional<Producto> buscarPorId(Long id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToProducto(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto por ID: " + id, e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public Optional<Producto> buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM " + TABLE + " WHERE codigo = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToProducto(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto por código: " + codigo, e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Producto> listarTodos() {
        String sql = "SELECT * FROM " + TABLE + " ORDER BY nombre";
        List<Producto> productos = new ArrayList<Producto>();
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(mapResultSetToProducto(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos", e);
        }
        
        return productos;
    }
    
    @Override
    public List<Producto> listarPorMarca(Long marcaId) {
        String sql = "SELECT * FROM " + TABLE + " WHERE marca_producto = ? ORDER BY nombre";
        List<Producto> productos = new ArrayList<Producto>();
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, marcaId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                productos.add(mapResultSetToProducto(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos por marca: " + marcaId, e);
        }
        
        return productos;
    }
    
    @Override
    public List<Producto> listarPorAgotarse(int umbralMinimo) {
        // Nota: Esta query requiere JOIN con tabla stock/inventario
        // Por ahora retorna lista vacía hasta definir modelo de stock
        return new ArrayList<Producto>();
    }
    
    @Override
    public Producto guardar(Producto producto) {
        if (producto.getId() == null) {
            return insertar(producto);
        } else {
            return actualizar(producto);
        }
    }
    
    private Producto insertar(Producto producto) {
        String sql = "INSERT INTO " + TABLE + 
                     " (codigo, nombre, precio_compra, precio_venta, marca_producto, imagen, modificado) " +
                     " VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, producto.getCodigo());
            stmt.setString(2, producto.getNombre());
            stmt.setBigDecimal(3, producto.getPrecioCompra());
            stmt.setBigDecimal(4, producto.getPrecioVenta());
            stmt.setLong(5, producto.getMarcaProducto());
            stmt.setString(6, producto.getImagen());
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                producto.setId(rs.getLong(1));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar producto: " + producto.getCodigo(), e);
        }
        
        return producto;
    }
    
    private Producto actualizar(Producto producto) {
        String sql = "UPDATE " + TABLE + 
                     " SET codigo = ?, nombre = ?, precio_compra = ?, precio_venta = ?, " +
                     " marca_producto = ?, imagen = ?, modificado = ? " +
                     " WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, producto.getCodigo());
            stmt.setString(2, producto.getNombre());
            stmt.setBigDecimal(3, producto.getPrecioCompra());
            stmt.setBigDecimal(4, producto.getPrecioVenta());
            stmt.setLong(5, producto.getMarcaProducto());
            stmt.setString(6, producto.getImagen());
            stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            stmt.setLong(8, producto.getId());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto ID: " + producto.getId(), e);
        }
        
        return producto;
    }
    
    @Override
    public boolean actualizarPrecioVenta(Long id, BigDecimal nuevoPrecioVenta) {
        String sql = "UPDATE " + TABLE + " SET precio_venta = ?, modificado = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBigDecimal(1, nuevoPrecioVenta);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setLong(3, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar precio de producto ID: " + id, e);
        }
    }
    
    @Override
    public boolean eliminar(Long id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto ID: " + id, e);
        }
    }
    
    /**
     * Mapea un ResultSet de PostgreSQL a una entidad Producto.
     * 
     * @param rs ResultSet con datos del producto
     * @return Producto mapeado
     * @throws SQLException Si hay error al leer el ResultSet
     */
    private Producto mapResultSetToProducto(ResultSet rs) throws SQLException {
        return new Producto(
            rs.getLong("id"),
            rs.getString("codigo"),
            rs.getString("nombre"),
            rs.getBigDecimal("precio_compra"),
            rs.getBigDecimal("precio_venta"),
            rs.getLong("marca_producto"),
            rs.getString("imagen"),
            rs.getTimestamp("modificado")
        );
    }
}
