package com.lavadero.unicartagena.edu.co.domain.port.out.empleado;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Cargo;
import java.util.List;

public interface CargoRepository {
    
    Cargo guardar(Cargo cargo);
    
    Cargo actualizar(Cargo cargo);
    
    boolean eliminar(Integer id);
    
    Cargo buscarPorId(Integer id);
    
    List<Cargo> listarTodos();
    
    List<Cargo> buscarPorNombre(String termino);
    
    boolean existePorId(Integer id);
    
    boolean existePorNombre(String nombre);
    
    boolean existePorNombreExcluyendoId(String nombre, Integer idExcluir);
}
