package com.lavadero.unicartagena.edu.co.domain.model.shared;

/**
 * Entidad Empresa del grupo empresarial ABC Ltda.
 * Tabla: empresas
 */
public class Empresa {
    private Long id;
    private Long idCompanyInfo;

    // Constructores
    public Empresa() {
    }

    public Empresa(Long id, Long idCompanyInfo) {
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
        return "Empresa{" +
                "id=" + id +
                ", idCompanyInfo=" + idCompanyInfo +
                '}';
    }
}
