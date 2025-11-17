package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.MarcaVehiculo;
import com.lavadero.unicartagena.edu.co.domain.model.vehiculos.Vehiculo;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.ActualizarVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.CrearVehiculoUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.vehiculo.GestionarMarcaVehiculoUseCase;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Diálogo para crear o editar vehículos.
 */
public class VehiculoDialog extends JDialog {
    
    private final Vehiculo vehiculoOriginal;
    private final GestionarMarcaVehiculoUseCase gestionarMarcaUseCase;
    private final CrearVehiculoUseCase crearVehiculoUseCase;
    private final ActualizarVehiculoUseCase actualizarVehiculoUseCase;
    
    private JTextField txtPlaca;
    private JComboBox<MarcaVehiculoItem> cmbMarca;
    private JTextField txtClienteId;
    private JButton btnGuardar, btnCancelar;
    private boolean confirmado = false;

    public VehiculoDialog(Frame parent, 
                         Vehiculo vehiculo,
                         GestionarMarcaVehiculoUseCase gestionarMarcaUseCase,
                         CrearVehiculoUseCase crearVehiculoUseCase,
                         ActualizarVehiculoUseCase actualizarVehiculoUseCase) {
        super(parent, vehiculo == null ? "Nuevo Vehículo" : "Editar Vehículo", true);
        this.vehiculoOriginal = vehiculo;
        this.gestionarMarcaUseCase = gestionarMarcaUseCase;
        this.crearVehiculoUseCase = crearVehiculoUseCase;
        this.actualizarVehiculoUseCase = actualizarVehiculoUseCase;
        
        inicializarComponentes();
        cargarDatos();
        
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Placa
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblPlaca = new JLabel("Placa:");
        lblPlaca.setFont(new Font("Arial", Font.BOLD, 13));
        panelFormulario.add(lblPlaca, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPlaca = new JTextField(15);
        txtPlaca.setFont(new Font("Arial", Font.PLAIN, 13));
        panelFormulario.add(txtPlaca, gbc);
        
        // Marca
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setFont(new Font("Arial", Font.BOLD, 13));
        panelFormulario.add(lblMarca, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cmbMarca = new JComboBox<>();
        cmbMarca.setFont(new Font("Arial", Font.PLAIN, 13));
        panelFormulario.add(cmbMarca, gbc);
        
        // Cliente ID
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel lblClienteId = new JLabel("ID Cliente:");
        lblClienteId.setFont(new Font("Arial", Font.BOLD, 13));
        panelFormulario.add(lblClienteId, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtClienteId = new JTextField(15);
        txtClienteId.setFont(new Font("Arial", Font.PLAIN, 13));
        panelFormulario.add(txtClienteId, gbc);
        
        // Nota informativa
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JLabel lblNota = new JLabel("<html><i>Nota: El cliente debe existir en el sistema</i></html>");
        lblNota.setFont(new Font("Arial", Font.ITALIC, 11));
        lblNota.setForeground(new Color(127, 140, 141));
        panelFormulario.add(lblNota, gbc);
        
        add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));
        
        btnGuardar = new JButton(vehiculoOriginal == null ? "Crear" : "Actualizar");
        btnGuardar.setBackground(new Color(39, 174, 96));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setPreferredSize(new Dimension(120, 35));
        btnGuardar.setOpaque(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardar());
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.setOpaque(true);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        // Cargar marcas
        try {
            List<MarcaVehiculo> marcas = gestionarMarcaUseCase.listarTodas();
            cmbMarca.removeAllItems();
            
            for (MarcaVehiculo marca : marcas) {
                cmbMarca.addItem(new MarcaVehiculoItem(marca));
            }
            
            if (marcas.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "No hay marcas registradas. Por favor, agregue marcas primero.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                btnGuardar.setEnabled(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar marcas: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            btnGuardar.setEnabled(false);
        }
        
        // Si es edición, cargar datos del vehículo
        if (vehiculoOriginal != null) {
            txtPlaca.setText(vehiculoOriginal.getPlaca());
            txtClienteId.setText(vehiculoOriginal.getClienteId().toString());
            
            // Seleccionar la marca correspondiente
            for (int i = 0; i < cmbMarca.getItemCount(); i++) {
                MarcaVehiculoItem item = cmbMarca.getItemAt(i);
                if (item.getMarca().getId().equals(vehiculoOriginal.getMarcaId())) {
                    cmbMarca.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void guardar() {
        // Validaciones
        String placa = txtPlaca.getText().trim();
        if (placa.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "La placa es obligatoria",
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE
            );
            txtPlaca.requestFocus();
            return;
        }
        
        if (cmbMarca.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar una marca",
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        String clienteIdStr = txtClienteId.getText().trim();
        if (clienteIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "El ID del cliente es obligatorio",
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE
            );
            txtClienteId.requestFocus();
            return;
        }
        
        Long clienteId;
        try {
            clienteId = Long.parseLong(clienteIdStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                this,
                "El ID del cliente debe ser un número válido",
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE
            );
            txtClienteId.requestFocus();
            return;
        }
        
        MarcaVehiculoItem itemSeleccionado = (MarcaVehiculoItem) cmbMarca.getSelectedItem();
        Integer marcaId = itemSeleccionado.getMarca().getId();
        
        try {
            if (vehiculoOriginal == null) {
                // Crear nuevo vehículo
                Vehiculo nuevoVehiculo = new Vehiculo(null, marcaId, placa, clienteId);
                crearVehiculoUseCase.crear(nuevoVehiculo);
                
                JOptionPane.showMessageDialog(
                    this,
                    "Vehículo creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                // Actualizar vehículo existente
                Vehiculo vehiculoActualizado = new Vehiculo(
                    vehiculoOriginal.getId(),
                    marcaId,
                    placa,
                    clienteId
                );
                actualizarVehiculoUseCase.actualizar(vehiculoActualizado);
                
                JOptionPane.showMessageDialog(
                    this,
                    "Vehículo actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
            confirmado = true;
            dispose();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error de validación: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Error inesperado: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    /**
     * Clase interna para envolver MarcaVehiculo en el ComboBox.
     */
    private static class MarcaVehiculoItem {
        private final MarcaVehiculo marca;

        public MarcaVehiculoItem(MarcaVehiculo marca) {
            this.marca = marca;
        }

        public MarcaVehiculo getMarca() {
            return marca;
        }

        @Override
        public String toString() {
            return marca.getMarca();
        }
    }
}
