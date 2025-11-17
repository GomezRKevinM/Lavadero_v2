package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;

import com.lavadero.unicartagena.edu.co.infrastructure.config.EmpleadoConfig;

public class EmpleadoApp {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE GESTIÓN DE EMPLEADOS      ║");
        System.out.println("║  Lavadero ABC Ltda.                    ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        try {
            EmpleadoController controller = EmpleadoConfig.getEmpleadoController();
            controller.iniciar();
        } catch (Exception e) {
            System.err.println("\nError fatal al iniciar la aplicación:");
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
