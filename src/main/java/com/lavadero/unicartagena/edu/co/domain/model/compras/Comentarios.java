package com.lavadero.unicartagena.edu.co.domain.model.compras;

/**
 * Entidad Comentarios - Comentarios sobre verificaciones de cotizaciones.
 * Tabla: comentarios
 */
public class Comentarios {
    private Long id;
    private Long cotizacionCheckId;
    private String comentario;

    // Constructores
    public Comentarios() {
    }

    public Comentarios(Long id, Long cotizacionCheckId, String comentario) {
        this.id = id;
        this.cotizacionCheckId = cotizacionCheckId;
        this.comentario = comentario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCotizacionCheckId() {
        return cotizacionCheckId;
    }

    public void setCotizacionCheckId(Long cotizacionCheckId) {
        this.cotizacionCheckId = cotizacionCheckId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Comentarios{" +
                "id=" + id +
                ", cotizacionCheckId=" + cotizacionCheckId +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
