package com.lavadero.unicartagena.edu.co.domain.model.compras;

import java.sql.Timestamp;

/**
 * Entidad NotaCorreccion - Notas de corrección de pedidos con errores.
 * Tabla: nota_correcion
 */
public class NotaCorreccion {
    private Long id;
    private Long idPedido;
    private String descripcion;
    private Long empleadoId;
    private Timestamp fecha;
    private Timestamp fechaMaximaRespuesta;
    private Long estadoId;

    // Constructores
    public NotaCorreccion() {
    }

    public NotaCorreccion(Long id, Long idPedido, String descripcion, Long empleadoId,
                         Timestamp fecha, Timestamp fechaMaximaRespuesta, Long estadoId) {
        this.id = id;
        this.idPedido = idPedido;
        this.descripcion = descripcion;
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.fechaMaximaRespuesta = fechaMaximaRespuesta;
        this.estadoId = estadoId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public Timestamp getFechaMaximaRespuesta() {
        return fechaMaximaRespuesta;
    }

    public void setFechaMaximaRespuesta(Timestamp fechaMaximaRespuesta) {
        this.fechaMaximaRespuesta = fechaMaximaRespuesta;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public String toString() {
        return "NotaCorreccion{" +
                "id=" + id +
                ", idPedido=" + idPedido +
                ", descripcion='" + descripcion + '\'' +
                ", empleadoId=" + empleadoId +
                ", fecha=" + fecha +
                ", fechaMaximaRespuesta=" + fechaMaximaRespuesta +
                ", estadoId=" + estadoId +
                '}';
    }
}
