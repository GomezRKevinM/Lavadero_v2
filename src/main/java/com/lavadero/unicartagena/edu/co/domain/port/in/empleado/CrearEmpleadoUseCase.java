package com.lavadero.unicartagena.edu.co.domain.port.in.empleado;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Empleado;

public interface CrearEmpleadoUseCase {
    
    Empleado crear(Empleado empleado);
}
