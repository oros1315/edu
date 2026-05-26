package model;

/**
 * Clase base para todos los usuarios del sistema.
 * De aqui heredan Estudiante, Docente y Coordinador.
 * Guarda los datos comunes que tiene cualquier persona
 * que vaya a iniciar sesion: codigo, contrasena, rol y estado.
 *
 * @author EduObservador
 * @version 1.0
 */
public class Usuario {

    /** Codigo con el que el usuario inicia sesion */
    private String codigo;

    /** Contrasena de acceso */
    private String contrasena;

    /** Puede ser ESTUDIANTE, DOCENTE o COORDINADOR */
    private String rol;

    /** Si esta en false, el usuario no puede entrar al sistema */
    private boolean estado;

    /**
     * Crea un usuario con todos sus datos.
     *
     * @param codigo     Codigo unico del usuario
     * @param contrasena Contrasena de acceso
     * @param rol        Rol dentro del sistema
     * @param estado     true si esta activo, false si esta deshabilitado
     */
    public Usuario(String codigo, String contrasena, String rol, boolean estado) {
        this.codigo = codigo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }

    /**
     * Devuelve un texto con la informacion basica del usuario.
     * Las subclases lo sobreescriben para agregar su propio prefijo de rol.
     *
     * @return Texto con codigo, rol y estado
     */
    public String mostrarInfo() {
        return "Codigo: " + codigo + " | Rol: " + rol + " | Estado: " + (estado ? "Activo" : "Inactivo");
    }


    /** @return Codigo del usuario */
    public String getCodigo() { return codigo; }

    /** @param codigo Nuevo codigo */
    public void setCodigo(String codigo) { this.codigo = codigo; }

    /** @return Contrasena */
    public String getContrasena() { return contrasena; }

    /** @param contrasena Nueva contrasena */
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    /** @return Rol del usuario */
    public String getRol() { return rol; }

    /** @param rol Nuevo rol */
    public void setRol(String rol) { this.rol = rol; }

    /** @return true si el usuario esta activo */
    public boolean isEstado() { return estado; }

    /** @param estado Nuevo estado */
    public void setEstado(boolean estado) { this.estado = estado; }
}
