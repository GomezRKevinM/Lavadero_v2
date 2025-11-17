package com.lavadero.unicartagena.edu.co.domain.port.out.empleado;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Contrato;
import java.util.List;

public interface ContratoRepository {
    
    Contrato guardar(Contrato contrato);
    
    Contrato actualizar(Contrato contrato);
    
    boolean eliminar(Long id);
    
    Contrato buscarPorId(Long id);
    
    List<Contrato> buscarPorEmpleado(Long empleadoId);
    
    List<Contrato> buscarPorCargo(Integer cargoId);
    
    boolean existePorId(Long id);
    
    boolean tieneContratosVigentes(Long empleadoId);
    
    int contarPorCargo(Integer cargoId);
}
