package com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Usuario;
import com.lavadero.unicartagena.edu.co.domain.port.out.usuario.UsuarioRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.config.DatabaseConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUsuarioRepository implements UsuarioRepository {

    @Override
    public Usuario guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (username, password, question_security, answer_security, empresa_id) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING id, creado, modificado";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getQuestionSecurity());
            stmt.setString(4, usuario.getAnswerSecurity());
            stmt.setLong(5, usuario.getEmpresaId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario.setId(rs.getLong("id"));
                usuario.setCreado(rs.getDate("creado"));
                usuario.setModificado(rs.getTimestamp("modificado"));
            }

            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET username = ?, password = ?, question_security = ?, " +
                    "answer_security = ?, empresa_id = ?, modificado = CURRENT_TIMESTAMP " +
                    "WHERE id = ? RETURNING modificado";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getQuestionSecurity());
            stmt.setString(4, usuario.getAnswerSecurity());
            stmt.setLong(5, usuario.getEmpresaId());
            stmt.setLong(6, usuario.getId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario.setModificado(rs.getTimestamp("modificado"));
            } else {
                throw new RuntimeException("No se encontró el usuario con ID: " + usuario.getId());
            }

            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("No se encontró el usuario con ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarPorId(Long id) {
        String sql = "SELECT id, username, password, question_security, answer_security, " +
                    "empresa_id, creado, modificado FROM usuarios WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT id, username, password, question_security, answer_security, " +
                    "empresa_id, creado, modificado FROM usuarios WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por username: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = "SELECT id, username, password, question_security, answer_security, " +
                    "empresa_id, creado, modificado FROM usuarios ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> listarPorEmpresa(Long empresaId) {
        String sql = "SELECT id, username, password, question_security, answer_security, " +
                    "empresa_id, creado, modificado FROM usuarios WHERE empresa_id = ? ORDER BY id";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, empresaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            return usuarios;

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios por empresa: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existeUsername(String username) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar username: " + e.getMessage(), e);
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setQuestionSecurity(rs.getString("question_security"));
        usuario.setAnswerSecurity(rs.getString("answer_security"));
        usuario.setEmpresaId(rs.getLong("empresa_id"));
        usuario.setCreado(rs.getDate("creado"));
        usuario.setModificado(rs.getTimestamp("modificado"));
        return usuario;
    }
}
