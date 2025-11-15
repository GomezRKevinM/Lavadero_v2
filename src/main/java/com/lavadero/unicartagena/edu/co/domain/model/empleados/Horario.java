package com.lavadero.unicartagena.edu.co.domain.model.empleados;

import java.sql.Time;

/**
 * Entidad Horario - Horarios laborales de contratos.
 * Tabla: horarios
 */
public class Horario {
    private Long id;
    private Long contratoId;
    private Time horaInicio;
    private Time horaReceso;
    private Time horaReanudacion;
    private Time horaFinal;

    // Constructores
    public Horario() {
    }

    public Horario(Long id, Long contratoId, Time horaInicio, Time horaReceso,
                  Time horaReanudacion, Time horaFinal) {
        this.id = id;
        this.contratoId = contratoId;
        this.horaInicio = horaInicio;
        this.horaReceso = horaReceso;
        this.horaReanudacion = horaReanudacion;
        this.horaFinal = horaFinal;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraReceso() {
        return horaReceso;
    }

    public void setHoraReceso(Time horaReceso) {
        this.horaReceso = horaReceso;
    }

    public Time getHoraReanudacion() {
        return horaReanudacion;
    }

    public void setHoraReanudacion(Time horaReanudacion) {
        this.horaReanudacion = horaReanudacion;
    }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "id=" + id +
                ", contratoId=" + contratoId +
                ", horaInicio=" + horaInicio +
                ", horaReceso=" + horaReceso +
                ", horaReanudacion=" + horaReanudacion +
                ", horaFinal=" + horaFinal +
                '}';
    }
}
