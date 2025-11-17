package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Contrato;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.ContratoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JdbcContratoRepository implements ContratoRepository {

    @Override
    public Contrato guardar(Contrato contrato) {
        String sql = "INSERT INTO contratos (fecha_inicio, fecha_vencimiento, salario_base, cargo_id, empleado_id) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, contrato.getFechaInicio());
            if (contrato.getFechaVencimiento() != null) {
                stmt.setDate(2, contrato.getFechaVencimiento());
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setBigDecimal(3, contrato.getSalarioBase());
            stmt.setInt(4, contrato.getCargoId());
            stmt.setLong(5, contrato.getEmpleadoId());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contrato.setId(rs.getLong("id"));
            }
            
            return contrato;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar contrato: " + e.getMessage(), e);
        }
    }

    @Override
    public Contrato actualizar(Contrato contrato) {
        String sql = "UPDATE contratos SET fecha_inicio = ?, fecha_vencimiento = ?, " +
                     "salario_base = ?, cargo_id = ?, empleado_id = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, contrato.getFechaInicio());
            if (contrato.getFechaVencimiento() != null) {
                stmt.setDate(2, contrato.getFechaVencimiento());
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setBigDecimal(3, contrato.getSalarioBase());
            stmt.setInt(4, contrato.getCargoId());
            stmt.setLong(5, contrato.getEmpleadoId());
            stmt.setLong(6, contrato.getId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Contrato con ID " + contrato.getId() + " no encontrado");
            }
            
            return contrato;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar contrato: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        String sql = "DELETE FROM contratos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar contrato: " + e.getMessage(), e);
        }
    }

    @Override
    public Contrato buscarPorId(Long id) {
        String sql = "SELECT id, fecha_inicio, fecha_vencimiento, salario_base, cargo_id, empleado_id " +
                     "FROM contratos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearContrato(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar contrato: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Contrato> buscarPorEmpleado(Long empleadoId) {
        String sql = "SELECT id, fecha_inicio, fecha_vencimiento, salario_base, cargo_id, empleado_id " +
                     "FROM contratos WHERE empleado_id = ? ORDER BY fecha_inicio DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, empleadoId);
            ResultSet rs = stmt.executeQuery();
            
            List<Contrato> contratos = new ArrayList<Contrato>();
            while (rs.next()) {
                contratos.add(mapearContrato(rs));
            }
            
            return contratos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar contratos por empleado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Contrato> buscarPorCargo(Integer cargoId) {
        String sql = "SELECT id, fecha_inicio, fecha_vencimiento, salario_base, cargo_id, empleado_id " +
                     "FROM contratos WHERE cargo_id = ? ORDER BY fecha_inicio DESC";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cargoId);
            ResultSet rs = stmt.executeQuery();
            
            List<Contrato> contratos = new ArrayList<Contrato>();
            while (rs.next()) {
                contratos.add(mapearContrato(rs));
            }
            
            return contratos;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar contratos por cargo: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorId(Long id) {
        String sql = "SELECT COUNT(*) FROM contratos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de contrato: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean tieneContratosVigentes(Long empleadoId) {
        String sql = "SELECT COUNT(*) FROM contratos " +
                     "WHERE empleado_id = ? " +
                     "AND fecha_inicio <= CURRENT_DATE " +
                     "AND (fecha_vencimiento IS NULL OR fecha_vencimiento >= CURRENT_DATE)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, empleadoId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar contratos vigentes: " + e.getMessage(), e);
        }
    }

    @Override
    public int contarPorCargo(Integer cargoId) {
        String sql = "SELECT COUNT(*) FROM contratos WHERE cargo_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, cargoId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al contar contratos por cargo: " + e.getMessage(), e);
        }
    }

    private Contrato mapearContrato(ResultSet rs) throws SQLException {
        Date fechaVencimiento = rs.getDate("fecha_vencimiento");
        if (rs.wasNull()) {
            fechaVencimiento = null;
        }
        
        return new Contrato(
            rs.getLong("id"),
            rs.getDate("fecha_inicio"),
            fechaVencimiento,
            rs.getBigDecimal("salario_base"),
            rs.getInt("cargo_id"),
            rs.getLong("empleado_id")
        );
    }
}
