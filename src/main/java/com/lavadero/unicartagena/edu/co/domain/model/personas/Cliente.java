package com.lavadero.unicartagena.edu.co.domain.model.personas;

/**
 * Entidad Cliente - Clientes del lavadero.
 * Tabla: clientes
 */
public class Cliente {
    private Long id;
    private Long infoPersonalId;
    private Long empresaId;

    // Constructores
    public Cliente() {
    }

    public Cliente(Long id, Long infoPersonalId, Long empresaId) {
        this.id = id;
        this.infoPersonalId = infoPersonalId;
        this.empresaId = empresaId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInfoPersonalId() {
        return infoPersonalId;
    }

    public void setInfoPersonalId(Long infoPersonalId) {
        this.infoPersonalId = infoPersonalId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", infoPersonalId=" + infoPersonalId +
                ", empresaId=" + empresaId +
                '}';
    }
}
