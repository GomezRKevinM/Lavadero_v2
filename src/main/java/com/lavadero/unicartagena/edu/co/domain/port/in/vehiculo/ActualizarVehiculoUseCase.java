package com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;

/**
 * Puerto de entrada para actualizar un vehículo.
 */
public interface ActualizarVehiculoUseCase {
    /**
     * Actualiza un vehículo existente.
     * @param vehiculo El vehículo con los datos actualizados
     * @return El vehículo actualizado
     * @throws IllegalArgumentException si los datos son inválidos
     * @throws RuntimeException si el vehículo o cliente no existe
     */
    Vehiculo actualizar(Vehiculo vehiculo);
}
