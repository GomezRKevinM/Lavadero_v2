package com.lavadero.unicartagena.edu.co.domain.model.vehiculos;

/**
 * Entidad Color - Colores de vehículos.
 * Tabla: colores
 */
public class Color {
    private Integer id;
    private String color;
    private String hexcode;

    // Constructores
    public Color() {
    }

    public Color(Integer id, String color, String hexcode) {
        this.id = id;
        this.color = color;
        this.hexcode = hexcode;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHexcode() {
        return hexcode;
    }

    public void setHexcode(String hexcode) {
        this.hexcode = hexcode;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", hexcode='" + hexcode + '\'' +
                '}';
    }
}
