package com.lavadero.unicartagena.edu.co.infrastructure.config;

import com.lavadero.unicartagena.edu.co.application.service.EmpleadoService;
import com.lavadero.unicartagena.edu.co.application.service.CargoService;
import com.lavadero.unicartagena.edu.co.application.service.ContratoService;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.*;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.*;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli.EmpleadoController;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence.*;

public class EmpleadoConfig {
    
    private static EmpleadoConfig instance;
    
    private final EmpleadoRepository empleadoRepository;
    private final InfoPersonalRepository infoPersonalRepository;
    private final CargoRepository cargoRepository;
    private final ContratoRepository contratoRepository;
    
    private final EmpleadoService empleadoService;
    private final CargoService cargoService;
    private final ContratoService contratoService;
    
    private final EmpleadoController empleadoController;
    
    private EmpleadoConfig() {
        this.empleadoRepository = new JdbcEmpleadoRepository();
        this.infoPersonalRepository = new JdbcInfoPersonalRepository();
        this.cargoRepository = new JdbcCargoRepository();
        this.contratoRepository = new JdbcContratoRepository();
        
        this.empleadoService = new EmpleadoService(
            empleadoRepository,
            infoPersonalRepository,
            contratoRepository
        );
        
        this.cargoService = new CargoService(
            cargoRepository,
            contratoRepository
        );
        
        this.contratoService = new ContratoService(
            contratoRepository,
            empleadoRepository,
            cargoRepository
        );
        
        this.empleadoController = new EmpleadoController(
            empleadoService,
            empleadoService,
            empleadoService,
            empleadoService
        );
    }
    
    public static synchronized EmpleadoConfig getInstance() {
        if (instance == null) {
            instance = new EmpleadoConfig();
        }
        return instance;
    }
    
    public static EmpleadoRepository getEmpleadoRepository() {
        return getInstance().empleadoRepository;
    }
    
    public static InfoPersonalRepository getInfoPersonalRepository() {
        return getInstance().infoPersonalRepository;
    }
    
    public static CargoRepository getCargoRepository() {
        return getInstance().cargoRepository;
    }
    
    public static ContratoRepository getContratoRepository() {
        return getInstance().contratoRepository;
    }
    
    public static CrearEmpleadoUseCase getCrearEmpleadoUseCase() {
        return getInstance().empleadoService;
    }
    
    public static BuscarEmpleadoUseCase getBuscarEmpleadoUseCase() {
        return getInstance().empleadoService;
    }
    
    public static ActualizarEmpleadoUseCase getActualizarEmpleadoUseCase() {
        return getInstance().empleadoService;
    }
    
    public static EliminarEmpleadoUseCase getEliminarEmpleadoUseCase() {
        return getInstance().empleadoService;
    }
    
    public static GestionarCargoUseCase getGestionarCargoUseCase() {
        return getInstance().cargoService;
    }
    
    public static GestionarContratoUseCase getGestionarContratoUseCase() {
        return getInstance().contratoService;
    }
    
    public static EmpleadoController getEmpleadoController() {
        return getInstance().empleadoController;
    }
}
