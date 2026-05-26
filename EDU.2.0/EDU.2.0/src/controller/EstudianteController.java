package controller;

import model.ObservacionModel;
import model.Observacion;
import model.ReclamoModel;

import java.util.List;

/**
 * Maneja lo que puede hacer un estudiante dentro del sistema.
 * Solo tiene operaciones de consulta: ver observaciones,
 * ver historial y presentar reclamos.
 * No puede crear ni modificar observaciones.
 *
 * @author EduObservador
 * @version 1.0
 */
public class EstudianteController {

    /** Para consultar las observaciones del estudiante */
    private ObservacionModel observacionModel;

    /** Para registrar los reclamos que presente el estudiante */
    private ReclamoModel reclamoModel;

    /**
     * Recibe los modelos necesarios para las operaciones del estudiante.
     *
     * @param observacionModel Modelo de observaciones
     * @param reclamoModel     Modelo de reclamos
     */
    public EstudianteController(ObservacionModel observacionModel, ReclamoModel reclamoModel) {
        this.observacionModel = observacionModel;
        this.reclamoModel = reclamoModel;
    }

    /**
     * Devuelve las observaciones activas del estudiante.
     * Las anuladas no aparecen en esta consulta.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista de observaciones activas
     */
    public List<Observacion> consultarObservaciones(String idEstudiante) {
        return observacionModel.obtenerObservaciones(idEstudiante);
    }

    /**
     * Devuelve todo el historial del estudiante, incluyendo las observaciones anuladas.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista completa del historial
     */
    public List<Observacion> consultarHistorial(String idEstudiante) {
        return observacionModel.obtenerHistorialCompleto(idEstudiante);
    }

    /**
     * Registra un reclamo presentado por el estudiante.
     *
     * @param descripcion Motivo del reclamo
     */
    public void solicitarReclamo(String descripcion) {
        reclamoModel.guardarReclamo(descripcion);
    }
}
