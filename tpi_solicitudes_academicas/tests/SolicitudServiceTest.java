import java.util.List;

import model.EstadoSolicitud;
import model.Prioridad;
import model.Solicitud;
import model.SolicitudRequest;
import model.TipoSolicitud;
import service.SolicitudInvalidaException;
import service.SolicitudNoEncontradaException;
import service.SolicitudService;

public class SolicitudServiceTest {

    public void testRegistrarSolicitudValidaQuedaPendienteYGuardada() throws Exception {
        RepositorioSolicitudesEnMemoria repo = new RepositorioSolicitudesEnMemoria();
        SolicitudService servicio = new SolicitudService(repo);

        Solicitud s = servicio.registrarSolicitud(request("Juana Perez", "12345"));

        Assert.assertEquals("SOL-1", s.getId(), "Id de la primera solicitud registrada");
        Assert.assertEquals(EstadoSolicitud.PENDIENTE, s.getEstado(), "Estado inicial de una solicitud nueva");
        Assert.assertEquals(1, repo.cantidadGuardada(), "Cantidad de solicitudes persistidas en el repositorio");
    }

    public void testRegistrarSolicitudConNombreVacioLanzaExcepcionYNoPersiste() {
        RepositorioSolicitudesEnMemoria repo = new RepositorioSolicitudesEnMemoria();
        SolicitudService servicio = new SolicitudService(repo);

        try {
            servicio.registrarSolicitud(request("", "12345"));
            Assert.fail("Se esperaba SolicitudInvalidaException");
        } catch (SolicitudInvalidaException esperada) {
            Assert.assertEquals(0, repo.cantidadGuardada(),
                    "Una solicitud invalida no deberia llegar a persistirse");
        } catch (Exception otra) {
            Assert.fail("Se esperaba SolicitudInvalidaException, no " + otra.getClass().getSimpleName());
        }
    }

    public void testIdsSeGeneranDeFormaIncremental() throws Exception {
        RepositorioSolicitudesEnMemoria repo = new RepositorioSolicitudesEnMemoria();
        SolicitudService servicio = new SolicitudService(repo);

        Solicitud s1 = servicio.registrarSolicitud(request("Juana Perez", "12345"));
        Solicitud s2 = servicio.registrarSolicitud(request("Luis Diaz", "54321"));

        Assert.assertEquals("SOL-1", s1.getId(), "Id de la primera solicitud");
        Assert.assertEquals("SOL-2", s2.getId(), "Id de la segunda solicitud");
    }

    public void testCambiarEstadoDeSolicitudExistente() throws Exception {
        RepositorioSolicitudesEnMemoria repo = new RepositorioSolicitudesEnMemoria();
        SolicitudService servicio = new SolicitudService(repo);
        Solicitud s = servicio.registrarSolicitud(request("Juana Perez", "12345"));

        servicio.cambiarEstado(s.getId(), EstadoSolicitud.EN_PROGRESO);

        Assert.assertEquals(EstadoSolicitud.EN_PROGRESO, s.getEstado(),
                "El estado deberia quedar actualizado tras cambiarEstado");
    }

    public void testCambiarEstadoDeSolicitudInexistenteLanzaExcepcion() {
        RepositorioSolicitudesEnMemoria repo = new RepositorioSolicitudesEnMemoria();
        SolicitudService servicio = new SolicitudService(repo);

        try {
            servicio.cambiarEstado("SOL-999", EstadoSolicitud.EN_PROGRESO);
            Assert.fail("Se esperaba SolicitudNoEncontradaException");
        } catch (SolicitudNoEncontradaException esperada) {
        } catch (Exception otra) {
            Assert.fail("Se esperaba SolicitudNoEncontradaException, no " + otra.getClass().getSimpleName());
        }
    }

    public void testAsignarResponsableActualizaAreaYResponsable() throws Exception {
        RepositorioSolicitudesEnMemoria repo = new RepositorioSolicitudesEnMemoria();
        SolicitudService servicio = new SolicitudService(repo);
        Solicitud s = servicio.registrarSolicitud(request("Luis Diaz", "54321"));

        servicio.asignarResponsable(s.getId(), "Academico", "Soto");

        Assert.assertEquals("Academico", s.getArea(), "Area tras asignar responsable");
        Assert.assertEquals("Soto", s.getResponsable(), "Responsable asignado");
    }

    public void testBuscarSolicitudesPorAreaFiltraCorrectamente() throws Exception {
        RepositorioSolicitudesEnMemoria repo = new RepositorioSolicitudesEnMemoria();
        SolicitudService servicio = new SolicitudService(repo);
        servicio.registrarSolicitud(request("Juana Perez", "12345"));
        servicio.registrarSolicitud(requestConArea("Marta Ruiz", "11111", "Academico"));

        List<Solicitud> resultado = servicio.buscarSolicitudes(null, null, "Alumnado");

        Assert.assertEquals(1, resultado.size(), "Cantidad de resultados filtrando por area Alumnado");
        Assert.assertEquals("Juana Perez", resultado.get(0).getNombreEstudiante(),
                "Nombre de la unica solicitud del area Alumnado");
    }

    public void testRegistrarPropagaExcepcionDePersistenciaSinOcultarla() {
        RepositorioSolicitudesEnMemoria repo = new RepositorioSolicitudesEnMemoria();
        repo.forzarFalloDePersistencia();
        SolicitudService servicio = new SolicitudService(repo);

        try {
            servicio.registrarSolicitud(request("Juana Perez", "12345"));
            Assert.fail("Se esperaba que la excepcion de persistencia se propagara, no que se tragara");
        } catch (repository.PersistenciaException esperada) {
        } catch (Exception otra) {
            Assert.fail("Se esperaba PersistenciaException, no " + otra.getClass().getSimpleName());
        }
    }

    private SolicitudRequest request(String nombre, String legajo) {
        return requestConArea(nombre, legajo, "Alumnado");
    }

    private SolicitudRequest requestConArea(String nombre, String legajo, String area) {
        return new SolicitudRequest(nombre, legajo, TipoSolicitud.CERTIFICADO,
                "descripcion de prueba", Prioridad.MEDIA, area, "Gomez");
    }
}
