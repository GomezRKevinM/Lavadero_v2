package com.lavadero.unicartagena.edu.co.application.service;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.ActualizarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.BuscarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.CrearVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.EliminarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.cliente.ClienteRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.MarcaVehiculoRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.VehiculoRepository;

import java.util.List;

/**
 * Servicio de aplicación para gestión de vehículos.
 * Implementa los casos de uso de vehículos.
 */
public class VehiculoService implements CrearVehiculoUseCase, ActualizarVehiculoUseCase,
        BuscarVehiculoUseCase, EliminarVehiculoUseCase {

    private final VehiculoRepository vehiculoRepository;
    private final ClienteRepository clienteRepository;
    private final MarcaVehiculoRepository marcaVehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository,
                          ClienteRepository clienteRepository,
                          MarcaVehiculoRepository marcaVehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.clienteRepository = clienteRepository;
        this.marcaVehiculoRepository = marcaVehiculoRepository;
    }

    @Override
    public Vehiculo crear(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo");
        }

        // Normalizar y validar datos del vehículo
        vehiculo.normalizarPlaca();
        vehiculo.validar();

        // Validar que el cliente existe (regla de negocio crítica)
        if (!clienteRepository.existePorId(vehiculo.getClienteId())) {
            throw new RuntimeException("El cliente con ID " + vehiculo.getClienteId() + " no existe");
        }

        // Validar que la marca existe
        if (marcaVehiculoRepository.buscarPorId(vehiculo.getMarcaId()) == null) {
            throw new RuntimeException("La marca con ID " + vehiculo.getMarcaId() + " no existe");
        }

        // Verificar que no existe otro vehículo con la misma placa
        if (vehiculoRepository.existePorPlaca(vehiculo.getPlaca())) {
            throw new IllegalArgumentException("Ya existe un vehículo con la placa " + vehiculo.getPlaca());
        }

        return vehiculoRepository.guardar(vehiculo);
    }

    @Override
    public Vehiculo actualizar(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo");
        }

        if (vehiculo.getId() == null) {
            throw new IllegalArgumentException("El ID del vehículo es obligatorio para actualizar");
        }

        // Verificar que el vehículo existe
        Vehiculo existente = vehiculoRepository.buscarPorId(vehiculo.getId());
        if (existente == null) {
            throw new RuntimeException("El vehículo con ID " + vehiculo.getId() + " no existe");
        }

        // Normalizar y validar datos del vehículo
        vehiculo.normalizarPlaca();
        vehiculo.validar();

        // Validar que el cliente existe
        if (!clienteRepository.existePorId(vehiculo.getClienteId())) {
            throw new RuntimeException("El cliente con ID " + vehiculo.getClienteId() + " no existe");
        }

        // Validar que la marca existe
        if (marcaVehiculoRepository.buscarPorId(vehiculo.getMarcaId()) == null) {
            throw new RuntimeException("La marca con ID " + vehiculo.getMarcaId() + " no existe");
        }

        // Verificar que no existe otro vehículo con la misma placa (excluyendo el actual)
        if (vehiculoRepository.existePorPlacaExcluyendoId(vehiculo.getPlaca(), vehiculo.getId())) {
            throw new IllegalArgumentException("Ya existe otro vehículo con la placa " + vehiculo.getPlaca());
        }

        return vehiculoRepository.guardar(vehiculo);
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del vehículo es obligatorio");
        }
        return vehiculoRepository.eliminar(id);
    }

    @Override
    public Vehiculo buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del vehículo es obligatorio");
        }
        return vehiculoRepository.buscarPorId(id);
    }

    @Override
    public Vehiculo buscarPorPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa es obligatoria");
        }
        return vehiculoRepository.buscarPorPlaca(placa.trim().toUpperCase());
    }

    @Override
    public List<Vehiculo> buscarPorCliente(Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente es obligatorio");
        }
        return vehiculoRepository.buscarPorClienteId(clienteId);
    }

    @Override
    public List<Vehiculo> buscarPorMarca(Integer marcaId) {
        if (marcaId == null) {
            throw new IllegalArgumentException("El ID de la marca es obligatorio");
        }
        return vehiculoRepository.buscarPorMarcaId(marcaId);
    }

    @Override
    public List<Vehiculo> listarTodos() {
        return vehiculoRepository.listarTodos();
    }
}
