package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.infrastructure.config.UsuarioConfig;

import javax.swing.*;

public class UsuarioGuiApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            UsuarioConfig config = UsuarioConfig.getInstance();
            UsuarioFrame frame = new UsuarioFrame(
                config.getCrearUsuarioUseCase(),
                config.getBuscarUsuarioUseCase(),
                config.getModificarUsuarioUseCase(),
                config.getEliminarUsuarioUseCase()
            );
            frame.setVisible(true);
        });
    }
}
