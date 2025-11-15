package com.lavadero.unicartagena.edu.co.domain.model.empleados;

/**
 * Entidad Cargo - Cargos que pueden desempeñar los empleados.
 * Tabla: cargo
 */
public class Cargo {
    private Integer id;
    private String nombre;

    // Constructores
    public Cargo() {
    }

    public Cargo(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
