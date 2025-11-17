package com.lavadero.unicartagena.edu.co.infrastructure.config;

import com.lavadero.unicartagena.edu.co.application.service.ColorService;
import com.lavadero.unicartagena.edu.co.application.service.MarcaVehiculoService;
import com.lavadero.unicartagena.edu.co.application.service.VehiculoService;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.*;
import com.lavadero.unicartagena.edu.co.domain.port.out.cliente.ClienteRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.ColorRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.MarcaVehiculoRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.VehiculoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli.VehiculoController;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence.JdbcColorRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence.JdbcMarcaVehiculoRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence.JdbcVehiculoRepository;

/**
 * Configuración de beans para el módulo de vehículos.
 * Implementa el patrón Singleton para gestionar las dependencias.
 */
public class VehiculoConfig {
    
    private static VehiculoConfig instance;
    
    private final VehiculoRepository vehiculoRepository;
    private final MarcaVehiculoRepository marcaVehiculoRepository;
    private final ColorRepository colorRepository;
    private final ClienteRepository clienteRepository;
    
    private final VehiculoService vehiculoService;
    private final MarcaVehiculoService marcaVehiculoService;
    private final ColorService colorService;
    
    private final VehiculoController vehiculoController;

    private VehiculoConfig() {
        // Inicializar repositorios
        this.vehiculoRepository = new JdbcVehiculoRepository();
        this.marcaVehiculoRepository = new JdbcMarcaVehiculoRepository();
        this.colorRepository = new JdbcColorRepository();
        this.clienteRepository = ClienteConfig.getInstance().getClienteRepository();
        
        // Inicializar servicios
        this.vehiculoService = new VehiculoService(
            vehiculoRepository,
            clienteRepository,
            marcaVehiculoRepository
        );
        this.marcaVehiculoService = new MarcaVehiculoService(marcaVehiculoRepository);
        this.colorService = new ColorService(colorRepository);
        
        // Inicializar controlador
        this.vehiculoController = new VehiculoController(
            vehiculoService,
            vehiculoService,
            vehiculoService,
            vehiculoService
        );
    }

    public static VehiculoConfig getInstance() {
        if (instance == null) {
            synchronized (VehiculoConfig.class) {
                if (instance == null) {
                    instance = new VehiculoConfig();
                }
            }
        }
        return instance;
    }

    // Getters para repositorios
    public VehiculoRepository getVehiculoRepository() {
        return vehiculoRepository;
    }

    public MarcaVehiculoRepository getMarcaVehiculoRepository() {
        return marcaVehiculoRepository;
    }

    public ColorRepository getColorRepository() {
        return colorRepository;
    }

    // Getters para servicios
    public VehiculoService getVehiculoService() {
        return vehiculoService;
    }

    public MarcaVehiculoService getMarcaVehiculoService() {
        return marcaVehiculoService;
    }

    public ColorService getColorService() {
        return colorService;
    }

    // Getters para controlador
    public VehiculoController getVehiculoController() {
        return vehiculoController;
    }

    // Métodos estáticos para casos de uso de Vehículo
    public static CrearVehiculoUseCase crearVehiculoUseCase() {
        return getInstance().getVehiculoService();
    }

    public static BuscarVehiculoUseCase buscarVehiculoUseCase() {
        return getInstance().getVehiculoService();
    }

    public static ActualizarVehiculoUseCase actualizarVehiculoUseCase() {
        return getInstance().getVehiculoService();
    }

    public static EliminarVehiculoUseCase eliminarVehiculoUseCase() {
        return getInstance().getVehiculoService();
    }

    // Métodos estáticos para casos de uso de MarcaVehiculo
    public static GestionarMarcaVehiculoUseCase gestionarMarcaVehiculoUseCase() {
        return getInstance().getMarcaVehiculoService();
    }

    // Métodos estáticos para casos de uso de Color
    public static GestionarColorUseCase gestionarColorUseCase() {
        return getInstance().getColorService();
    }
}
