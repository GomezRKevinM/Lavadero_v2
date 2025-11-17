package com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Color;
import java.util.List;

/**
 * Puerto de salida para persistencia de colores.
 */
public interface ColorRepository {
    /**
     * Guarda un color (crear o actualizar).
     * @param color El color a guardar
     * @return El color guardado con su ID
     */
    Color guardar(Color color);

    /**
     * Busca un color por su ID.
     * @param id El ID del color
     * @return El color encontrado o null
     */
    Color buscarPorId(Integer id);

    /**
     * Busca un color por su nombre.
     * @param nombre El nombre del color
     * @return El color encontrado o null
     */
    Color buscarPorNombre(String nombre);

    /**
     * Lista todos los colores.
     * @return Lista de todos los colores
     */
    List<Color> listarTodos();

    /**
     * Elimina un color por su ID.
     * @param id El ID del color a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean eliminar(Integer id);

    /**
     * Verifica si existe un color con un nombre dado.
     * @param nombre El nombre a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorNombre(String nombre);
}
