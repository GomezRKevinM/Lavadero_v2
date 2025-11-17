package com.lavadero.unicartagena.edu.co.application.service;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Color;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.GestionarColorUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.out.vehiculo.ColorRepository;

import java.util.List;

/**
 * Servicio de aplicación para gestión de colores de vehículos.
 * Implementa los casos de uso de colores.
 */
public class ColorService implements GestionarColorUseCase {

    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public Color crear(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("El color no puede ser nulo");
        }

        if (color.getColor() == null || color.getColor().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del color es obligatorio");
        }

        // Normalizar nombre
        color.setColor(color.getColor().trim());

        // Validar hexcode si está presente
        if (color.getHexcode() != null && !color.getHexcode().trim().isEmpty()) {
            validarHexcode(color.getHexcode());
        }

        // Verificar que no existe otro color con el mismo nombre
        if (colorRepository.existePorNombre(color.getColor())) {
            throw new IllegalArgumentException("Ya existe un color con el nombre " + color.getColor());
        }

        return colorRepository.guardar(color);
    }

    @Override
    public Color buscarPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del color es obligatorio");
        }
        return colorRepository.buscarPorId(id);
    }

    @Override
    public Color buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del color es obligatorio");
        }
        return colorRepository.buscarPorNombre(nombre.trim());
    }

    @Override
    public List<Color> listarTodos() {
        return colorRepository.listarTodos();
    }

    @Override
    public Color actualizar(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("El color no puede ser nulo");
        }

        if (color.getId() == null) {
            throw new IllegalArgumentException("El ID del color es obligatorio para actualizar");
        }

        if (color.getColor() == null || color.getColor().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del color es obligatorio");
        }

        // Verificar que el color existe
        Color existente = colorRepository.buscarPorId(color.getId());
        if (existente == null) {
            throw new RuntimeException("El color con ID " + color.getId() + " no existe");
        }

        // Normalizar nombre
        color.setColor(color.getColor().trim());

        // Validar hexcode si está presente
        if (color.getHexcode() != null && !color.getHexcode().trim().isEmpty()) {
            validarHexcode(color.getHexcode());
        }

        // Verificar que no existe otro color con el mismo nombre (permitir si es el mismo)
        Color porNombre = colorRepository.buscarPorNombre(color.getColor());
        if (porNombre != null && !porNombre.getId().equals(color.getId())) {
            throw new IllegalArgumentException("Ya existe otro color con el nombre " + color.getColor());
        }

        return colorRepository.guardar(color);
    }

    @Override
    public boolean eliminar(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del color es obligatorio");
        }
        return colorRepository.eliminar(id);
    }

    /**
     * Valida el formato del código hexadecimal.
     * @param hexcode El código hexadecimal a validar
     * @throws IllegalArgumentException si el formato es inválido
     */
    private void validarHexcode(String hexcode) {
        String hex = hexcode.trim();
        if (!hex.matches("^#?[0-9A-Fa-f]{6}$")) {
            throw new IllegalArgumentException("El código hexadecimal debe tener el formato #RRGGBB o RRGGBB");
        }
    }
}
