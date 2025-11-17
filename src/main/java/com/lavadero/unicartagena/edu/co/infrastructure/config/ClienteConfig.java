package com.lavadero.unicartagena.edu.co.infrastructure.config;
import com.lavadero.unicartagena.edu.co.application.service.ClienteService;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.BuscarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.CrearClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.EliminarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.ModificarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.cliente.ClienteRepository;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli.ClienteController;
import com.lavadero.unicartagena.edu.co.infrastructure.adapter.out.persistence.JdbcClienteRepository;
public class ClienteConfig {
    private static ClienteConfig instance;
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;
    private final ClienteController clienteController;
    private ClienteConfig() {
        this.clienteRepository = new JdbcClienteRepository();
        this.clienteService = new ClienteService(clienteRepository);
        this.clienteController = new ClienteController(
            clienteService,
            clienteService,
            clienteService,
            clienteService
        );
    }
    public static ClienteConfig getInstance() {
        if (instance == null) {
            synchronized (ClienteConfig.class) {
                if (instance == null) {
                    instance = new ClienteConfig();
                }
            }
        }
        return instance;
    }
    public ClienteRepository getClienteRepository() {
        return clienteRepository;
    }
    public ClienteService getClienteService() {
        return clienteService;
    }
    public ClienteController getClienteController() {
        return clienteController;
    }
    public CrearClienteUseCase getCrearClienteUseCase() {
        return clienteService;
    }
    public BuscarClienteUseCase getBuscarClienteUseCase() {
        return clienteService;
    }
    public ModificarClienteUseCase getModificarClienteUseCase() {
        return clienteService;
    }
    public EliminarClienteUseCase getEliminarClienteUseCase() {
        return clienteService;
    }
}
