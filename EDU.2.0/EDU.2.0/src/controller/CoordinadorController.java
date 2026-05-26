package controller;

import model.ObservacionModel;
import model.Observacion;
import model.UsuarioModel;
import model.Usuario;
import model.ReporteModel;

import java.util.List;

/**
 * Maneja todas las operaciones administrativas del coordinador.
 * Es el controlador con mas permisos: puede gestionar usuarios,
 * anular observaciones y generar reportes de convivencia.
 *
 * @author EduObservador
 * @version 1.0
 */
public class CoordinadorController {

    /** Para crear, editar y deshabilitar usuarios */
    private UsuarioModel usuarioModel;

    /** Para anular observaciones y consultar historiales */
    private ObservacionModel observacionModel;

    /** Para generar reportes de convivencia */
    private ReporteModel reporteModel;

    /**
     * Recibe los tres modelos que necesita el coordinador para trabajar.
     *
     * @param usuarioModel     Modelo de usuarios
     * @param observacionModel Modelo de observaciones
     * @param reporteModel     Modelo de reportes
     */
    public CoordinadorController(UsuarioModel usuarioModel,
                                  ObservacionModel observacionModel,
                                  ReporteModel reporteModel) {
        this.usuarioModel = usuarioModel;
        this.observacionModel = observacionModel;
        this.reporteModel = reporteModel;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Si el codigo ya existe, no lo crea y devuelve false.
     *
     * @param codigo     Codigo del nuevo usuario
     * @param contrasena Contrasena
     * @param rol        ESTUDIANTE, DOCENTE o COORDINADOR
     * @return true si se creo, false si el codigo ya estaba en uso
     */
    public boolean crearUsuario(String codigo, String contrasena, String rol) {
        return usuarioModel.crearUsuario(codigo, contrasena, rol);
    }

    /**
     * Cambia la contrasena de un usuario existente.
     *
     * @param codigo          Codigo del usuario
     * @param nuevaContrasena Nueva contrasena
     * @return true si se actualizo, false si el usuario no existe
     */
    public boolean editarUsuario(String codigo, String nuevaContrasena) {
        return usuarioModel.editarUsuario(codigo, nuevaContrasena);
    }

    /**
     * Deshabilita un usuario para que no pueda iniciar sesion.
     * No lo elimina, solo lo marca como inactivo.
     *
     * @param codigo Codigo del usuario a deshabilitar
     * @return true si se deshabilito, false si no existe o ya estaba inactivo
     */
    public boolean deshabilitarUsuario(String codigo) {
        return usuarioModel.deshabilitarUsuario(codigo);
    }

    /**
     * Anula una observacion y registra el motivo.
     * Solo el coordinador puede hacer esto.
     *
     * @param id            ID de la observacion
     * @param justificacion Por que se anula
     * @return true si se anulo, false si no se encontro o ya estaba anulada
     */
    public boolean anularObservacion(int id, String justificacion) {
        return observacionModel.anularObservacion(id, justificacion);
    }

    /**
     * Genera el reporte completo de convivencia de un estudiante.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Texto con el reporte listo para mostrar en consola
     */
    public String generarReportes(String idEstudiante) {
        return reporteModel.generarReporte(idEstudiante);
    }

    /**
     * Devuelve el historial completo de un estudiante,
     * incluyendo observaciones activas y anuladas.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Lista con todo el historial
     */
    public List<Observacion> consultarHistorial(String idEstudiante) {
        return observacionModel.obtenerHistorialCompleto(idEstudiante);
    }

    /**
     * Devuelve la lista de todos los usuarios registrados en el sistema.
     *
     * @return Lista de usuarios
     */
    public List<Usuario> listarUsuarios() {
        return usuarioModel.listarUsuarios();
    }
}
