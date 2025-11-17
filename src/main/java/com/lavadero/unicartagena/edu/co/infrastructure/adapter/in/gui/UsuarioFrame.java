package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Usuario;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.BuscarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.CrearUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.EliminarUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.ModificarUsuarioUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioFrame extends JFrame {

    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final BuscarUsuarioUseCase buscarUsuarioUseCase;
    private final ModificarUsuarioUseCase modificarUsuarioUseCase;
    private final EliminarUsuarioUseCase eliminarUsuarioUseCase;

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear, btnModificar, btnEliminar, btnRefrescar, btnCambiarPassword;

    public UsuarioFrame(CrearUsuarioUseCase crearUsuarioUseCase,
                       BuscarUsuarioUseCase buscarUsuarioUseCase,
                       ModificarUsuarioUseCase modificarUsuarioUseCase,
                       EliminarUsuarioUseCase eliminarUsuarioUseCase) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.buscarUsuarioUseCase = buscarUsuarioUseCase;
        this.modificarUsuarioUseCase = modificarUsuarioUseCase;
        this.eliminarUsuarioUseCase = eliminarUsuarioUseCase;

        inicializarComponentes();
        cargarUsuarios();
    }

    private void inicializarComponentes() {
        setTitle("Gestión de Usuarios");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSuperior.setBackground(new Color(240, 240, 240));

        btnCrear = crearBotonEstilizado("Crear Usuario", new Color(76, 175, 80));
        btnModificar = crearBotonEstilizado("Modificar", new Color(33, 150, 243));
        btnCambiarPassword = crearBotonEstilizado("Cambiar Contraseña", new Color(255, 152, 0));
        btnEliminar = crearBotonEstilizado("Eliminar", new Color(244, 67, 54));
        btnRefrescar = crearBotonEstilizado("Refrescar", new Color(158, 158, 158));

        btnCrear.addActionListener(e -> abrirFormularioCrear());
        btnModificar.addActionListener(e -> abrirFormularioModificar());
        btnCambiarPassword.addActionListener(e -> abrirFormularioCambiarPassword());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnRefrescar.addActionListener(e -> cargarUsuarios());

        panelSuperior.add(btnCrear);
        panelSuperior.add(btnModificar);
        panelSuperior.add(btnCambiarPassword);
        panelSuperior.add(btnEliminar);
        panelSuperior.add(btnRefrescar);

        String[] columnas = {"ID", "Username", "Pregunta Seguridad", "Empresa ID", "Creado", "Modificado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.setRowHeight(25);
        tablaUsuarios.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(160, 40));
        return boton;
    }

    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        try {
            List<Usuario> usuarios = buscarUsuarioUseCase.listarTodos();
            for (Usuario usuario : usuarios) {
                Object[] fila = {
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getQuestionSecurity(),
                    usuario.getEmpresaId(),
                    usuario.getCreado(),
                    usuario.getModificado()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar usuarios: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioCrear() {
        UsuarioFormularioDialog dialogo = new UsuarioFormularioDialog(this, crearUsuarioUseCase, null);
        dialogo.setVisible(true);
        if (dialogo.isGuardado()) {
            cargarUsuarios();
        }
    }

    private void abrirFormularioModificar() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un usuario de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
        try {
            Usuario usuario = buscarUsuarioUseCase.buscarPorId(id);
            if (usuario != null) {
                UsuarioFormularioDialog dialogo = new UsuarioFormularioDialog(
                    this,
                    modificarUsuarioUseCase,
                    usuario
                );
                dialogo.setVisible(true);
                if (dialogo.isGuardado()) {
                    cargarUsuarios();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar usuario: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioCambiarPassword() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un usuario de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
        String username = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JPasswordField txtPasswordActual = new JPasswordField();
        JPasswordField txtPasswordNueva = new JPasswordField();
        JPasswordField txtConfirmarPassword = new JPasswordField();

        panel.add(new JLabel("Contraseña Actual:"));
        panel.add(txtPasswordActual);
        panel.add(new JLabel("Nueva Contraseña:"));
        panel.add(txtPasswordNueva);
        panel.add(new JLabel("Confirmar Contraseña:"));
        panel.add(txtConfirmarPassword);

        int result = JOptionPane.showConfirmDialog(this, panel,
            "Cambiar Contraseña - " + username,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String passwordActual = new String(txtPasswordActual.getPassword());
            String passwordNueva = new String(txtPasswordNueva.getPassword());
            String confirmar = new String(txtConfirmarPassword.getPassword());

            if (!passwordNueva.equals(confirmar)) {
                JOptionPane.showMessageDialog(this,
                    "Las contraseñas nuevas no coinciden",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                modificarUsuarioUseCase.cambiarPassword(id, passwordActual, passwordNueva);
                JOptionPane.showMessageDialog(this,
                    "Contraseña cambiada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al cambiar contraseña: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un usuario de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
        String username = (String) modeloTabla.getValueAt(filaSeleccionada, 1);

        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar el usuario '" + username + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                eliminarUsuarioUseCase.eliminar(id);
                JOptionPane.showMessageDialog(this,
                    "Usuario eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarUsuarios();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar usuario: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
