package model;

/**
 * Representa un reclamo que presenta un estudiante sobre una observacion.
 * Guarda el motivo que expone el estudiante para solicitar una revision
 * o aclaracion de lo registrado en su historial.
 *
 * @author EduObservador
 * @version 1.0
 */
public class Reclamo {

    /** Numero que identifica este reclamo */
    private int id;

    /** Motivo o descripcion del reclamo presentado por el estudiante */
    private String descripcion;

    /**
     * Crea un nuevo reclamo con su identificador y descripcion.
     *
     * @param id          Numero identificador del reclamo
     * @param descripcion Motivo del reclamo
     */
    public Reclamo(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * Metodo para registrar el reclamo en el sistema.
     * La logica de persistencia la maneja ReclamoModel.
     */
    public void crear() {

    }

    /**
     * Devuelve el ID y la descripcion del reclamo en texto.
     *
     * @return Texto con los datos del reclamo
     */
    public String mostrar() {
        return "Reclamo ID: " + id + " | Descripcion: " + descripcion;
    }

    /** @return ID del reclamo */
    public int getId() { return id; }

    /** @param id Nuevo ID */
    public void setId(int id) { this.id = id; }

    /** @return Descripcion del reclamo */
    public String getDescripcion() { return descripcion; }

    /** @param descripcion Nueva descripcion */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
