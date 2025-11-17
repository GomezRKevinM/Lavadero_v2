package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.EmpleadoCompleto;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.*;
import com.lavadero.unicartagena.edu.co.infrastructure.config.EmpleadoConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Interfaz gráfica para gestión de empleados.
 */
public class EmpleadoFrame extends JFrame {
    
    private final CrearEmpleadoUseCase crearEmpleadoUseCase;
    private final BuscarEmpleadoUseCase buscarEmpleadoUseCase;
    private final ActualizarEmpleadoUseCase actualizarEmpleadoUseCase;
    private final EliminarEmpleadoUseCase eliminarEmpleadoUseCase;
    private final GestionarCargoUseCase gestionarCargoUseCase;
    
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnEditar, btnEliminar, btnRefrescar;
    private JButton btnBuscar, btnGestionarCargos;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;

    public EmpleadoFrame(CrearEmpleadoUseCase crearEmpleadoUseCase,
                        BuscarEmpleadoUseCase buscarEmpleadoUseCase,
                        ActualizarEmpleadoUseCase actualizarEmpleadoUseCase,
                        EliminarEmpleadoUseCase eliminarEmpleadoUseCase,
                        GestionarCargoUseCase gestionarCargoUseCase) {
        this.crearEmpleadoUseCase = crearEmpleadoUseCase;
        this.buscarEmpleadoUseCase = buscarEmpleadoUseCase;
        this.actualizarEmpleadoUseCase = actualizarEmpleadoUseCase;
        this.eliminarEmpleadoUseCase = eliminarEmpleadoUseCase;
        this.gestionarCargoUseCase = gestionarCargoUseCase;
        
        inicializarComponentes();
        cargarEmpleados();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Gestión de Empleados - Lavadero ABC");
        setSize(1200, 700);
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
        
        JLabel lblTitulo = new JLabel("Gestión de Empleados");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] filtros = {"Por Nombre", "Por Identificación", "Por ID", "Con Usuario", "Sin Usuario"};
        cmbFiltro = new JComboBox<>(filtros);
        cmbFiltro.setFont(new Font("Arial", Font.PLAIN, 13));
        
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(41, 128, 185));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 13));
        btnBuscar.setOpaque(true);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(e -> buscarEmpleado());
        
        btnGestionarCargos = new JButton("Gestionar Cargos");
        btnGestionarCargos.setBackground(new Color(142, 68, 173));
        btnGestionarCargos.setForeground(Color.WHITE);
        btnGestionarCargos.setFont(new Font("Arial", Font.BOLD, 13));
        btnGestionarCargos.setOpaque(true);
        btnGestionarCargos.setBorderPainted(false);
        btnGestionarCargos.setFocusPainted(false);
        btnGestionarCargos.addActionListener(e -> abrirGestionCargos());
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(cmbFiltro);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(Box.createHorizontalStrut(20));
        panelBusqueda.add(btnGestionarCargos);
        
        panel.add(panelBusqueda, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        String[] columnas = {"ID", "Nombre Completo", "Identificación", "Teléfono", "Correo", "Usuario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEmpleados.setRowHeight(30);
        tablaEmpleados.getTableHeader().setReorderingAllowed(false);
        tablaEmpleados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        
        tablaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaEmpleados.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaEmpleados.getColumnModel().getColumn(2).setPreferredWidth(120);
        tablaEmpleados.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablaEmpleados.getColumnModel().getColumn(4).setPreferredWidth(180);
        tablaEmpleados.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        tablaEmpleados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarEmpleadoSeleccionado();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        btnNuevo = crearBoton("Nuevo", new Color(39, 174, 96));
        btnEditar = crearBoton("Editar", new Color(241, 196, 15));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnRefrescar = crearBoton("Refrescar", new Color(52, 152, 219));
        
        btnNuevo.addActionListener(e -> crearNuevoEmpleado());
        btnEditar.addActionListener(e -> editarEmpleadoSeleccionado());
        btnEliminar.addActionListener(e -> eliminarEmpleadoSeleccionado());
        btnRefrescar.addActionListener(e -> cargarEmpleados());
        
        panel.add(btnNuevo);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnRefrescar);
        
        return panel;
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(120, 35));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void aplicarEstilos() {
        getContentPane().setBackground(Color.WHITE);
    }

    private void cargarEmpleados() {
        modeloTabla.setRowCount(0);
        try {
            List<EmpleadoCompleto> empleados = buscarEmpleadoUseCase.listarTodos();
            for (EmpleadoCompleto emp : empleados) {
                modeloTabla.addRow(new Object[]{
                    emp.getId(),
                    emp.getNombreCompleto(),
                    emp.getIdentificacion(),
                    emp.getTelefono(),
                    emp.getCorreo(),
                    emp.tieneUsuario() ? "Sí" : "No"
                });
            }
        } catch (Exception e) {
            mostrarError("Error al cargar empleados: " + e.getMessage());
        }
    }

    private void buscarEmpleado() {
        String filtro = (String) cmbFiltro.getSelectedItem();
        String termino = txtBuscar.getText().trim();
        
        modeloTabla.setRowCount(0);
        try {
            List<EmpleadoCompleto> empleados = null;
            
            if (filtro.equals("Por Nombre")) {
                empleados = buscarEmpleadoUseCase.buscarPorNombre(termino);
            } else if (filtro.equals("Por Identificación")) {
                EmpleadoCompleto emp = buscarEmpleadoUseCase.buscarPorIdentificacion(termino);
                if (emp != null) {
                    modeloTabla.addRow(new Object[]{
                        emp.getId(), emp.getNombreCompleto(), emp.getIdentificacion(),
                        emp.getTelefono(), emp.getCorreo(), emp.tieneUsuario() ? "Sí" : "No"
                    });
                }
                return;
            } else if (filtro.equals("Por ID")) {
                try {
                    Long id = Long.parseLong(termino);
                    EmpleadoCompleto emp = buscarEmpleadoUseCase.buscarPorId(id);
                    if (emp != null) {
                        modeloTabla.addRow(new Object[]{
                            emp.getId(), emp.getNombreCompleto(), emp.getIdentificacion(),
                            emp.getTelefono(), emp.getCorreo(), emp.tieneUsuario() ? "Sí" : "No"
                        });
                    }
                } catch (NumberFormatException e) {
                    mostrarError("ID inválido");
                }
                return;
            } else if (filtro.equals("Con Usuario")) {
                empleados = buscarEmpleadoUseCase.buscarConUsuario();
            } else if (filtro.equals("Sin Usuario")) {
                empleados = buscarEmpleadoUseCase.buscarSinUsuario();
            }
            
            if (empleados != null) {
                for (EmpleadoCompleto emp : empleados) {
                    modeloTabla.addRow(new Object[]{
                        emp.getId(), emp.getNombreCompleto(), emp.getIdentificacion(),
                        emp.getTelefono(), emp.getCorreo(), emp.tieneUsuario() ? "Sí" : "No"
                    });
                }
            }
        } catch (Exception e) {
            mostrarError("Error en búsqueda: " + e.getMessage());
        }
    }

    private void crearNuevoEmpleado() {
        EmpleadoDialog dialog = new EmpleadoDialog(this, crearEmpleadoUseCase, 
                                                     actualizarEmpleadoUseCase, null);
        dialog.setVisible(true);
        if (dialog.isConfirmado()) {
            cargarEmpleados();
        }
    }

    private void editarEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Seleccione un empleado para editar");
            return;
        }
        
        Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
        try {
            EmpleadoCompleto empleado = buscarEmpleadoUseCase.buscarPorId(id);
            if (empleado != null) {
                EmpleadoDialog dialog = new EmpleadoDialog(this, crearEmpleadoUseCase,
                                                             actualizarEmpleadoUseCase, empleado);
                dialog.setVisible(true);
                if (dialog.isConfirmado()) {
                    cargarEmpleados();
                }
            }
        } catch (Exception e) {
            mostrarError("Error al editar: " + e.getMessage());
        }
    }

    private void eliminarEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Seleccione un empleado para eliminar");
            return;
        }
        
        Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombreCompleto = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar al empleado:\n" + nombreCompleto + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                eliminarEmpleadoUseCase.eliminar(id);
                mostrarExito("Empleado eliminado exitosamente");
                cargarEmpleados();
            } catch (Exception e) {
                mostrarError("Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void abrirGestionCargos() {
        CargoDialog dialog = new CargoDialog(this, gestionarCargoUseCase);
        dialog.setVisible(true);
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            EmpleadoFrame frame = new EmpleadoFrame(
                EmpleadoConfig.getCrearEmpleadoUseCase(),
                EmpleadoConfig.getBuscarEmpleadoUseCase(),
                EmpleadoConfig.getActualizarEmpleadoUseCase(),
                EmpleadoConfig.getEliminarEmpleadoUseCase(),
                EmpleadoConfig.getGestionarCargoUseCase()
            );
            frame.setVisible(true);
        });
    }
}
