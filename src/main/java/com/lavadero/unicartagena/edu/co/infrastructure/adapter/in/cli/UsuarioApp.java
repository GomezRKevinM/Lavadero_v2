package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;

import com.lavadero.unicartagena.edu.co.infrastructure.config.UsuarioConfig;

public class UsuarioApp {
    public static void main(String[] args) {
        UsuarioConfig config = UsuarioConfig.getInstance();
        UsuarioController controller = config.getUsuarioController();
        controller.mostrarMenu();
    }
}
