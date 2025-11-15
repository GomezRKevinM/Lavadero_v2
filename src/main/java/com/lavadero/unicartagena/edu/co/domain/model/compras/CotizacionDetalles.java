package com.lavadero.unicartagena.edu.co.domain.model.compras;

import java.math.BigDecimal;

/**
 * Entidad CotizacionDetalles - Detalles de productos en cotizaciones.
 * Tabla: cotizacion_detalles
 */
public class CotizacionDetalles {
    private Long id;
    private Long productoId;
    private Long cotizacionId;
    private Integer cantidad;
    private BigDecimal precioCompra;
    private BigDecimal precioUnitario;
    private BigDecimal precioTotal;
    private BigDecimal descuento;
    private BigDecimal iva;

    // Constructores
    public CotizacionDetalles() {
    }

    public CotizacionDetalles(Long id, Long productoId, Long cotizacionId, Integer cantidad,
                             BigDecimal precioCompra, BigDecimal precioUnitario,
                             BigDecimal precioTotal, BigDecimal descuento, BigDecimal iva) {
        this.id = id;
        this.productoId = productoId;
        this.cotizacionId = cotizacionId;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
        this.descuento = descuento;
        this.iva = iva;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getCotizacionId() {
        return cotizacionId;
    }

    public void setCotizacionId(Long cotizacionId) {
        this.cotizacionId = cotizacionId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    @Override
    public String toString() {
        return "CotizacionDetalles{" +
                "id=" + id +
                ", productoId=" + productoId +
                ", cotizacionId=" + cotizacionId +
                ", cantidad=" + cantidad +
                ", precioCompra=" + precioCompra +
                ", precioUnitario=" + precioUnitario +
                ", precioTotal=" + precioTotal +
                ", descuento=" + descuento +
                ", iva=" + iva +
                '}';
    }
}
