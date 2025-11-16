package com.lavadero.unicartagena.edu.co.application.service;
import com.lavadero.unicartagena.edu.co.domain.model.personas.Cliente;
import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.BuscarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.CrearClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.EliminarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.ModificarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.cliente.ClienteRepository;
import java.util.List;
public class ClienteService implements CrearClienteUseCase, BuscarClienteUseCase,
                                       ModificarClienteUseCase, EliminarClienteUseCase {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    @Override
    public Cliente crear(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (cliente.getInfoPersonal() == null) {
            throw new IllegalArgumentException("El cliente debe tener información personal asociada");
        }
        InfoPersonal info = cliente.getInfoPersonal();
        if (info.getNombre() == null || info.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (info.getApellidos() == null || info.getApellidos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }
        if (info.getIdentificacion() == null || info.getIdentificacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        if (info.getTipoIdentificacion() == null) {
            throw new IllegalArgumentException("El tipo de identificación es obligatorio");
        }
        if (cliente.getEmpresaId() == null) {
            throw new IllegalArgumentException("El cliente debe estar asociado a una empresa");
        }
        if (cliente.getId() != null) {
            throw new IllegalArgumentException("No se puede especificar ID al crear un cliente nuevo");
        }
        return clienteRepository.guardar(cliente);
    }
    @Override
    public Cliente buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de cliente inválido");
        }
        return clienteRepository.buscarPorId(id);
    }
    @Override
    public List<Cliente> listarPorEmpresa(Long empresaId) {
        if (empresaId == null || empresaId <= 0) {
            throw new IllegalArgumentException("ID de empresa inválido");
        }
        return clienteRepository.listarPorEmpresa(empresaId);
    }
    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }
    @Override
    public Cliente actualizar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        if (cliente.getId() == null || cliente.getId() <= 0) {
            throw new IllegalArgumentException("Debe especificar un ID válido para actualizar");
        }
        if (!clienteRepository.existePorId(cliente.getId())) {
            throw new IllegalArgumentException("El cliente con ID " + cliente.getId() + " no existe");
        }
        if (cliente.getInfoPersonal() == null) {
            throw new IllegalArgumentException("El cliente debe tener información personal asociada");
        }
        InfoPersonal info = cliente.getInfoPersonal();
        if (info.getNombre() == null || info.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (info.getApellidos() == null || info.getApellidos().trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos son obligatorios");
        }
        if (info.getIdentificacion() == null || info.getIdentificacion().trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria");
        }
        if (cliente.getEmpresaId() == null) {
            throw new IllegalArgumentException("El cliente debe estar asociado a una empresa");
        }
        return clienteRepository.actualizar(cliente);
    }
    @Override
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de cliente inválido");
        }
        return clienteRepository.eliminar(id);
    }
}
