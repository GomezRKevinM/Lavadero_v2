package com.lavadero.unicartagena.edu.co.domain.model.compras;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entidad Pedido - Pedidos realizados a proveedores.
 * Tabla: pedido
 */
public class Pedido {
    private Long id;
    private String codigo;
    private Long estadoId;
    private Timestamp emitido;
    private Timestamp fechaEntrega;
    private BigDecimal valorPagado;
    private BigDecimal iva;
    private Long cotizacionId;
    private Long proveedorId;

    // Constructores
    public Pedido() {
    }

    public Pedido(Long id, String codigo, Long estadoId, Timestamp emitido,
                 Timestamp fechaEntrega, BigDecimal valorPagado, BigDecimal iva,
                 Long cotizacionId, Long proveedorId) {
        this.id = id;
        this.codigo = codigo;
        this.estadoId = estadoId;
        this.emitido = emitido;
        this.fechaEntrega = fechaEntrega;
        this.valorPagado = valorPagado;
        this.iva = iva;
        this.cotizacionId = cotizacionId;
        this.proveedorId = proveedorId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public Timestamp getEmitido() {
        return emitido;
    }

    public void setEmitido(Timestamp emitido) {
        this.emitido = emitido;
    }

    public Timestamp getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Timestamp fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public BigDecimal getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(BigDecimal valorPagado) {
        this.valorPagado = valorPagado;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Long getCotizacionId() {
        return cotizacionId;
    }

    public void setCotizacionId(Long cotizacionId) {
        this.cotizacionId = cotizacionId;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", estadoId=" + estadoId +
                ", emitido=" + emitido +
                ", fechaEntrega=" + fechaEntrega +
                ", valorPagado=" + valorPagado +
                ", iva=" + iva +
                ", cotizacionId=" + cotizacionId +
                ", proveedorId=" + proveedorId +
                '}';
    }
}
