package com.lavadero.unicartagena.edu.co.domain.model.empleados;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

public class Contrato {
    private Long id;
    private Date fechaInicio;
    private Date fechaVencimiento;
    private BigDecimal salarioBase;
    private Integer cargoId;
    private Long empleadoId;

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

    public void validar() {
        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio es obligatoria");
        }
        
        if (salarioBase == null || salarioBase.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El salario base debe ser un valor positivo");
        }
        
        if (cargoId == null) {
            throw new IllegalArgumentException("El contrato debe tener un cargo asociado");
        }
        
        if (empleadoId == null) {
            throw new IllegalArgumentException("El contrato debe estar asociado a un empleado");
        }
        
        if (fechaVencimiento != null && fechaVencimiento.before(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de vencimiento debe ser posterior a la fecha de inicio");
        }
    }

    public boolean esIndefinido() {
        return fechaVencimiento == null;
    }

    public boolean estaVigente() {
        Date hoy = new Date(System.currentTimeMillis());
        
        if (fechaInicio.after(hoy)) {
            return false;
        }
        
        if (fechaVencimiento == null) {
            return true;
        }
        
        return !fechaVencimiento.before(hoy);
    }

    public boolean proximoAVencer() {
        if (fechaVencimiento == null) {
            return false;
        }
        
        Date hoy = new Date(System.currentTimeMillis());
        
        if (fechaVencimiento.before(hoy)) {
            return false;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoy);
        cal.add(Calendar.DAY_OF_MONTH, 30);
        Date dentroDeUnMes = new Date(cal.getTimeInMillis());
        
        return fechaVencimiento.before(dentroDeUnMes) || fechaVencimiento.equals(dentroDeUnMes);
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
