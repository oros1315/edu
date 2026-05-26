package ui;

import controller.LoginController;
import model.*;
import persistence.FileManager;
import persistence.DataLoader;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {


    private LoginController loginController;
    private AutenticacionModel autenticacionModel;
    private HistorialObservaciones historial;
    private ObservacionModel observacionModel;
    private UsuarioModel usuarioModel;
    private ReclamoModel reclamoModel;
    private ActividadModel actividadModel;
    private ReporteModel reporteModel;
    private FileManager fileManager;

    private JTextField txtCodigo;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JLabel lblMensaje;

    public LoginFrame() {
        inicializarModelos();
        inicializarUI();
        cargarDatos();
    }


    private void inicializarModelos() {
        autenticacionModel = new AutenticacionModel();
        historial = new HistorialObservaciones();
        observacionModel = new ObservacionModel(historial);
        usuarioModel = new UsuarioModel(autenticacionModel);
        reclamoModel = new ReclamoModel();
        actividadModel = new ActividadModel();
        reporteModel = new ReporteModel(observacionModel);
        loginController = new LoginController(autenticacionModel);
        fileManager = new FileManager(autenticacionModel, historial, reclamoModel, actividadModel);
    }

    private void cargarDatos() {
        DataLoader dataLoader = new DataLoader(autenticacionModel, historial, observacionModel);
        fileManager.cargarTodosLosDatos();
        dataLoader.cargarDatosInicialesSiNecesario();
    }

    private void guardarDatos() {
        fileManager.guardarTodosLosDatos();
    }


    private void inicializarUI() {
        setTitle("EduObservador v2.0 - Acceso");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana
        setResizable(false);


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(248, 250, 252));


        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;


        JLabel lblIcono = new JLabel("🛡️", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        gbc.gridy = 0;
        cardPanel.add(lblIcono, gbc);

        JLabel lblTitulo = new JLabel("EduObservador", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(30, 58, 138));
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        cardPanel.add(lblTitulo, gbc);

        JLabel lblSubtitulo = new JLabel("Acceso al Sistema", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(100, 116, 139));
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 25, 0);
        cardPanel.add(lblSubtitulo, gbc);


        JLabel lblCodigo = new JLabel("Código de Usuario");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCodigo.setForeground(new Color(71, 85, 105));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 5, 0);
        cardPanel.add(lblCodigo, gbc);

        txtCodigo = new JTextField(15);
        txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCodigo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 15, 0);
        cardPanel.add(txtCodigo, gbc);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblContrasena.setForeground(new Color(71, 85, 105));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 5, 0);
        cardPanel.add(lblContrasena, gbc);

        txtContrasena = new JPasswordField(15);
        txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 25, 0);
        cardPanel.add(txtContrasena, gbc);


        btnLogin = new JButton("Iniciar Sesión") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = new Color(29, 78, 216);
                if (getModel().isPressed()) {
                    g2.setColor(bg.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bg.brighter());
                } else {
                    g2.setColor(bg);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);


        btnLogin.setContentAreaFilled(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);

        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new LoginAction());
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 15, 0);
        cardPanel.add(btnLogin, gbc);


        lblMensaje = new JLabel("", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMensaje.setForeground(new Color(220, 38, 38)); // Rojo error
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 0, 10, 0);
        cardPanel.add(lblMensaje, gbc);

        JLabel lblInfo = new JLabel("<html><center>Usuarios de prueba:<br>COORD001 | DOC001 | EST001<br>Clave: ...123</center></html>");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblInfo.setForeground(new Color(148, 163, 184));
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 9;
        cardPanel.add(lblInfo, gbc);

        mainPanel.add(cardPanel);
        add(mainPanel);

        getRootPane().setDefaultButton(btnLogin);
    }


    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codigo = txtCodigo.getText().trim();
            String contrasena = new String(txtContrasena.getPassword()).trim();

            if (codigo.isEmpty() || contrasena.isEmpty()) {
                lblMensaje.setText("Por favor ingrese código y contraseña");
                return;
            }

            try {

                Usuario usuario = loginController.autenticar(codigo, contrasena);

                guardarDatos();
                String rol = loginController.redirigirSegunRol(usuario);
                dispose();

                switch (rol) {
                    case "ESTUDIANTE":
                        new EstudianteFrame(usuario.getCodigo(), observacionModel, reclamoModel, fileManager).setVisible(true);
                        break;
                    case "DOCENTE":
                        new DocenteFrame(usuario.getCodigo(), observacionModel, actividadModel, fileManager).setVisible(true);
                        break;
                    case "COORDINADOR":
                        new CoordinadorFrame(
                                usuario.getCodigo(),
                                usuarioModel,
                                observacionModel,
                                reporteModel,
                                fileManager,
                                historial
                        ).setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Rol no reconocido");
                        System.exit(0);
                }
            } catch (Exception ex) {

                String mensajeError = ex.getMessage();

                if (mensajeError != null && mensajeError.contains("Exception:")) {
                    mensajeError = mensajeError.split("Exception:")[1].trim();
                }

                lblMensaje.setText(mensajeError);
                txtContrasena.setText("");
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {

                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}