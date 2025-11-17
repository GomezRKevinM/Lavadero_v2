package com.lavadero.unicartagena.edu.co.application.service;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Empleado;
import com.lavadero.unicartagena.edu.co.domain.model.empleados.EmpleadoCompleto;
import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.*;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.EmpleadoRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.InfoPersonalRepository;
import com.lavadero.unicartagena.edu.co.domain.port.out.empleado.ContratoRepository;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoService implements 
    CrearEmpleadoUseCase,
    ActualizarEmpleadoUseCase,
    BuscarEmpleadoUseCase,
    EliminarEmpleadoUseCase {
    
    private final EmpleadoRepository empleadoRepository;
    private final InfoPersonalRepository infoPersonalRepository;
    private final ContratoRepository contratoRepository;
    
    public EmpleadoService(
            EmpleadoRepository empleadoRepository,
            InfoPersonalRepository infoPersonalRepository,
            ContratoRepository contratoRepository) {
        this.empleadoRepository = empleadoRepository;
        this.infoPersonalRepository = infoPersonalRepository;
        this.contratoRepository = contratoRepository;
    }
    
    @Override
    public Empleado crear(Empleado empleado) {
        empleado.validar();
        
        if (!infoPersonalRepository.existePorId(empleado.getInfoPersonalId())) {
            throw new IllegalStateException(
                "La información personal con ID " + empleado.getInfoPersonalId() + " no existe");
        }
        
        if (empleadoRepository.existePorInfoPersonal(empleado.getInfoPersonalId())) {
            throw new IllegalStateException(
                "Ya existe un empleado con la información personal ID " + empleado.getInfoPersonalId());
        }
        
        return empleadoRepository.guardar(empleado);
    }
    
    @Override
    public Empleado actualizar(Empleado empleado) {
        if (empleado.getId() == null) {
            throw new IllegalArgumentException("El empleado debe tener un ID para actualizar");
        }
        
        if (!empleadoRepository.existePorId(empleado.getId())) {
            throw new IllegalStateException("El empleado con ID " + empleado.getId() + " no existe");
        }
        
        empleado.validar();
        
        if (!infoPersonalRepository.existePorId(empleado.getInfoPersonalId())) {
            throw new IllegalStateException(
                "La información personal con ID " + empleado.getInfoPersonalId() + " no existe");
        }
        
        return empleadoRepository.actualizar(empleado);
    }
    
    @Override
    public EmpleadoCompleto buscarPorId(Long id) {
        Empleado empleado = empleadoRepository.buscarPorId(id);
        if (empleado == null) {
            return null;
        }
        
        InfoPersonal infoPersonal = infoPersonalRepository.buscarPorId(empleado.getInfoPersonalId());
        if (infoPersonal == null) {
            throw new IllegalStateException(
                "Inconsistencia de datos: empleado ID " + id + 
                " tiene infoPersonalId inválido: " + empleado.getInfoPersonalId());
        }
        
        return new EmpleadoCompleto(empleado, infoPersonal);
    }
    
    @Override
    public List<EmpleadoCompleto> listarTodos() {
        List<Empleado> empleados = empleadoRepository.listarTodos();
        List<EmpleadoCompleto> empleadosCompletos = new ArrayList<EmpleadoCompleto>();
        
        for (Empleado empleado : empleados) {
            InfoPersonal infoPersonal = infoPersonalRepository.buscarPorId(empleado.getInfoPersonalId());
            if (infoPersonal != null) {
                empleadosCompletos.add(new EmpleadoCompleto(empleado, infoPersonal));
            }
        }
        
        return empleadosCompletos;
    }
    
    @Override
    public List<EmpleadoCompleto> buscarPorNombre(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return listarTodos();
        }
        
        // Buscar información personal por nombre
        List<InfoPersonal> infoPersonales = infoPersonalRepository.buscarPorNombre(termino);
        List<EmpleadoCompleto> empleadosCompletos = new ArrayList<EmpleadoCompleto>();
        
        for (InfoPersonal infoPersonal : infoPersonales) {
            Empleado empleado = empleadoRepository.buscarPorInfoPersonal(infoPersonal.getInfoPersonalId());
            if (empleado != null) {
                empleadosCompletos.add(new EmpleadoCompleto(empleado, infoPersonal));
            }
        }
        
        return empleadosCompletos;
    }
    
    @Override
    public EmpleadoCompleto buscarPorIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación no puede estar vacía");
        }
        
        InfoPersonal infoPersonal = infoPersonalRepository.buscarPorIdentificacion(identificacion);
        if (infoPersonal == null) {
            return null;
        }
        
        Empleado empleado = empleadoRepository.buscarPorInfoPersonal(infoPersonal.getInfoPersonalId());
        if (empleado == null) {
            return null;
        }
        
        return new EmpleadoCompleto(empleado, infoPersonal);
    }
    
    @Override
    public List<EmpleadoCompleto> buscarConUsuario() {
        List<Empleado> empleados = empleadoRepository.buscarConUsuario();
        List<EmpleadoCompleto> empleadosCompletos = new ArrayList<EmpleadoCompleto>();
        
        for (Empleado empleado : empleados) {
            InfoPersonal infoPersonal = infoPersonalRepository.buscarPorId(empleado.getInfoPersonalId());
            if (infoPersonal != null) {
                empleadosCompletos.add(new EmpleadoCompleto(empleado, infoPersonal));
            }
        }
        
        return empleadosCompletos;
    }
    
    @Override
    public List<EmpleadoCompleto> buscarSinUsuario() {
        List<Empleado> empleados = empleadoRepository.buscarSinUsuario();
        List<EmpleadoCompleto> empleadosCompletos = new ArrayList<EmpleadoCompleto>();
        
        for (Empleado empleado : empleados) {
            InfoPersonal infoPersonal = infoPersonalRepository.buscarPorId(empleado.getInfoPersonalId());
            if (infoPersonal != null) {
                empleadosCompletos.add(new EmpleadoCompleto(empleado, infoPersonal));
            }
        }
        
        return empleadosCompletos;
    }
    
    @Override
    public boolean eliminar(Long id) {
        if (!empleadoRepository.existePorId(id)) {
            throw new IllegalStateException("El empleado con ID " + id + " no existe");
        }
        
        if (contratoRepository.tieneContratosVigentes(id)) {
            throw new IllegalStateException(
                "No se puede eliminar el empleado porque tiene contratos vigentes. " +
                "Primero debe finalizar o eliminar los contratos activos.");
        }
        
        return empleadoRepository.eliminar(id);
    }
}
