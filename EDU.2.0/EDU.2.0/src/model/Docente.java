package model;

/**
 * Representa a un docente dentro del sistema.
 * Hereda todo de Usuario y su rol queda fijo como DOCENTE.
 * El docente es quien registra las observaciones sobre los estudiantes.
 *
 * @author EduObservador
 * @version 1.0
 */
public class Docente extends Usuario {

    /**
     * Crea un docente con su codigo y contrasena.
     * El rol y el estado activo se asignan automaticamente.
     *
     * @param codigo     Codigo del docente
     * @param contrasena Contrasena de acceso
     */
    public Docente(String codigo, String contrasena) {
        super(codigo, contrasena, "DOCENTE", true);
    }

    /**
     * Muestra la informacion del docente con su prefijo de rol.
     *
     * @return Texto con los datos del docente
     */
    @Override
    public String mostrarInfo() {
        return "[DOCENTE] " + super.mostrarInfo();
    }
}
