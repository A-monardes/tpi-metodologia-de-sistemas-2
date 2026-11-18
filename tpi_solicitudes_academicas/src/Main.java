import model.EstadoSolicitud;
import model.Prioridad;
import model.Solicitud;
import model.SolicitudRequest;
import model.TipoSolicitud;
import repository.ArchivoSolicitudRepository;
import repository.PersistenciaException;
import repository.SolicitudRepository;
import service.ConsolaPresentador;
import service.ReporteService;
import service.SolicitudInvalidaException;
import service.SolicitudNoEncontradaException;
import service.SolicitudService;

public class Main {
    public static void main(String[] args) {
        SolicitudRepository repositorio = new ArchivoSolicitudRepository("solicitudes.txt");
        SolicitudService servicio = new SolicitudService(repositorio);
        ReporteService reporteService = new ReporteService();
        ConsolaPresentador presentador = new ConsolaPresentador();

        registrar(servicio, new SolicitudRequest(
                "Juana Perez", "12345", TipoSolicitud.REINCORPORACION,
                "Solicita reincorporacion tras baja por salud", Prioridad.ALTA, "Alumnado", "Gomez"));
        registrar(servicio, new SolicitudRequest(
                "Luis Diaz", "54321", TipoSolicitud.CERTIFICADO,
                "Pide certificado de alumno regular", Prioridad.MEDIA, "Alumnado", "Gomez"));
        registrar(servicio, new SolicitudRequest(
                "Marta Ruiz", "11111", TipoSolicitud.EQUIVALENCIA,
                "Pide equivalencia de materia de otra universidad", Prioridad.ALTA, "Academico", "Soto"));
        registrar(servicio, new SolicitudRequest(
                "", "22222", TipoSolicitud.OTRO,
                "Solicitud con nombre vacio (deberia fallar)", Prioridad.MEDIA, "Alumnado", "Gomez"));

        cambiarEstado(servicio, "SOL-1", EstadoSolicitud.EN_PROGRESO);
        asignarResponsable(servicio, "SOL-2", "Academico", "Soto");
        cambiarEstado(servicio, "SOL-3", EstadoSolicitud.RESUELTA);

        System.out.println("--- Busqueda por area Alumnado ---");
        presentador.mostrarResultadosBusqueda(servicio.buscarSolicitudes(null, null, "Alumnado"));

        presentador.mostrarReporte(reporteService.generar(servicio.listarTodas()));
    }

    private static void registrar(SolicitudService servicio, SolicitudRequest datos) {
        try {
            Solicitud s = servicio.registrarSolicitud(datos);
            System.out.println("Solicitud registrada: " + s.getId());
        } catch (SolicitudInvalidaException e) {
            System.out.println("ERROR al registrar solicitud: " + e.getMessage());
        } catch (PersistenciaException e) {
            System.out.println("ERROR de persistencia al registrar solicitud: " + e.getMessage());
        }
    }

    private static void cambiarEstado(SolicitudService servicio, String id, EstadoSolicitud nuevoEstado) {
        try {
            servicio.cambiarEstado(id, nuevoEstado);
            System.out.println("Estado actualizado para " + id + " -> " + nuevoEstado);
        } catch (SolicitudNoEncontradaException | PersistenciaException e) {
            System.out.println("ERROR al cambiar estado de " + id + ": " + e.getMessage());
        }
    }

    private static void asignarResponsable(SolicitudService servicio, String id, String area, String responsable) {
        try {
            servicio.asignarResponsable(id, area, responsable);
            System.out.println("Responsable asignado a " + id);
        } catch (SolicitudNoEncontradaException | PersistenciaException e) {
            System.out.println("ERROR al asignar responsable a " + id + ": " + e.getMessage());
        }
    }
}
