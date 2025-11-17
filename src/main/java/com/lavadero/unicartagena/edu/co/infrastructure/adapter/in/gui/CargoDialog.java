package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Cargo;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.GestionarCargoUseCase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Diálogo para gestionar cargos.
 */
public class CargoDialog extends JDialog {
    
    private final GestionarCargoUseCase gestionarCargoUseCase;
    private JTable tablaCargos;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnEditar, btnEliminar, btnRefrescar;

    public CargoDialog(Frame parent, GestionarCargoUseCase gestionarCargoUseCase) {
        super(parent, "Gestión de Cargos", true);
        this.gestionarCargoUseCase = gestionarCargoUseCase;
        
        inicializarComponentes();
        cargarCargos();
    }

    private void inicializarComponentes() {
        setSize(600, 450);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        // Tabla
        String[] columnas = {"ID", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaCargos = new JTable(modeloTabla);
        tablaCargos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCargos.setRowHeight(25);
        tablaCargos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        
        JScrollPane scrollPane = new JScrollPane(tablaCargos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNuevo = crearBoton("Nuevo", new Color(39, 174, 96));
        btnEditar = crearBoton("Editar", new Color(241, 196, 15));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnRefrescar = crearBoton("Refrescar", new Color(52, 152, 219));
        
        btnNuevo.addActionListener(e -> crearCargo());
        btnEditar.addActionListener(e -> editarCargo());
        btnEliminar.addActionListener(e -> eliminarCargo());
        btnRefrescar.addActionListener(e -> cargarCargos());
        
        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        return boton;
    }

    private void cargarCargos() {
        modeloTabla.setRowCount(0);
        try {
            List<Cargo> cargos = gestionarCargoUseCase.listarTodos();
            for (Cargo cargo : cargos) {
                modeloTabla.addRow(new Object[]{cargo.getId(), cargo.getNombre()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cargos: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void crearCargo() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del cargo:", 
                                                    "Nuevo Cargo", JOptionPane.QUESTION_MESSAGE);
        if (nombre != null && !nombre.trim().isEmpty()) {
            try {
                Cargo cargo = new Cargo(null, nombre.trim());
                gestionarCargoUseCase.crear(cargo);
                cargarCargos();
                JOptionPane.showMessageDialog(this, "Cargo creado exitosamente", 
                                             "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarCargo() {
        int filaSeleccionada = tablaCargos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cargo para editar", 
                                         "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer id = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombreActual = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        String nuevoNombre = (String) JOptionPane.showInputDialog(this, 
            "Nombre del cargo:", "Editar Cargo", JOptionPane.QUESTION_MESSAGE, 
            null, null, nombreActual);
        
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            try {
                Cargo cargo = new Cargo(id, nuevoNombre.trim());
                gestionarCargoUseCase.actualizar(cargo);
                cargarCargos();
                JOptionPane.showMessageDialog(this, "Cargo actualizado exitosamente", 
                                             "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCargo() {
        int filaSeleccionada = tablaCargos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cargo para eliminar", 
                                         "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Integer id = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar el cargo:\n" + nombre + "?\n\n" +
            "ADVERTENCIA: No se puede eliminar si tiene contratos asociados.",
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                gestionarCargoUseCase.eliminar(id);
                cargarCargos();
                JOptionPane.showMessageDialog(this, "Cargo eliminado exitosamente", 
                                             "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                             "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
