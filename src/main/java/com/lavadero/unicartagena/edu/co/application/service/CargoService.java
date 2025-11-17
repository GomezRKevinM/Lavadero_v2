package com.lavadero.unicartagena.edu.co.application.service;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Cargo;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.GestionarCargoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.CargoRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.ContratoRepository;

import java.util.List;

public class CargoService implements GestionarCargoUseCase {
    
    private final CargoRepository cargoRepository;
    private final ContratoRepository contratoRepository;
    
    public CargoService(CargoRepository cargoRepository, ContratoRepository contratoRepository) {
        this.cargoRepository = cargoRepository;
        this.contratoRepository = contratoRepository;
    }
    
    @Override
    public Cargo crear(Cargo cargo) {
        if (cargo.getNombre() == null || cargo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cargo es obligatorio");
        }
        
        String nombreNormalizado = cargo.getNombre().trim();
        cargo.setNombre(nombreNormalizado);
        
        if (cargoRepository.existePorNombre(nombreNormalizado)) {
            throw new IllegalStateException(
                "Ya existe un cargo con el nombre '" + nombreNormalizado + "'");
        }
        
        return cargoRepository.guardar(cargo);
    }
    
    @Override
    public Cargo actualizar(Cargo cargo) {
        if (cargo.getId() == null) {
            throw new IllegalArgumentException("El cargo debe tener un ID para actualizar");
        }
        
        if (!cargoRepository.existePorId(cargo.getId())) {
            throw new IllegalStateException("El cargo con ID " + cargo.getId() + " no existe");
        }
        
        if (cargo.getNombre() == null || cargo.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cargo es obligatorio");
        }
        
        String nombreNormalizado = cargo.getNombre().trim();
        cargo.setNombre(nombreNormalizado);
        
        if (cargoRepository.existePorNombreExcluyendoId(nombreNormalizado, cargo.getId())) {
            throw new IllegalStateException(
                "Ya existe otro cargo con el nombre '" + nombreNormalizado + "'");
        }
        
        return cargoRepository.actualizar(cargo);
    }
    
    @Override
    public boolean eliminar(Integer id) {
        if (!cargoRepository.existePorId(id)) {
            throw new IllegalStateException("El cargo con ID " + id + " no existe");
        }
        
        int contratosAsociados = contratoRepository.contarPorCargo(id);
        if (contratosAsociados > 0) {
            throw new IllegalStateException(
                "No se puede eliminar el cargo porque tiene " + contratosAsociados + 
                " contrato(s) asociado(s). Primero debe reasignar o eliminar los contratos.");
        }
        
        return cargoRepository.eliminar(id);
    }
    
    @Override
    public Cargo buscarPorId(Integer id) {
        return cargoRepository.buscarPorId(id);
    }
    
    @Override
    public List<Cargo> listarTodos() {
        return cargoRepository.listarTodos();
    }
    
    @Override
    public List<Cargo> buscarPorNombre(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return listarTodos();
        }
        return cargoRepository.buscarPorNombre(termino);
    }
}
