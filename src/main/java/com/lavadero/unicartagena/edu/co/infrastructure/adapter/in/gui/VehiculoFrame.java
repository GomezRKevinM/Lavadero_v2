package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.MarcaVehiculo;
import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.*;
import com.lavadero.unicartagena.edu.co.infrastructure.config.VehiculoConfig;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Interfaz gráfica para gestión de vehículos.
 */
public class VehiculoFrame extends JFrame {
    
    private final CrearVehiculoUseCase crearVehiculoUseCase;
    private final BuscarVehiculoUseCase buscarVehiculoUseCase;
    private final ActualizarVehiculoUseCase actualizarVehiculoUseCase;
    private final EliminarVehiculoUseCase eliminarVehiculoUseCase;
    private final GestionarMarcaVehiculoUseCase gestionarMarcaUseCase;
    
    private JTable tablaVehiculos;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnEditar, btnEliminar, btnRefrescar;
    private JButton btnBuscarPlaca, btnBuscarCliente, btnGestionarMarcas;
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;

    public VehiculoFrame(CrearVehiculoUseCase crearVehiculoUseCase,
                        BuscarVehiculoUseCase buscarVehiculoUseCase,
                        ActualizarVehiculoUseCase actualizarVehiculoUseCase,
                        EliminarVehiculoUseCase eliminarVehiculoUseCase,
                        GestionarMarcaVehiculoUseCase gestionarMarcaUseCase) {
        this.crearVehiculoUseCase = crearVehiculoUseCase;
        this.buscarVehiculoUseCase = buscarVehiculoUseCase;
        this.actualizarVehiculoUseCase = actualizarVehiculoUseCase;
        this.eliminarVehiculoUseCase = eliminarVehiculoUseCase;
        this.gestionarMarcaUseCase = gestionarMarcaUseCase;
        
        inicializarComponentes();
        cargarVehiculos();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Gestión de Vehículos - Lavadero ABC");
        setSize(1100, 650);
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
        
        // Título
        JLabel lblTitulo = new JLabel("Gestión de Vehículos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] filtros = {"Por Placa", "Por Cliente ID", "Por Marca ID", "Por ID"};
        cmbFiltro = new JComboBox<>(filtros);
        cmbFiltro.setFont(new Font("Arial", Font.PLAIN, 13));
        
        txtBuscar = new JTextField(15);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btnBuscarPlaca = new JButton("Buscar");
        btnBuscarPlaca.setBackground(new Color(41, 128, 185));
        btnBuscarPlaca.setForeground(Color.WHITE);
        btnBuscarPlaca.setFont(new Font("Arial", Font.BOLD, 13));
        btnBuscarPlaca.setOpaque(true);
        btnBuscarPlaca.setBorderPainted(false);
        btnBuscarPlaca.setFocusPainted(false);
        btnBuscarPlaca.addActionListener(e -> buscarVehiculo());
        
        btnGestionarMarcas = new JButton("Gestionar Marcas");
        btnGestionarMarcas.setBackground(new Color(142, 68, 173));
        btnGestionarMarcas.setForeground(Color.WHITE);
        btnGestionarMarcas.setFont(new Font("Arial", Font.BOLD, 13));
        btnGestionarMarcas.setOpaque(true);
        btnGestionarMarcas.setBorderPainted(false);
        btnGestionarMarcas.setFocusPainted(false);
        btnGestionarMarcas.addActionListener(e -> abrirGestionMarcas());
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(cmbFiltro);
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscarPlaca);
        panelBusqueda.add(Box.createHorizontalStrut(20));
        panelBusqueda.add(btnGestionarMarcas);
        
        panel.add(panelBusqueda, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        String[] columnas = {"ID", "Placa", "Marca ID", "Marca", "Cliente ID"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaVehiculos = new JTable(modeloTabla);
        tablaVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaVehiculos.setRowHeight(28);
        tablaVehiculos.getTableHeader().setReorderingAllowed(false);
        tablaVehiculos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        
        tablaVehiculos.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaVehiculos.getColumnModel().getColumn(1).setPreferredWidth(120);
        tablaVehiculos.getColumnModel().getColumn(2).setPreferredWidth(80);
        tablaVehiculos.getColumnModel().getColumn(3).setPreferredWidth(200);
        tablaVehiculos.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        tablaVehiculos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    editarVehiculoSeleccionado();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaVehiculos);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        btnNuevo = crearBoton("Nuevo Vehículo", new Color(39, 174, 96), e -> nuevoVehiculo());
        btnEditar = crearBoton("Editar", new Color(243, 156, 18), e -> editarVehiculoSeleccionado());
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60), e -> eliminarVehiculoSeleccionado());
        btnRefrescar = crearBoton("Refrescar", new Color(52, 152, 219), e -> cargarVehiculos());
        
        panel.add(btnNuevo);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnRefrescar);
        
        return panel;
    }

    private JButton crearBoton(String texto, Color color, java.awt.event.ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setPreferredSize(new Dimension(150, 40));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.addActionListener(action);
        return boton;
    }

    private void aplicarEstilos() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarVehiculos() {
        modeloTabla.setRowCount(0);
        try {
            List<Vehiculo> vehiculos = buscarVehiculoUseCase.listarTodos();
            
            for (Vehiculo vehiculo : vehiculos) {
                String nombreMarca = obtenerNombreMarca(vehiculo.getMarcaId());
                Object[] fila = {
                    vehiculo.getId(),
                    vehiculo.getPlaca(),
                    vehiculo.getMarcaId(),
                    nombreMarca,
                    vehiculo.getClienteId()
                };
                modeloTabla.addRow(fila);
            }
            
            actualizarEstadoBotones();
        } catch (Exception e) {
            mostrarError("Error al cargar vehículos: " + e.getMessage());
        }
    }

    private String obtenerNombreMarca(Integer marcaId) {
        try {
            MarcaVehiculo marca = gestionarMarcaUseCase.buscarPorId(marcaId);
            return marca != null ? marca.getMarca() : "Desconocida";
        } catch (Exception e) {
            return "Error";
        }
    }

    private void buscarVehiculo() {
        String textoBusqueda = txtBuscar.getText().trim();
        
        if (textoBusqueda.isEmpty()) {
            cargarVehiculos();
            return;
        }
        
        modeloTabla.setRowCount(0);
        
        try {
            String filtroSeleccionado = (String) cmbFiltro.getSelectedItem();
            List<Vehiculo> vehiculos = null;
            
            switch (filtroSeleccionado) {
                case "Por Placa":
                    Vehiculo v = buscarVehiculoUseCase.buscarPorPlaca(textoBusqueda);
                    if (v != null) {
                        agregarVehiculoATabla(v);
                    } else {
                        mostrarInfo("No se encontró vehículo con placa: " + textoBusqueda);
                    }
                    break;
                    
                case "Por Cliente ID":
                    vehiculos = buscarVehiculoUseCase.buscarPorCliente(Long.parseLong(textoBusqueda));
                    if (vehiculos.isEmpty()) {
                        mostrarInfo("No se encontraron vehículos para el cliente ID: " + textoBusqueda);
                    } else {
                        for (Vehiculo vehiculo : vehiculos) {
                            agregarVehiculoATabla(vehiculo);
                        }
                    }
                    break;
                    
                case "Por Marca ID":
                    vehiculos = buscarVehiculoUseCase.buscarPorMarca(Integer.parseInt(textoBusqueda));
                    if (vehiculos.isEmpty()) {
                        mostrarInfo("No se encontraron vehículos para la marca ID: " + textoBusqueda);
                    } else {
                        for (Vehiculo vehiculo : vehiculos) {
                            agregarVehiculoATabla(vehiculo);
                        }
                    }
                    break;
                    
                case "Por ID":
                    Vehiculo vehiculo = buscarVehiculoUseCase.buscarPorId(Long.parseLong(textoBusqueda));
                    if (vehiculo != null) {
                        agregarVehiculoATabla(vehiculo);
                    } else {
                        mostrarInfo("No se encontró vehículo con ID: " + textoBusqueda);
                    }
                    break;
            }
            
        } catch (NumberFormatException e) {
            mostrarError("Formato de número inválido");
        } catch (Exception e) {
            mostrarError("Error al buscar: " + e.getMessage());
        }
    }

    private void agregarVehiculoATabla(Vehiculo vehiculo) {
        String nombreMarca = obtenerNombreMarca(vehiculo.getMarcaId());
        Object[] fila = {
            vehiculo.getId(),
            vehiculo.getPlaca(),
            vehiculo.getMarcaId(),
            nombreMarca,
            vehiculo.getClienteId()
        };
        modeloTabla.addRow(fila);
    }

    private void nuevoVehiculo() {
        VehiculoDialog dialog = new VehiculoDialog(
            this, 
            null, 
            gestionarMarcaUseCase,
            crearVehiculoUseCase,
            actualizarVehiculoUseCase
        );
        dialog.setVisible(true);
        
        if (dialog.isConfirmado()) {
            cargarVehiculos();
        }
    }

    private void editarVehiculoSeleccionado() {
        int filaSeleccionada = tablaVehiculos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Debe seleccionar un vehículo para editar");
            return;
        }
        
        try {
            Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
            Vehiculo vehiculo = buscarVehiculoUseCase.buscarPorId(id);
            
            if (vehiculo != null) {
                VehiculoDialog dialog = new VehiculoDialog(
                    this, 
                    vehiculo, 
                    gestionarMarcaUseCase,
                    crearVehiculoUseCase,
                    actualizarVehiculoUseCase
                );
                dialog.setVisible(true);
                
                if (dialog.isConfirmado()) {
                    cargarVehiculos();
                }
            }
        } catch (Exception e) {
            mostrarError("Error al editar vehículo: " + e.getMessage());
        }
    }

    private void eliminarVehiculoSeleccionado() {
        int filaSeleccionada = tablaVehiculos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Debe seleccionar un vehículo para eliminar");
            return;
        }
        
        Long id = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
        String placa = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar el vehículo con placa " + placa + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                boolean eliminado = eliminarVehiculoUseCase.eliminar(id);
                
                if (eliminado) {
                    mostrarExito("Vehículo eliminado correctamente");
                    cargarVehiculos();
                } else {
                    mostrarError("No se pudo eliminar el vehículo");
                }
            } catch (Exception e) {
                mostrarError("Error al eliminar: " + e.getMessage());
            }
        }
    }

    private void abrirGestionMarcas() {
        MarcaVehiculoDialog dialog = new MarcaVehiculoDialog(this, gestionarMarcaUseCase);
        dialog.setVisible(true);
        cargarVehiculos(); // Refrescar por si cambió alguna marca
    }

    private void actualizarEstadoBotones() {
        boolean hayVehiculos = modeloTabla.getRowCount() > 0;
        btnEditar.setEnabled(hayVehiculos);
        btnEliminar.setEnabled(hayVehiculos);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                VehiculoConfig config = VehiculoConfig.getInstance();
                
                VehiculoFrame frame = new VehiculoFrame(
                    config.getVehiculoService(),
                    config.getVehiculoService(),
                    config.getVehiculoService(),
                    config.getVehiculoService(),
                    config.getMarcaVehiculoService()
                );
                
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Error al iniciar la aplicación: " + e.getMessage(),
                    "Error Fatal",
                    JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        });
    }
}
