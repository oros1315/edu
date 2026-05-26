package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Almacena todas las observaciones del sistema en memoria usando un ArrayList.
 * Es la unica lista donde se guardan las observaciones; todos los modelos
 * que necesiten consultarlas o modificarlas pasan por aqui.
 *
 * @author EduObservador
 * @version 1.0
 */
public class HistorialObservaciones {

    /** Lista donde viven todas las observaciones mientras el programa esta corriendo */
    private List<Observacion> observaciones;

    /**
     * Inicia el historial con la lista vacia.
     */
    public HistorialObservaciones() {
        this.observaciones = new ArrayList<>();
    }

    /**
     * Agrega una nueva observacion al historial.
     *
     * @param observacion La observacion que se va a guardar
     */
    public void agregarObservacion(Observacion observacion) {
        observaciones.add(observacion);
    }

    /**
     * Devuelve solo las observaciones activas de un estudiante.
     * Las anuladas no aparecen aqui; para verlas todas se usa verHistorialCompleto.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista de observaciones activas del estudiante
     */
    public List<Observacion> verHistorial(String idEstudiante) {
        List<Observacion> resultado = new ArrayList<>();
        for (Observacion obs : observaciones) {
            if (obs.getIdEstudiante().equals(idEstudiante) && obs.isActiva()) {
                resultado.add(obs);
            }
        }
        return resultado;
    }

    /**
     * Devuelve todas las observaciones de un estudiante, activas y anuladas.
     * Se usa cuando el coordinador necesita ver el historial completo.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista con todo el historial del estudiante
     */
    public List<Observacion> verHistorialCompleto(String idEstudiante) {
        List<Observacion> resultado = new ArrayList<>();
        for (Observacion obs : observaciones) {
            if (obs.getIdEstudiante().equals(idEstudiante)) {
                resultado.add(obs);
            }
        }
        return resultado;
    }

    /**
     * Busca una observacion por su ID para editarla o anularla.
     * Si no existe, devuelve null.
     *
     * @param id ID de la observacion
     * @return La observacion encontrada o null si no existe
     */
    public Observacion buscarPorId(int id) {
        for (Observacion obs : observaciones) {
            if (obs.getId() == id) {
                return obs;
            }
        }
        return null;
    }

    /**
     * Devuelve la lista completa de observaciones del sistema.
     *
     * @return Todas las observaciones
     */
    public List<Observacion> getObservaciones() {
        return observaciones;
    }
}
