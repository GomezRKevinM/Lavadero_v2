package com.lavadero.unicartagena.edu.co.infrastructure.config;

import com.lavadero.unicartagena.edu.co.application.service.UsuarioService;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.BuscarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.CrearUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.EliminarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.ModificarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.usuario.UsuarioRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli.UsuarioController;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence.JdbcUsuarioRepository;

public class UsuarioConfig {

    private static UsuarioConfig instance;

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final UsuarioController usuarioController;

    private UsuarioConfig() {
        this.usuarioRepository = new JdbcUsuarioRepository();
        this.usuarioService = new UsuarioService(usuarioRepository);
        this.usuarioController = new UsuarioController(
            usuarioService,
            usuarioService,
            usuarioService,
            usuarioService
        );
    }

    public static synchronized UsuarioConfig getInstance() {
        if (instance == null) {
            instance = new UsuarioConfig();
        }
        return instance;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public UsuarioController getUsuarioController() {
        return usuarioController;
    }

    public CrearUsuarioUseCase getCrearUsuarioUseCase() {
        return usuarioService;
    }

    public BuscarUsuarioUseCase getBuscarUsuarioUseCase() {
        return usuarioService;
    }

    public ModificarUsuarioUseCase getModificarUsuarioUseCase() {
        return usuarioService;
    }

    public EliminarUsuarioUseCase getEliminarUsuarioUseCase() {
        return usuarioService;
    }
}
