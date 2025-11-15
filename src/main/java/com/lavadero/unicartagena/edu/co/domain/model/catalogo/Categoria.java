package com.lavadero.unicartagena.edu.co.domain.model.catalogo;

/**
 * Entidad Categoria - Categorías de productos y servicios.
 * Tabla: categorias
 */
public class Categoria {
    private Long id;
    private String nombre;
    private Long empresaId;

    // Constructores
    public Categoria() {
    }

    public Categoria(Long id, String nombre, Long empresaId) {
        this.id = id;
        this.nombre = nombre;
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

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", empresaId=" + empresaId +
                '}';
    }
}
