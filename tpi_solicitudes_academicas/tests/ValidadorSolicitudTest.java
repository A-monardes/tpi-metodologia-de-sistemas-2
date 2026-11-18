import model.Prioridad;
import model.SolicitudRequest;
import model.TipoSolicitud;
import service.SolicitudInvalidaException;
import service.ValidadorSolicitud;

public class ValidadorSolicitudTest {

    private final ValidadorSolicitud validador = new ValidadorSolicitud();

    public void testValidarDatosCorrectosNoLanza() throws Exception {
        SolicitudRequest datos = request("Ana Lopez", "99999");
        validador.validar(datos);
    }

    public void testValidarNombreVacioLanzaExcepcion() {
        SolicitudRequest datos = request("", "99999");
        try {
            validador.validar(datos);
            Assert.fail("Se esperaba SolicitudInvalidaException por nombre vacio");
        } catch (SolicitudInvalidaException esperada) {
            Assert.assertTrue(esperada.getMessage().toLowerCase().contains("nombre"),
                    "El mensaje de error deberia mencionar el nombre");
        }
    }

    public void testValidarNombreNuloLanzaExcepcion() {
        SolicitudRequest datos = request(null, "99999");
        try {
            validador.validar(datos);
            Assert.fail("Se esperaba SolicitudInvalidaException por nombre nulo");
        } catch (SolicitudInvalidaException esperada) {
        }
    }

    public void testValidarLegajoVacioLanzaExcepcion() {
        SolicitudRequest datos = request("Ana Lopez", "   ");
        try {
            validador.validar(datos);
            Assert.fail("Se esperaba SolicitudInvalidaException por legajo vacio/en blanco");
        } catch (SolicitudInvalidaException esperada) {
            Assert.assertTrue(esperada.getMessage().toLowerCase().contains("legajo"),
                    "El mensaje de error deberia mencionar el legajo");
        }
    }

    private SolicitudRequest request(String nombre, String legajo) {
        return new SolicitudRequest(nombre, legajo, TipoSolicitud.OTRO,
                "descripcion de prueba", Prioridad.MEDIA, "Alumnado", "Gomez");
    }
}
