package com.lavadero.unicartagena.edu.co.domain.model.compras;

import java.math.BigDecimal;

/**
 * Entidad NotaCorreccionDetalles - Detalles de corrección de productos en pedidos.
 * Tabla: nota_correccion_detalles
 */
public class NotaCorreccionDetalles {
    private Long id;
    private Long notaCorreccionId;
    private Long pedidoDetalleId;
    private Integer cantidadRecibida;
    private BigDecimal precioUnitarioRecibido;
    private BigDecimal ivaRecibido;

    // Constructores
    public NotaCorreccionDetalles() {
    }

    public NotaCorreccionDetalles(Long id, Long notaCorreccionId, Long pedidoDetalleId,
                                 Integer cantidadRecibida, BigDecimal precioUnitarioRecibido,
                                 BigDecimal ivaRecibido) {
        this.id = id;
        this.notaCorreccionId = notaCorreccionId;
        this.pedidoDetalleId = pedidoDetalleId;
        this.cantidadRecibida = cantidadRecibida;
        this.precioUnitarioRecibido = precioUnitarioRecibido;
        this.ivaRecibido = ivaRecibido;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotaCorreccionId() {
        return notaCorreccionId;
    }

    public void setNotaCorreccionId(Long notaCorreccionId) {
        this.notaCorreccionId = notaCorreccionId;
    }

    public Long getPedidoDetalleId() {
        return pedidoDetalleId;
    }

    public void setPedidoDetalleId(Long pedidoDetalleId) {
        this.pedidoDetalleId = pedidoDetalleId;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public BigDecimal getPrecioUnitarioRecibido() {
        return precioUnitarioRecibido;
    }

    public void setPrecioUnitarioRecibido(BigDecimal precioUnitarioRecibido) {
        this.precioUnitarioRecibido = precioUnitarioRecibido;
    }

    public BigDecimal getIvaRecibido() {
        return ivaRecibido;
    }

    public void setIvaRecibido(BigDecimal ivaRecibido) {
        this.ivaRecibido = ivaRecibido;
    }

    @Override
    public String toString() {
        return "NotaCorreccionDetalles{" +
                "id=" + id +
                ", notaCorreccionId=" + notaCorreccionId +
                ", pedidoDetalleId=" + pedidoDetalleId +
                ", cantidadRecibida=" + cantidadRecibida +
                ", precioUnitarioRecibido=" + precioUnitarioRecibido +
                ", ivaRecibido=" + ivaRecibido +
                '}';
    }
}
