package model;

import java.util.Date;
import java.util.List;

/**
 * Maneja todas las operaciones sobre observaciones: guardarlas,
 * consultarlas, editarlas y anularlas.
 * Trabaja junto con HistorialObservaciones, que es donde
 * realmente se almacenan los datos en memoria.
 *
 * Aqui no hay nada de consola: ni Scanner ni System.out.
 *
 * @author EduObservador
 * @version 1.0
 */
public class ObservacionModel {

    /** El historial donde se guardan todas las observaciones */
    private HistorialObservaciones historial;

    /** Contador para asignar IDs unicos a cada nueva observacion */
    private int contadorId;

    /**
     * Crea el modelo con un historial vacio.
     */
    public ObservacionModel() {
        this.historial = new HistorialObservaciones();
        this.contadorId = 1;
    }

    /**
     * Crea el modelo usando un historial que ya existe.
     * Sirve para que varios modelos compartan la misma lista de observaciones.
     *
     * @param historial Historial compartido del sistema
     */
    public ObservacionModel(HistorialObservaciones historial) {
        this.historial = historial;
        this.contadorId = 1;
    }

    /**
     * Crea una nueva observacion y la guarda en el historial.
     *
     * @param descripcion    Lo que ocurrio
     * @param fecha          Cuando ocurrio
     * @param tipo           ACADEMICA, DISCIPLINARIA o FELICITACION
     * @param severidad      TIPO_I, TIPO_II o TIPO_III
     * @param materia        Asignatura relacionada (puede ser N/A)
     * @param articuloManual Articulo del manual que aplica
     * @param idEstudiante   Codigo del estudiante observado
     * @param idDocente      Codigo del docente que registra
     */
    public void guardarObservacion(String descripcion, Date fecha, String tipo, String severidad,
                                    String materia, String articuloManual,
                                    String idEstudiante, String idDocente) {
        Observacion obs = new Observacion(contadorId++, descripcion, fecha, tipo, severidad,
                materia, articuloManual, idEstudiante, idDocente);
        historial.agregarObservacion(obs);
    }

    /**
     * Devuelve solo las observaciones activas de un estudiante.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista de observaciones activas
     */
    public List<Observacion> obtenerObservaciones(String idEstudiante) {
        return historial.verHistorial(idEstudiante);
    }

    /**
     * Edita los campos de una observacion buscandola por su ID.
     * No se puede editar una observacion que ya fue anulada.
     *
     * @param id             ID de la observacion
     * @param descripcion    Nueva descripcion
     * @param severidad      Nueva severidad
     * @param materia        Nueva materia
     * @param articuloManual Nuevo articulo del manual
     * @return true si se edito, false si no se encontro o ya estaba anulada
     */
    public boolean editarObservacion(int id, String descripcion, String severidad,
                                      String materia, String articuloManual) {
        Observacion obs = historial.buscarPorId(id);
        if (obs != null && obs.isActiva()) {
            obs.editar(descripcion, severidad, materia, articuloManual);
            return true;
        }
        return false;
    }

    /**
     * Anula una observacion y registra el motivo en la descripcion.
     * Solo el coordinador puede hacer esto desde CoordinadorController.
     *
     * @param id            ID de la observacion a anular
     * @param justificacion Por que se esta anulando
     * @return true si se anulo, false si no se encontro o ya estaba anulada
     */
    public boolean anularObservacion(int id, String justificacion) {
        Observacion obs = historial.buscarPorId(id);
        if (obs != null && obs.isActiva()) {
            obs.setActiva(false);
            obs.setDescripcion("[ANULADA - " + justificacion + "] " + obs.getDescripcion());
            return true;
        }
        return false;
    }

    /**
     * Devuelve todo el historial de un estudiante, incluyendo las anuladas.
     * Se usa principalmente para generar reportes.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista completa del historial
     */
    public List<Observacion> obtenerHistorialCompleto(String idEstudiante) {
        return historial.verHistorialCompleto(idEstudiante);
    }
}
