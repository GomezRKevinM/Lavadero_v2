package com.lavadero.unicartagena.edu.co.domain.port.out.empleado;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Empleado;
import java.util.List;

public interface EmpleadoRepository {
    
    Empleado guardar(Empleado empleado);
    
    Empleado actualizar(Empleado empleado);
    
    boolean eliminar(Long id);
    
    Empleado buscarPorId(Long id);
    
    List<Empleado> listarTodos();
    
    Empleado buscarPorInfoPersonal(Long infoPersonalId);
    
    List<Empleado> buscarConUsuario();
    
    List<Empleado> buscarSinUsuario();
    
    boolean existePorId(Long id);
    
    boolean existePorInfoPersonal(Long infoPersonalId);
}
