package com.lavadero.unicartagena.edu.co;

import static org.junit.jupiter.api.Assertions.*;

import com.lavadero.unicartagena.edu.co.domain.model.catalogo.Producto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Tests unitarios para entidades del dominio.
 * 
 * <p>Estos tests validan la creación y comportamiento de las entidades
 * sin requerir conexión a base de datos (tests de unidad puros).</p>
 * 
 * <p>Para tests de integración con JDBC, crear clase separada
 * en src/test/java/.../integration/ProductoRepositoryIntegrationTest.java</p>
 */
@DisplayName("Tests de Entidades del Dominio")
class AppTest {
    
    @Test
    @DisplayName("Debe crear Producto con todos los campos correctamente")
    void testCrearProducto_shouldSetAllFields_whenCalledWithValidData() {
        // Arrange
        Long expectedId = 1L;
        String expectedCodigo = "PROD-001";
        String expectedNombre = "Shampoo para Autos";
        BigDecimal expectedPrecioCompra = new BigDecimal("10000.00");
        BigDecimal expectedPrecioVenta = new BigDecimal("15000.00");
        Long expectedMarca = 2L;
        Timestamp expectedTimestamp = new Timestamp(System.currentTimeMillis());
        
        // Act
        Producto producto = new Producto();
        producto.setId(expectedId);
        producto.setCodigo(expectedCodigo);
        producto.setNombre(expectedNombre);
        producto.setPrecioCompra(expectedPrecioCompra);
        producto.setPrecioVenta(expectedPrecioVenta);
        producto.setMarcaProducto(expectedMarca);
        producto.setModificado(expectedTimestamp);
        
        // Assert
        assertAll("Validar todos los campos del producto",
            () -> assertEquals(expectedId, producto.getId()),
            () -> assertEquals(expectedCodigo, producto.getCodigo()),
            () -> assertEquals(expectedNombre, producto.getNombre()),
            () -> assertEquals(expectedPrecioCompra, producto.getPrecioCompra()),
            () -> assertEquals(expectedPrecioVenta, producto.getPrecioVenta()),
            () -> assertEquals(expectedMarca, producto.getMarcaProducto()),
            () -> assertEquals(expectedTimestamp, producto.getModificado())
        );
    }
    
    @Test
    @DisplayName("Debe crear Producto usando constructor con parámetros")
    void testCrearProductoConConstructor_shouldInitializeFields_whenCalledWithAllParameters() {
        // Arrange & Act
        Producto producto = new Producto(
            1L,
            "PROD-002",
            "Cera Premium",
            new BigDecimal("20000.00"),
            new BigDecimal("30000.00"),
            3L,
            "cera.png",
            new Timestamp(System.currentTimeMillis())
        );
        
        // Assert
        assertAll("Validar inicialización por constructor",
            () -> assertNotNull(producto),
            () -> assertEquals("PROD-002", producto.getCodigo()),
            () -> assertEquals("Cera Premium", producto.getNombre()),
            () -> assertEquals(new BigDecimal("20000.00"), producto.getPrecioCompra()),
            () -> assertEquals("cera.png", producto.getImagen())
        );
    }
    
    @Test
    @DisplayName("Debe calcular nuevo precio de venta con incremento del 35%")
    void testCalcularNuevoPrecioVenta_shouldApply35PercentIncrease_whenPurchasePriceIncreases() {
        // Arrange
        BigDecimal precioCompraAnterior = new BigDecimal("10000.00");
        BigDecimal precioVentaActual = new BigDecimal("15000.00");
        BigDecimal precioCompraNuevo = new BigDecimal("12000.00");
        
        // Act - Aplicar regla de negocio
        BigDecimal diferencia = precioCompraNuevo.subtract(precioCompraAnterior);
        BigDecimal aumento = diferencia.multiply(new BigDecimal("0.35"));
        BigDecimal nuevoPrecioVenta = precioVentaActual.add(aumento);
        
        // Assert
        // Diferencia: 12000 - 10000 = 2000
        // Aumento 35%: 2000 * 0.35 = 700
        // Nuevo precio venta: 15000 + 700 = 15700
        assertAll("Validar regla de negocio de precios",
            () -> assertEquals(0, new BigDecimal("2000.00").compareTo(diferencia)),
            () -> assertEquals(0, new BigDecimal("700.00").compareTo(aumento)),
            () -> assertEquals(0, new BigDecimal("15700.00").compareTo(nuevoPrecioVenta))
        );
    }
    
    @Test
    @DisplayName("toString() debe retornar representación formateada")
    void testToString_shouldReturnFormattedString_whenCalled() {
        // Arrange
        Producto producto = new Producto();
        producto.setId(5L);
        producto.setCodigo("TEST-001");
        producto.setNombre("Producto Test");
        
        // Act
        String resultado = producto.toString();
        
        // Assert
        assertAll("Validar formato toString",
            () -> assertNotNull(resultado),
            () -> assertTrue(resultado.contains("id=5")),
            () -> assertTrue(resultado.contains("codigo='TEST-001'")),
            () -> assertTrue(resultado.contains("nombre='Producto Test'"))
        );
    }
}
