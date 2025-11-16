package com.lavadero.unicartagena.edu.co.domain.model.personas;
public class Cliente {
    private Long id;
    private InfoPersonal infoPersonal;
    private Long empresaId;
    public Cliente() {
    }
    public Cliente(Long id, InfoPersonal infoPersonal, Long empresaId) {
        this.id = id;
        this.infoPersonal = infoPersonal;
        this.empresaId = empresaId;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public InfoPersonal getInfoPersonal() {
        return infoPersonal;
    }
    public void setInfoPersonal(InfoPersonal infoPersonal) {
        this.infoPersonal = infoPersonal;
    }
    public Long getEmpresaId() {
        return empresaId;
    }
    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
    public String getNombreCompleto() {
        return infoPersonal != null ? infoPersonal.getNombreCompleto() : null;
    }
    public String getCorreo() {
        return infoPersonal != null ? infoPersonal.getCorreo() : null;
    }
    public String getIdentificacion() {
        return infoPersonal != null ? infoPersonal.getIdentificacion() : null;
    }
    public String getTelefono() {
        return infoPersonal != null ? infoPersonal.getTelefono() : null;
    }
    public boolean puedeRecibirCorreo() {
        return infoPersonal != null && infoPersonal.tieneCorreoValido();
    }
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", infoPersonal=" + infoPersonal +
                ", empresaId=" + empresaId +
                '}';
    }
}
