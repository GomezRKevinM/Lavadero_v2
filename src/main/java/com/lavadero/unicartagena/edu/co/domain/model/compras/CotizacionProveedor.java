package com.lavadero.unicartagena.edu.co.domain.model.compras;

/**
 * Entidad CotizacionProveedor - Relación entre cotizaciones y proveedores.
 * Tabla: cotizacion_proveedor
 */
public class CotizacionProveedor {
    private Long id;
    private Long cotizacionId;
    private Long proveedorId;
    private Long estadoId;

    // Constructores
    public CotizacionProveedor() {
    }

    public CotizacionProveedor(Long id, Long cotizacionId, Long proveedorId, Long estadoId) {
        this.id = id;
        this.cotizacionId = cotizacionId;
        this.proveedorId = proveedorId;
        this.estadoId = estadoId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public String toString() {
        return "CotizacionProveedor{" +
                "id=" + id +
                ", cotizacionId=" + cotizacionId +
                ", proveedorId=" + proveedorId +
                ", estadoId=" + estadoId +
                '}';
    }
}
