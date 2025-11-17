package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;
import com.lavadero.unicartagena.edu.co.domain.model.personas.Cliente;
import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import com.lavadero.unicartagena.edu.co.domain.model.shared.TipoIdentificacion;
import com.lavadero.unicartagena.edu.co.domain.port.out.cliente.ClienteRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class JdbcClienteRepository implements ClienteRepository {
    @Override
    public Cliente guardar(Cliente cliente) {
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);
            Long infoPersonalId = guardarInfoPersonal(conn, cliente.getInfoPersonal());
            String sqlCliente = "INSERT INTO clientes (info_personal_id, empresa_id) VALUES (?, ?) RETURNING id";
            try (PreparedStatement stmt = conn.prepareStatement(sqlCliente)) {
                stmt.setLong(1, infoPersonalId);
                stmt.setLong(2, cliente.getEmpresaId());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    cliente.setId(rs.getLong("id"));
                }
            }
            InfoPersonal infoConId = cliente.getInfoPersonal().conId(infoPersonalId);
            cliente.setInfoPersonal(infoConId);
            conn.commit();
            return cliente;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Error al revertir transacción: " + ex.getMessage(), ex);
                }
            }
            throw new RuntimeException("Error al guardar cliente: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    private Long guardarInfoPersonal(Connection conn, InfoPersonal info) throws SQLException {
        if (info.getInfoPersonalId() != null) {
            return info.getInfoPersonalId();
        }
        String sql = "INSERT INTO info_personal (tipo_identificacion_id, nombre, apellidos, " +
                    "identificacion, telefono, correo, direccion) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, info.getTipoIdentificacion().getId());
            stmt.setString(2, info.getNombre());
            stmt.setString(3, info.getApellidos());
            stmt.setString(4, info.getIdentificacion());
            stmt.setString(5, info.getTelefono());
            stmt.setString(6, info.getCorreo());
            stmt.setString(7, info.getDireccion());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
            throw new SQLException("No se pudo obtener el ID de info_personal");
        }
    }
    @Override
    public Cliente actualizar(Cliente cliente) {
        Connection conn = null;
        try {
            conn = DatabaseConfig.getConnection();
            conn.setAutoCommit(false);
            actualizarInfoPersonal(conn, cliente.getInfoPersonal());
            String sqlCliente = "UPDATE clientes SET empresa_id = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sqlCliente)) {
                stmt.setLong(1, cliente.getEmpresaId());
                stmt.setLong(2, cliente.getId());
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new RuntimeException("No se encontró el cliente con ID: " + cliente.getId());
                }
            }
            conn.commit();
            return cliente;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Error al revertir transacción: " + ex.getMessage(), ex);
                }
            }
            throw new RuntimeException("Error al actualizar cliente: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    private void actualizarInfoPersonal(Connection conn, InfoPersonal info) throws SQLException {
        if (info.getInfoPersonalId() == null) {
            throw new IllegalArgumentException("InfoPersonal debe tener un ID para actualizar");
        }
        String sql = "UPDATE info_personal SET tipo_identificacion_id = ?, nombre = ?, " +
                    "apellidos = ?, identificacion = ?, telefono = ?, correo = ?, direccion = ? " +
                    "WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, info.getTipoIdentificacion().getId());
            stmt.setString(2, info.getNombre());
            stmt.setString(3, info.getApellidos());
            stmt.setString(4, info.getIdentificacion());
            stmt.setString(5, info.getTelefono());
            stmt.setString(6, info.getCorreo());
            stmt.setString(7, info.getDireccion());
            stmt.setLong(8, info.getInfoPersonalId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se encontró info_personal con ID: " + info.getInfoPersonalId());
            }
        }
    }
    @Override
    public Cliente buscarPorId(Long id) {
        String sql = "SELECT c.id, c.empresa_id, " +
                    "ip.id AS info_id, ip.tipo_identificacion_id, ip.nombre, ip.apellidos, " +
                    "ip.identificacion, ip.telefono, ip.correo, ip.direccion " +
                    "FROM clientes c " +
                    "INNER JOIN info_personal ip ON c.info_personal_id = ip.id " +
                    "WHERE c.id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapearClienteCompleto(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente por ID: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Cliente> listarPorEmpresa(Long empresaId) {
        String sql = "SELECT c.id, c.empresa_id, " +
                    "ip.id AS info_id, ip.tipo_identificacion_id, ip.nombre, ip.apellidos, " +
                    "ip.identificacion, ip.telefono, ip.correo, ip.direccion " +
                    "FROM clientes c " +
                    "INNER JOIN info_personal ip ON c.info_personal_id = ip.id " +
                    "WHERE c.empresa_id = ? ORDER BY c.id";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, empresaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(mapearClienteCompleto(rs));
            }
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes por empresa: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Cliente> listarTodos() {
        String sql = "SELECT c.id, c.empresa_id, " +
                    "ip.id AS info_id, ip.tipo_identificacion_id, ip.nombre, ip.apellidos, " +
                    "ip.identificacion, ip.telefono, ip.correo, ip.direccion " +
                    "FROM clientes c " +
                    "INNER JOIN info_personal ip ON c.info_personal_id = ip.id " +
                    "ORDER BY c.id";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(mapearClienteCompleto(rs));
            }
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar todos los clientes: " + e.getMessage(), e);
        }
    }
    @Override
    public boolean eliminar(Long id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("23")) {
                throw new IllegalStateException(
                    "No se puede eliminar el cliente porque tiene registros relacionados", e);
            }
            throw new RuntimeException("Error al eliminar cliente: " + e.getMessage(), e);
        }
    }
    @Override
    public boolean existePorId(Long id) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de cliente: " + e.getMessage(), e);
        }
    }
    private Cliente mapearClienteCompleto(ResultSet rs) throws SQLException {
        TipoIdentificacion tipoId = new TipoIdentificacion();
        tipoId.setId(rs.getLong("tipo_identificacion_id"));
        InfoPersonal infoPersonal = new InfoPersonal(
            rs.getLong("info_id"),
            tipoId,
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("identificacion"),
            rs.getString("telefono"),
            rs.getString("correo"),
            rs.getString("direccion")
        );
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id"));
        cliente.setInfoPersonal(infoPersonal);
        cliente.setEmpresaId(rs.getLong("empresa_id"));
        return cliente;
    }
}
