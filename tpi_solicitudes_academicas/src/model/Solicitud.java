package model;

import java.time.LocalDate;

public class Solicitud {
    private final String id;
    private final String nombreEstudiante;
    private final String legajo;
    private final TipoSolicitud tipo;
    private final String descripcion;
    private final Prioridad prioridad;
    private EstadoSolicitud estado;
    private String area;
    private String responsable;
    private final LocalDate fechaCreacion;

    public Solicitud(String id, SolicitudRequest datos, LocalDate fechaCreacion) {
        this.id = id;
        this.nombreEstudiante = datos.getNombreEstudiante();
        this.legajo = datos.getLegajo();
        this.tipo = datos.getTipo();
        this.descripcion = datos.getDescripcion();
        this.prioridad = datos.getPrioridad();
        this.estado = EstadoSolicitud.PENDIENTE;
        this.area = datos.getArea();
        this.responsable = datos.getResponsable();
        this.fechaCreacion = fechaCreacion;
    }

    public void cambiarEstado(EstadoSolicitud nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void asignarResponsable(String area, String responsable) {
        this.area = area;
        this.responsable = responsable;
    }

    public String getId() { return id; }
    public String getNombreEstudiante() { return nombreEstudiante; }
    public String getLegajo() { return legajo; }
    public TipoSolicitud getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public Prioridad getPrioridad() { return prioridad; }
    public EstadoSolicitud getEstado() { return estado; }
    public String getArea() { return area; }
    public String getResponsable() { return responsable; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }

    public String toLineaArchivo() {
        return String.join(";", id, nombreEstudiante, legajo, tipo.name(),
                prioridad.name(), estado.name(), area, responsable, fechaCreacion.toString());
    }
}
