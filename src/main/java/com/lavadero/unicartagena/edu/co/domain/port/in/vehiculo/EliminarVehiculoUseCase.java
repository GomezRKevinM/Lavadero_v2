package com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo;

/**
 * Puerto de entrada para eliminar un vehículo.
 */
public interface EliminarVehiculoUseCase {
    /**
     * Elimina un vehículo por su ID.
     * @param id El ID del vehículo a eliminar
     * @return true si se eliminó correctamente, false si no existía
     */
    boolean eliminar(Long id);
}
