package com.lavadero.unicartagena.edu.co.domain.port.out.usuario;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Usuario;
import java.util.List;

public interface UsuarioRepository {
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Usuario usuario);
    void eliminar(Long id);
    Usuario buscarPorId(Long id);
    Usuario buscarPorUsername(String username);
    List<Usuario> listarTodos();
    List<Usuario> listarPorEmpresa(Long empresaId);
    boolean existeUsername(String username);
}
