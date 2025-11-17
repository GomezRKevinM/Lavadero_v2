package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;

import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.ActualizarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.BuscarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.CrearVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.EliminarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.infrastructure.config.VehiculoConfig;

/**
 * Aplicación principal para gestión de vehículos.
 */
public class VehiculoApp {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    SISTEMA DE GESTIÓN DE VEHÍCULOS    ");
        System.out.println("       Lavadero ABC Ltda.              ");
        System.out.println("========================================\n");
        
        try {
            // Obtener los casos de uso desde la configuración
            CrearVehiculoUseCase crearVehiculoUseCase = VehiculoConfig.crearVehiculoUseCase();
            BuscarVehiculoUseCase buscarVehiculoUseCase = VehiculoConfig.buscarVehiculoUseCase();
            ActualizarVehiculoUseCase actualizarVehiculoUseCase = VehiculoConfig.actualizarVehiculoUseCase();
            EliminarVehiculoUseCase eliminarVehiculoUseCase = VehiculoConfig.eliminarVehiculoUseCase();
            
            // Crear e iniciar el controlador
            VehiculoController controller = new VehiculoController(
                crearVehiculoUseCase,
                buscarVehiculoUseCase,
                actualizarVehiculoUseCase,
                eliminarVehiculoUseCase
            );
            
            controller.iniciar();
            
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
