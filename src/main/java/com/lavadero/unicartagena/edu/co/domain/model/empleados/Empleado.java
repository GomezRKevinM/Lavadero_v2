package com.lavadero.unicartagena.edu.co.domain.model.empleados;

import java.sql.Timestamp;

public class Empleado {
    private Long id;
    private Long infoPersonalId;
    private Long usuarioId;
    private Timestamp modificado;

    public Empleado() {
    }

    public Empleado(Long id, Long infoPersonalId, Long usuarioId, Timestamp modificado) {
        this.id = id;
        this.infoPersonalId = infoPersonalId;
        this.usuarioId = usuarioId;
        this.modificado = modificado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInfoPersonalId() {
        return infoPersonalId;
    }

    public void setInfoPersonalId(Long infoPersonalId) {
        this.infoPersonalId = infoPersonalId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Timestamp getModificado() {
        return modificado;
    }

    public void setModificado(Timestamp modificado) {
        this.modificado = modificado;
    }

    public void validar() {
        if (infoPersonalId == null) {
            throw new IllegalArgumentException("El empleado debe tener información personal asociada");
        }
    }

    public boolean tieneUsuario() {
        return usuarioId != null;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", infoPersonalId=" + infoPersonalId +
                ", usuarioId=" + usuarioId +
                ", modificado=" + modificado +
                '}';
    }
}
