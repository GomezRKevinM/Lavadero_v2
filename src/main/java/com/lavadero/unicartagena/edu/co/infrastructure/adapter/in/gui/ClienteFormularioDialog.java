package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;
import com.lavadero.unicartagena.edu.co.domain.model.personas.Cliente;
import com.lavadero.unicartagena.edu.co.domain.model.personas.InfoPersonal;
import com.lavadero.unicartagena.edu.co.domain.model.shared.TipoIdentificacion;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.CrearClienteUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.cliente.ModificarClienteUseCase;
import javax.swing.*;
import java.awt.*;
public class ClienteFormularioDialog extends JDialog {
    private final CrearClienteUseCase crearClienteUseCase;
    private final ModificarClienteUseCase modificarClienteUseCase;
    private final Cliente clienteEditar;
    private JComboBox<String> cmbTipoIdentificacion;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtIdentificacion;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JTextField txtEmpresaId;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private boolean guardado = false;
    public ClienteFormularioDialog(JFrame parent,
                                  CrearClienteUseCase crearUseCase,
                                  ModificarClienteUseCase modificarUseCase,
                                  Cliente cliente) {
        super(parent, cliente == null ? "Nuevo Cliente" : "Editar Cliente", true);
        this.crearClienteUseCase = crearUseCase;
        this.modificarClienteUseCase = modificarUseCase;
        this.clienteEditar = cliente;
        inicializarComponentes();
        if (cliente != null) {
            cargarDatosCliente(cliente);
        }
    }
    private void inicializarComponentes() {
        setSize(500, 550);
        setLocationRelativeTo(getParent());
        setResizable(false);
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel lblTitulo = new JLabel(clienteEditar == null ? "Nuevo Cliente" : "Editar Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        panelFormulario.add(lblTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        JSeparator separador1 = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelFormulario.add(separador1, gbc);
        JLabel lblSeccion1 = new JLabel("Información Personal");
        lblSeccion1.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 2;
        panelFormulario.add(lblSeccion1, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblTipo = new JLabel("Tipo de Identificación:");
        lblTipo.setFont(new Font("Arial", Font.PLAIN, 13));
        panelFormulario.add(lblTipo, gbc);
        String[] tipos = {"Cédula de Ciudadanía (CC)", "Cédula de Extranjería (CE)",
                         "Tarjeta de Identidad (TI)", "Pasaporte (PA)", "NIT"};
        cmbTipoIdentificacion = new JComboBox<>(tipos);
        gbc.gridx = 1;
        panelFormulario.add(cmbTipoIdentificacion, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Nombre: *"), gbc);
        txtNombre = new JTextField(20);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblApellidos = new JLabel("Apellidos: *");
        lblApellidos.setFont(new Font("Arial", Font.PLAIN, 13));
        panelFormulario.add(lblApellidos, gbc);
        txtApellidos = new JTextField(20);
        txtApellidos.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        panelFormulario.add(txtApellidos, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelFormulario.add(new JLabel("Número de Identificación: *"), gbc);
        txtIdentificacion = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtIdentificacion, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtTelefono, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        panelFormulario.add(new JLabel("Correo Electrónico:"), gbc);
        txtCorreo = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtCorreo, gbc);
        gbc.gridx = 0;
        gbc.gridy = 9;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        txtDireccion = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtDireccion, gbc);
        JSeparator separador2 = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panelFormulario.add(separador2, gbc);
        JLabel lblSeccion2 = new JLabel("Información Empresarial");
        lblSeccion2.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridy = 11;
        gbc.insets = new Insets(5, 5, 5, 5);
        panelFormulario.add(lblSeccion2, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 12;
        panelFormulario.add(new JLabel("ID de Empresa: *"), gbc);
        txtEmpresaId = new JTextField(20);
        txtEmpresaId.setText("1");
        gbc.gridx = 1;
        panelFormulario.add(txtEmpresaId, gbc);
        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(new Font("Arial", Font.ITALIC, 11));
        lblNota.setForeground(Color.GRAY);
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 5, 5, 5);
        panelFormulario.add(lblNota, gbc);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnGuardar = new JButton(clienteEditar == null ? "Crear Cliente" : "Guardar Cambios");
        btnGuardar.setBackground(new Color(46, 204, 113));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setOpaque(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setPreferredSize(new Dimension(150, 35));
        btnGuardar.addActionListener(e -> guardarCliente());
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(149, 165, 166));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setOpaque(true);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setPreferredSize(new Dimension(120, 35));
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }
    private void cargarDatosCliente(Cliente cliente) {
        InfoPersonal info = cliente.getInfoPersonal();
        Long tipoId = info.getTipoIdentificacion().getId();
        cmbTipoIdentificacion.setSelectedIndex(tipoId.intValue() - 1);
        txtNombre.setText(info.getNombre());
        txtApellidos.setText(info.getApellidos());
        txtIdentificacion.setText(info.getIdentificacion());
        txtTelefono.setText(info.getTelefono());
        txtCorreo.setText(info.getCorreo());
        txtDireccion.setText(info.getDireccion());
        txtEmpresaId.setText(cliente.getEmpresaId().toString());
    }
    private void guardarCliente() {
        try {
            if (!validarCampos()) {
                return;
            }
            TipoIdentificacion tipoId = new TipoIdentificacion();
            tipoId.setId((long) (cmbTipoIdentificacion.getSelectedIndex() + 1));
            InfoPersonal infoPersonal;
            if (clienteEditar != null) {
                infoPersonal = new InfoPersonal(
                    clienteEditar.getInfoPersonal().getInfoPersonalId(),
                    tipoId,
                    txtNombre.getText().trim(),
                    txtApellidos.getText().trim(),
                    txtIdentificacion.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtDireccion.getText().trim()
                );
            } else {
                infoPersonal = new InfoPersonal(
                    tipoId,
                    txtNombre.getText().trim(),
                    txtApellidos.getText().trim(),
                    txtIdentificacion.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtDireccion.getText().trim()
                );
            }
            Cliente cliente = new Cliente();
            if (clienteEditar != null) {
                cliente.setId(clienteEditar.getId());
            }
            cliente.setInfoPersonal(infoPersonal);
            cliente.setEmpresaId(Long.parseLong(txtEmpresaId.getText().trim()));
            if (clienteEditar == null) {
                crearClienteUseCase.crear(cliente);
                JOptionPane.showMessageDialog(this,
                    "Cliente creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                modificarClienteUseCase.actualizar(cliente);
                JOptionPane.showMessageDialog(this,
                    "Cliente actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            guardado = true;
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "El ID de empresa debe ser un número válido",
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                "Error de validación: " + e.getMessage(),
                "Error de Validación",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar cliente: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El nombre es obligatorio",
                "Validación",
                JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Los apellidos son obligatorios",
                "Validación",
                JOptionPane.WARNING_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        if (txtIdentificacion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El número de identificación es obligatorio",
                "Validación",
                JOptionPane.WARNING_MESSAGE);
            txtIdentificacion.requestFocus();
            return false;
        }
        if (txtEmpresaId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El ID de empresa es obligatorio",
                "Validación",
                JOptionPane.WARNING_MESSAGE);
            txtEmpresaId.requestFocus();
            return false;
        }
        return true;
    }
    public boolean isGuardado() {
        return guardado;
    }
}
