package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.VehiculoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC del repositorio de vehículos.
 */
public class JdbcVehiculoRepository implements VehiculoRepository {

    @Override
    public Vehiculo guardar(Vehiculo vehiculo) {
        if (vehiculo.getId() == null) {
            return insertar(vehiculo);
        } else {
            return actualizar(vehiculo);
        }
    }

    private Vehiculo insertar(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculos (marca_id, placa, cliente_id) VALUES (?, ?, ?) RETURNING id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, vehiculo.getMarcaId());
            stmt.setString(2, vehiculo.getPlaca());
            stmt.setLong(3, vehiculo.getClienteId());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                vehiculo.setId(rs.getLong("id"));
            }
            
            return vehiculo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar vehículo: " + e.getMessage(), e);
        }
    }

    private Vehiculo actualizar(Vehiculo vehiculo) {
        String sql = "UPDATE vehiculos SET marca_id = ?, placa = ?, cliente_id = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, vehiculo.getMarcaId());
            stmt.setString(2, vehiculo.getPlaca());
            stmt.setLong(3, vehiculo.getClienteId());
            stmt.setLong(4, vehiculo.getId());
            
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("No se encontró el vehículo con ID " + vehiculo.getId());
            }
            
            return vehiculo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar vehículo: " + e.getMessage(), e);
        }
    }

    @Override
    public Vehiculo buscarPorId(Long id) {
        String sql = "SELECT id, marca_id, placa, cliente_id FROM vehiculos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearVehiculo(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar vehículo por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public Vehiculo buscarPorPlaca(String placa) {
        String sql = "SELECT id, marca_id, placa, cliente_id FROM vehiculos WHERE UPPER(placa) = UPPER(?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearVehiculo(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar vehículo por placa: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Vehiculo> buscarPorClienteId(Long clienteId) {
        String sql = "SELECT id, marca_id, placa, cliente_id FROM vehiculos WHERE cliente_id = ? ORDER BY placa";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, clienteId);
            ResultSet rs = stmt.executeQuery();
            
            List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
            while (rs.next()) {
                vehiculos.add(mapearVehiculo(rs));
            }
            
            return vehiculos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar vehículos por cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Vehiculo> buscarPorMarcaId(Integer marcaId) {
        String sql = "SELECT id, marca_id, placa, cliente_id FROM vehiculos WHERE marca_id = ? ORDER BY placa";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, marcaId);
            ResultSet rs = stmt.executeQuery();
            
            List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
            while (rs.next()) {
                vehiculos.add(mapearVehiculo(rs));
            }
            
            return vehiculos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar vehículos por marca: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Vehiculo> listarTodos() {
        String sql = "SELECT id, marca_id, placa, cliente_id FROM vehiculos ORDER BY placa";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
            while (rs.next()) {
                vehiculos.add(mapearVehiculo(rs));
            }
            
            return vehiculos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar vehículos: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        String sql = "DELETE FROM vehiculos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int rows = stmt.executeUpdate();
            
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar vehículo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorPlaca(String placa) {
        String sql = "SELECT COUNT(*) FROM vehiculos WHERE UPPER(placa) = UPPER(?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de placa: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorPlacaExcluyendoId(String placa, Long idExcluir) {
        String sql = "SELECT COUNT(*) FROM vehiculos WHERE UPPER(placa) = UPPER(?) AND id != ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, placa);
            stmt.setLong(2, idExcluir);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de placa: " + e.getMessage(), e);
        }
    }

    /**
     * Mapea un ResultSet a un objeto Vehiculo.
     */
    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(rs.getLong("id"));
        vehiculo.setMarcaId(rs.getInt("marca_id"));
        vehiculo.setPlaca(rs.getString("placa"));
        vehiculo.setClienteId(rs.getLong("cliente_id"));
        return vehiculo;
    }
}
