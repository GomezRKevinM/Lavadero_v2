package com.lavadero.unicartagena.edu.co.domain.model.vehiculos;

/**
 * Entidad MarcaVehiculo - Marcas de vehículos.
 * Tabla: marca_vehiculos
 */
public class MarcaVehiculo {
    private Integer id;
    private String marca;

    // Constructores
    public MarcaVehiculo() {
    }

    public MarcaVehiculo(Integer id, String marca) {
        this.id = id;
        this.marca = marca;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "MarcaVehiculo{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                '}';
    }
}
