package com.lavadero.unicartagena.edu.co.domain.model.personas;
import com.lavadero.unicartagena.edu.co.domain.model.shared.TipoIdentificacion;
import java.util.Objects;
public class InfoPersonal {
    private final Long infoPersonalId;
    private final TipoIdentificacion tipoIdentificacion;
    private final String nombre;
    private final String apellidos;
    private final String identificacion;
    private final String telefono;
    private final String correo;
    private final String direccion;
    public InfoPersonal(Long infoPersonalId, TipoIdentificacion tipoIdentificacion,
                       String nombre, String apellidos, String identificacion,
                       String telefono, String correo, String direccion) {
        this.infoPersonalId = infoPersonalId;
        this.tipoIdentificacion = tipoIdentificacion;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }
    public InfoPersonal(TipoIdentificacion tipoIdentificacion, String nombre,
                       String apellidos, String identificacion, String telefono,
                       String correo, String direccion) {
        this(null, tipoIdentificacion, nombre, apellidos, identificacion,
             telefono, correo, direccion);
    }
    public Long getInfoPersonalId() {
        return infoPersonalId;
    }
    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public String getIdentificacion() {
        return identificacion;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getCorreo() {
        return correo;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }
    public boolean tieneCorreoValido() {
        return correo != null && correo.contains("@") && correo.contains(".");
    }
    public boolean tieneTelefono() {
        return telefono != null && !telefono.trim().isEmpty();
    }
    public InfoPersonal conId(Long nuevoId) {
        return new InfoPersonal(nuevoId, tipoIdentificacion, nombre, apellidos,
                               identificacion, telefono, correo, direccion);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoPersonal that = (InfoPersonal) o;
        return tipoIdentificacion == that.tipoIdentificacion &&
               Objects.equals(nombre, that.nombre) &&
               Objects.equals(apellidos, that.apellidos) &&
               Objects.equals(identificacion, that.identificacion);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tipoIdentificacion, nombre, apellidos, identificacion);
    }
    @Override
    public String toString() {
        return "InfoPersonal{" +
                "infoPersonalId=" + infoPersonalId +
                ", tipoIdentificacion=" + tipoIdentificacion +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", identificacion='" + identificacion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
