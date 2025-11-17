package com.lavadero.unicartagena.edu.co.application.service;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Contrato;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.GestionarContratoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.ContratoRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.EmpleadoRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.CargoRepository;

import java.util.ArrayList;
import java.util.List;

public class ContratoService implements GestionarContratoUseCase {
    
    private final ContratoRepository contratoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final CargoRepository cargoRepository;
    
    public ContratoService(
            ContratoRepository contratoRepository,
            EmpleadoRepository empleadoRepository,
            CargoRepository cargoRepository) {
        this.contratoRepository = contratoRepository;
        this.empleadoRepository = empleadoRepository;
        this.cargoRepository = cargoRepository;
    }
    
    @Override
    public Contrato crear(Contrato contrato) {
        contrato.validar();
        
        if (!empleadoRepository.existePorId(contrato.getEmpleadoId())) {
            throw new IllegalStateException(
                "El empleado con ID " + contrato.getEmpleadoId() + " no existe");
        }
        
        // Verificar que el cargo existe
        if (!cargoRepository.existePorId(contrato.getCargoId())) {
            throw new IllegalStateException(
                "El cargo con ID " + contrato.getCargoId() + " no existe");
        }
        
        return contratoRepository.guardar(contrato);
    }
    
    @Override
    public Contrato actualizar(Contrato contrato) {
        if (contrato.getId() == null) {
            throw new IllegalArgumentException("El contrato debe tener un ID para actualizar");
        }
        
        if (!contratoRepository.existePorId(contrato.getId())) {
            throw new IllegalStateException("El contrato con ID " + contrato.getId() + " no existe");
        }
        
        contrato.validar();
        
        if (!empleadoRepository.existePorId(contrato.getEmpleadoId())) {
            throw new IllegalStateException(
                "El empleado con ID " + contrato.getEmpleadoId() + " no existe");
        }
        
        // Verificar que el cargo existe
        if (!cargoRepository.existePorId(contrato.getCargoId())) {
            throw new IllegalStateException(
                "El cargo con ID " + contrato.getCargoId() + " no existe");
        }
        
        return contratoRepository.actualizar(contrato);
    }
    
    @Override
    public boolean eliminar(Long id) {
        if (!contratoRepository.existePorId(id)) {
            throw new IllegalStateException("El contrato con ID " + id + " no existe");
        }
        
        Contrato contrato = contratoRepository.buscarPorId(id);
        
        if (contrato.estaVigente()) {
            System.out.println("ADVERTENCIA: Se está eliminando un contrato vigente (ID: " + id + ")");
        }
        
        return contratoRepository.eliminar(id);
    }
    
    @Override
    public Contrato buscarPorId(Long id) {
        return contratoRepository.buscarPorId(id);
    }
    
    @Override
    public List<Contrato> buscarPorEmpleado(Long empleadoId) {
        return contratoRepository.buscarPorEmpleado(empleadoId);
    }
    
    @Override
    public Contrato buscarContratoVigente(Long empleadoId) {
        List<Contrato> contratos = contratoRepository.buscarPorEmpleado(empleadoId);
        
        for (Contrato contrato : contratos) {
            if (contrato.estaVigente()) {
                return contrato;
            }
        }
        
        return null;
    }
    
    @Override
    public List<Contrato> listarVigentes() {
        // Necesitamos buscar todos los empleados y sus contratos
        // Como no tenemos un método directo, usamos la lógica en memoria
        // En una implementación real, esto debería ser una consulta SQL optimizada
        
        List<Contrato> todosContratos = new ArrayList<Contrato>();
        List<Contrato> vigentes = new ArrayList<Contrato>();
        
        // Esta es una implementación simplificada
        // En producción, se debería hacer una consulta directa en la base de datos
        // con filtro de fechas
        
        for (Contrato contrato : todosContratos) {
            if (contrato.estaVigente()) {
                vigentes.add(contrato);
            }
        }
        
        return vigentes;
    }
    
    @Override
    public List<Contrato> listarProximosAVencer() {
        // Similar a listarVigentes, en producción debería ser una consulta SQL
        List<Contrato> todosContratos = new ArrayList<Contrato>();
        List<Contrato> proximosAVencer = new ArrayList<Contrato>();
        
        for (Contrato contrato : todosContratos) {
            if (contrato.proximoAVencer()) {
                proximosAVencer.add(contrato);
            }
        }
        
        return proximosAVencer;
    }
    
    @Override
    public List<Contrato> buscarPorCargo(Integer cargoId) {
        return contratoRepository.buscarPorCargo(cargoId);
    }
}
