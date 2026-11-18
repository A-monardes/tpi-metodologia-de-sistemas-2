package model;

/** Datos de entrada para registrar una solicitud. */
public class SolicitudRequest {
    private final String nombreEstudiante;
    private final String legajo;
    private final TipoSolicitud tipo;
    private final String descripcion;
    private final Prioridad prioridad;
    private final String area;
    private final String responsable;

    public SolicitudRequest(String nombreEstudiante, String legajo, TipoSolicitud tipo,
                             String descripcion, Prioridad prioridad, String area,
                             String responsable) {
        this.nombreEstudiante = nombreEstudiante;
        this.legajo = legajo;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.area = area;
        this.responsable = responsable;
    }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public String getLegajo() { return legajo; }
    public TipoSolicitud getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public Prioridad getPrioridad() { return prioridad; }
    public String getArea() { return area; }
    public String getResponsable() { return responsable; }
}
