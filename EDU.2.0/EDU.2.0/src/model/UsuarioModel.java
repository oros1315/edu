package model;

import java.util.List;

/**
 * Se encarga de crear, editar y deshabilitar usuarios del sistema.
 * Solo el coordinador usa este modelo, ya que es el unico con
 * permisos para gestionar el catalogo de estudiantes y docentes.
 *
 * @author EduObservador
 * @version 1.0
 */
public class UsuarioModel {

    /** El modelo de autenticacion que tiene la lista de usuarios */
    private AutenticacionModel autenticacionModel;

    /**
     * Recibe el modelo de autenticacion para compartir la misma lista de usuarios.
     *
     * @param autenticacionModel Modelo de autenticacion del sistema
     */
    public UsuarioModel(AutenticacionModel autenticacionModel) {
        this.autenticacionModel = autenticacionModel;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Primero revisa que el codigo no exista; si ya existe, no lo crea.
     *
     * @param codigo     Codigo del nuevo usuario
     * @param contrasena Contrasena
     * @param rol        ESTUDIANTE, DOCENTE o COORDINADOR
     * @return true si se creo, false si el codigo ya estaba en uso
     */
    public boolean crearUsuario(String codigo, String contrasena, String rol) {
        if (autenticacionModel.buscarPorCodigo(codigo) != null) {
            return false;
        }

        Usuario nuevoUsuario;
        switch (rol.toUpperCase()) {
            case "DOCENTE":
                nuevoUsuario = new Docente(codigo, contrasena);
                break;
            case "COORDINADOR":
                nuevoUsuario = new Coordinador(codigo, contrasena);
                break;
            default:
                nuevoUsuario = new Estudiante(codigo, contrasena);
                break;
        }

        autenticacionModel.agregarUsuario(nuevoUsuario);
        return true;
    }

    /**
     * Cambia la contrasena de un usuario existente.
     * Si el usuario no existe, devuelve false.
     *
     * @param codigo          Codigo del usuario a editar
     * @param nuevaContrasena Nueva contrasena (no puede estar vacia)
     * @return true si se edito, false si el usuario no existe
     */
    public boolean editarUsuario(String codigo, String nuevaContrasena) {
        Usuario usuario = autenticacionModel.buscarPorCodigo(codigo);
        if (usuario != null) {
            if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
                usuario.setContrasena(nuevaContrasena);
            }
            return true;
        }
        return false;
    }

    /**
     * Deshabilita un usuario para que no pueda iniciar sesion.
     * No lo elimina del sistema, solo lo marca como inactivo.
     *
     * @param codigo Codigo del usuario a deshabilitar
     * @return true si se deshabilito, false si no existe o ya estaba inactivo
     */
    public boolean deshabilitarUsuario(String codigo) {
        Usuario usuario = autenticacionModel.buscarPorCodigo(codigo);
        if (usuario != null && usuario.isEstado()) {
            usuario.setEstado(false);
            return true;
        }
        return false;
    }

    /**
     * Devuelve la lista completa de usuarios del sistema.
     *
     * @return Lista de todos los usuarios
     */
    public List<Usuario> listarUsuarios() {
        return autenticacionModel.getUsuarios();
    }
}
