package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de validar quién puede entrar al sistema.
 * Guarda la lista de usuarios registrados y verifica
 * que el codigo y la contrasena sean correctos al iniciar sesion.
 *
 * @author EduObservador
 * @version 2.0
 */
public class AutenticacionModel {

    /** Lista de todos los usuarios registrados en el sistema */
    private List<Usuario> usuarios;

    public AutenticacionModel() {
        this.usuarios = new ArrayList<>();
        cargarUsuariosPorDefecto();
    }

    private void cargarUsuariosPorDefecto() {
        usuarios.add(new Coordinador("COORD001", "coord123"));
        usuarios.add(new Docente("DOC001", "doc123"));
        usuarios.add(new Estudiante("EST001", "est123"));
        usuarios.add(new Estudiante("EST002", "est456"));
    }

    /**
     * Revisa si el codigo y la contrasena son correctos y si el usuario esta activo.
     * Ahora lanza una Excepcion con el motivo exacto del error.
     *
     * @param codigo     Codigo ingresado
     * @param contrasena Contrasena ingresada
     * @return El usuario si las credenciales son validas
     * @throws Exception con el mensaje exacto del fallo
     */
    public Usuario validarUsuario(String codigo, String contrasena) throws Exception {
        Usuario u = buscarPorCodigo(codigo);

        if (u == null) {
            throw new Exception("El código de usuario no existe.");
        }

        if (!u.getContrasena().equals(contrasena)) {
            throw new Exception("La contraseña es incorrecta.");
        }


        if (!u.isEstado()) {
            throw new Exception("El usuario está deshabilitado.");
        }

        return u;
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario buscarPorCodigo(String codigo) {
        for (Usuario u : usuarios) {
            if (u.getCodigo().equals(codigo)) {
                return u;
            }
        }
        return null;
    }
}