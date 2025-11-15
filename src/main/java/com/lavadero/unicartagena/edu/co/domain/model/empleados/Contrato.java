package com.lavadero.unicartagena.edu.co.domain.model.empleados;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Entidad Contrato - Contratos laborales de empleados.
 * Tabla: contratos
 */
public class Contrato {
    private Long id;
    private Date fechaInicio;
    private Date fechaVencimiento;
    private BigDecimal salarioBase;
    private Integer cargoId;
    private Long empleadoId;

    // Constructores
    public Contrato() {
    }

    public Contrato(Long id, Date fechaInicio, Date fechaVencimiento,
                   BigDecimal salarioBase, Integer cargoId, Long empleadoId) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.salarioBase = salarioBase;
        this.cargoId = cargoId;
        this.empleadoId = empleadoId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaVencimiento=" + fechaVencimiento +
                ", salarioBase=" + salarioBase +
                ", cargoId=" + cargoId +
                ", empleadoId=" + empleadoId +
                '}';
    }
}
