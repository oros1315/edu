package model;

/**
 * Representa al coordinador o administrador del sistema.
 * Hereda todo de Usuario y su rol queda fijo como COORDINADOR.
 * Es el unico que puede anular observaciones, gestionar usuarios
 * y generar reportes de convivencia.
 *
 * @author EduObservador
 * @version 1.0
 */
public class Coordinador extends Usuario {

    /**
     * Crea un coordinador con su codigo y contrasena.
     * El rol y el estado activo se asignan automaticamente.
     *
     * @param codigo     Codigo del coordinador
     * @param contrasena Contrasena de acceso
     */
    public Coordinador(String codigo, String contrasena) {
        super(codigo, contrasena, "COORDINADOR", true);
    }

    /**
     * Muestra la informacion del coordinador con su prefijo de rol.
     *
     * @return Texto con los datos del coordinador
     */
    @Override
    public String mostrarInfo() {
        return "[COORDINADOR] " + super.mostrarInfo();
    }
}
