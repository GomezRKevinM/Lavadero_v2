package com.lavadero.unicartagena.edu.co.domain.model.shared;

/**
 * Entidad Estado - Maneja estados de múltiples entidades del sistema.
 * Tabla: estados
 */
public class Estado {
    private Long id;
    private String entidad;  // Ej: "pedido", "cotizacion", "cola_cliente"
    private String nombre;   // Ej: "pendiente", "aprobado", "rechazado"

    // Constructores
    public Estado() {
    }

    public Estado(Long id, String entidad, String nombre) {
        this.id = id;
        this.entidad = entidad;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Estado{" +
                "id=" + id +
                ", entidad='" + entidad + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
