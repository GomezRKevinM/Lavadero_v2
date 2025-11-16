package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;
import com.lavadero.unicartagena.edu.co.domain.model.personas.Cliente;
import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import com.lavadero.unicartagena.edu.co.domain.model.shared.TipoIdentificacion;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.BuscarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.CrearClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.EliminarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.ModificarClienteUseCase;
import java.util.List;
import java.util.Scanner;
public class ClienteController {
    private final CrearClienteUseCase crearClienteUseCase;
    private final BuscarClienteUseCase buscarClienteUseCase;
    private final ModificarClienteUseCase modificarClienteUseCase;
    private final EliminarClienteUseCase eliminarClienteUseCase;
    private final Scanner scanner;
    public ClienteController(
            CrearClienteUseCase crearClienteUseCase,
            BuscarClienteUseCase buscarClienteUseCase,
            ModificarClienteUseCase modificarClienteUseCase,
            EliminarClienteUseCase eliminarClienteUseCase) {
        this.crearClienteUseCase = crearClienteUseCase;
        this.buscarClienteUseCase = buscarClienteUseCase;
        this.modificarClienteUseCase = modificarClienteUseCase;
        this.eliminarClienteUseCase = eliminarClienteUseCase;
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
                    crearCliente();
                    break;
                case 4:
                    modificarCliente();
                    break;
                case 5:
                    eliminarCliente();
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
        System.out.println("     GESTIÓN DE CLIENTES - LAVADERO    ");
        System.out.println("========================================");
        System.out.println("1. Listar todos los clientes");
        System.out.println("2. Buscar cliente por ID");
        System.out.println("3. Crear nuevo cliente");
        System.out.println("4. Modificar cliente");
        System.out.println("5. Eliminar cliente");
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
        System.out.println("\n========== LISTA DE CLIENTES ==========");
        try {
            List<Cliente> clientes = buscarClienteUseCase.listarTodos();
            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados.");
            } else {
                System.out.printf("%-5s | %-25s | %-15s | %-15s%n",
                    "ID", "Nombre Completo", "Identificación", "Empresa ID");
                System.out.println("------------------------------------------------------------------------------");
                for (Cliente cliente : clientes) {
                    InfoPersonal info = cliente.getInfoPersonal();
                    System.out.printf("%-5d | %-25s | %-15s | %-15d%n",
                        cliente.getId(),
                        info.getNombreCompleto(),
                        info.getIdentificacion(),
                        cliente.getEmpresaId());
                }
                System.out.println("\nTotal de clientes: " + clientes.size());
            }
        } catch (Exception e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
    }
    private void buscarPorId() {
        System.out.println("\n========== BUSCAR CLIENTE ==========");
        System.out.print("Ingrese el ID del cliente: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Cliente cliente = buscarClienteUseCase.buscarPorId(id);
            if (cliente != null) {
                System.out.println("\nCliente encontrado:");
                mostrarDetallesCliente(cliente);
            } else {
                System.out.println("No se encontró un cliente con ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Debe ser un número.");
        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
    }
    private void crearCliente() {
        System.out.println("\n========== CREAR NUEVO CLIENTE ==========");
        try {
            System.out.println("\n--- Información Personal ---");
            System.out.print("Tipo de identificación (1=CC, 2=CE, 3=TI, 4=PA, 5=NIT): ");
            Long tipoIdValue = Long.parseLong(scanner.nextLine());
            TipoIdentificacion tipoId = new TipoIdentificacion();
            tipoId.setId(tipoIdValue);
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellidos: ");
            String apellidos = scanner.nextLine();
            System.out.print("Número de identificación: ");
            String identificacion = scanner.nextLine();
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();
            System.out.print("Correo electrónico: ");
            String correo = scanner.nextLine();
            System.out.print("Dirección: ");
            String direccion = scanner.nextLine();
            System.out.println("\n--- Información del Cliente ---");
            System.out.print("ID de la empresa: ");
            Long empresaId = Long.parseLong(scanner.nextLine());
            InfoPersonal infoPersonal = new InfoPersonal(
                tipoId, nombre, apellidos, identificacion,
                telefono, correo, direccion
            );
            Cliente cliente = new Cliente();
            cliente.setInfoPersonal(infoPersonal);
            cliente.setEmpresaId(empresaId);
            Cliente clienteCreado = crearClienteUseCase.crear(cliente);
            System.out.println("\n¡Cliente creado exitosamente!");
            mostrarDetallesCliente(clienteCreado);
        } catch (NumberFormatException e) {
            System.out.println("Error: Los valores numéricos no son válidos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al crear cliente: " + e.getMessage());
        }
    }
    private void modificarCliente() {
        System.out.println("\n========== MODIFICAR CLIENTE ==========");
        System.out.print("Ingrese el ID del cliente a modificar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Cliente clienteExistente = buscarClienteUseCase.buscarPorId(id);
            if (clienteExistente == null) {
                System.out.println("No se encontró un cliente con ID: " + id);
                return;
            }
            System.out.println("\nCliente actual:");
            mostrarDetallesCliente(clienteExistente);
            System.out.println("\n--- Actualizar Información Personal ---");
            InfoPersonal infoActual = clienteExistente.getInfoPersonal();
            System.out.print("Tipo de identificación (actual: " + infoActual.getTipoIdentificacion().getId() + "): ");
            String tipoInput = scanner.nextLine();
            Long tipoIdValue = tipoInput.isEmpty() ? infoActual.getTipoIdentificacion().getId() : Long.parseLong(tipoInput);
            TipoIdentificacion tipoId = new TipoIdentificacion();
            tipoId.setId(tipoIdValue);
            System.out.print("Nombre (actual: " + infoActual.getNombre() + "): ");
            String nombre = scanner.nextLine();
            nombre = nombre.isEmpty() ? infoActual.getNombre() : nombre;
            System.out.print("Apellidos (actual: " + infoActual.getApellidos() + "): ");
            String apellidos = scanner.nextLine();
            apellidos = apellidos.isEmpty() ? infoActual.getApellidos() : apellidos;
            System.out.print("Identificación (actual: " + infoActual.getIdentificacion() + "): ");
            String identificacion = scanner.nextLine();
            identificacion = identificacion.isEmpty() ? infoActual.getIdentificacion() : identificacion;
            System.out.print("Teléfono (actual: " + infoActual.getTelefono() + "): ");
            String telefono = scanner.nextLine();
            telefono = telefono.isEmpty() ? infoActual.getTelefono() : telefono;
            System.out.print("Correo (actual: " + infoActual.getCorreo() + "): ");
            String correo = scanner.nextLine();
            correo = correo.isEmpty() ? infoActual.getCorreo() : correo;
            System.out.print("Dirección (actual: " + infoActual.getDireccion() + "): ");
            String direccion = scanner.nextLine();
            direccion = direccion.isEmpty() ? infoActual.getDireccion() : direccion;
            System.out.println("\n--- Actualizar Empresa ---");
            System.out.print("ID de empresa (actual: " + clienteExistente.getEmpresaId() + "): ");
            String empresaInput = scanner.nextLine();
            Long empresaId = empresaInput.isEmpty() ? clienteExistente.getEmpresaId() : Long.parseLong(empresaInput);
            InfoPersonal infoActualizado = new InfoPersonal(
                infoActual.getInfoPersonalId(),
                tipoId, nombre, apellidos, identificacion,
                telefono, correo, direccion
            );
            Cliente clienteActualizado = new Cliente();
            clienteActualizado.setId(id);
            clienteActualizado.setInfoPersonal(infoActualizado);
            clienteActualizado.setEmpresaId(empresaId);
            modificarClienteUseCase.actualizar(clienteActualizado);
            System.out.println("\n¡Cliente actualizado exitosamente!");
            mostrarDetallesCliente(clienteActualizado);
        } catch (NumberFormatException e) {
            System.out.println("Error: Los valores numéricos no son válidos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al modificar cliente: " + e.getMessage());
        }
    }
    private void eliminarCliente() {
        System.out.println("\n========== ELIMINAR CLIENTE ==========");
        System.out.print("Ingrese el ID del cliente a eliminar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Cliente cliente = buscarClienteUseCase.buscarPorId(id);
            if (cliente == null) {
                System.out.println("No se encontró un cliente con ID: " + id);
                return;
            }
            System.out.println("\nCliente a eliminar:");
            mostrarDetallesCliente(cliente);
            System.out.print("\n¿Está seguro de eliminar este cliente? (S/N): ");
            String confirmacion = scanner.nextLine();
            if (confirmacion.equalsIgnoreCase("S")) {
                boolean eliminado = eliminarClienteUseCase.eliminar(id);
                if (eliminado) {
                    System.out.println("\n¡Cliente eliminado exitosamente!");
                } else {
                    System.out.println("No se pudo eliminar el cliente.");
                }
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número válido.");
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
    private void mostrarDetallesCliente(Cliente cliente) {
        InfoPersonal info = cliente.getInfoPersonal();
        System.out.println("----------------------------------------");
        System.out.println("ID Cliente:          " + cliente.getId());
        System.out.println("Empresa ID:          " + cliente.getEmpresaId());
        System.out.println("\n--- Información Personal ---");
        System.out.println("Nombre Completo:     " + info.getNombreCompleto());
        System.out.println("Tipo ID:             " + info.getTipoIdentificacion().getId());
        System.out.println("Identificación:      " + info.getIdentificacion());
        System.out.println("Teléfono:            " + info.getTelefono());
        System.out.println("Correo:              " + info.getCorreo());
        System.out.println("Dirección:           " + info.getDireccion());
        System.out.println("----------------------------------------");
    }
    private void esperarEnter() {
        System.out.print("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
}
