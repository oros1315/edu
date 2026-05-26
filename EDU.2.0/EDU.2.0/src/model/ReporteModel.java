package model;

import java.util.List;

/**
 * Genera reportes de convivencia a partir de las observaciones
 * registradas en el sistema. El resultado es un texto formateado
 * que la vista puede mostrar directamente en consola.
 *
 * @author EduObservador
 * @version 1.0
 */
public class ReporteModel {

    /** Necesitamos el modelo de observaciones para sacar los datos */
    private ObservacionModel observacionModel;

    /**
     * Recibe el modelo de observaciones para poder consultarlas.
     *
     * @param observacionModel Modelo de observaciones del sistema
     */
    public ReporteModel(ObservacionModel observacionModel) {
        this.observacionModel = observacionModel;
    }

    /**
     * Genera el reporte completo de un estudiante con todas sus observaciones,
     * activas y anuladas. Al final muestra un resumen con los totales.
     *
     * @param idEstudiante Codigo del estudiante
     * @return Texto con el reporte listo para mostrar
     */
    public String generarReporte(String idEstudiante) {
        List<Observacion> lista = observacionModel.obtenerHistorialCompleto(idEstudiante);

        if (lista.isEmpty()) {
            return "No hay observaciones registradas para el estudiante: " + idEstudiante;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== REPORTE DE CONVIVENCIA =====\n");
        sb.append("Estudiante: ").append(idEstudiante).append("\n");
        sb.append("Total de observaciones: ").append(lista.size()).append("\n");
        sb.append("----------------------------------\n");

        int activas = 0;
        int anuladas = 0;

        for (Observacion obs : lista) {
            sb.append(obs.mostrar()).append("\n");
            if (obs.isActiva()) activas++;
            else anuladas++;
        }

        sb.append("----------------------------------\n");
        sb.append("Activas: ").append(activas).append(" | Anuladas: ").append(anuladas).append("\n");
        sb.append("==================================");

        return sb.toString();
    }
}
