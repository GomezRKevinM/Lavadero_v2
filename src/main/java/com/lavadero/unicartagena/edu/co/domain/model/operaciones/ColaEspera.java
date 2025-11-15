package com.lavadero.unicartagena.edu.co.domain.model.operaciones;

/**
 * Entidad ColaEspera - Colas de espera por área de trabajo.
 * Tabla: cola_espera
 */
public class ColaEspera {
    private Integer id;
    private Long areaTrabajoId;

    // Constructores
    public ColaEspera() {
    }

    public ColaEspera(Integer id, Long areaTrabajoId) {
        this.id = id;
        this.areaTrabajoId = areaTrabajoId;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAreaTrabajoId() {
        return areaTrabajoId;
    }

    public void setAreaTrabajoId(Long areaTrabajoId) {
        this.areaTrabajoId = areaTrabajoId;
    }

    @Override
    public String toString() {
        return "ColaEspera{" +
                "id=" + id +
                ", areaTrabajoId=" + areaTrabajoId +
                '}';
    }
}
