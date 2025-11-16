package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;
import com.lavadero.unicartagena.edu.co.infrastructure.config.ClienteConfig;
import javax.swing.*;
public class ClienteGuiApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            try {
                ClienteConfig config = ClienteConfig.getInstance();
                ClienteFrame frame = new ClienteFrame(
                    config.getCrearClienteUseCase(),
                    config.getBuscarClienteUseCase(),
                    config.getModificarClienteUseCase(),
                    config.getEliminarClienteUseCase()
                );
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Error al iniciar la aplicación: " + e.getMessage(),
                    "Error Fatal",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}
