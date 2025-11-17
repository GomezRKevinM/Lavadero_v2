package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.MarcaVehiculo;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.GestionarMarcaVehiculoUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Diálogo para gestionar marcas de vehículos.
 */
public class MarcaVehiculoDialog extends JDialog {
    
    private final GestionarMarcaVehiculoUseCase gestionarMarcaUseCase;
    
    private JTable tablaMarcas;
    private DefaultTableModel modeloTabla;
    private JButton btnNueva, btnEditar, btnEliminar, btnCerrar;

    public MarcaVehiculoDialog(Frame parent, GestionarMarcaVehiculoUseCase gestionarMarcaUseCase) {
        super(parent, "Gestión de Marcas de Vehículos", true);
        this.gestionarMarcaUseCase = gestionarMarcaUseCase;
        
        inicializarComponentes();
        cargarMarcas();
        
        setSize(600, 450);
        setLocationRelativeTo(parent);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JLabel lblTitulo = new JLabel("Marcas de Vehículos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Panel central con tabla
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        String[] columnas = {"ID", "Marca"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaMarcas = new JTable(modeloTabla);
        tablaMarcas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaMarcas.setRowHeight(25);
        tablaMarcas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaMarcas.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablaMarcas.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        JScrollPane scrollPane = new JScrollPane(tablaMarcas);
        panelCentral.add(scrollPane, BorderLayout.CENTER);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        btnNueva = crearBoton("Nueva Marca", new Color(39, 174, 96), e -> nuevaMarca());
        btnEditar = crearBoton("Editar", new Color(243, 156, 18), e -> editarMarcaSeleccionada());
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60), e -> eliminarMarcaSeleccionada());
        btnCerrar = crearBoton("Cerrar", new Color(149, 165, 166), e -> dispose());
        
        panelBotones.add(btnNueva);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color color, java.awt.event.ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setPreferredSize(new Dimension(130, 35));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.addActionListener(action);
        return boton;
    }

    private void cargarMarcas() {
        modeloTabla.setRowCount(0);
        try {
            List<MarcaVehiculo> marcas = gestionarMarcaUseCase.listarTodas();
            
            for (MarcaVehiculo marca : marcas) {
                Object[] fila = {marca.getId(), marca.getMarca()};
                modeloTabla.addRow(fila);
            }
            
            actualizarEstadoBotones();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar marcas: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void nuevaMarca() {
        String nombreMarca = JOptionPane.showInputDialog(
            this,
            "Ingrese el nombre de la nueva marca:",
            "Nueva Marca",
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (nombreMarca != null && !nombreMarca.trim().isEmpty()) {
            try {
                MarcaVehiculo nuevaMarca = new MarcaVehiculo(null, nombreMarca.trim());
                gestionarMarcaUseCase.crear(nuevaMarca);
                
                JOptionPane.showMessageDialog(
                    this,
                    "Marca creada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                cargarMarcas();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error de validación: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al crear marca: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void editarMarcaSeleccionada() {
        int filaSeleccionada = tablaMarcas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar una marca para editar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        Integer id = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombreActual = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        String nuevoNombre = (String) JOptionPane.showInputDialog(
            this,
            "Ingrese el nuevo nombre de la marca:",
            "Editar Marca",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            nombreActual
        );
        
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            try {
                MarcaVehiculo marcaActualizada = new MarcaVehiculo(id, nuevoNombre.trim());
                gestionarMarcaUseCase.actualizar(marcaActualizada);
                
                JOptionPane.showMessageDialog(
                    this,
                    "Marca actualizada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                cargarMarcas();
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error de validación: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar marca: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void eliminarMarcaSeleccionada() {
        int filaSeleccionada = tablaMarcas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar una marca para eliminar",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        Integer id = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar la marca \"" + nombre + "\"?\n\n" +
            "ADVERTENCIA: Esto puede afectar a los vehículos que usan esta marca.",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                boolean eliminado = gestionarMarcaUseCase.eliminar(id);
                
                if (eliminado) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Marca eliminada correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarMarcas();
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "No se pudo eliminar la marca",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar marca: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void actualizarEstadoBotones() {
        boolean hayMarcas = modeloTabla.getRowCount() > 0;
        btnEditar.setEnabled(hayMarcas);
        btnEliminar.setEnabled(hayMarcas);
    }
}
