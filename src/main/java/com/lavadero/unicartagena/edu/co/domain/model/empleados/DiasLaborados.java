package com.lavadero.unicartagena.edu.co.domain.model.empleados;

import com.lavadero.unicartagena.edu.co.shared.constant.Dia;

/**
 * Entidad DiasLaborados - Días de la semana que trabaja un empleado según su horario.
 * Tabla: dias_laborados
 */
public class DiasLaborados {
    private Long id;
    private Long horarioId;
    private Dia dia;

    // Constructores
    public DiasLaborados() {
    }

    public DiasLaborados(Long id, Long horarioId, Dia dia) {
        this.id = id;
        this.horarioId = horarioId;
        this.dia = dia;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Long horarioId) {
        this.horarioId = horarioId;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "DiasLaborados{" +
                "id=" + id +
                ", horarioId=" + horarioId +
                ", dia=" + dia +
                '}';
    }
}
