package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.ActualizarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.BuscarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.CrearVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.EliminarVehiculoUseCase;

import java.util.List;
import java.util.Scanner;

/**
 * Controlador CLI para gestión de vehículos.
 */
public class VehiculoController {
    
    private final CrearVehiculoUseCase crearVehiculoUseCase;
    private final BuscarVehiculoUseCase buscarVehiculoUseCase;
    private final ActualizarVehiculoUseCase actualizarVehiculoUseCase;
    private final EliminarVehiculoUseCase eliminarVehiculoUseCase;
    private final Scanner scanner;

    public VehiculoController(
            CrearVehiculoUseCase crearVehiculoUseCase,
            BuscarVehiculoUseCase buscarVehiculoUseCase,
            ActualizarVehiculoUseCase actualizarVehiculoUseCase,
            EliminarVehiculoUseCase eliminarVehiculoUseCase) {
        this.crearVehiculoUseCase = crearVehiculoUseCase;
        this.buscarVehiculoUseCase = buscarVehiculoUseCase;
        this.actualizarVehiculoUseCase = actualizarVehiculoUseCase;
        this.eliminarVehiculoUseCase = eliminarVehiculoUseCase;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean continuar = true;
        
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    listarTodos();
                    break;
                case 2:
                    buscarPorId();
                    break;
                case 3:
                    buscarPorPlaca();
                    break;
                case 4:
                    buscarPorCliente();
                    break;
                case 5:
                    buscarPorMarca();
                    break;
                case 6:
                    crearVehiculo();
                    break;
                case 7:
                    actualizarVehiculo();
                    break;
                case 8:
                    eliminarVehiculo();
                    break;
                case 0:
                    continuar = false;
                    System.out.println("\n¡Hasta pronto!");
                    break;
                default:
                    System.out.println("\nOpción no válida. Intente de nuevo.");
            }
            
            if (continuar) {
                esperarEnter();
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println("    GESTIÓN DE VEHÍCULOS - LAVADERO    ");
        System.out.println("========================================");
        System.out.println("1. Listar todos los vehículos");
        System.out.println("2. Buscar vehículo por ID");
        System.out.println("3. Buscar vehículo por placa");
        System.out.println("4. Buscar vehículos por cliente");
        System.out.println("5. Buscar vehículos por marca");
        System.out.println("6. Crear nuevo vehículo");
        System.out.println("7. Actualizar vehículo");
        System.out.println("8. Eliminar vehículo");
        System.out.println("0. Salir");
        System.out.println("========================================");
        System.out.print("Seleccione una opción: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void listarTodos() {
        System.out.println("\n========== LISTA DE VEHÍCULOS ==========");
        try {
            List<Vehiculo> vehiculos = buscarVehiculoUseCase.listarTodos();
            
            if (vehiculos.isEmpty()) {
                System.out.println("No hay vehículos registrados.");
            } else {
                System.out.printf("%-5s | %-10s | %-10s | %-12s%n",
                    "ID", "Placa", "Marca ID", "Cliente ID");
                System.out.println("------------------------------------------------");
                
                for (Vehiculo vehiculo : vehiculos) {
                    System.out.printf("%-5d | %-10s | %-10d | %-12d%n",
                        vehiculo.getId(),
                        vehiculo.getPlaca(),
                        vehiculo.getMarcaId(),
                        vehiculo.getClienteId());
                }
                
                System.out.println("\nTotal de vehículos: " + vehiculos.size());
            }
        } catch (Exception e) {
            System.out.println("Error al listar vehículos: " + e.getMessage());
        }
    }

    private void buscarPorId() {
        System.out.println("\n========== BUSCAR VEHÍCULO POR ID ==========");
        System.out.print("Ingrese el ID del vehículo: ");
        
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Vehiculo vehiculo = buscarVehiculoUseCase.buscarPorId(id);
            
            if (vehiculo != null) {
                mostrarVehiculo(vehiculo);
            } else {
                System.out.println("No se encontró un vehículo con ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error al buscar vehículo: " + e.getMessage());
        }
    }

    private void buscarPorPlaca() {
        System.out.println("\n========== BUSCAR VEHÍCULO POR PLACA ==========");
        System.out.print("Ingrese la placa del vehículo: ");
        
        try {
            String placa = scanner.nextLine();
            Vehiculo vehiculo = buscarVehiculoUseCase.buscarPorPlaca(placa);
            
            if (vehiculo != null) {
                mostrarVehiculo(vehiculo);
            } else {
                System.out.println("No se encontró un vehículo con placa: " + placa);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar vehículo: " + e.getMessage());
        }
    }

    private void buscarPorCliente() {
        System.out.println("\n========== BUSCAR VEHÍCULOS POR CLIENTE ==========");
        System.out.print("Ingrese el ID del cliente: ");
        
        try {
            Long clienteId = Long.parseLong(scanner.nextLine());
            List<Vehiculo> vehiculos = buscarVehiculoUseCase.buscarPorCliente(clienteId);
            
            if (vehiculos.isEmpty()) {
                System.out.println("El cliente no tiene vehículos registrados.");
            } else {
                System.out.printf("%-5s | %-10s | %-10s%n",
                    "ID", "Placa", "Marca ID");
                System.out.println("--------------------------------");
                
                for (Vehiculo vehiculo : vehiculos) {
                    System.out.printf("%-5d | %-10s | %-10d%n",
                        vehiculo.getId(),
                        vehiculo.getPlaca(),
                        vehiculo.getMarcaId());
                }
                
                System.out.println("\nTotal de vehículos: " + vehiculos.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error al buscar vehículos: " + e.getMessage());
        }
    }

    private void buscarPorMarca() {
        System.out.println("\n========== BUSCAR VEHÍCULOS POR MARCA ==========");
        System.out.print("Ingrese el ID de la marca: ");
        
        try {
            Integer marcaId = Integer.parseInt(scanner.nextLine());
            List<Vehiculo> vehiculos = buscarVehiculoUseCase.buscarPorMarca(marcaId);
            
            if (vehiculos.isEmpty()) {
                System.out.println("No hay vehículos de esta marca.");
            } else {
                System.out.printf("%-5s | %-10s | %-12s%n",
                    "ID", "Placa", "Cliente ID");
                System.out.println("----------------------------------");
                
                for (Vehiculo vehiculo : vehiculos) {
                    System.out.printf("%-5d | %-10s | %-12d%n",
                        vehiculo.getId(),
                        vehiculo.getPlaca(),
                        vehiculo.getClienteId());
                }
                
                System.out.println("\nTotal de vehículos: " + vehiculos.size());
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error al buscar vehículos: " + e.getMessage());
        }
    }

    private void crearVehiculo() {
        System.out.println("\n========== CREAR NUEVO VEHÍCULO ==========");
        
        try {
            System.out.print("Placa: ");
            String placa = scanner.nextLine();
            
            System.out.print("ID de la marca: ");
            Integer marcaId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("ID del cliente (debe existir): ");
            Long clienteId = Long.parseLong(scanner.nextLine());
            
            Vehiculo vehiculo = new Vehiculo(null, marcaId, placa, clienteId);
            Vehiculo creado = crearVehiculoUseCase.crear(vehiculo);
            
            System.out.println("\n¡Vehículo creado exitosamente!");
            mostrarVehiculo(creado);
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de número inválido.");
        } catch (Exception e) {
            System.out.println("Error al crear vehículo: " + e.getMessage());
        }
    }

    private void actualizarVehiculo() {
        System.out.println("\n========== ACTUALIZAR VEHÍCULO ==========");
        System.out.print("Ingrese el ID del vehículo a actualizar: ");
        
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Vehiculo existente = buscarVehiculoUseCase.buscarPorId(id);
            
            if (existente == null) {
                System.out.println("No se encontró un vehículo con ID: " + id);
                return;
            }
            
            System.out.println("\nDatos actuales:");
            mostrarVehiculo(existente);
            
            System.out.println("\nIngrese los nuevos datos (Enter para mantener el actual):");
            
            System.out.print("Nueva placa [" + existente.getPlaca() + "]: ");
            String placa = scanner.nextLine();
            if (placa.trim().isEmpty()) {
                placa = existente.getPlaca();
            }
            
            System.out.print("Nuevo ID de marca [" + existente.getMarcaId() + "]: ");
            String marcaIdStr = scanner.nextLine();
            Integer marcaId = marcaIdStr.trim().isEmpty() ? 
                existente.getMarcaId() : Integer.parseInt(marcaIdStr);
            
            System.out.print("Nuevo ID de cliente [" + existente.getClienteId() + "]: ");
            String clienteIdStr = scanner.nextLine();
            Long clienteId = clienteIdStr.trim().isEmpty() ? 
                existente.getClienteId() : Long.parseLong(clienteIdStr);
            
            Vehiculo vehiculo = new Vehiculo(id, marcaId, placa, clienteId);
            Vehiculo actualizado = actualizarVehiculoUseCase.actualizar(vehiculo);
            
            System.out.println("\n¡Vehículo actualizado exitosamente!");
            mostrarVehiculo(actualizado);
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de número inválido.");
        } catch (Exception e) {
            System.out.println("Error al actualizar vehículo: " + e.getMessage());
        }
    }

    private void eliminarVehiculo() {
        System.out.println("\n========== ELIMINAR VEHÍCULO ==========");
        System.out.print("Ingrese el ID del vehículo a eliminar: ");
        
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Vehiculo vehiculo = buscarVehiculoUseCase.buscarPorId(id);
            
            if (vehiculo == null) {
                System.out.println("No se encontró un vehículo con ID: " + id);
                return;
            }
            
            System.out.println("\nVehículo a eliminar:");
            mostrarVehiculo(vehiculo);
            
            System.out.print("\n¿Está seguro de eliminar este vehículo? (S/N): ");
            String confirmacion = scanner.nextLine();
            
            if (confirmacion.equalsIgnoreCase("S")) {
                boolean eliminado = eliminarVehiculoUseCase.eliminar(id);
                
                if (eliminado) {
                    System.out.println("¡Vehículo eliminado exitosamente!");
                } else {
                    System.out.println("No se pudo eliminar el vehículo.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error al eliminar vehículo: " + e.getMessage());
        }
    }

    private void mostrarVehiculo(Vehiculo vehiculo) {
        System.out.println("\n--- Datos del Vehículo ---");
        System.out.println("ID: " + vehiculo.getId());
        System.out.println("Placa: " + vehiculo.getPlaca());
        System.out.println("ID Marca: " + vehiculo.getMarcaId());
        System.out.println("ID Cliente: " + vehiculo.getClienteId());
        System.out.println("--------------------------");
    }

    private void esperarEnter() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
