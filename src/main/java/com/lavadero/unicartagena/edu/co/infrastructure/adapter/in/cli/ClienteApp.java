package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;
import com.lavadero.unicartagena.edu.co.infrastructure.config.ClienteConfig;
public class ClienteApp {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  SISTEMA DE GESTIÓN - LAVADERO ABC    ");
        System.out.println("     Módulo: Gestión de Clientes       ");
        System.out.println("========================================");
        System.out.println();
        try {
            ClienteConfig config = ClienteConfig.getInstance();
            ClienteController controller = config.getClienteController();
            controller.iniciar();
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
