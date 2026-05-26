package model;

import java.util.Date;

/**
 * Version de una observacion vista desde el lado del estudiante.
 * Hereda todo de Observacion, pero el estudiante solo puede consultarla,
 * no editarla ni eliminarla. El metodo consultar() es el unico
 * punto de acceso que tiene el estudiante a sus observaciones.
 *
 * @author EduObservador
 * @version 1.0
 */
public class ObservacionEstudiante extends Observacion {

    /**
     * Crea la vista del estudiante sobre una observacion existente.
     *
     * @param id             Numero identificador
     * @param descripcion    Descripcion de la observacion
     * @param fecha          Fecha del registro
     * @param tipo           Tipo de observacion
     * @param severidad      Nivel de gravedad
     * @param materia        Materia relacionada
     * @param articuloManual Articulo del manual aplicable
     * @param idEstudiante   Codigo del estudiante
     * @param idDocente      Codigo del docente que la registro
     */
    public ObservacionEstudiante(int id, String descripcion, Date fecha, String tipo,
                                  String severidad, String materia, String articuloManual,
                                  String idEstudiante, String idDocente) {
        super(id, descripcion, fecha, tipo, severidad, materia, articuloManual, idEstudiante, idDocente);
    }

    /**
     * Permite al estudiante ver el detalle de su observacion.
     * No modifica ningun dato, solo muestra la informacion.
     *
     * @return Texto con los datos de la observacion, con prefijo de consulta
     */
    public String consultar() {
        return "[CONSULTA ESTUDIANTE] " + super.mostrar();
    }
}
