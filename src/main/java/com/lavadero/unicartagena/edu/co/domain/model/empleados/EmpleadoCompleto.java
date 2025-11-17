package com.lavadero.unicartagena.edu.co.domain.model.empleados;

import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import java.sql.Timestamp;

public class EmpleadoCompleto {
    private final Empleado empleado;
    private final InfoPersonal infoPersonal;

    public EmpleadoCompleto(Empleado empleado, InfoPersonal infoPersonal) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser null");
        }
        if (infoPersonal == null) {
            throw new IllegalArgumentException("La información personal no puede ser null");
        }
        this.empleado = empleado;
        this.infoPersonal = infoPersonal;
    }

    public Long getId() {
        return empleado.getId();
    }

    public Long getInfoPersonalId() {
        return empleado.getInfoPersonalId();
    }

    public Long getUsuarioId() {
        return empleado.getUsuarioId();
    }

    public Timestamp getModificado() {
        return empleado.getModificado();
    }

    public boolean tieneUsuario() {
        return empleado.tieneUsuario();
    }

    public String getNombre() {
        return infoPersonal.getNombre();
    }

    public String getApellidos() {
        return infoPersonal.getApellidos();
    }

    public String getNombreCompleto() {
        return infoPersonal.getNombreCompleto();
    }

    public String getIdentificacion() {
        return infoPersonal.getIdentificacion();
    }

    public String getTelefono() {
        return infoPersonal.getTelefono();
    }

    public String getCorreo() {
        return infoPersonal.getCorreo();
    }

    public String getDireccion() {
        return infoPersonal.getDireccion();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public InfoPersonal getInfoPersonal() {
        return infoPersonal;
    }

    @Override
    public String toString() {
        return "EmpleadoCompleto{" +
                "id=" + getId() +
                ", nombreCompleto='" + getNombreCompleto() + '\'' +
                ", identificacion='" + getIdentificacion() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", tieneUsuario=" + tieneUsuario() +
                '}';
    }
}
