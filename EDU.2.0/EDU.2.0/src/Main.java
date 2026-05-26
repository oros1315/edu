import model.*;
import persistence.FileManager;
import persistence.DataLoader;
import ui.LoginFrame;
import javax.swing.*;

/**
 * Punto de entrada del sistema EduObservador v1.0 con interfaz gráfica Swing.
 * Esta versión NO usa las vistas de consola (LoginVista, VistaEstudiante, etc.)
 */
public class    Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }


            AutenticacionModel autenticacionModel = new AutenticacionModel();
            HistorialObservaciones historial = new HistorialObservaciones();
            ObservacionModel observacionModel = new ObservacionModel(historial);
            UsuarioModel usuarioModel = new UsuarioModel(autenticacionModel);
            ReclamoModel reclamoModel = new ReclamoModel();
            ActividadModel actividadModel = new ActividadModel();
            ReporteModel reporteModel = new ReporteModel(observacionModel);


            FileManager fileManager = new FileManager(autenticacionModel, historial, reclamoModel, actividadModel);
            DataLoader dataLoader = new DataLoader(autenticacionModel, historial, observacionModel);


            fileManager.cargarTodosLosDatos();
            dataLoader.cargarDatosInicialesSiNecesario();


            new LoginFrame().setVisible(true);
        });
    }
}