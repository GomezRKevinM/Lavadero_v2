package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.cli;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Usuario;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.BuscarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.CrearUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.EliminarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.ModificarUsuarioUseCase;

import java.util.List;
import java.util.Scanner;

public class UsuarioController {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final BuscarUsuarioUseCase buscarUsuarioUseCase;
    private final ModificarUsuarioUseCase modificarUsuarioUseCase;
    private final EliminarUsuarioUseCase eliminarUsuarioUseCase;
    private final Scanner scanner;

    public UsuarioController(CrearUsuarioUseCase crearUsuarioUseCase,
                            BuscarUsuarioUseCase buscarUsuarioUseCase,
                            ModificarUsuarioUseCase modificarUsuarioUseCase,
                            EliminarUsuarioUseCase eliminarUsuarioUseCase) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.buscarUsuarioUseCase = buscarUsuarioUseCase;
        this.modificarUsuarioUseCase = modificarUsuarioUseCase;
        this.eliminarUsuarioUseCase = eliminarUsuarioUseCase;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== GESTIÓN DE USUARIOS ===");
            System.out.println("1. Crear Usuario");
            System.out.println("2. Buscar Usuario");
            System.out.println("3. Listar Todos los Usuarios");
            System.out.println("4. Modificar Usuario");
            System.out.println("5. Cambiar Contraseña");
            System.out.println("6. Eliminar Usuario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    buscarUsuario();
                    break;
                case 3:
                    listarUsuarios();
                    break;
                case 4:
                    modificarUsuario();
                    break;
                case 5:
                    cambiarPassword();
                    break;
                case 6:
                    eliminarUsuario();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private void crearUsuario() {
        System.out.println("\n--- Crear Usuario ---");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Pregunta de seguridad: ");
        String questionSecurity = scanner.nextLine();

        System.out.print("Respuesta de seguridad: ");
        String answerSecurity = scanner.nextLine();

        System.out.print("ID de empresa: ");
        Long empresaId = scanner.nextLong();
        scanner.nextLine();

        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(username);
            usuario.setPassword(password);
            usuario.setQuestionSecurity(questionSecurity);
            usuario.setAnswerSecurity(answerSecurity);
            usuario.setEmpresaId(empresaId);

            Usuario usuarioCreado = crearUsuarioUseCase.crear(usuario);
            System.out.println("✓ Usuario creado exitosamente con ID: " + usuarioCreado.getId());
        } catch (Exception e) {
            System.err.println("✗ Error al crear usuario: " + e.getMessage());
        }
    }

    private void buscarUsuario() {
        System.out.println("\n--- Buscar Usuario ---");
        System.out.println("1. Por ID");
        System.out.println("2. Por Username");
        System.out.print("Seleccione: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        try {
            Usuario usuario = null;
            if (opcion == 1) {
                System.out.print("ID: ");
                Long id = scanner.nextLong();
                scanner.nextLine();
                usuario = buscarUsuarioUseCase.buscarPorId(id);
            } else if (opcion == 2) {
                System.out.print("Username: ");
                String username = scanner.nextLine();
                usuario = buscarUsuarioUseCase.buscarPorUsername(username);
            }

            if (usuario != null) {
                mostrarUsuario(usuario);
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (Exception e) {
            System.err.println("✗ Error al buscar usuario: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        System.out.println("\n--- Lista de Usuarios ---");
        try {
            List<Usuario> usuarios = buscarUsuarioUseCase.listarTodos();
            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados");
            } else {
                for (Usuario usuario : usuarios) {
                    System.out.println("---------------------");
                    mostrarUsuario(usuario);
                }
            }
        } catch (Exception e) {
            System.err.println("✗ Error al listar usuarios: " + e.getMessage());
        }
    }

    private void modificarUsuario() {
        System.out.println("\n--- Modificar Usuario ---");
        System.out.print("ID del usuario a modificar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            Usuario usuario = buscarUsuarioUseCase.buscarPorId(id);
            if (usuario == null) {
                System.out.println("Usuario no encontrado");
                return;
            }

            System.out.println("Usuario actual:");
            mostrarUsuario(usuario);

            System.out.print("\nNuevo username (enter para mantener): ");
            String username = scanner.nextLine();
            if (!username.isEmpty()) {
                usuario.setUsername(username);
            }

            System.out.print("Nueva pregunta de seguridad (enter para mantener): ");
            String question = scanner.nextLine();
            if (!question.isEmpty()) {
                usuario.setQuestionSecurity(question);
            }

            System.out.print("¿Cambiar respuesta de seguridad? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("Nueva respuesta: ");
                usuario.setAnswerSecurity(scanner.nextLine());
            }

            Usuario actualizado = modificarUsuarioUseCase.actualizar(usuario);
            System.out.println("✓ Usuario actualizado exitosamente");
            mostrarUsuario(actualizado);
        } catch (Exception e) {
            System.err.println("✗ Error al modificar usuario: " + e.getMessage());
        }
    }

    private void cambiarPassword() {
        System.out.println("\n--- Cambiar Contraseña ---");
        System.out.print("ID del usuario: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Contraseña actual: ");
        String passwordActual = scanner.nextLine();

        System.out.print("Nueva contraseña: ");
        String passwordNueva = scanner.nextLine();

        System.out.print("Confirmar nueva contraseña: ");
        String confirmar = scanner.nextLine();

        if (!passwordNueva.equals(confirmar)) {
            System.err.println("✗ Las contraseñas no coinciden");
            return;
        }

        try {
            modificarUsuarioUseCase.cambiarPassword(id, passwordActual, passwordNueva);
            System.out.println("✓ Contraseña cambiada exitosamente");
        } catch (Exception e) {
            System.err.println("✗ Error al cambiar contraseña: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        System.out.println("\n--- Eliminar Usuario ---");
        System.out.print("ID del usuario a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            Usuario usuario = buscarUsuarioUseCase.buscarPorId(id);
            if (usuario == null) {
                System.out.println("Usuario no encontrado");
                return;
            }

            System.out.println("Usuario a eliminar:");
            mostrarUsuario(usuario);

            System.out.print("\n¿Está seguro de eliminar este usuario? (s/n): ");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("s")) {
                eliminarUsuarioUseCase.eliminar(id);
                System.out.println("✓ Usuario eliminado exitosamente");
            } else {
                System.out.println("Operación cancelada");
            }
        } catch (Exception e) {
            System.err.println("✗ Error al eliminar usuario: " + e.getMessage());
        }
    }

    private void mostrarUsuario(Usuario usuario) {
        System.out.println("ID: " + usuario.getId());
        System.out.println("Username: " + usuario.getUsername());
        System.out.println("Pregunta de seguridad: " + usuario.getQuestionSecurity());
        System.out.println("Empresa ID: " + usuario.getEmpresaId());
        System.out.println("Creado: " + usuario.getCreado());
        System.out.println("Modificado: " + usuario.getModificado());
    }
}
