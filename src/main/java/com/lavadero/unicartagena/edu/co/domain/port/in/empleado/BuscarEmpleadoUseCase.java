package com.lavadero.unicartagena.edu.co.domain.port.in.empleado;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.EmpleadoCompleto;
import java.util.List;

public interface BuscarEmpleadoUseCase {
    
    EmpleadoCompleto buscarPorId(Long id);
    
    List<EmpleadoCompleto> listarTodos();
    
    List<EmpleadoCompleto> buscarPorNombre(String termino);
    
    EmpleadoCompleto buscarPorIdentificacion(String identificacion);
    
    List<EmpleadoCompleto> buscarConUsuario();
    
    List<EmpleadoCompleto> buscarSinUsuario();
}
