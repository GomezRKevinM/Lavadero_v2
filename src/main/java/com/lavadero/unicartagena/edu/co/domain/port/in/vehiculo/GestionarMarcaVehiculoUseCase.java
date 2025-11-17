package com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.MarcaVehiculo;
import java.util.List;

/**
 * Puerto de entrada para gestionar marcas de vehículos.
 */
public interface GestionarMarcaVehiculoUseCase {
    /**
     * Crea una nueva marca de vehículo.
     * @param marca La marca a crear
     * @return La marca creada con su ID asignado
     */
    MarcaVehiculo crear(MarcaVehiculo marca);

    /**
     * Busca una marca por su ID.
     * @param id El ID de la marca
     * @return La marca encontrada o null si no existe
     */
    MarcaVehiculo buscarPorId(Integer id);

    /**
     * Busca una marca por su nombre.
     * @param nombre El nombre de la marca
     * @return La marca encontrada o null si no existe
     */
    MarcaVehiculo buscarPorNombre(String nombre);

    /**
     * Lista todas las marcas de vehículos.
     * @return Lista de todas las marcas
     */
    List<MarcaVehiculo> listarTodas();

    /**
     * Actualiza una marca existente.
     * @param marca La marca con los datos actualizados
     * @return La marca actualizada
     */
    MarcaVehiculo actualizar(MarcaVehiculo marca);

    /**
     * Elimina una marca por su ID.
     * @param id El ID de la marca a eliminar
     * @return true si se eliminó correctamente, false si no existía
     */
    boolean eliminar(Integer id);
}
