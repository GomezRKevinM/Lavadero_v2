package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.EmpleadoCompleto;
import com.lavadero.unicartagena.edu.co.domain.model.empleados.Empleado;
import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import com.lavadero.unicartagena.edu.co.domain.model.shared.TipoIdentificacion;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.*;

import java.util.List;
import java.util.Scanner;

public class EmpleadoController {
    
    private final CrearEmpleadoUseCase crearEmpleadoUseCase;
    private final BuscarEmpleadoUseCase buscarEmpleadoUseCase;
    private final ActualizarEmpleadoUseCase actualizarEmpleadoUseCase;
    private final EliminarEmpleadoUseCase eliminarEmpleadoUseCase;
    private final Scanner scanner;

    public EmpleadoController(
            CrearEmpleadoUseCase crearEmpleadoUseCase,
            BuscarEmpleadoUseCase buscarEmpleadoUseCase,
            ActualizarEmpleadoUseCase actualizarEmpleadoUseCase,
            EliminarEmpleadoUseCase eliminarEmpleadoUseCase) {
        this.crearEmpleadoUseCase = crearEmpleadoUseCase;
        this.buscarEmpleadoUseCase = buscarEmpleadoUseCase;
        this.actualizarEmpleadoUseCase = actualizarEmpleadoUseCase;
        this.eliminarEmpleadoUseCase = eliminarEmpleadoUseCase;
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
                    buscarPorNombre();
                    break;
                case 4:
                    buscarPorIdentificacion();
                    break;
                case 5:
                    listarConUsuario();
                    break;
                case 6:
                    listarSinUsuario();
                    break;
                case 7:
                    crearEmpleado();
                    break;
                case 8:
                    actualizarEmpleado();
                    break;
                case 9:
                    eliminarEmpleado();
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
        System.out.println("\n=========================================");
        System.out.println("     GESTIÓN DE EMPLEADOS - LAVADERO    ");
        System.out.println("=========================================");
        System.out.println("1. Listar todos los empleados");
        System.out.println("2. Buscar empleado por ID");
        System.out.println("3. Buscar empleados por nombre");
        System.out.println("4. Buscar empleado por identificación");
        System.out.println("5. Listar empleados con usuario");
        System.out.println("6. Listar empleados sin usuario");
        System.out.println("7. Crear nuevo empleado");
        System.out.println("8. Actualizar empleado");
        System.out.println("9. Eliminar empleado");
        System.out.println("0. Salir");
        System.out.println("=========================================");
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
        System.out.println("\n--- LISTADO DE EMPLEADOS ---");
        try {
            List<EmpleadoCompleto> empleados = buscarEmpleadoUseCase.listarTodos();
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados registrados.");
            } else {
                for (EmpleadoCompleto emp : empleados) {
                    mostrarEmpleado(emp);
                }
                System.out.println("\nTotal: " + empleados.size() + " empleado(s)");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarPorId() {
        System.out.print("\nIngrese el ID del empleado: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            EmpleadoCompleto empleado = buscarEmpleadoUseCase.buscarPorId(id);
            if (empleado != null) {
                mostrarEmpleadoDetallado(empleado);
            } else {
                System.out.println("Empleado no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarPorNombre() {
        System.out.print("\nIngrese el nombre o apellido a buscar: ");
        String termino = scanner.nextLine();
        try {
            List<EmpleadoCompleto> empleados = buscarEmpleadoUseCase.buscarPorNombre(termino);
            if (empleados.isEmpty()) {
                System.out.println("No se encontraron empleados.");
            } else {
                for (EmpleadoCompleto emp : empleados) {
                    mostrarEmpleado(emp);
                }
                System.out.println("\nTotal: " + empleados.size() + " empleado(s)");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarPorIdentificacion() {
        System.out.print("\nIngrese el número de identificación: ");
        String identificacion = scanner.nextLine();
        try {
            EmpleadoCompleto empleado = buscarEmpleadoUseCase.buscarPorIdentificacion(identificacion);
            if (empleado != null) {
                mostrarEmpleadoDetallado(empleado);
            } else {
                System.out.println("Empleado no encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarConUsuario() {
        System.out.println("\n--- EMPLEADOS CON USUARIO DEL SISTEMA ---");
        try {
            List<EmpleadoCompleto> empleados = buscarEmpleadoUseCase.buscarConUsuario();
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados con usuario.");
            } else {
                for (EmpleadoCompleto emp : empleados) {
                    mostrarEmpleado(emp);
                }
                System.out.println("\nTotal: " + empleados.size() + " empleado(s)");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarSinUsuario() {
        System.out.println("\n--- EMPLEADOS SIN USUARIO DEL SISTEMA ---");
        try {
            List<EmpleadoCompleto> empleados = buscarEmpleadoUseCase.buscarSinUsuario();
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados sin usuario.");
            } else {
                for (EmpleadoCompleto emp : empleados) {
                    mostrarEmpleado(emp);
                }
                System.out.println("\nTotal: " + empleados.size() + " empleado(s)");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearEmpleado() {
        System.out.println("\n--- CREAR NUEVO EMPLEADO ---");
        System.out.println("Nota: Debe tener la información personal ya registrada");
        
        try {
            System.out.print("ID de información personal: ");
            Long infoPersonalId = Long.parseLong(scanner.nextLine());
            
            System.out.print("ID de usuario (deje vacío si no tiene): ");
            String usuarioIdStr = scanner.nextLine().trim();
            Long usuarioId = usuarioIdStr.isEmpty() ? null : Long.parseLong(usuarioIdStr);
            
            Empleado empleado = new Empleado(null, infoPersonalId, usuarioId, null);
            empleado = crearEmpleadoUseCase.crear(empleado);
            
            System.out.println("\n¡Empleado creado exitosamente!");
            System.out.println("ID asignado: " + empleado.getId());
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de número inválido.");
        } catch (Exception e) {
            System.out.println("Error al crear empleado: " + e.getMessage());
        }
    }

    private void actualizarEmpleado() {
        System.out.println("\n--- ACTUALIZAR EMPLEADO ---");
        
        try {
            System.out.print("ID del empleado a actualizar: ");
            Long id = Long.parseLong(scanner.nextLine());
            
            EmpleadoCompleto actual = buscarEmpleadoUseCase.buscarPorId(id);
            if (actual == null) {
                System.out.println("Empleado no encontrado.");
                return;
            }
            
            System.out.println("\nDatos actuales:");
            mostrarEmpleadoDetallado(actual);
            
            System.out.print("\nNuevo ID de información personal (" + actual.getInfoPersonalId() + "): ");
            String infoStr = scanner.nextLine().trim();
            Long infoPersonalId = infoStr.isEmpty() ? actual.getInfoPersonalId() : Long.parseLong(infoStr);
            
            System.out.print("Nuevo ID de usuario (" + (actual.getUsuarioId() != null ? actual.getUsuarioId() : "ninguno") + "): ");
            String usuarioStr = scanner.nextLine().trim();
            Long usuarioId = usuarioStr.isEmpty() ? actual.getUsuarioId() : 
                             (usuarioStr.equalsIgnoreCase("ninguno") ? null : Long.parseLong(usuarioStr));
            
            Empleado empleado = new Empleado(id, infoPersonalId, usuarioId, null);
            actualizarEmpleadoUseCase.actualizar(empleado);
            
            System.out.println("\n¡Empleado actualizado exitosamente!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Formato de número inválido.");
        } catch (Exception e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
        }
    }

    private void eliminarEmpleado() {
        System.out.println("\n--- ELIMINAR EMPLEADO ---");
        
        try {
            System.out.print("ID del empleado a eliminar: ");
            Long id = Long.parseLong(scanner.nextLine());
            
            EmpleadoCompleto empleado = buscarEmpleadoUseCase.buscarPorId(id);
            if (empleado == null) {
                System.out.println("Empleado no encontrado.");
                return;
            }
            
            System.out.println("\nEmpleado a eliminar:");
            mostrarEmpleadoDetallado(empleado);
            
            System.out.print("\n¿Está seguro de eliminar este empleado? (S/N): ");
            String confirmacion = scanner.nextLine().trim();
            
            if (confirmacion.equalsIgnoreCase("S")) {
                if (eliminarEmpleadoUseCase.eliminar(id)) {
                    System.out.println("\n¡Empleado eliminado exitosamente!");
                } else {
                    System.out.println("\nNo se pudo eliminar el empleado.");
                }
            } else {
                System.out.println("\nOperación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID inválido.");
        } catch (Exception e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
        }
    }

    private void mostrarEmpleado(EmpleadoCompleto empleado) {
        System.out.println("\n  ID: " + empleado.getId());
        System.out.println("  Nombre: " + empleado.getNombreCompleto());
        System.out.println("  Identificación: " + empleado.getIdentificacion());
        System.out.println("  Teléfono: " + empleado.getTelefono());
        System.out.println("  Usuario: " + (empleado.tieneUsuario() ? "Sí (ID: " + empleado.getUsuarioId() + ")" : "No"));
    }

    private void mostrarEmpleadoDetallado(EmpleadoCompleto empleado) {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("  ID Empleado: " + empleado.getId());
        System.out.println("  Nombre completo: " + empleado.getNombreCompleto());
        System.out.println("  Identificación: " + empleado.getIdentificacion());
        System.out.println("  Teléfono: " + empleado.getTelefono());
        System.out.println("  Correo: " + empleado.getCorreo());
        System.out.println("  Dirección: " + empleado.getDireccion());
        System.out.println("  Info Personal ID: " + empleado.getInfoPersonalId());
        System.out.println("  Usuario ID: " + (empleado.getUsuarioId() != null ? empleado.getUsuarioId() : "Sin usuario"));
        System.out.println("  Última modificación: " + empleado.getModificado());
        System.out.println("═══════════════════════════════════════");
    }

    private void esperarEnter() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }
}
