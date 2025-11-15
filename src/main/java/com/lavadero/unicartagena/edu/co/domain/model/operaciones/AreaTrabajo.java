package com.lavadero.unicartagena.edu.co.domain.model.operaciones;

/**
 * Entidad AreaTrabajo - Áreas de trabajo o cubículos del lavadero.
 * Tabla: area_trabajo
 */
public class AreaTrabajo {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long empresaId;

    // Constructores
    public AreaTrabajo() {
    }

    public AreaTrabajo(Long id, String nombre, String descripcion, Long empresaId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empresaId = empresaId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public String toString() {
        return "AreaTrabajo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", empresaId=" + empresaId +
                '}';
    }
}
