package com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Color;
import java.util.List;

/**
 * Puerto de entrada para gestionar colores de vehículos.
 */
public interface GestionarColorUseCase {
    /**
     * Crea un nuevo color.
     * @param color El color a crear
     * @return El color creado con su ID asignado
     */
    Color crear(Color color);

    /**
     * Busca un color por su ID.
     * @param id El ID del color
     * @return El color encontrado o null si no existe
     */
    Color buscarPorId(Integer id);

    /**
     * Busca un color por su nombre.
     * @param nombre El nombre del color
     * @return El color encontrado o null si no existe
     */
    Color buscarPorNombre(String nombre);

    /**
     * Lista todos los colores.
     * @return Lista de todos los colores
     */
    List<Color> listarTodos();

    /**
     * Actualiza un color existente.
     * @param color El color con los datos actualizados
     * @return El color actualizado
     */
    Color actualizar(Color color);

    /**
     * Elimina un color por su ID.
     * @param id El ID del color a eliminar
     * @return true si se eliminó correctamente, false si no existía
     */
    boolean eliminar(Integer id);
}
