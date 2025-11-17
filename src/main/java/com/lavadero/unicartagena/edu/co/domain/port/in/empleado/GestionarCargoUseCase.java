package com.lavadero.unicartagena.edu.co.domain.port.in.empleado;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Cargo;
import java.util.List;

public interface GestionarCargoUseCase {
    
    Cargo crear(Cargo cargo);
    
    Cargo actualizar(Cargo cargo);
    
    boolean eliminar(Integer id);
    
    Cargo buscarPorId(Integer id);
    
    List<Cargo> listarTodos();
    
    List<Cargo> buscarPorNombre(String termino);
}
