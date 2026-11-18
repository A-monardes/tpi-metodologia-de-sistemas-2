package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.EstadoSolicitud;
import model.Prioridad;
import model.Solicitud;
import model.SolicitudRequest;
import repository.PersistenciaException;
import repository.SolicitudRepository;

public class SolicitudService {

    private final SolicitudRepository repositorio;
    private final ValidadorSolicitud validador;

    public SolicitudService(SolicitudRepository repositorio) {
        this.repositorio = repositorio;
        this.validador = new ValidadorSolicitud();
    }

    public Solicitud registrarSolicitud(SolicitudRequest datos)
            throws SolicitudInvalidaException, PersistenciaException {
        validador.validar(datos);
        Solicitud nueva = construirSolicitud(datos);
        repositorio.guardar(nueva);
        return nueva;
    }

    private Solicitud construirSolicitud(SolicitudRequest datos) {
        String id = repositorio.generarSiguienteId();
        return new Solicitud(id, datos, LocalDate.now());
    }

    public void cambiarEstado(String idSolicitud, EstadoSolicitud nuevoEstado)
            throws SolicitudNoEncontradaException, PersistenciaException {
        Solicitud solicitud = buscarObligatoria(idSolicitud);
        solicitud.cambiarEstado(nuevoEstado);
        repositorio.actualizar(solicitud);
    }

    public void asignarResponsable(String idSolicitud, String area, String responsable)
            throws SolicitudNoEncontradaException, PersistenciaException {
        Solicitud solicitud = buscarObligatoria(idSolicitud);
        solicitud.asignarResponsable(area, responsable);
        repositorio.actualizar(solicitud);
    }

    public List<Solicitud> buscarSolicitudes(EstadoSolicitud estado, Prioridad prioridad, String area) {
        List<Solicitud> resultado = new ArrayList<>();
        for (Solicitud s : repositorio.listarTodas()) {
            if (coincide(s, estado, prioridad, area)) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public List<Solicitud> listarTodas() {
        return repositorio.listarTodas();
    }

    private boolean coincide(Solicitud s, EstadoSolicitud estado, Prioridad prioridad, String area) {
        if (estado != null && s.getEstado() != estado) return false;
        if (prioridad != null && s.getPrioridad() != prioridad) return false;
        if (area != null && !area.isEmpty() && !s.getArea().equals(area)) return false;
        return true;
    }

    private Solicitud buscarObligatoria(String idSolicitud) throws SolicitudNoEncontradaException {
        Solicitud s = repositorio.buscarPorId(idSolicitud);
        if (s == null) {
            throw new SolicitudNoEncontradaException(idSolicitud);
        }
        return s;
    }
}
