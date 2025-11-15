package com.lavadero.unicartagena.edu.co.domain.model.vehiculos;

/**
 * Entidad Vehiculo - Vehículos de clientes.
 * Tabla: vehiculos
 */
public class Vehiculo {
    private Long id;
    private Integer marcaId;
    private String placa;
    private Long clienteId;

    // Constructores
    public Vehiculo() {
    }

    public Vehiculo(Long id, Integer marcaId, String placa, Long clienteId) {
        this.id = id;
        this.marcaId = marcaId;
        this.placa = placa;
        this.clienteId = clienteId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "id=" + id +
                ", marcaId=" + marcaId +
                ", placa='" + placa + '\'' +
                ", clienteId=" + clienteId +
                '}';
    }
}
