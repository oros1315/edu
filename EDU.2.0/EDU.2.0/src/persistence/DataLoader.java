package persistence;

import model.*;
import java.lang.reflect.Field;

/**
 * Carga datos iniciales si no existen y actualiza contadores.
 */
public class DataLoader {
    
    private AutenticacionModel autenticacionModel;
    private HistorialObservaciones historial;
    private ObservacionModel observacionModel;
    
    public DataLoader(AutenticacionModel autenticacionModel, 
                      HistorialObservaciones historial,
                      ObservacionModel observacionModel) {
        this.autenticacionModel = autenticacionModel;
        this.historial = historial;
        this.observacionModel = observacionModel;
    }
    
    public void cargarDatosInicialesSiNecesario() {
        if (autenticacionModel.getUsuarios().isEmpty()) {
            cargarUsuariosPorDefecto();
        }
        actualizarContadorObservaciones();
    }
    
    private void cargarUsuariosPorDefecto() {
        autenticacionModel.agregarUsuario(new Coordinador("COORD001", "coord123"));
        autenticacionModel.agregarUsuario(new Docente("DOC001", "doc123"));
        autenticacionModel.agregarUsuario(new Estudiante("EST001", "est123"));
        autenticacionModel.agregarUsuario(new Estudiante("EST002", "est456"));
    }
    
    private void actualizarContadorObservaciones() {
        try {
            Field counterField = ObservacionModel.class.getDeclaredField("contadorId");
            counterField.setAccessible(true);
            
            int maxId = 0;
            for (Observacion obs : historial.getObservaciones()) {
                if (obs.getId() > maxId) {
                    maxId = obs.getId();
                }
            }
            
            if (maxId > 0) {
                counterField.setInt(observacionModel, maxId + 1);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error actualizando contador: " + e.getMessage());
        }
    }
}