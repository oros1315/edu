package ui;

import controller.EstudianteController;
import model.*;
import persistence.FileManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class EstudianteFrame extends JFrame {

    private EstudianteController controller;
    private ObservacionModel observacionModel;
    private ReclamoModel reclamoModel;
    private FileManager fileManager;
    private String idEstudiante;

    private JTable tablaObservaciones;
    private DefaultTableModel tableModel;
    private JTextArea txtReclamo;

    private CardLayout cardLayout;
    private JPanel panelCentral;
    private JButton btnNavActivo;

    public EstudianteFrame(String idEstudiante, ObservacionModel observacionModel,
                           ReclamoModel reclamoModel, FileManager fileManager) {
        this.idEstudiante = idEstudiante;
        this.observacionModel = observacionModel;
        this.reclamoModel = reclamoModel;
        this.fileManager = fileManager;
        this.controller = new EstudianteController(observacionModel, reclamoModel);

        setTitle("EduObservador v2.0 - Estudiante");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarUI();
        cargarObservacionesActivas();

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

        JLabel lblTitulo = new JLabel("🛡️ EduObservador - Portal Estudiantil");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo, BorderLayout.WEST);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        headerRight.setOpaque(false);
        JLabel lblUser = new JLabel("ID: " + idEstudiante);
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

        JLabel lblMenu = new JLabel("MI CONVIVENCIA");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMenu.setForeground(new Color(148, 163, 184));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(lblMenu);
        sidebarPanel.add(Box.createVerticalStrut(20));

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(new Color(248, 250, 252));
        panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelCentral.add(crearPanelObservaciones(), "Activas");
        panelCentral.add(crearPanelHistorial(), "Historial");
        panelCentral.add(crearPanelReclamos(), "Reclamos");


        JButton btnActivas = crearBotonMenu("📋 Observaciones Activas", "Activas");
        JButton btnHistorial = crearBotonMenu("🔍 Historial Completo", "Historial");
        JButton btnReclamos = crearBotonMenu("✉️ Solicitar Reclamo", "Reclamos");

        sidebarPanel.add(btnActivas);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnHistorial);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnReclamos);

        marcarBotonActivo(btnActivas);

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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);

        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setBorder(new EmptyBorder(10, 15, 10, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }


    private JPanel crearPanelObservaciones() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Mis Observaciones Activas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        // Tabla
        String[] columnas = {"ID", "Tipo", "Severidad", "Fecha", "Materia", "Descripción", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaObservaciones = new JTable(tableModel);
        estilizarTabla(tablaObservaciones);

        JScrollPane scrollPane = new JScrollPane(tablaObservaciones);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(241, 245, 249)));
        tarjeta.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnRefrescar = estilizarBoton("🔄 Refrescar Datos", new Color(100, 116, 139));
        btnRefrescar.addActionListener(e -> cargarObservacionesActivas());
        bottomPanel.add(btnRefrescar);

        tarjeta.add(bottomPanel, BorderLayout.SOUTH);
        panel.add(tarjeta, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Mi Historial Completo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        String[] columnas = {"ID", "Tipo", "Severidad", "Fecha", "Materia", "Descripción", "Estado"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tablaHistorial = new JTable(model);
        estilizarTabla(tablaHistorial);

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(241, 245, 249)));
        tarjeta.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnCargar = estilizarBoton("⬇️ Cargar Todo el Historial", new Color(37, 99, 235));
        btnCargar.addActionListener(e -> {
            model.setRowCount(0);
            List<Observacion> lista = controller.consultarHistorial(idEstudiante);
            for (Observacion obs : lista) {
                model.addRow(new Object[]{
                        obs.getId(), obs.getTipo(), obs.getSeveridad(), obs.getFecha(),
                        obs.getMateria(), obs.getDescripcion().length() > 50 ? obs.getDescripcion().substring(0, 50) + "..." : obs.getDescripcion(),
                        obs.isActiva() ? "Activa" : "Anulada"
                });
            }
            JOptionPane.showMessageDialog(panel, "Total: " + lista.size() + " observaciones en el historial.", "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        bottomPanel.add(btnCargar);

        tarjeta.add(bottomPanel, BorderLayout.SOUTH);
        panel.add(tarjeta, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelReclamos() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Solicitud de Reclamos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjeta = new JPanel(new BorderLayout(10, 15));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(30, 30, 30, 30)
        ));

        JLabel lblInstruccion = new JLabel("<html>Si consideras que una observación registrada es injusta, " +
                "describe detalladamente el motivo de tu reclamo a continuación. El Coordinador lo revisará.</html>");
        lblInstruccion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInstruccion.setForeground(new Color(71, 85, 105));
        tarjeta.add(lblInstruccion, BorderLayout.NORTH);

        txtReclamo = new JTextArea();
        txtReclamo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtReclamo.setLineWrap(true);
        txtReclamo.setWrapStyleWord(true);
        txtReclamo.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(txtReclamo);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));
        tarjeta.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnEnviar = estilizarBoton("Enviar Reclamo", new Color(16, 185, 129));
        btnEnviar.addActionListener(e -> {
            String descripcion = txtReclamo.getText().trim();
            if (descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Por favor escriba el motivo del reclamo.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.solicitarReclamo(descripcion);
            txtReclamo.setText("");
            fileManager.guardarTodosLosDatos();
            JOptionPane.showMessageDialog(panel, "Tu reclamo ha sido enviado exitosamente y está pendiente de revisión.");
        });
        bottomPanel.add(btnEnviar);

        tarjeta.add(bottomPanel, BorderLayout.SOUTH);
        panel.add(tarjeta, BorderLayout.CENTER);

        return panel;
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

    private void cargarObservacionesActivas() {
        tableModel.setRowCount(0);
        List<Observacion> lista = controller.consultarObservaciones(idEstudiante);
        for (Observacion obs : lista) {
            tableModel.addRow(new Object[]{
                    obs.getId(), obs.getTipo(), obs.getSeveridad(), obs.getFecha(),
                    obs.getMateria(), obs.getDescripcion().length() > 50 ? obs.getDescripcion().substring(0, 50) + "..." : obs.getDescripcion(),
                    obs.isActiva() ? "Activa" : "Anulada"
            });
        }
    }
}