package com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.MarcaVehiculo;
import java.util.List;

/**
 * Puerto de salida para persistencia de marcas de vehículos.
 */
public interface MarcaVehiculoRepository {
    /**
     * Guarda una marca de vehículo (crear o actualizar).
     * @param marca La marca a guardar
     * @return La marca guardada con su ID
     */
    MarcaVehiculo guardar(MarcaVehiculo marca);

    /**
     * Busca una marca por su ID.
     * @param id El ID de la marca
     * @return La marca encontrada o null
     */
    MarcaVehiculo buscarPorId(Integer id);

    /**
     * Busca una marca por su nombre.
     * @param nombre El nombre de la marca
     * @return La marca encontrada o null
     */
    MarcaVehiculo buscarPorNombre(String nombre);

    /**
     * Lista todas las marcas.
     * @return Lista de todas las marcas
     */
    List<MarcaVehiculo> listarTodas();

    /**
     * Elimina una marca por su ID.
     * @param id El ID de la marca a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean eliminar(Integer id);

    /**
     * Verifica si existe una marca con un nombre dado.
     * @param nombre El nombre a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorNombre(String nombre);
}
