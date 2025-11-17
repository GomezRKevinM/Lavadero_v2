package com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;

/**
 * Puerto de entrada para crear un vehículo.
 * Regla de negocio: Los vehículos solo pueden corresponder a un cliente existente.
 */
public interface CrearVehiculoUseCase {
    /**
     * Crea un nuevo vehículo asociado a un cliente existente.
     * @param vehiculo El vehículo a crear
     * @return El vehículo creado con su ID asignado
     * @throws IllegalArgumentException si los datos del vehículo son inválidos
     * @throws RuntimeException si el cliente no existe
     */
    Vehiculo crear(Vehiculo vehiculo);
}
