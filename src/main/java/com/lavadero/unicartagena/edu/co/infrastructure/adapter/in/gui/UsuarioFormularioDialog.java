package com.lavadero.unicartagena.edu.co.infrastructure.adapter.in.gui;

import com.lavadero.unicartagena.edu.co.domain.model.empleados.Usuario;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.CrearUsuarioUseCase;
import com.lavadero.unicartagena.edu.co.domain.port.in.usuario.ModificarUsuarioUseCase;

import javax.swing.*;
import java.awt.*;

public class UsuarioFormularioDialog extends JDialog {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtQuestionSecurity;
    private JPasswordField txtAnswerSecurity;
    private JTextField txtEmpresaId;
    private JButton btnGuardar, btnCancelar;

    private Usuario usuario;
    private Object useCase;
    private boolean guardado = false;

    public UsuarioFormularioDialog(Frame owner, Object useCase, Usuario usuario) {
        super(owner, usuario == null ? "Crear Usuario" : "Modificar Usuario", true);
        this.useCase = useCase;
        this.usuario = usuario;

        inicializarComponentes();
        if (usuario != null) {
            cargarDatos();
        }
    }

    private void inicializarComponentes() {
        setSize(500, 450);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        panelFormulario.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        panelFormulario.add(txtPassword, gbc);

        if (usuario != null) {
            JLabel lblInfo = new JLabel("(Dejar vacío para mantener la actual)");
            lblInfo.setFont(new Font("Arial", Font.ITALIC, 10));
            lblInfo.setForeground(Color.GRAY);
            gbc.gridx = 1;
            gbc.gridy = 2;
            panelFormulario.add(lblInfo, gbc);
            gbc.gridy = 3;
        } else {
            gbc.gridy = 2;
        }

        gbc.gridx = 0;
        panelFormulario.add(new JLabel("Pregunta de Seguridad:"), gbc);

        gbc.gridx = 1;
        txtQuestionSecurity = new JTextField(20);
        panelFormulario.add(txtQuestionSecurity, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panelFormulario.add(new JLabel("Respuesta de Seguridad:"), gbc);

        gbc.gridx = 1;
        txtAnswerSecurity = new JPasswordField(20);
        panelFormulario.add(txtAnswerSecurity, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panelFormulario.add(new JLabel("ID de Empresa:"), gbc);

        gbc.gridx = 1;
        txtEmpresaId = new JTextField(20);
        panelFormulario.add(txtEmpresaId, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        btnGuardar = crearBotonEstilizado("Guardar", new Color(76, 175, 80));
        btnCancelar = crearBotonEstilizado("Cancelar", new Color(158, 158, 158));

        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
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
        boton.setPreferredSize(new Dimension(120, 40));
        return boton;
    }

    private void cargarDatos() {
        txtUsername.setText(usuario.getUsername());
        txtQuestionSecurity.setText(usuario.getQuestionSecurity());
        txtEmpresaId.setText(usuario.getEmpresaId().toString());
    }

    private void guardar() {
        try {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            String questionSecurity = txtQuestionSecurity.getText().trim();
            String answerSecurity = new String(txtAnswerSecurity.getPassword());
            String empresaIdStr = txtEmpresaId.getText().trim();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El username es obligatorio",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (empresaIdStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "El ID de empresa es obligatorio",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long empresaId = Long.parseLong(empresaIdStr);

            if (usuario == null) {
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "La contraseña es obligatoria",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setUsername(username);
                nuevoUsuario.setPassword(password);
                nuevoUsuario.setQuestionSecurity(questionSecurity);
                nuevoUsuario.setAnswerSecurity(answerSecurity);
                nuevoUsuario.setEmpresaId(empresaId);

                ((CrearUsuarioUseCase) useCase).crear(nuevoUsuario);
                JOptionPane.showMessageDialog(this,
                    "Usuario creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                usuario.setUsername(username);
                usuario.setQuestionSecurity(questionSecurity);
                usuario.setEmpresaId(empresaId);

                if (!password.isEmpty()) {
                    usuario.setPassword(password);
                }

                if (!answerSecurity.isEmpty()) {
                    usuario.setAnswerSecurity(answerSecurity);
                }

                ((ModificarUsuarioUseCase) useCase).actualizar(usuario);
                JOptionPane.showMessageDialog(this,
                    "Usuario actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            }

            guardado = true;
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "El ID de empresa debe ser un número válido",
                "Error de validación",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isGuardado() {
        return guardado;
    }
}
