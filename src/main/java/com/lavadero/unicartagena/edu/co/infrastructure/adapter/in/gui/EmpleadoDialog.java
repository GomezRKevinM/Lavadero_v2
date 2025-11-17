package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Empleado;
import com.lavadero.unicartagena.edu.co.domain.model.empleados.EmpleadoCompleto;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.CrearEmpleadoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.empleado.ActualizarEmpleadoUseCase;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogo para crear/editar empleados.
 */
public class EmpleadoDialog extends JDialog {
    
    private final CrearEmpleadoUseCase crearUseCase;
    private final ActualizarEmpleadoUseCase actualizarUseCase;
    private final EmpleadoCompleto empleadoActual;
    
    private JTextField txtInfoPersonalId;
    private JTextField txtUsuarioId;
    private boolean confirmado = false;

    public EmpleadoDialog(Frame parent, CrearEmpleadoUseCase crearUseCase,
                         ActualizarEmpleadoUseCase actualizarUseCase,
                         EmpleadoCompleto empleadoActual) {
        super(parent, empleadoActual == null ? "Nuevo Empleado" : "Editar Empleado", true);
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.empleadoActual = empleadoActual;
        
        inicializarComponentes();
        if (empleadoActual != null) {
            cargarDatos();
        }
    }

    private void inicializarComponentes() {
        setSize(450, 250);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // InfoPersonal ID
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelFormulario.add(new JLabel("Info Personal ID:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtInfoPersonalId = new JTextField(20);
        panelFormulario.add(txtInfoPersonalId, gbc);
        
        // Usuario ID
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 0;
        panelFormulario.add(new JLabel("Usuario ID (opcional):"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtUsuarioId = new JTextField(20);
        panelFormulario.add(txtUsuarioId, gbc);
        
        // Nota informativa
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel lblNota = new JLabel("<html><i>Nota: La información personal debe estar registrada previamente</i></html>");
        lblNota.setForeground(new Color(100, 100, 100));
        panelFormulario.add(lblNota, gbc);
        
        add(panelFormulario, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(39, 174, 96));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setOpaque(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.addActionListener(e -> guardar());
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setOpaque(true);
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        txtInfoPersonalId.setText(String.valueOf(empleadoActual.getInfoPersonalId()));
        if (empleadoActual.getUsuarioId() != null) {
            txtUsuarioId.setText(String.valueOf(empleadoActual.getUsuarioId()));
        }
    }

    private void guardar() {
        try {
            Long infoPersonalId = Long.parseLong(txtInfoPersonalId.getText().trim());
            
            String usuarioIdStr = txtUsuarioId.getText().trim();
            Long usuarioId = usuarioIdStr.isEmpty() ? null : Long.parseLong(usuarioIdStr);
            
            if (empleadoActual == null) {
                // Crear nuevo
                Empleado empleado = new Empleado(null, infoPersonalId, usuarioId, null);
                crearUseCase.crear(empleado);
                JOptionPane.showMessageDialog(this, "Empleado creado exitosamente", 
                                             "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Actualizar existente
                Empleado empleado = new Empleado(empleadoActual.getId(), infoPersonalId, 
                                                usuarioId, null);
                actualizarUseCase.actualizar(empleado);
                JOptionPane.showMessageDialog(this, "Empleado actualizado exitosamente", 
                                             "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
            confirmado = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los IDs deben ser números válidos", 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                                         "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}
