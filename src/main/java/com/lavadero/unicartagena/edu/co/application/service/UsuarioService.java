package com.lavadero.unicartagena.edu.co.application.service;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Usuario;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.BuscarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.CrearUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.EliminarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.ModificarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.usuario.UsuarioRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class UsuarioService implements CrearUsuarioUseCase, BuscarUsuarioUseCase,
        ModificarUsuarioUseCase, EliminarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario crear(Usuario usuario) {
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        if (usuario.getUsername().length() < 4) {
            throw new IllegalArgumentException("El username debe tener al menos 4 caracteres");
        }

        if (usuario.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }

        if (usuarioRepository.existeUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El username ya existe");
        }

        usuario.setPassword(encriptarPassword(usuario.getPassword()));

        if (usuario.getAnswerSecurity() != null && !usuario.getAnswerSecurity().isEmpty()) {
            usuario.setAnswerSecurity(encriptarPassword(usuario.getAnswerSecurity()));
        }

        return usuarioRepository.guardar(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return usuarioRepository.buscarPorId(id);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username no puede ser vacío");
        }
        return usuarioRepository.buscarPorUsername(username);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    @Override
    public List<Usuario> listarPorEmpresa(Long empresaId) {
        if (empresaId == null) {
            throw new IllegalArgumentException("El ID de empresa no puede ser nulo");
        }
        return usuarioRepository.listarPorEmpresa(empresaId);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario debe tener un ID para actualizar");
        }

        Usuario existente = usuarioRepository.buscarPorId(usuario.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró el usuario con ID: " + usuario.getId());
        }

        if (usuario.getUsername() != null && !usuario.getUsername().equals(existente.getUsername())) {
            if (usuarioRepository.existeUsername(usuario.getUsername())) {
                throw new IllegalArgumentException("El username ya existe");
            }
        }

        return usuarioRepository.actualizar(usuario);
    }

    @Override
    public void cambiarPassword(Long id, String passwordActual, String passwordNueva) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        if (passwordNueva == null || passwordNueva.length() < 6) {
            throw new IllegalArgumentException("La nueva contraseña debe tener al menos 6 caracteres");
        }

        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró el usuario");
        }

        String passwordActualEncriptada = encriptarPassword(passwordActual);
        if (!usuario.getPassword().equals(passwordActualEncriptada)) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        usuario.setPassword(encriptarPassword(passwordNueva));
        usuarioRepository.actualizar(usuario);
    }

    @Override
    public void eliminar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró el usuario con ID: " + id);
        }

        usuarioRepository.eliminar(id);
    }

    private String encriptarPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }
}
