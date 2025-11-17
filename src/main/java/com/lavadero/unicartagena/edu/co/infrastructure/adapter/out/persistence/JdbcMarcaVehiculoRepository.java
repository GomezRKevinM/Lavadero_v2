package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.MarcaVehiculo;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.MarcaVehiculoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del repositorio de marcas de vehículos.
 */
public class JdbcMarcaVehiculoRepository implements MarcaVehiculoRepository {

    @Override
    public MarcaVehiculo guardar(MarcaVehiculo marca) {
        if (marca.getId() == null) {
            return insertar(marca);
        } else {
            return actualizar(marca);
        }
    }

    private MarcaVehiculo insertar(MarcaVehiculo marca) {
        String sql = "INSERT INTO marca_vehiculos (marca) VALUES (?) RETURNING id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, marca.getMarca());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                marca.setId(rs.getInt("id"));
            }
            
            return marca;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar marca de vehículo: " + e.getMessage(), e);
        }
    }

    private MarcaVehiculo actualizar(MarcaVehiculo marca) {
        String sql = "UPDATE marca_vehiculos SET marca = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, marca.getMarca());
            stmt.setInt(2, marca.getId());
            
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("No se encontró la marca con ID " + marca.getId());
            }
            
            return marca;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar marca de vehículo: " + e.getMessage(), e);
        }
    }

    @Override
    public MarcaVehiculo buscarPorId(Integer id) {
        String sql = "SELECT id, marca FROM marca_vehiculos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearMarcaVehiculo(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar marca por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public MarcaVehiculo buscarPorNombre(String nombre) {
        String sql = "SELECT id, marca FROM marca_vehiculos WHERE UPPER(marca) = UPPER(?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearMarcaVehiculo(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar marca por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public List<MarcaVehiculo> listarTodas() {
        String sql = "SELECT id, marca FROM marca_vehiculos ORDER BY marca";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            List<MarcaVehiculo> marcas = new ArrayList<MarcaVehiculo>();
            while (rs.next()) {
                marcas.add(mapearMarcaVehiculo(rs));
            }
            
            return marcas;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar marcas: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM marca_vehiculos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar marca: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM marca_vehiculos WHERE UPPER(marca) = UPPER(?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de marca: " + e.getMessage(), e);
        }
    }

    /**
     * Mapea un ResultSet a un objeto MarcaVehiculo.
     */
    private MarcaVehiculo mapearMarcaVehiculo(ResultSet rs) throws SQLException {
        MarcaVehiculo marca = new MarcaVehiculo();
        marca.setId(rs.getInt("id"));
        marca.setMarca(rs.getString("marca"));
        return marca;
    }
}
