package com.lavadero.unicartagena.edu.co.domain.model.shared;

/**
 * Entidad Banco - Entidades bancarias del sistema.
 * Tabla: bancos
 */
public class Banco {
    private Long id;
    private String banco;  // Nombre del banco

    // Constructores
    public Banco() {
    }

    public Banco(Long id, String banco) {
        this.id = id;
        this.banco = banco;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    @Override
    public String toString() {
        return "Banco{" +
                "id=" + id +
                ", banco='" + banco + '\'' +
                '}';
    }
}
