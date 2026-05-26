package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda y administra los reclamos que presentan los estudiantes.
 * Cada reclamo queda registrado en memoria mientras el programa este corriendo.
 *
 * @author EduObservador
 * @version 1.0
 */
public class ReclamoModel {

    /** Lista donde se van acumulando los reclamos */
    private List<Reclamo> reclamos;

    /** Contador para asignar IDs unicos a cada reclamo */
    private int contadorId;

    /**
     * Inicia el modelo con la lista de reclamos vacia.
     */
    public ReclamoModel() {
        this.reclamos = new ArrayList<>();
        this.contadorId = 1;
    }

    /**
     * Registra un nuevo reclamo con su descripcion.
     *
     * @param descripcion Motivo del reclamo
     */
    public void guardarReclamo(String descripcion) {
        Reclamo reclamo = new Reclamo(contadorId++, descripcion);
        reclamos.add(reclamo);
    }

    /**
     * Devuelve todos los reclamos registrados en el sistema.
     *
     * @return Lista de reclamos
     */
    public List<Reclamo> listarReclamos() {
        return reclamos;
    }
}
