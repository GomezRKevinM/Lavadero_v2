package com.lavadero.unicartagena.edu.co.domain.model.personas;

/**
 * Entidad CompanyInfo - Información de empresas (proveedores/empresas del grupo).
 * Tabla: company_info
 */
public class CompanyInfo {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private String nit;
    private String webpage;

    // Constructores
    public CompanyInfo() {
    }

    public CompanyInfo(Long id, String nombre, String direccion, String telefono, 
                      String email, String nit, String webpage) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.nit = nit;
        this.webpage = webpage;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", nit='" + nit + '\'' +
                ", webpage='" + webpage + '\'' +
                '}';
    }
}
