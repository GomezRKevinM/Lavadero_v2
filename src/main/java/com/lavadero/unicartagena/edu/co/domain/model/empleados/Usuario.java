package com.lavadero.unicartagena.edu.co.domain.model.empleados;

import java.sql.Timestamp;

/**
 * Entidad Usuario - Usuarios del sistema para empleados.
 * Tabla: usuarios
 */
public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String questionSecurity;
    private String answerSecurity;
    private Long empresaId;
    private java.sql.Date creado;
    private Timestamp modificado;

    // Constructores
    public Usuario() {
    }

    public Usuario(Long id, String username, String password, String questionSecurity,
                  String answerSecurity, Long empresaId, java.sql.Date creado, Timestamp modificado) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.questionSecurity = questionSecurity;
        this.answerSecurity = answerSecurity;
        this.empresaId = empresaId;
        this.creado = creado;
        this.modificado = modificado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuestionSecurity() {
        return questionSecurity;
    }

    public void setQuestionSecurity(String questionSecurity) {
        this.questionSecurity = questionSecurity;
    }

    public String getAnswerSecurity() {
        return answerSecurity;
    }

    public void setAnswerSecurity(String answerSecurity) {
        this.answerSecurity = answerSecurity;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public java.sql.Date getCreado() {
        return creado;
    }

    public void setCreado(java.sql.Date creado) {
        this.creado = creado;
    }

    public Timestamp getModificado() {
        return modificado;
    }

    public void setModificado(Timestamp modificado) {
        this.modificado = modificado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", empresaId=" + empresaId +
                ", creado=" + creado +
                ", modificado=" + modificado +
                '}';
    }
}
