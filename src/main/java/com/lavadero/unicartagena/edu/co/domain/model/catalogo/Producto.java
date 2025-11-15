package com.lavadero.unicartagena.edu.co.domain.model.catalogo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Entidad Producto - Productos del lavadero.
 * Tabla: productos
 */
public class Producto {
    private Long id;
    private String codigo;
    private String nombre;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Long marcaProducto;  // FK a producto_marca
    private String imagen;
    private Timestamp modificado;

    // Constructores
    public Producto() {
    }

    public Producto(Long id, String codigo, String nombre, BigDecimal precioCompra,
                   BigDecimal precioVenta, Long marcaProducto, String imagen, Timestamp modificado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.marcaProducto = marcaProducto;
        this.imagen = imagen;
        this.modificado = modificado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Long getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(Long marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Timestamp getModificado() {
        return modificado;
    }

    public void setModificado(Timestamp modificado) {
        this.modificado = modificado;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precioCompra=" + precioCompra +
                ", precioVenta=" + precioVenta +
                ", marcaProducto=" + marcaProducto +
                ", imagen='" + imagen + '\'' +
                ", modificado=" + modificado +
                '}';
    }
}
