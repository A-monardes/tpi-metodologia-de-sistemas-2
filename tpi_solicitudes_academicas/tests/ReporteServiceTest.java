import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.EstadoSolicitud;
import model.Prioridad;
import model.Solicitud;
import model.SolicitudRequest;
import model.TipoSolicitud;
import service.ReporteService;
import service.ReporteSolicitudes;

public class ReporteServiceTest {

    private final ReporteService reporteService = new ReporteService();

    public void testGenerarConListaVaciaDaTodosLosTotalesEnCero() {
        ReporteSolicitudes reporte = reporteService.generar(new ArrayList<Solicitud>());

        Assert.assertEquals(0, reporte.getTotal(), "Total con lista vacia");
        Assert.assertEquals(0, reporte.getPendientes(), "Pendientes con lista vacia");
        Assert.assertEquals(0, reporte.getEnProgreso(), "En progreso con lista vacia");
        Assert.assertEquals(0, reporte.getResueltas(), "Resueltas con lista vacia");
        Assert.assertEquals(0, reporte.getRechazadas(), "Rechazadas con lista vacia");
    }

    public void testGenerarCuentaCadaEstadoPorSeparado() {
        List<Solicitud> solicitudes = new ArrayList<>();
        solicitudes.add(crear(EstadoSolicitud.PENDIENTE));
        solicitudes.add(crear(EstadoSolicitud.EN_PROGRESO));
        solicitudes.add(crear(EstadoSolicitud.EN_PROGRESO));
        solicitudes.add(crear(EstadoSolicitud.RESUELTA));
        solicitudes.add(crear(EstadoSolicitud.RECHAZADA));

        ReporteSolicitudes reporte = reporteService.generar(solicitudes);

        Assert.assertEquals(5, reporte.getTotal(), "Total de solicitudes");
        Assert.assertEquals(1, reporte.getPendientes(), "Cantidad de pendientes");
        Assert.assertEquals(2, reporte.getEnProgreso(), "Cantidad en progreso");
        Assert.assertEquals(1, reporte.getResueltas(), "Cantidad de resueltas");
        Assert.assertEquals(1, reporte.getRechazadas(), "Cantidad de rechazadas");
    }

    private Solicitud crear(EstadoSolicitud estadoDeseado) {
        SolicitudRequest datos = new SolicitudRequest("Estudiante Prueba", "00000", TipoSolicitud.OTRO,
                "descripcion", Prioridad.MEDIA, "Alumnado", "Gomez");
        Solicitud s = new Solicitud("SOL-TEST", datos, LocalDate.now());
        s.cambiarEstado(estadoDeseado);
        return s;
    }
}
