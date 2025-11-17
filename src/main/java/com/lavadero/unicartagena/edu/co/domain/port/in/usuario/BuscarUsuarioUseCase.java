package com.lavadero.unicartagena.edu.co.domain.port.in.usuario;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Usuario;
import java.util.List;

public interface BuscarUsuarioUseCase {
    Usuario buscarPorId(Long id);
    Usuario buscarPorUsername(String username);
    List<Usuario> listarTodos();
    List<Usuario> listarPorEmpresa(Long empresaId);
}
