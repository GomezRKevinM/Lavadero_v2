package com.lavadero.unicartagena.edu.co.application.service;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.MarcaVehiculo;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.GestionarMarcaVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.MarcaVehiculoRepository;

import java.util.List;

/**
 * Servicio de aplicación para gestión de marcas de vehículos.
 * Implementa los casos de uso de marcas.
 */
public class MarcaVehiculoService implements GestionarMarcaVehiculoUseCase {

    private final MarcaVehiculoRepository marcaVehiculoRepository;

    public MarcaVehiculoService(MarcaVehiculoRepository marcaVehiculoRepository) {
        this.marcaVehiculoRepository = marcaVehiculoRepository;
    }

    @Override
    public MarcaVehiculo crear(MarcaVehiculo marca) {
        if (marca == null) {
            throw new IllegalArgumentException("La marca no puede ser nula");
        }

        if (marca.getMarca() == null || marca.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca es obligatorio");
        }

        // Normalizar nombre
        marca.setMarca(marca.getMarca().trim());

        // Verificar que no existe otra marca con el mismo nombre
        if (marcaVehiculoRepository.existePorNombre(marca.getMarca())) {
            throw new IllegalArgumentException("Ya existe una marca con el nombre " + marca.getMarca());
        }

        return marcaVehiculoRepository.guardar(marca);
    }

    @Override
    public MarcaVehiculo buscarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la marca es obligatorio");
        }
        return marcaVehiculoRepository.buscarPorId(id);
    }

    @Override
    public MarcaVehiculo buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca es obligatorio");
        }
        return marcaVehiculoRepository.buscarPorNombre(nombre.trim());
    }

    @Override
    public List<MarcaVehiculo> listarTodas() {
        return marcaVehiculoRepository.listarTodas();
    }

    @Override
    public MarcaVehiculo actualizar(MarcaVehiculo marca) {
        if (marca == null) {
            throw new IllegalArgumentException("La marca no puede ser nula");
        }

        if (marca.getId() == null) {
            throw new IllegalArgumentException("El ID de la marca es obligatorio para actualizar");
        }

        if (marca.getMarca() == null || marca.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca es obligatorio");
        }

        // Verificar que la marca existe
        MarcaVehiculo existente = marcaVehiculoRepository.buscarPorId(marca.getId());
        if (existente == null) {
            throw new RuntimeException("La marca con ID " + marca.getId() + " no existe");
        }

        // Normalizar nombre
        marca.setMarca(marca.getMarca().trim());

        // Verificar que no existe otra marca con el mismo nombre (permitir si es la misma)
        MarcaVehiculo porNombre = marcaVehiculoRepository.buscarPorNombre(marca.getMarca());
        if (porNombre != null && !porNombre.getId().equals(marca.getId())) {
            throw new IllegalArgumentException("Ya existe otra marca con el nombre " + marca.getMarca());
        }

        return marcaVehiculoRepository.guardar(marca);
    }

    @Override
    public boolean eliminar(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la marca es obligatorio");
        }
        return marcaVehiculoRepository.eliminar(id);
    }
}
