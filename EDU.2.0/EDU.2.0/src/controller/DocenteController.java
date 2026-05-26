package controller;

import model.ObservacionModel;
import model.Observacion;
import model.ActividadModel;

import java.util.Date;
import java.util.List;

/**
 * Maneja todo lo que puede hacer un docente en el sistema.
 * Registrar observaciones, editarlas, asignar actividades
 * y consultar el historial de cualquier estudiante.
 *
 * @author EduObservador
 * @version 1.0
 */
public class DocenteController {

    /** Para crear, editar y consultar observaciones */
    private ObservacionModel observacionModel;

    /** Para asignar actividades a los estudiantes */
    private ActividadModel actividadModel;

    /**
     * Recibe los modelos que necesita el docente para trabajar.
     *
     * @param observacionModel Modelo de observaciones
     * @param actividadModel   Modelo de actividades
     */
    public DocenteController(ObservacionModel observacionModel, ActividadModel actividadModel) {
        this.observacionModel = observacionModel;
        this.actividadModel = actividadModel;
    }

    /**
     * Registra una nueva observacion sobre un estudiante.
     * El docente debe especificar el tipo, el nivel de gravedad
     * y el articulo del manual que aplica.
     *
     * @param descripcion    Lo que ocurrio
     * @param fecha          Cuando ocurrio
     * @param tipo           ACADEMICA, DISCIPLINARIA o FELICITACION
     * @param severidad      TIPO_I, TIPO_II o TIPO_III
     * @param materia        Asignatura relacionada
     * @param articuloManual Articulo del manual de convivencia
     * @param idEstudiante   Codigo del estudiante observado
     * @param idDocente      Codigo del docente que registra
     */
    public void registrarObservacion(String descripcion, Date fecha, String tipo,
                                      String severidad, String materia,
                                      String articuloManual, String idEstudiante,
                                      String idDocente) {
        observacionModel.guardarObservacion(descripcion, fecha, tipo, severidad,
                materia, articuloManual, idEstudiante, idDocente);
    }

    /**
     * Edita una observacion existente buscandola por su ID.
     * No se puede editar una observacion que ya fue anulada.
     *
     * @param id             ID de la observacion a editar
     * @param descripcion    Nueva descripcion
     * @param severidad      Nueva severidad
     * @param materia        Nueva materia
     * @param articuloManual Nuevo articulo del manual
     * @return true si se edito correctamente, false si no se encontro
     */
    public boolean editarObservacion(int id, String descripcion, String severidad,
                                      String materia, String articuloManual) {
        return observacionModel.editarObservacion(id, descripcion, severidad, materia, articuloManual);
    }

    /**
     * Asigna una actividad de seguimiento a un estudiante.
     *
     * @param idEstudiante Codigo del estudiante
     * @param descripcion  Descripcion de la actividad
     */
    public void asignarActividad(String idEstudiante, String descripcion) {
        actividadModel.asignarActividad(idEstudiante, descripcion);
    }

    /**
     * Devuelve las observaciones activas de un estudiante.
     * Util para que el docente revise el estado de un alumno
     * antes de registrar una nueva observacion.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista de observaciones activas del estudiante
     */
    public List<Observacion> consultarHistorial(String idEstudiante) {
        return observacionModel.obtenerObservaciones(idEstudiante);
    }
}
