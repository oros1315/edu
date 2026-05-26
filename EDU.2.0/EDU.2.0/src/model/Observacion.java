package model;

import java.util.Date;

/**
 * Representa una observacion registrada en el sistema.
 * Puede ser academica, disciplinaria o una felicitacion.
 * Esta clase esta pensada para crecer: si en el futuro se necesita
 * un nuevo tipo de observacion, se crea una subclase sin tocar este codigo.
 *
 * @author EduObservador
 * @version 1.0
 */
public class Observacion {

    /** Numero que identifica de forma unica a esta observacion */
    private int id;

    /** Texto que describe lo que ocurrio */
    private String descripcion;

    /** Fecha en que se registro la observacion */
    private Date fecha;

    /** ACADEMICA, DISCIPLINARIA o FELICITACION */
    private String tipo;

    /** Nivel de gravedad: TIPO_I, TIPO_II o TIPO_III */
    private String severidad;

    /** Asignatura relacionada con la observacion, si aplica */
    private String materia;

    /** Articulo del manual de convivencia que se aplica en este caso */
    private String articuloManual;

    /**
     * Indica si la observacion sigue vigente.
     * Cuando el coordinador la anula, este campo pasa a false.
     */
    private boolean activa;

    /** Codigo del estudiante al que pertenece esta observacion */
    private String idEstudiante;

    /** Codigo del docente que la registro */
    private String idDocente;

    /**
     * Crea una nueva observacion con todos sus datos.
     * Por defecto queda activa al momento de crearla.
     *
     * @param id             Numero identificador
     * @param descripcion    Descripcion de lo ocurrido
     * @param fecha          Fecha del registro
     * @param tipo           Tipo de observacion
     * @param severidad      Nivel de gravedad
     * @param materia        Materia relacionada
     * @param articuloManual Articulo del manual aplicable
     * @param idEstudiante   Codigo del estudiante
     * @param idDocente      Codigo del docente que registra
     */
    public Observacion(int id, String descripcion, Date fecha, String tipo,
                       String severidad, String materia, String articuloManual,
                       String idEstudiante, String idDocente) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.severidad = severidad;
        this.materia = materia;
        this.articuloManual = articuloManual;
        this.idEstudiante = idEstudiante;
        this.idDocente = idDocente;
        this.activa = true;
    }

    /**
     * Devuelve todos los datos de la observacion en una sola linea de texto.
     * Se usa para mostrarla en consola o en reportes.
     *
     * @return Texto con todos los campos de la observacion
     */
    public String mostrar() {
        return "ID: " + id
                + " | Tipo: " + tipo
                + " | Severidad: " + severidad
                + " | Fecha: " + fecha
                + " | Materia: " + materia
                + " | Descripcion: " + descripcion
                + " | Articulo: " + articuloManual
                + " | Estado: " + (activa ? "Activa" : "ANULADA");
    }

    /**
     * Actualiza los campos que se pueden modificar de una observacion.
     * El id, la fecha, el estudiante y el docente no se pueden cambiar.
     *
     * @param descripcion    Nueva descripcion
     * @param severidad      Nuevo nivel de gravedad
     * @param materia        Nueva materia
     * @param articuloManual Nuevo articulo del manual
     */
    public void editar(String descripcion, String severidad, String materia, String articuloManual) {
        this.descripcion = descripcion;
        this.severidad = severidad;
        this.materia = materia;
        this.articuloManual = articuloManual;
    }

    // ========== GETTERS Y SETTERS ==========

    /** @return ID de la observacion */
    public int getId() { return id; }

    /** @param id Nuevo ID */
    public void setId(int id) { this.id = id; }

    /** @return Descripcion */
    public String getDescripcion() { return descripcion; }

    /** @param descripcion Nueva descripcion */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** @return Fecha de registro */
    public Date getFecha() { return fecha; }

    /** @param fecha Nueva fecha */
    public void setFecha(Date fecha) { this.fecha = fecha; }

    /** @return Tipo de observacion */
    public String getTipo() { return tipo; }

    /** @param tipo Nuevo tipo */
    public void setTipo(String tipo) { this.tipo = tipo; }

    /** @return Nivel de severidad */
    public String getSeveridad() { return severidad; }

    /** @param severidad Nueva severidad */
    public void setSeveridad(String severidad) { this.severidad = severidad; }

    /** @return Materia relacionada */
    public String getMateria() { return materia; }

    /** @param materia Nueva materia */
    public void setMateria(String materia) { this.materia = materia; }

    /** @return Articulo del manual */
    public String getArticuloManual() { return articuloManual; }

    /** @param articuloManual Nuevo articulo */
    public void setArticuloManual(String articuloManual) { this.articuloManual = articuloManual; }

    /** @return true si la observacion esta activa */
    public boolean isActiva() { return activa; }

    /** @param activa Nuevo estado de la observacion */
    public void setActiva(boolean activa) { this.activa = activa; }

    /** @return Codigo del estudiante */
    public String getIdEstudiante() { return idEstudiante; }

    /** @param idEstudiante Codigo del estudiante */
    public void setIdEstudiante(String idEstudiante) { this.idEstudiante = idEstudiante; }

    /** @return Codigo del docente */
    public String getIdDocente() { return idDocente; }

    /** @param idDocente Codigo del docente */
    public void setIdDocente(String idDocente) { this.idDocente = idDocente; }
}
