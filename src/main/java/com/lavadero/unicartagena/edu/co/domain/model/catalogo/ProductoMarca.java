package com.lavadero.unicartagena.edu.co.domain.model.catalogo;

/**
 * Entidad ProductoMarca - Marcas de productos.
 * Tabla: producto_marca
 */
public class ProductoMarca {
    private Long id;
    private String marcaProducto;

    // Constructores
    public ProductoMarca() {
    }

    public ProductoMarca(Long id, String marcaProducto) {
        this.id = id;
        this.marcaProducto = marcaProducto;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(String marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    @Override
    public String toString() {
        return "ProductoMarca{" +
                "id=" + id +
                ", marcaProducto='" + marcaProducto + '\'' +
                '}';
    }
}
