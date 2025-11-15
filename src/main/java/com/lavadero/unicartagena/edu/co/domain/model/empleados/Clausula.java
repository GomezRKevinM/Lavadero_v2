package com.lavadero.unicartagena.edu.co.domain.model.empleados;

/**
 * Entidad Clausula - Cláusulas de los contratos.
 * Tabla: clausula
 */
public class Clausula {
    private Long id;
    private String descripcion;
    private Long contratoId;

    // Constructores
    public Clausula() {
    }

    public Clausula(Long id, String descripcion, Long contratoId) {
        this.id = id;
        this.descripcion = descripcion;
        this.contratoId = contratoId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    @Override
    public String toString() {
        return "Clausula{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", contratoId=" + contratoId +
                '}';
    }
}
