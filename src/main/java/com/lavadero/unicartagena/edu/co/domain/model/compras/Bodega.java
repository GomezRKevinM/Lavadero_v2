package com.lavadero.unicartagena.edu.co.domain.model.compras;

/**
 * Entidad Bodega - Almacenes para productos.
 * Tabla: bodegas
 */
public class Bodega {
    private Long id;
    private String codigo;
    private String nombre;
    private String ubicacion;
    private Long empresaId;

    // Constructores
    public Bodega() {
    }

    public Bodega(Long id, String codigo, String nombre, String ubicacion, Long empresaId) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.empresaId = empresaId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    @Override
    public String toString() {
        return "Bodega{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", empresaId=" + empresaId +
                '}';
    }
}
