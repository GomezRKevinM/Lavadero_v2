package com.lavadero.unicartagena.edu.co.domain.model.personas;

/**
 * Entidad Proveedor - Proveedores de productos.
 * Tabla: proveedores
 */
public class Proveedor {
    private Long id;
    private Long idCompanyInfo;

    // Constructores
    public Proveedor() {
    }

    public Proveedor(Long id, Long idCompanyInfo) {
        this.id = id;
        this.idCompanyInfo = idCompanyInfo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCompanyInfo() {
        return idCompanyInfo;
    }

    public void setIdCompanyInfo(Long idCompanyInfo) {
        this.idCompanyInfo = idCompanyInfo;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "id=" + id +
                ", idCompanyInfo=" + idCompanyInfo +
                '}';
    }
}
