package com.lavadero.unicartagena.edu.co.domain.port.in.empleado;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Contrato;
import java.util.List;

public interface GestionarContratoUseCase {
    
    Contrato crear(Contrato contrato);
    
    Contrato actualizar(Contrato contrato);
    
    boolean eliminar(Long id);
    
    Contrato buscarPorId(Long id);
    
    List<Contrato> buscarPorEmpleado(Long empleadoId);
    
    Contrato buscarContratoVigente(Long empleadoId);
    
    List<Contrato> listarVigentes();
    
    List<Contrato> listarProximosAVencer();
    
    List<Contrato> buscarPorCargo(Integer cargoId);
}
