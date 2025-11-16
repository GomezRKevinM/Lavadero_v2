package com.lavadero.unicartagena.edu.co.domain.port.in.usuario;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Usuario;

public interface ModificarUsuarioUseCase {
    Usuario actualizar(Usuario usuario);
    void cambiarPassword(Long id, String passwordActual, String passwordNueva);
}
