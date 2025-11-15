package com.lavadero.unicartagena.edu.co.domain.model.personas;

import com.lavadero.unicartagena.edu.co.shared.constant.TipoCuenta;
import java.sql.Timestamp;

/**
 * Entidad InfoPago - Información bancaria para pagos.
 * Tabla: info_pago
 */
public class InfoPago {
    private Long id;
    private Long bancoId;
    private TipoCuenta tipo;
    private String numeroCuenta;
    private String titular;
    private Timestamp modificado;
    private Long idCompany;

    // Constructores
    public InfoPago() {
    }

    public InfoPago(Long id, Long bancoId, TipoCuenta tipo, String numeroCuenta,
                   String titular, Timestamp modificado, Long idCompany) {
        this.id = id;
        this.bancoId = bancoId;
        this.tipo = tipo;
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.modificado = modificado;
        this.idCompany = idCompany;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBancoId() {
        return bancoId;
    }

    public void setBancoId(Long bancoId) {
        this.bancoId = bancoId;
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        this.tipo = tipo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Timestamp getModificado() {
        return modificado;
    }

    public void setModificado(Timestamp modificado) {
        this.modificado = modificado;
    }

    public Long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Long idCompany) {
        this.idCompany = idCompany;
    }

    @Override
    public String toString() {
        return "InfoPago{" +
                "id=" + id +
                ", bancoId=" + bancoId +
                ", tipo=" + tipo +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", titular='" + titular + '\'' +
                ", modificado=" + modificado +
                ", idCompany=" + idCompany +
                '}';
    }
}
