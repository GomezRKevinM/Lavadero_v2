package com.lavadero.unicartagena.edu.co.domain.model.personas;

/**
 * Entidad InfoPersonal - Información personal de personas físicas.
 * Tabla: info_personal
 */
public class InfoPersonal {
    private Long id;
    private Long tipoIdentificacionId;
    private String nombre;
    private String apellidos;
    private String identificacion;
    private String telefono;
    private String correo;
    private String direccion;

    // Constructores
    public InfoPersonal() {
    }

    public InfoPersonal(Long id, Long tipoIdentificacionId, String nombre, String apellidos,
                       String identificacion, String telefono, String correo, String direccion) {
        this.id = id;
        this.tipoIdentificacionId = tipoIdentificacionId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTipoIdentificacionId() {
        return tipoIdentificacionId;
    }

    public void setTipoIdentificacionId(Long tipoIdentificacionId) {
        this.tipoIdentificacionId = tipoIdentificacionId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "InfoPersonal{" +
                "id=" + id +
                ", tipoIdentificacionId=" + tipoIdentificacionId +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
