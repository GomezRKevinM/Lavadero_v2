package com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;
import java.util.List;

/**
 * Puerto de entrada para buscar vehículos.
 */
public interface BuscarVehiculoUseCase {
    /**
     * Busca un vehículo por su ID.
     * @param id El ID del vehículo
     * @return El vehículo encontrado o null si no existe
     */
    Vehiculo buscarPorId(Long id);

    /**
     * Busca un vehículo por su placa.
     * @param placa La placa del vehículo
     * @return El vehículo encontrado o null si no existe
     */
    Vehiculo buscarPorPlaca(String placa);

    /**
     * Busca todos los vehículos de un cliente.
     * @param clienteId El ID del cliente
     * @return Lista de vehículos del cliente (vacía si no tiene)
     */
    List<Vehiculo> buscarPorCliente(Long clienteId);

    /**
     * Busca todos los vehículos de una marca.
     * @param marcaId El ID de la marca
     * @return Lista de vehículos de la marca (vacía si no hay)
     */
    List<Vehiculo> buscarPorMarca(Integer marcaId);

    /**
     * Lista todos los vehículos.
     * @return Lista de todos los vehículos
     */
    List<Vehiculo> listarTodos();
}
