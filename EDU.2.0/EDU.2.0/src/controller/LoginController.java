package controller;

import model.AutenticacionModel;
import model.Usuario;

/**
 * Controla el proceso de inicio de sesion.
 */
public class LoginController {

    private AutenticacionModel autenticacionModel;

    public LoginController(AutenticacionModel autenticacionModel) {
        this.autenticacionModel = autenticacionModel;
    }

    /**
     * Valida el codigo y la contrasena ingresados.
     * Propaga la excepcion si hay algun error en las credenciales.
     */
    public Usuario autenticar(String codigo, String contrasena) throws Exception {
        return autenticacionModel.validarUsuario(codigo, contrasena);
    }

    public String redirigirSegunRol(Usuario usuario) {
        if (usuario == null) return "INVALIDO";
        return usuario.getRol().toUpperCase();
    }
}