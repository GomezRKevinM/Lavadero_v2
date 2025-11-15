package com.lavadero.unicartagena.edu.co.domain.model.compras;

import java.sql.Timestamp;

/**
 * Entidad Cotizacion - Cotizaciones de compra a proveedores.
 * Tabla: cotizacion
 */
public class Cotizacion {
    private Long id;
    private Timestamp emitido;
    private Timestamp expira;
    private Long empleadoRealiza;
    private Long estadoId;

    // Constructores
    public Cotizacion() {
    }

    public Cotizacion(Long id, Timestamp emitido, Timestamp expira,
                     Long empleadoRealiza, Long estadoId) {
        this.id = id;
        this.emitido = emitido;
        this.expira = expira;
        this.empleadoRealiza = empleadoRealiza;
        this.estadoId = estadoId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getEmitido() {
        return emitido;
    }

    public void setEmitido(Timestamp emitido) {
        this.emitido = emitido;
    }

    public Timestamp getExpira() {
        return expira;
    }

    public void setExpira(Timestamp expira) {
        this.expira = expira;
    }

    public Long getEmpleadoRealiza() {
        return empleadoRealiza;
    }

    public void setEmpleadoRealiza(Long empleadoRealiza) {
        this.empleadoRealiza = empleadoRealiza;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public String toString() {
        return "Cotizacion{" +
                "id=" + id +
                ", emitido=" + emitido +
                ", expira=" + expira +
                ", empleadoRealiza=" + empleadoRealiza +
                ", estadoId=" + estadoId +
                '}';
    }
}
