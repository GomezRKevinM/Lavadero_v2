package com.lavadero.unicartagena.edu.co.domain.model.operaciones;

import java.sql.Timestamp;

/**
 * Entidad ColaCliente - Clientes en cola de espera.
 * Tabla: cola_cliente
 */
public class ColaCliente {
    private Integer colaId;
    private Long clienteId;
    private Long estadoId;
    private Timestamp fechaIngreso;
    private Integer turno;

    // Constructores
    public ColaCliente() {
    }

    public ColaCliente(Integer colaId, Long clienteId, Long estadoId,
                      Timestamp fechaIngreso, Integer turno) {
        this.colaId = colaId;
        this.clienteId = clienteId;
        this.estadoId = estadoId;
        this.fechaIngreso = fechaIngreso;
        this.turno = turno;
    }

    // Getters y Setters
    public Integer getColaId() {
        return colaId;
    }

    public void setColaId(Integer colaId) {
        this.colaId = colaId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    @Override
    public String toString() {
        return "ColaCliente{" +
                "colaId=" + colaId +
                ", clienteId=" + clienteId +
                ", estadoId=" + estadoId +
                ", fechaIngreso=" + fechaIngreso +
                ", turno=" + turno +
                '}';
    }
}
