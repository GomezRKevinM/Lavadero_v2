package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;
import com.lavadero.unicartagena.edu.co.domain.model.personas.Cliente;
import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import com.lavadero.unicartagena.edu.co.domain.model.shared.TipoIdentificacion;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.BuscarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.CrearClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.EliminarClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.ModificarClienteUseCase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
public class ClienteFrame extends JFrame {
    private final CrearClienteUseCase crearClienteUseCase;
    private final BuscarClienteUseCase buscarClienteUseCase;
    private final ModificarClienteUseCase modificarClienteUseCase;
    private final EliminarClienteUseCase eliminarClienteUseCase;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnEditar, btnEliminar, btnRefrescar, btnBuscar;
    private JTextField txtBuscar;
    public ClienteFrame(CrearClienteUseCase crearClienteUseCase,
                       BuscarClienteUseCase buscarClienteUseCase,
                       ModificarClienteUseCase modificarClienteUseCase,
                       EliminarClienteUseCase eliminarClienteUseCase) {
        this.crearClienteUseCase = crearClienteUseCase;
        this.buscarClienteUseCase = buscarClienteUseCase;
        this.modificarClienteUseCase = modificarClienteUseCase;
        this.eliminarClienteUseCase = eliminarClienteUseCase;
        inicializarComponentes();
        cargarClientes();
    }
    private void inicializarComponentes() {
        setTitle("Sistema de Gestión de Clientes - Lavadero ABC");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
        aplicarEstilos();
    }
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnBuscar = new JButton("Buscar por ID");
        btnBuscar.setBackground(new Color(41, 128, 185));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setOpaque(true);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 13));
        btnBuscar.addActionListener(e -> buscarCliente());
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panel.add(panelBusqueda, BorderLayout.CENTER);
        return panel;
    }
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        String[] columnas = {"ID", "Nombre Completo", "Identificación", "Teléfono", "Correo", "Empresa ID"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaClientes.setRowHeight(25);
        tablaClientes.getTableHeader().setReorderingAllowed(false);
        tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(200);
        tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(80);
        tablaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarClienteSeleccionado();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnNuevo = new JButton("Nuevo Cliente");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnRefrescar = new JButton("Refrescar");
        btnNuevo.setBackground(new Color(46, 204, 113));
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setOpaque(true);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setFont(new Font("Arial", Font.BOLD, 14));
        btnEditar.setBackground(new Color(52, 152, 219));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setOpaque(true);
        btnEditar.setBorderPainted(false);
        btnEditar.setFocusPainted(false);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefrescar.setBackground(new Color(149, 165, 166));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setOpaque(true);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 14));
        Dimension btnSize = new Dimension(150, 35);
        btnNuevo.setPreferredSize(btnSize);
        btnEditar.setPreferredSize(btnSize);
        btnEliminar.setPreferredSize(btnSize);
        btnRefrescar.setPreferredSize(btnSize);
        btnNuevo.addActionListener(e -> abrirFormularioNuevo());
        btnEditar.addActionListener(e -> editarClienteSeleccionado());
        btnEliminar.addActionListener(e -> eliminarClienteSeleccionado());
        btnRefrescar.addActionListener(e -> cargarClientes());
        panel.add(btnNuevo);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnRefrescar);
        return panel;
    }
    private void aplicarEstilos() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cargarClientes() {
        try {
            List<Cliente> clientes = buscarClienteUseCase.listarTodos();
            modeloTabla.setRowCount(0);
            for (Cliente cliente : clientes) {
                InfoPersonal info = cliente.getInfoPersonal();
                Object[] fila = {
                    cliente.getId(),
                    info.getNombreCompleto(),
                    info.getIdentificacion(),
                    info.getTelefono(),
                    info.getCorreo(),
                    cliente.getEmpresaId()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar clientes: " + e.getMessage());
        }
    }
    private void buscarCliente() {
        String idText = txtBuscar.getText().trim();
        if (idText.isEmpty()) {
            mostrarAdvertencia("Por favor ingrese un ID para buscar");
            return;
        }
        try {
            Long id = Long.parseLong(idText);
            Cliente cliente = buscarClienteUseCase.buscarPorId(id);
            if (cliente != null) {
                modeloTabla.setRowCount(0);
                InfoPersonal info = cliente.getInfoPersonal();
                Object[] fila = {
                    cliente.getId(),
                    info.getNombreCompleto(),
                    info.getIdentificacion(),
                    info.getTelefono(),
                    info.getCorreo(),
                    cliente.getEmpresaId()
                };
                modeloTabla.addRow(fila);
                tablaClientes.setRowSelectionInterval(0, 0);
            } else {
                mostrarAdvertencia("No se encontró un cliente con ID: " + id);
            }
        } catch (NumberFormatException e) {
            mostrarError("El ID debe ser un número válido");
        } catch (Exception e) {
            mostrarError("Error al buscar cliente: " + e.getMessage());
        }
    }
    private void abrirFormularioNuevo() {
        ClienteFormularioDialog dialogo = new ClienteFormularioDialog(
            this,
            crearClienteUseCase,
            modificarClienteUseCase,
            null
        );
        dialogo.setVisible(true);
        if (dialogo.isGuardado()) {
            cargarClientes();
        }
    }
    private void editarClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Por favor seleccione un cliente de la tabla");
            return;
        }
        try {
            Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
            Cliente cliente = buscarClienteUseCase.buscarPorId(id);
            if (cliente != null) {
                ClienteFormularioDialog dialogo = new ClienteFormularioDialog(
                    this,
                    crearClienteUseCase,
                    modificarClienteUseCase,
                    cliente
                );
                dialogo.setVisible(true);
                if (dialogo.isGuardado()) {
                    cargarClientes();
                }
            } else {
                mostrarError("No se pudo cargar el cliente");
            }
        } catch (Exception e) {
            mostrarError("Error al editar cliente: " + e.getMessage());
        }
    }
    private void eliminarClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Por favor seleccione un cliente de la tabla");
            return;
        }
        try {
            Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar al cliente:\n" + nombre + " (ID: " + id + ")?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirmacion == JOptionPane.YES_OPTION) {
                eliminarClienteUseCase.eliminar(id);
                mostrarExito("Cliente eliminado exitosamente");
                cargarClientes();
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar cliente: " + e.getMessage());
        }
    }
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
