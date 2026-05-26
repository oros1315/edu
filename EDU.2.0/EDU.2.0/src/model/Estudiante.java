package model;

/**
 * Representa a un estudiante dentro del sistema.
 * Hereda todo de Usuario y su rol queda fijo como ESTUDIANTE.
 * Solo puede consultar observaciones, no tiene permisos para editarlas
 * ni eliminarlas.
 *
 * @author EduObservador
 * @version 1.0
 */
public class Estudiante extends Usuario {

    /**
     * Crea un estudiante con su codigo y contrasena.
     * El rol y el estado activo se asignan automaticamente.
     *
     * @param codigo     Codigo del estudiante
     * @param contrasena Contrasena de acceso
     */
    public Estudiante(String codigo, String contrasena) {
        super(codigo, contrasena, "ESTUDIANTE", true);
    }

    /**
     * Muestra la informacion del estudiante con su prefijo de rol.
     *
     * @return Texto con los datos del estudiante
     */
    @Override
    public String mostrarInfo() {
        return "[ESTUDIANTE] " + super.mostrarInfo();
    }
}
