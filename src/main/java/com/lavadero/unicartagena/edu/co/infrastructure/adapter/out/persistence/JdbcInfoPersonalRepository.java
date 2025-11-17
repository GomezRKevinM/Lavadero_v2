package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import com.lavadero.unicartagena.edu.co.domain.model.shared.TipoIdentificacion;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.InfoPersonalRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcInfoPersonalRepository implements InfoPersonalRepository {

    @Override
    public InfoPersonal guardar(InfoPersonal infoPersonal) {
        String sql = "INSERT INTO info_personal (tipo_identificacion_id, nombre, apellidos, " +
                     "identificacion, telefono, correo, direccion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING info_personal_id";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, infoPersonal.getTipoIdentificacion().getId());
            stmt.setString(2, infoPersonal.getNombre());
            stmt.setString(3, infoPersonal.getApellidos());
            stmt.setString(4, infoPersonal.getIdentificacion());
            stmt.setString(5, infoPersonal.getTelefono());
            stmt.setString(6, infoPersonal.getCorreo());
            stmt.setString(7, infoPersonal.getDireccion());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("info_personal_id");
                return infoPersonal.conId(id);
            }
            
            return infoPersonal;
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar información personal: " + e.getMessage(), e);
        }
    }

    @Override
    public InfoPersonal actualizar(InfoPersonal infoPersonal) {
        String sql = "UPDATE info_personal SET tipo_identificacion_id = ?, nombre = ?, " +
                     "apellidos = ?, identificacion = ?, telefono = ?, correo = ?, direccion = ? " +
                     "WHERE info_personal_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, infoPersonal.getTipoIdentificacion().getId());
            stmt.setString(2, infoPersonal.getNombre());
            stmt.setString(3, infoPersonal.getApellidos());
            stmt.setString(4, infoPersonal.getIdentificacion());
            stmt.setString(5, infoPersonal.getTelefono());
            stmt.setString(6, infoPersonal.getCorreo());
            stmt.setString(7, infoPersonal.getDireccion());
            stmt.setLong(8, infoPersonal.getInfoPersonalId());
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Información personal con ID " + infoPersonal.getInfoPersonalId() + " no encontrada");
            }
            
            return infoPersonal;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar información personal: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        String sql = "DELETE FROM info_personal WHERE info_personal_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar información personal: " + e.getMessage(), e);
        }
    }

    @Override
    public InfoPersonal buscarPorId(Long id) {
        String sql = "SELECT ip.info_personal_id, ip.tipo_identificacion_id, ti.tipo, " +
                     "ip.nombre, ip.apellidos, ip.identificacion, ip.telefono, ip.correo, ip.direccion " +
                     "FROM info_personal ip " +
                     "JOIN tipo_identificacion ti ON ip.tipo_identificacion_id = ti.id " +
                     "WHERE ip.info_personal_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearInfoPersonal(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar información personal: " + e.getMessage(), e);
        }
    }

    @Override
    public InfoPersonal buscarPorIdentificacion(String identificacion) {
        String sql = "SELECT ip.info_personal_id, ip.tipo_identificacion_id, ti.tipo, " +
                     "ip.nombre, ip.apellidos, ip.identificacion, ip.telefono, ip.correo, ip.direccion " +
                     "FROM info_personal ip " +
                     "JOIN tipo_identificacion ti ON ip.tipo_identificacion_id = ti.id " +
                     "WHERE ip.identificacion = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, identificacion);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearInfoPersonal(rs);
            }
            
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar información personal por identificación: " + e.getMessage(), e);
        }
    }

    @Override
    public List<InfoPersonal> buscarPorNombre(String termino) {
        String sql = "SELECT ip.info_personal_id, ip.tipo_identificacion_id, ti.tipo, " +
                     "ip.nombre, ip.apellidos, ip.identificacion, ip.telefono, ip.correo, ip.direccion " +
                     "FROM info_personal ip " +
                     "JOIN tipo_identificacion ti ON ip.tipo_identificacion_id = ti.id " +
                     "WHERE LOWER(ip.nombre) LIKE LOWER(?) OR LOWER(ip.apellidos) LIKE LOWER(?) " +
                     "ORDER BY ip.nombre, ip.apellidos";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String patron = "%" + termino + "%";
            stmt.setString(1, patron);
            stmt.setString(2, patron);
            ResultSet rs = stmt.executeQuery();
            
            List<InfoPersonal> resultados = new ArrayList<InfoPersonal>();
            while (rs.next()) {
                resultados.add(mapearInfoPersonal(rs));
            }
            
            return resultados;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar información personal por nombre: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorId(Long id) {
        String sql = "SELECT COUNT(*) FROM info_personal WHERE info_personal_id = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de información personal: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorIdentificacion(String identificacion) {
        String sql = "SELECT COUNT(*) FROM info_personal WHERE identificacion = ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, identificacion);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de información personal por identificación: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existePorIdentificacionExcluyendoId(String identificacion, Long idExcluir) {
        String sql = "SELECT COUNT(*) FROM info_personal WHERE identificacion = ? AND info_personal_id != ?";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, identificacion);
            stmt.setLong(2, idExcluir);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de información personal: " + e.getMessage(), e);
        }
    }

    private InfoPersonal mapearInfoPersonal(ResultSet rs) throws SQLException {
        TipoIdentificacion tipoIdentificacion = new TipoIdentificacion(
            rs.getLong("tipo_identificacion_id"),
            rs.getString("tipo")
        );
        
        return new InfoPersonal(
            rs.getLong("info_personal_id"),
            tipoIdentificacion,
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("identificacion"),
            rs.getString("telefono"),
            rs.getString("correo"),
            rs.getString("direccion")
        );
    }
}
