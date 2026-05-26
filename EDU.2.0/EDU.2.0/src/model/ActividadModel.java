package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda y administra las actividades que los docentes
 * le asignan a los estudiantes como parte del seguimiento
 * de sus observaciones.
 *
 * @author EduObservador
 * @version 1.0
 */
public class ActividadModel {

    /** Lista donde se guardan todas las actividades asignadas */
    private List<Actividad> actividades;

    /** Contador para asignar IDs unicos a cada actividad */
    private int contadorId;

    /**
     * Inicia el modelo con la lista de actividades vacia.
     */
    public ActividadModel() {
        this.actividades = new ArrayList<>();
        this.contadorId = 1;
    }

    /**
     * Registra una nueva actividad para un estudiante.
     * El codigo del estudiante queda incluido en la descripcion
     * para saber a quien pertenece cada actividad.
     *
     * @param idEstudiante Codigo del estudiante
     * @param descripcion  Descripcion de la actividad a realizar
     */
    public void asignarActividad(String idEstudiante, String descripcion) {
        Actividad actividad = new Actividad(contadorId++, "[Estudiante: " + idEstudiante + "] " + descripcion);
        actividades.add(actividad);
    }

    /**
     * Devuelve todas las actividades asignadas a un estudiante especifico.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista de actividades del estudiante
     */
    public List<Actividad> listarActividades(String idEstudiante) {
        List<Actividad> resultado = new ArrayList<>();
        for (Actividad a : actividades) {
            if (a.getDescripcion().contains("[Estudiante: " + idEstudiante + "]")) {
                resultado.add(a);
            }
        }
        return resultado;
    }
}
