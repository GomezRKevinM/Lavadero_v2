package com.lavadero.unicartagena.edu.co.domain.model.compras;

import java.sql.Date;

/**
 * Entidad CotizacionCheck - Verificación de cotizaciones por empleados.
 * Tabla: cotizacion_check
 */
public class CotizacionCheck {
    private Long id;
    private Long cotizacionProveedorId;
    private Long empleadoId;
    private Date fechaRevision;

    // Constructores
    public CotizacionCheck() {
    }

    public CotizacionCheck(Long id, Long cotizacionProveedorId, Long empleadoId, Date fechaRevision) {
        this.id = id;
        this.cotizacionProveedorId = cotizacionProveedorId;
        this.empleadoId = empleadoId;
        this.fechaRevision = fechaRevision;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCotizacionProveedorId() {
        return cotizacionProveedorId;
    }

    public void setCotizacionProveedorId(Long cotizacionProveedorId) {
        this.cotizacionProveedorId = cotizacionProveedorId;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    @Override
    public String toString() {
        return "CotizacionCheck{" +
                "id=" + id +
                ", cotizacionProveedorId=" + cotizacionProveedorId +
                ", empleadoId=" + empleadoId +
                ", fechaRevision=" + fechaRevision +
                '}';
    }
}
