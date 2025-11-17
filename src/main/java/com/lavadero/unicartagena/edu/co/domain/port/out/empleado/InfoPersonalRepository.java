package com.lavadero.unicartagena.edu.co.domain.port.out.empleado;

import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import java.util.List;

public interface InfoPersonalRepository {
    
    InfoPersonal guardar(InfoPersonal infoPersonal);
    
    InfoPersonal actualizar(InfoPersonal infoPersonal);
    
    boolean eliminar(Long id);
    
    InfoPersonal buscarPorId(Long id);
    
    InfoPersonal buscarPorIdentificacion(String identificacion);
    
    List<InfoPersonal> buscarPorNombre(String termino);
    
    boolean existePorId(Long id);
    
    boolean existePorIdentificacion(String identificacion);
    
    boolean existePorIdentificacionExcluyendoId(String identificacion, Long idExcluir);
}
