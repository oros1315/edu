package ui;

import controller.DocenteController;
import model.*;
import persistence.FileManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class DocenteFrame extends JFrame {


    private DocenteController controller;
    private ObservacionModel observacionModel;
    private ActividadModel actividadModel;
    private FileManager fileManager;
    private String idDocente;

    private JTextField txtEstudiante, txtDescripcion, txtMateria, txtArticulo;
    private JComboBox<String> cbTipo, cbSeveridad;
    private JTextField txtBuscarEstudiante;
    private JTable tablaResultados;
    private DefaultTableModel tableModel;

    private CardLayout cardLayout;
    private JPanel panelCentral;
    private JButton btnNavActivo;

    public DocenteFrame(String idDocente, ObservacionModel observacionModel,
                        ActividadModel actividadModel, FileManager fileManager) {
        this.idDocente = idDocente;
        this.observacionModel = observacionModel;
        this.actividadModel = actividadModel;
        this.fileManager = fileManager;
        this.controller = new DocenteController(observacionModel, actividadModel);

        setTitle("EduObservador v2.0 - Docente");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarUI();

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

        JLabel lblTitulo = new JLabel("🛡️ EduObservador - Modo Docente");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo, BorderLayout.WEST);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        headerRight.setOpaque(false);
        JLabel lblUser = new JLabel("ID: " + idDocente);
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

        panelCentral.add(crearPanelRegistrar(), "Registrar");
        panelCentral.add(crearPanelEditar(), "Editar");
        panelCentral.add(crearPanelActividad(), "Actividad");
        panelCentral.add(crearPanelConsultar(), "Consultar");

        JButton btnRegistrar = crearBotonMenu("📝 Registrar Observación", "Registrar");
        JButton btnEditar = crearBotonMenu("✏️ Editar Observación", "Editar");
        JButton btnActividad = crearBotonMenu("📋 Asignar Actividad", "Actividad");
        JButton btnConsultar = crearBotonMenu("🔍 Consultar Historial", "Consultar");

        sidebarPanel.add(btnRegistrar);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnEditar);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnActividad);
        sidebarPanel.add(Box.createVerticalStrut(10));
        sidebarPanel.add(btnConsultar);

        marcarBotonActivo(btnRegistrar);

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

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
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

    private JPanel crearPanelRegistrar() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Registrar Nueva Observación");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjetaForm = new JPanel(new GridBagLayout());
        tarjetaForm.setBackground(Color.WHITE);
        tarjetaForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lbl1 = new JLabel("Código del Estudiante:");
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl1, gbc);
        txtEstudiante = new JTextField(20);
        estilizarCampo(txtEstudiante);
        gbc.gridx = 1; tarjetaForm.add(txtEstudiante, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lbl2 = new JLabel("Tipo de Observación:");
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl2, gbc);
        cbTipo = new JComboBox<>(new String[]{"ACADEMICA", "DISCIPLINARIA", "FELICITACION"});
        cbTipo.setBackground(Color.WHITE);
        cbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; tarjetaForm.add(cbTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lbl3 = new JLabel("Severidad:");
        lbl3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl3, gbc);
        cbSeveridad = new JComboBox<>(new String[]{"TIPO_I", "TIPO_II", "TIPO_III"});
        cbSeveridad.setBackground(Color.WHITE);
        cbSeveridad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; tarjetaForm.add(cbSeveridad, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lbl4 = new JLabel("Materia (Opcional):");
        lbl4.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl4, gbc);
        txtMateria = new JTextField(20);
        estilizarCampo(txtMateria);
        gbc.gridx = 1; tarjetaForm.add(txtMateria, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lbl5 = new JLabel("Artículo del Manual:");
        lbl5.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl5, gbc);
        txtArticulo = new JTextField(20);
        estilizarCampo(txtArticulo);
        gbc.gridx = 1; tarjetaForm.add(txtArticulo, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lbl6 = new JLabel("Descripción Detallada:");
        lbl6.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl6, gbc);
        txtDescripcion = new JTextField(20);
        estilizarCampo(txtDescripcion);
        gbc.gridx = 1; tarjetaForm.add(txtDescripcion, gbc);

        JButton btnRegistrar = estilizarBoton("Guardar Observación", new Color(16, 185, 129));
        btnRegistrar.addActionListener(e -> registrarObservacion());
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 15, 10, 15);
        tarjetaForm.add(btnRegistrar, gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(tarjetaForm, BorderLayout.NORTH);
        panel.add(wrapper, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEditar() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Editar Observación");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjetaForm = new JPanel(new GridBagLayout());
        tarjetaForm.setBackground(Color.WHITE);
        tarjetaForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lbl1 = new JLabel("ID de la Observación:");
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl1, gbc);
        JTextField txtId = new JTextField(10);
        estilizarCampo(txtId);
        gbc.gridx = 1; tarjetaForm.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lbl2 = new JLabel("Nueva Descripción:");
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl2, gbc);
        JTextField txtNuevaDesc = new JTextField(20);
        estilizarCampo(txtNuevaDesc);
        gbc.gridx = 1; tarjetaForm.add(txtNuevaDesc, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lbl3 = new JLabel("Nueva Severidad:");
        lbl3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl3, gbc);
        JComboBox<String> cbNuevaSev = new JComboBox<>(new String[]{"TIPO_I", "TIPO_II", "TIPO_III"});
        cbNuevaSev.setBackground(Color.WHITE);
        cbNuevaSev.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; tarjetaForm.add(cbNuevaSev, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lbl4 = new JLabel("Nueva Materia:");
        lbl4.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl4, gbc);
        JTextField txtNuevaMat = new JTextField(20);
        estilizarCampo(txtNuevaMat);
        gbc.gridx = 1; tarjetaForm.add(txtNuevaMat, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lbl5 = new JLabel("Nuevo Artículo:");
        lbl5.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl5, gbc);
        JTextField txtNuevaArt = new JTextField(20);
        estilizarCampo(txtNuevaArt);
        gbc.gridx = 1; tarjetaForm.add(txtNuevaArt, gbc);

        JButton btnEditar = estilizarBoton("Actualizar Observación", new Color(245, 158, 11));
        btnEditar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                boolean exito = controller.editarObservacion(id, txtNuevaDesc.getText().trim(),
                        (String) cbNuevaSev.getSelectedItem(), txtNuevaMat.getText().trim(),
                        txtNuevaArt.getText().trim());
                if (exito) {
                    fileManager.guardarTodosLosDatos();
                    JOptionPane.showMessageDialog(panel, "Observación editada exitosamente");
                    txtId.setText(""); txtNuevaDesc.setText(""); txtNuevaMat.setText(""); txtNuevaArt.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel, "No se encontró la observación o ya está anulada", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "ID inválido. Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 15, 10, 15);
        tarjetaForm.add(btnEditar, gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(tarjetaForm, BorderLayout.NORTH);
        panel.add(wrapper, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelActividad() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Asignar Actividad de Seguimiento");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjetaForm = new JPanel(new GridBagLayout());
        tarjetaForm.setBackground(Color.WHITE);
        tarjetaForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lbl1 = new JLabel("Código del Estudiante:");
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl1, gbc);
        JTextField txtEstAct = new JTextField(20);
        estilizarCampo(txtEstAct);
        gbc.gridx = 1; tarjetaForm.add(txtEstAct, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lbl2 = new JLabel("Descripción de la Actividad:");
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tarjetaForm.add(lbl2, gbc);
        JTextField txtDescAct = new JTextField(20);
        estilizarCampo(txtDescAct);
        gbc.gridx = 1; tarjetaForm.add(txtDescAct, gbc);

        JButton btnAsignar = estilizarBoton("Asignar Actividad", new Color(37, 99, 235));
        btnAsignar.addActionListener(e -> {
            String estudiante = txtEstAct.getText().trim();
            String descripcion = txtDescAct.getText().trim();
            if (estudiante.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Complete todos los campos obligatorios", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.asignarActividad(estudiante, descripcion);
            fileManager.guardarTodosLosDatos();
            JOptionPane.showMessageDialog(panel, "Actividad asignada exitosamente");
            txtEstAct.setText(""); txtDescAct.setText("");
        });
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 15, 10, 15);
        tarjetaForm.add(btnAsignar, gbc);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(tarjetaForm, BorderLayout.NORTH);
        panel.add(wrapper, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelConsultar() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setOpaque(false);

        JLabel lblTitulo = new JLabel("Consulta de Observaciones Activas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(30, 41, 59));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel tarjetaBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        tarjetaBusqueda.setBackground(Color.WHITE);
        tarjetaBusqueda.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblCod = new JLabel("Código del Estudiante:");
        lblCod.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtBuscarEstudiante = new JTextField(20);
        estilizarCampo(txtBuscarEstudiante);

        JButton btnBuscar = estilizarBoton("🔍 Buscar", new Color(30, 58, 138));
        btnBuscar.addActionListener(e -> cargarHistorial());

        tarjetaBusqueda.add(lblCod);
        tarjetaBusqueda.add(txtBuscarEstudiante);
        tarjetaBusqueda.add(btnBuscar);

        JPanel tarjetaTabla = new JPanel(new BorderLayout());
        tarjetaTabla.setBackground(Color.WHITE);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(10, 10, 10, 10)
        ));

        String[] columnas = {"ID", "Tipo", "Severidad", "Fecha", "Materia", "Descripción"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaResultados = new JTable(tableModel);
        estilizarTabla(tablaResultados);

        JScrollPane scrollPane = new JScrollPane(tablaResultados);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(241, 245, 249)));
        tarjetaTabla.add(scrollPane, BorderLayout.CENTER);

        panel.add(tarjetaBusqueda, BorderLayout.NORTH);
        panel.add(tarjetaTabla, BorderLayout.CENTER);

        return panel;
    }

    private void registrarObservacion() {
        String estudiante = txtEstudiante.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String tipo = (String) cbTipo.getSelectedItem();
        String severidad = (String) cbSeveridad.getSelectedItem();
        String materia = txtMateria.getText().trim();
        String articulo = txtArticulo.getText().trim();

        if (estudiante.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (materia.isEmpty()) materia = "N/A";

        controller.registrarObservacion(descripcion, new Date(), tipo, severidad,
                materia, articulo, estudiante, idDocente);
        fileManager.guardarTodosLosDatos();

        JOptionPane.showMessageDialog(this, "Observación registrada exitosamente");
        txtEstudiante.setText(""); txtDescripcion.setText(""); txtMateria.setText(""); txtArticulo.setText("");
    }

    private void cargarHistorial() {
        String estudiante = txtBuscarEstudiante.getText().trim();
        if (estudiante.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del estudiante", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);
        List<Observacion> lista = controller.consultarHistorial(estudiante);
        for (Observacion obs : lista) {
            tableModel.addRow(new Object[]{
                    obs.getId(),
                    obs.getTipo(),
                    obs.getSeveridad(),
                    obs.getFecha(),
                    obs.getMateria(),
                    obs.getDescripcion().length() > 60 ? obs.getDescripcion().substring(0, 60) + "..." : obs.getDescripcion()
            });
        }

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El estudiante no tiene observaciones activas.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Se encontraron " + lista.size() + " observaciones activas.");
        }
    }
}