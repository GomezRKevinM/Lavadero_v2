package com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;
import java.util.List;

/**
 * Puerto de salida para persistencia de vehículos.
 */
public interface VehiculoRepository {
    /**
     * Guarda un vehículo (crear o actualizar).
     * @param vehiculo El vehículo a guardar
     * @return El vehículo guardado con su ID
     */
    Vehiculo guardar(Vehiculo vehiculo);

    /**
     * Busca un vehículo por su ID.
     * @param id El ID del vehículo
     * @return El vehículo encontrado o null
     */
    Vehiculo buscarPorId(Long id);

    /**
     * Busca un vehículo por su placa.
     * @param placa La placa del vehículo
     * @return El vehículo encontrado o null
     */
    Vehiculo buscarPorPlaca(String placa);

    /**
     * Busca todos los vehículos de un cliente.
     * @param clienteId El ID del cliente
     * @return Lista de vehículos del cliente
     */
    List<Vehiculo> buscarPorClienteId(Long clienteId);

    /**
     * Busca todos los vehículos de una marca.
     * @param marcaId El ID de la marca
     * @return Lista de vehículos de la marca
     */
    List<Vehiculo> buscarPorMarcaId(Integer marcaId);

    /**
     * Lista todos los vehículos.
     * @return Lista de todos los vehículos
     */
    List<Vehiculo> listarTodos();

    /**
     * Elimina un vehículo por su ID.
     * @param id El ID del vehículo a eliminar
     * @return true si se eliminó, false si no existía
     */
    boolean eliminar(Long id);

    /**
     * Verifica si existe un vehículo con una placa dada.
     * @param placa La placa a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorPlaca(String placa);

    /**
     * Verifica si existe un vehículo con una placa dada, excluyendo un ID específico.
     * Útil para actualizaciones.
     * @param placa La placa a verificar
     * @param idExcluir El ID a excluir de la búsqueda
     * @return true si existe otro vehículo con esa placa, false en caso contrario
     */
    boolean existePorPlacaExcluyendoId(String placa, Long idExcluir);
}
