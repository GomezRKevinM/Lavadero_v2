package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Cargo;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.CargoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCargoRepository implements CargoRepository {

    @Override
    public Cargo guardar(Cargo cargo) {
        String sql = "INSERT INTO cargo (nombre) VALUES (?) RETURNING id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cargo.getNombre());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cargo.setId(rs.getInt("id"));
            }
            
            return cargo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public Cargo actualizar(Cargo cargo) {
        String sql = "UPDATE cargo SET nombre = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cargo.getNombre());
            stmt.setInt(2, cargo.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Cargo con ID " + cargo.getId() + " no encontrado");
            }
            
            return cargo;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM cargo WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public Cargo buscarPorId(Integer id) {
        String sql = "SELECT id, nombre FROM cargo WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearCargo(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cargo> listarTodos() {
        String sql = "SELECT id, nombre FROM cargo ORDER BY nombre";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Cargo> cargos = new ArrayList<Cargo>();
            while (rs.next()) {
                cargos.add(mapearCargo(rs));
            }
            
            return cargos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar cargos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cargo> buscarPorNombre(String termino) {
        String sql = "SELECT id, nombre FROM cargo WHERE LOWER(nombre) LIKE LOWER(?) ORDER BY nombre";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + termino + "%");
            ResultSet rs = stmt.executeQuery();
            
            List<Cargo> cargos = new ArrayList<Cargo>();
            while (rs.next()) {
                cargos.add(mapearCargo(rs));
            }
            
            return cargos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cargos por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorId(Integer id) {
        String sql = "SELECT COUNT(*) FROM cargo WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM cargo WHERE LOWER(nombre) = LOWER(?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de cargo por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorNombreExcluyendoId(String nombre, Integer idExcluir) {
        String sql = "SELECT COUNT(*) FROM cargo WHERE LOWER(nombre) = LOWER(?) AND id != ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nombre);
            stmt.setInt(2, idExcluir);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de cargo por nombre: " + e.getMessage(), e);
        }
    }

    private Cargo mapearCargo(ResultSet rs) throws SQLException {
        return new Cargo(
            rs.getInt("id"),
            rs.getString("nombre")
        );
    }
}
