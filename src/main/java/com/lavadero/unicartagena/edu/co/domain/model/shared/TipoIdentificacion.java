package com.lavadero.unicartagena.edu.co.domain.model.shared;

/**
 * Entidad TipoIdentificacion - Tipos de documentos de identidad.
 * Tabla: tipo_identificacion
 */
public class TipoIdentificacion {
    private Long id;
    private String tipo;  // Ej: "CC", "NIT", "CE", "Pasaporte"

    // Constructores
    public TipoIdentificacion() {
    }

    public TipoIdentificacion(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "TipoIdentificacion{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
