package model;

/**
 * Representa una actividad que el docente le asigna a un estudiante
 * como parte del seguimiento de una observacion disciplinaria o academica.
 *
 * @author EduObservador
 * @version 1.0
 */
public class Actividad {

    /** Numero que identifica esta actividad */
    private int id;

    /** Descripcion de la tarea o actividad asignada */
    private String descripcion;

    /**
     * Crea una actividad con su identificador y descripcion.
     *
     * @param id          Numero identificador
     * @param descripcion Descripcion de la actividad
     */
    public Actividad(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * Metodo para registrar la asignacion de la actividad.
     * La logica la maneja ActividadModel.
     */
    public void asignar() {
    }

    /**
     * Devuelve el ID y la descripcion de la actividad en texto.
     *
     * @return Texto con los datos de la actividad
     */
    public String mostrar() {
        return "Actividad ID: " + id + " | Descripcion: " + descripcion;
    }

    /** @return ID de la actividad */
    public int getId() { return id; }

    /** @param id Nuevo ID */
    public void setId(int id) { this.id = id; }

    /** @return Descripcion */
    public String getDescripcion() { return descripcion; }

    /** @param descripcion Nueva descripcion */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
