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

    /**
     * Valida que el vehículo tenga todos los datos obligatorios.
     * @throws IllegalArgumentException si falta algún dato obligatorio
     */
    public void validar() {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa es obligatoria");
        }
        if (marcaId == null) {
            throw new IllegalArgumentException("La marca es obligatoria");
        }
        if (clienteId == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        validarFormatoPlaca();
    }

    /**
     * Valida el formato básico de la placa.
     * Permite letras, números y guiones, entre 5 y 10 caracteres.
     */
    private void validarFormatoPlaca() {
        String placaLimpia = placa.trim().toUpperCase();
        if (placaLimpia.length() < 5 || placaLimpia.length() > 10) {
            throw new IllegalArgumentException("La placa debe tener entre 5 y 10 caracteres");
        }
        if (!placaLimpia.matches("[A-Z0-9\\-]+")) {
            throw new IllegalArgumentException("La placa solo puede contener letras, números y guiones");
        }
    }

    /**
     * Normaliza la placa (mayúsculas y sin espacios).
     */
    public void normalizarPlaca() {
        if (placa != null) {
            this.placa = placa.trim().toUpperCase();
        }
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
