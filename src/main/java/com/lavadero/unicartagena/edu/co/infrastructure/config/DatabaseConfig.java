package com.lavadero.unicartagena.edu.co.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Configuración de conexión a PostgreSQL usando HikariCP para pool de conexiones.
 * 
 * <p>Proporciona conexiones a la base de datos PostgreSQL del sistema de lavadero.
 * Utiliza HikariCP como pool de conexiones para mejor rendimiento y gestión de recursos.</p>
 * 
 * <p>Configurar las siguientes variables de entorno o modificar directamente:</p>
 * <ul>
 *   <li>DB_URL - URL de conexión a PostgreSQL</li>
 *   <li>DB_USER - Usuario de la base de datos</li>
 *   <li>DB_PASSWORD - Contraseña de la base de datos</li>
 * </ul>
 */
public class DatabaseConfig {
    
    private static HikariDataSource dataSource;
    
    static {
        HikariConfig config = new HikariConfig();
        
        // Configuración de conexión a PostgreSQL
        config.setJdbcUrl(System.getenv().getOrDefault("DB_URL", 
            "jdbc:postgresql://localhost:5432/7502420013_28_lavadero"));
        config.setUsername(System.getenv().getOrDefault("DB_USER", "7502420013_kgomez"));
        config.setPassword(System.getenv().getOrDefault("DB_PASSWORD", "root"));
        
        // Configuración del pool de conexiones
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000); // 30 segundos
        config.setIdleTimeout(600000); // 10 minutos
        config.setMaxLifetime(1800000); // 30 minutos
        
        // Propiedades específicas de PostgreSQL
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        // Schema por defecto
        config.setSchema("Lavadero_28_v2");
        
        dataSource = new HikariDataSource(config);
    }
    
    /**
     * Obtiene una conexión del pool de conexiones.
     * 
     * @return Connection Una conexión activa a la base de datos
     * @throws SQLException Si ocurre un error al obtener la conexión
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    /**
     * Cierra el pool de conexiones.
     * Debe llamarse al finalizar la aplicación.
     */
    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
    
    /**
     * Verifica si la conexión a la base de datos está activa.
     * 
     * @return boolean true si la conexión es exitosa, false en caso contrario
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al probar conexión: " + e.getMessage());
            return false;
        }
    }
}
