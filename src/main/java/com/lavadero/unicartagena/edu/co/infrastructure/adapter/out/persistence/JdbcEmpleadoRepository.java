package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Empleado;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.EmpleadoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcEmpleadoRepository implements EmpleadoRepository {

    @Override
    public Empleado guardar(Empleado empleado) {
        String sql = "INSERT INTO empleados (info_personal_id, usuario_id, modificado) " +
                     "VALUES (?, ?, CURRENT_TIMESTAMP) RETURNING id, modificado";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, empleado.getInfoPersonalId());
            if (empleado.getUsuarioId() != null) {
                stmt.setLong(2, empleado.getUsuarioId());
            } else {
                stmt.setNull(2, Types.BIGINT);
            }
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empleado.setId(rs.getLong("id"));
                empleado.setModificado(rs.getTimestamp("modificado"));
            }
            
            return empleado;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public Empleado actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET info_personal_id = ?, usuario_id = ?, " +
                     "modificado = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, empleado.getInfoPersonalId());
            if (empleado.getUsuarioId() != null) {
                stmt.setLong(2, empleado.getUsuarioId());
            } else {
                stmt.setNull(2, Types.BIGINT);
            }
            stmt.setLong(3, empleado.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Empleado con ID " + empleado.getId() + " no encontrado");
            }
            
            // Actualizar el timestamp
            empleado.setModificado(new Timestamp(System.currentTimeMillis()));
            
            return empleado;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public Empleado buscarPorId(Long id) {
        String sql = "SELECT id, info_personal_id, usuario_id, modificado FROM empleados WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearEmpleado(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Empleado> listarTodos() {
        String sql = "SELECT id, info_personal_id, usuario_id, modificado FROM empleados " +
                     "ORDER BY id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Empleado> empleados = new ArrayList<Empleado>();
            while (rs.next()) {
                empleados.add(mapearEmpleado(rs));
            }
            
            return empleados;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar empleados: " + e.getMessage(), e);
        }
    }

    @Override
    public Empleado buscarPorInfoPersonal(Long infoPersonalId) {
        String sql = "SELECT id, info_personal_id, usuario_id, modificado FROM empleados " +
                     "WHERE info_personal_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, infoPersonalId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearEmpleado(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleado por info personal: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Empleado> buscarConUsuario() {
        String sql = "SELECT id, info_personal_id, usuario_id, modificado FROM empleados " +
                     "WHERE usuario_id IS NOT NULL ORDER BY id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Empleado> empleados = new ArrayList<Empleado>();
            while (rs.next()) {
                empleados.add(mapearEmpleado(rs));
            }
            
            return empleados;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleados con usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Empleado> buscarSinUsuario() {
        String sql = "SELECT id, info_personal_id, usuario_id, modificado FROM empleados " +
                     "WHERE usuario_id IS NULL ORDER BY id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Empleado> empleados = new ArrayList<Empleado>();
            while (rs.next()) {
                empleados.add(mapearEmpleado(rs));
            }
            
            return empleados;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleados sin usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorId(Long id) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorInfoPersonal(Long infoPersonalId) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE info_personal_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, infoPersonalId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de empleado por info personal: " + e.getMessage(), e);
        }
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Long usuarioId = rs.getLong("usuario_id");
        if (rs.wasNull()) {
            usuarioId = null;
        }
        
        return new Empleado(
            rs.getLong("id"),
            rs.getLong("info_personal_id"),
            usuarioId,
            rs.getTimestamp("modificado")
        );
    }
}
