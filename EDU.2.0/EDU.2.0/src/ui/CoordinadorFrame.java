package ui;

import controller.CoordinadorController;
import model.*;
import persistence.FileManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class CoordinadorFrame extends JFrame {
    private HistorialObservaciones historial;


    private CoordinadorController controller;
    private UsuarioModel usuarioModel;
    private ObservacionModel observacionModel;
    private ReporteModel reporteModel;
    private FileManager fileManager;
    private String idCoordinador;

    private JTable tablaUsuarios, tablaObservaciones;
    private DefaultTableModel modelUsuarios, modelObservaciones;
    private JTextArea txtReporte;


    private CardLayout cardLayout;
    private JPanel panelCentral;
    private JButton btnNavActivo;

    public CoordinadorFrame(String idCoordinador,
                            UsuarioModel usuarioModel,
                            ObservacionModel observacionModel,
                            ReporteModel reporteModel,
                            FileManager fileManager,
                            HistorialObservaciones historial) {

        this.idCoordinador = idCoordinador;
        this.usuarioModel = usuarioModel;
        this.observacionModel = observacionModel;
        this.reporteModel = reporteModel;
        this.fileManager = fileManager;
        this.controller = new CoordinadorController(usuarioModel, observacionModel, reporteModel);


        this.historial = historial;

        setTitle("EduObservador v2.0 - Coordinador");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarUI();
        cargarUsuarios();
        cargarTodasObservaciones();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                fileManager.guardarTodosLosDatos();
            }
        });
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());


        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 58, 138));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("🛡️ EduObservador - Modo Coordinador");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo, BorderLayout.WEST);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        headerRight.setOpaque(false);
        JLabel lblUser = new JLabel("ID: " + idCoordinador);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(new Color(147, 197, 253));


        JButton btnCerrar = estilizarBoton("Cerrar Sesión", new Color(220, 38, 38));
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.addActionListener(e -> {
            fileManager.guardarTodosLosDatos();
            dispose();
            new LoginFrame().setVisible(true);
        });

        headerRight.add(lblUser);
        headerRight.add(btnCerrar);
        headerPanel.add(headerRight, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);


        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setBackground(new Color(30, 41, 59));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel lblMenu = new JLabel("MENÚ PRINCIPAL");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMenu.setForeground(new Color(148, 163, 184));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(lblMenu);
        sidebarPanel.add(Box.createVerticalStrut(20));


        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(new Color(248, 250, 252));
        panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelCentral.add(crearPanelUsuarios(), "Usuarios");
        panelCentral.add(crearPanelAnular(), "Anular");
        panelCentral.add(crearPanelReportes(), "Reportes");
        panelCentral.add(crearPanelHistorial(), "Historial");


        JButton btnUsuarios = crearBotonMenu("👥 Gestión Usuarios", "Usuarios");
        JButton btnAnular = crearBotonMenu("🚫 Anular Observación", "Anular");
        JButton btnReportes = crearBotonMenu("📊 Generar Reportes", "Reportes");
        JButton btnHistorial = crearBotonMenu("🔍 Historial Estudiantes", "Historial");

        sidebarPanel.add(btnUsuarios);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnAnular);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnReportes);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnHistorial);

        marcarBotonActivo(btnUsuarios);

        add(sidebarPanel, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
    }


    private JButton crearBotonMenu(String texto, String nombreCarta) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                if (btnNavActivo == this) {
                    g2.setColor(new Color(37, 99, 235));
                } else if (getModel().isPressed()) {
                    g2.setColor(new Color(37, 99, 235));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(51, 65, 85));
                } else {
                    g2.setColor(new Color(30, 41, 59));
                }
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(new Color(203, 213, 225));

        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 15, 12, 15));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            cardLayout.show(panelCentral, nombreCarta);
            marcarBotonActivo(btn);
            if(nombreCarta.equals("Anular")) cargarTodasObservaciones();
        });
        return btn;
    }

    private void marcarBotonActivo(JButton btn) {
        if (btnNavActivo != null) {
            btnNavActivo.setForeground(new Color(203, 213, 225));
            btnNavActivo.getParent().repaint();
        }
        btnNavActivo = btn;
        btnNavActivo.setForeground(Color.WHITE);
        btnNavActivo.getParent().repaint();
    }

    private JButton estilizarBoton(String texto, Color bg) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);

        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setBorder(new EmptyBorder(10, 15, 10, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(35);
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(241, 245, 249));
        tabla.setSelectionBackground(new Color(219, 234, 254));
        tabla.setSelectionForeground(new Color(30, 58, 138));

        JTableHeader header = tabla.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(new Color(71, 85, 105));
        header.setPreferredSize(new Dimension(100, 40));
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Gestión de Usuarios del Sistema");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        String[] columnas = {"Código", "Rol", "Contraseña", "Estado"};
        modelUsuarios = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaUsuarios = new JTable(modelUsuarios);
        estilizarTabla(tablaUsuarios);

        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(241, 245, 249)));
        tarjeta.add(scrollPane, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        JButton btnRefrescar = estilizarBoton("Refrescar", new Color(100, 116, 139));
        btnRefrescar.addActionListener(e -> cargarUsuarios());

        JButton btnDeshabilitar = estilizarBoton("Deshabilitar Usuario", new Color(239, 68, 68));
        btnDeshabilitar.addActionListener(e -> mostrarDialogoDeshabilitar());

        JButton btnEditar = estilizarBoton("Editar Contraseña", new Color(245, 158, 11));
        btnEditar.addActionListener(e -> mostrarDialogoEditarUsuario());

        JButton btnCrear = estilizarBoton("+ Crear Usuario", new Color(16, 185, 129));
        btnCrear.addActionListener(e -> mostrarDialogoCrearUsuario());

        botonesPanel.add(btnRefrescar);
        botonesPanel.add(btnDeshabilitar);
        botonesPanel.add(btnEditar);
        botonesPanel.add(btnCrear);

        tarjeta.add(botonesPanel, BorderLayout.SOUTH);
        panel.add(tarjeta, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelAnular() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Anulación de Observaciones");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjetaForm = new JPanel(new GridBagLayout());
        tarjetaForm.setBackground(Color.WHITE);
        tarjetaForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID Observación:");
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; tarjetaForm.add(lblId, gbc);

        JTextField txtId = new JTextField(15);
        estilizarCampoTexto(txtId);
        gbc.gridx = 1; tarjetaForm.add(txtId, gbc);

        JLabel lblJust = new JLabel("Justificación:");
        lblJust.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1; tarjetaForm.add(lblJust, gbc);

        JTextField txtJustificacion = new JTextField(30);
        estilizarCampoTexto(txtJustificacion);
        gbc.gridx = 1; tarjetaForm.add(txtJustificacion, gbc);

        JButton btnAnular = estilizarBoton("Confirmar Anulación", new Color(220, 38, 38));
        btnAnular.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String justificacion = txtJustificacion.getText().trim();
                if (justificacion.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Debe ingresar una justificación");
                    return;
                }
                if (controller.anularObservacion(id, justificacion)) {
                    fileManager.guardarTodosLosDatos();
                    JOptionPane.showMessageDialog(panel, "Observación anulada exitosamente");
                    txtId.setText(""); txtJustificacion.setText("");
                    cargarTodasObservaciones();
                } else {
                    JOptionPane.showMessageDialog(panel, "No se encontró la observación o ya está anulada", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "ID inválido. Ingrese un número.");
            }
        });
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        tarjetaForm.add(btnAnular, gbc);

        JPanel tarjetaTabla = new JPanel(new BorderLayout());
        tarjetaTabla.setBackground(Color.WHITE);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        String[] columnas = {"ID", "Estudiante", "Tipo", "Severidad", "Estado"};
        modelObservaciones = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaObservaciones = new JTable(modelObservaciones);
        estilizarTabla(tablaObservaciones);

        JScrollPane scrollPane = new JScrollPane(tablaObservaciones);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(241, 245, 249)));
        tarjetaTabla.add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnCargarObs = estilizarBoton("Refrescar Observaciones", new Color(100, 116, 139));
        btnCargarObs.addActionListener(e -> cargarTodasObservaciones());

        bottomPanel.add(btnCargarObs);

        tarjetaTabla.add(bottomPanel, BorderLayout.SOUTH);


        JPanel wrapper = new JPanel(new BorderLayout(0, 15));
        wrapper.setOpaque(false);
        wrapper.add(tarjetaForm, BorderLayout.NORTH);
        wrapper.add(tarjetaTabla, BorderLayout.CENTER);

        panel.add(wrapper, BorderLayout.CENTER);

        return panel;
    }

    private void estilizarCampoTexto(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
    }

    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Generador de Reportes de Convivencia");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel(new BorderLayout(10, 15));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JPanel busquedaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        busquedaPanel.setBackground(Color.WHITE);

        JLabel lblCod = new JLabel("Código del Estudiante:");
        lblCod.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextField txtEstudiante = new JTextField(20);
        txtEstudiante.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnGenerar = estilizarBoton("Generar Reporte", new Color(37, 99, 235));
        btnGenerar.addActionListener(e -> {
            String reporte = controller.generarReportes(txtEstudiante.getText().trim());
            txtReporte.setText(reporte);
        });

        busquedaPanel.add(lblCod);
        busquedaPanel.add(txtEstudiante);
        busquedaPanel.add(btnGenerar);
        tarjeta.add(busquedaPanel, BorderLayout.NORTH);

        txtReporte = new JTextArea();
        txtReporte.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtReporte.setEditable(false);
        txtReporte.setMargin(new Insets(15, 15, 15, 15));
        JScrollPane scrollPane = new JScrollPane(txtReporte);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        tarjeta.add(scrollPane, BorderLayout.CENTER);

        panel.add(tarjeta, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Consulta de Historial Completo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel lbl = new JLabel("Código del Estudiante:");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JTextField txtEstudiante = new JTextField(20);
        txtEstudiante.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnBuscar = estilizarBoton("🔍 Consultar", new Color(30, 58, 138));
        btnBuscar.addActionListener(e -> {
            String estudiante = txtEstudiante.getText().trim();
            if (estudiante.isEmpty()) return;
            List<Observacion> lista = controller.consultarHistorial(estudiante);

            JDialog dialog = new JDialog(this, "Historial de " + estudiante, true);
            dialog.setSize(950, 500);
            dialog.setLocationRelativeTo(this);
            dialog.getContentPane().setBackground(Color.WHITE);

            String[] columnas = {"ID", "Tipo", "Severidad", "Fecha", "Materia", "Estado", "Descripción"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0) {
                @Override public boolean isCellEditable(int row, int column) { return false; }
            };
            for (Observacion obs : lista) {
                model.addRow(new Object[]{
                        obs.getId(), obs.getTipo(), obs.getSeveridad(),
                        obs.getFecha(), obs.getMateria(),
                        obs.isActiva() ? "Activa" : "Anulada",
                        obs.getDescripcion().length() > 60 ? obs.getDescripcion().substring(0, 60) + "..." : obs.getDescripcion()
                });
            }

            JTable tabla = new JTable(model);
            estilizarTabla(tabla);
            JScrollPane scroll = new JScrollPane(tabla);
            scroll.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            dialog.add(scroll);
            dialog.setVisible(true);
        });

        tarjeta.add(lbl);
        tarjeta.add(txtEstudiante);
        tarjeta.add(btnBuscar);

        panel.add(tarjeta, BorderLayout.NORTH);
        return panel;
    }


    private void cargarUsuarios() {
        modelUsuarios.setRowCount(0);
        List<Usuario> usuarios = controller.listarUsuarios();
        for (Usuario u : usuarios) {
            modelUsuarios.addRow(new Object[]{
                    u.getCodigo(),
                    u.getRol(),
                    u.getContrasena(),
                    u.isEstado() ? "Activo" : "Inactivo"
            });
        }
    }

    private void cargarTodasObservaciones() {

        modelObservaciones.setRowCount(0);

        List<Observacion> lista = historial.getObservaciones();

        for (Observacion obs : lista) {

            modelObservaciones.addRow(new Object[]{
                    obs.getId(),
                    obs.getIdEstudiante(),
                    obs.getTipo(),
                    obs.getSeveridad(),
                    obs.isActiva() ? "Activa" : "Anulada"
            });
        }

        modelObservaciones.fireTableDataChanged();
    }
    private void mostrarDialogoCrearUsuario() {
        JDialog dialog = new JDialog(this, "Crear Usuario", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblTitulo = new JLabel("Nuevo Usuario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        dialog.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; dialog.add(new JLabel("Código:"), gbc);
        JTextField txtCodigo = new JTextField(15);
        gbc.gridx = 1; dialog.add(txtCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("Contraseña:"), gbc);
        JPasswordField txtPass = new JPasswordField(15);
        gbc.gridx = 1; dialog.add(txtPass, gbc);

        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Rol:"), gbc);
        JComboBox<String> cbRol = new JComboBox<>(new String[]{"ESTUDIANTE", "DOCENTE", "COORDINADOR"});
        cbRol.setBackground(Color.WHITE);
        gbc.gridx = 1; dialog.add(cbRol, gbc);

        JButton btnGuardar = estilizarBoton("Crear Usuario", new Color(16, 185, 129));
        btnGuardar.addActionListener(e -> {
            String codigo = txtCodigo.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();
            String rol = (String) cbRol.getSelectedItem();
            if (codigo.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Complete todos los campos");
                return;
            }
            boolean exito = controller.crearUsuario(codigo, pass, rol);
            if (exito) {
                fileManager.guardarTodosLosDatos();
                cargarUsuarios();
                JOptionPane.showMessageDialog(dialog, "Usuario creado exitosamente");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "El código ya existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.insets = new Insets(25, 15, 10, 15);
        dialog.add(btnGuardar, gbc);

        dialog.setVisible(true);
    }

    private void mostrarDialogoEditarUsuario() {
        JDialog dialog = new JDialog(this, "Editar Contraseña", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.WHITE);
        dialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; dialog.add(new JLabel("Código:"), gbc);
        JTextField txtCodigo = new JTextField(15);
        gbc.gridx = 1; dialog.add(txtCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Nueva Clave:"), gbc);
        JPasswordField txtPass = new JPasswordField(15);
        gbc.gridx = 1; dialog.add(txtPass, gbc);

        JButton btnGuardar = estilizarBoton("Actualizar", new Color(245, 158, 11));
        btnGuardar.addActionListener(e -> {
            String codigo = txtCodigo.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();
            if (codigo.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Complete todos los campos");
                return;
            }
            boolean exito = controller.editarUsuario(codigo, pass);
            if (exito) {
                fileManager.guardarTodosLosDatos();
                cargarUsuarios();
                JOptionPane.showMessageDialog(dialog, "Contraseña actualizada");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.insets = new Insets(20, 15, 10, 15);
        dialog.add(btnGuardar, gbc);

        dialog.setVisible(true);
    }

    private void mostrarDialogoDeshabilitar() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código del usuario a deshabilitar:");
        if (codigo != null && !codigo.trim().isEmpty()) {
            boolean exito = controller.deshabilitarUsuario(codigo.trim());
            if (exito) {
                fileManager.guardarTodosLosDatos();
                cargarUsuarios();
                JOptionPane.showMessageDialog(this, "Usuario deshabilitado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado o ya inactivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}