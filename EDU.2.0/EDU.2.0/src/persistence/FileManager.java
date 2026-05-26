package persistence;

import model.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Maneja la persistencia de datos en archivos de texto.
 * Guarda y carga usuarios, observaciones, reclamos y actividades.
 */
public class FileManager {

    private static final String DATA_DIR = "data/";
    private static final String USUARIOS_FILE = DATA_DIR + "usuarios.txt";
    private static final String OBSERVACIONES_FILE = DATA_DIR + "observaciones.txt";
    private static final String RECLAMOS_FILE = DATA_DIR + "reclamos.txt";
    private static final String ACTIVIDADES_FILE = DATA_DIR + "actividades.txt";
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private AutenticacionModel autenticacionModel;
    private HistorialObservaciones historial;
    private ReclamoModel reclamoModel;
    private ActividadModel actividadModel;
    
    public FileManager(AutenticacionModel autenticacionModel, HistorialObservaciones historial,
                       ReclamoModel reclamoModel, ActividadModel actividadModel) {
        this.autenticacionModel = autenticacionModel;
        this.historial = historial;
        this.reclamoModel = reclamoModel;
        this.actividadModel = actividadModel;
        crearDirectorioSiNoExiste();
    }
    
    private void crearDirectorioSiNoExiste() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    

    
    public void guardarTodosLosDatos() {
        guardarUsuarios();
        guardarObservaciones();
        guardarReclamos();
        guardarActividades();
    }
    
    private void guardarUsuarios() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USUARIOS_FILE))) {
            for (Usuario u : autenticacionModel.getUsuarios()) {
                writer.println(u.getCodigo() + "|" + u.getContrasena() + "|" + 
                              u.getRol() + "|" + u.isEstado());
            }
        } catch (IOException e) {
            System.err.println("Error guardando usuarios: " + e.getMessage());
        }
    }
    
    private void guardarObservaciones() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(OBSERVACIONES_FILE))) {
            for (Observacion obs : historial.getObservaciones()) {
                writer.println(obs.getId() + "|" + 
                              escapePipe(obs.getDescripcion()) + "|" +
                              DATE_FORMAT.format(obs.getFecha()) + "|" +
                              obs.getTipo() + "|" +
                              obs.getSeveridad() + "|" +
                              escapePipe(obs.getMateria()) + "|" +
                              escapePipe(obs.getArticuloManual()) + "|" +
                              obs.isActiva() + "|" +
                              obs.getIdEstudiante() + "|" +
                              obs.getIdDocente());
            }
        } catch (IOException e) {
            System.err.println("Error guardando observaciones: " + e.getMessage());
        }
    }
    
    private void guardarReclamos() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RECLAMOS_FILE))) {
        } catch (IOException e) {
            System.err.println("Error guardando reclamos: " + e.getMessage());
        }
    }
    
    private void guardarActividades() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACTIVIDADES_FILE))) {
        } catch (IOException e) {
            System.err.println("Error guardando actividades: " + e.getMessage());
        }
    }
    
    // ==================== CARGAR DATOS ====================
    
    public void cargarTodosLosDatos() {
        cargarUsuarios();
        cargarObservaciones();
    }
    

    private void cargarUsuarios() {
        File file = new File(USUARIOS_FILE);
        if (!file.exists()) return;
        autenticacionModel.getUsuarios().clear();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 4) {
                    String codigo = partes[0];
                    String contrasena = partes[1];
                    String rol = partes[2];
                    boolean estado = Boolean.parseBoolean(partes[3]);
                    
                    Usuario usuario = null;
                    switch (rol.toUpperCase()) {
                        case "ESTUDIANTE":
                            usuario = new Estudiante(codigo, contrasena);
                            break;
                        case "DOCENTE":
                            usuario = new Docente(codigo, contrasena);
                            break;
                        case "COORDINADOR":
                            usuario = new Coordinador(codigo, contrasena);
                            break;
                    }
                    if (usuario != null) {
                        usuario.setEstado(estado);
                        autenticacionModel.agregarUsuario(usuario);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando usuarios: " + e.getMessage());
        }
    }

    private void cargarObservaciones() {
        File file = new File(OBSERVACIONES_FILE);
        System.out.println("Buscando archivo en: " + file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("¡ERROR! El archivo no existe en esa ruta.");
            return;
        }
        System.out.println("Archivo encontrado. Tamaño: " + file.length() + " bytes.");
        historial.getObservaciones().clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 10) {
                    int id = Integer.parseInt(partes[0]);
                    String descripcion = unescapePipe(partes[1]);
                    Date fecha = DATE_FORMAT.parse(partes[2]);
                    String tipo = partes[3];
                    String severidad = partes[4];
                    String materia = unescapePipe(partes[5]);
                    String articuloManual = unescapePipe(partes[6]);
                    boolean activa = Boolean.parseBoolean(partes[7]);
                    String idEstudiante = partes[8];
                    String idDocente = partes[9];

                    Observacion obs = new Observacion(id, descripcion, fecha, tipo, severidad,
                            materia, articuloManual, idEstudiante, idDocente);
                    obs.setActiva(activa);

                    historial.agregarObservacion(obs);
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando observaciones: " + e.getMessage());
        }
    }
    private String escapePipe(String s) {
        return s == null ? "" : s.replace("|", "\\|");
    }
    
    private String unescapePipe(String s) {
        return s == null ? "" : s.replace("\\|", "|");
    }
}