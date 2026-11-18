import java.util.ArrayList;
import java.util.List;

import model.Solicitud;
import repository.SolicitudRepository;

/** Test double de SolicitudRepository, 100% en memoria. */
public class RepositorioSolicitudesEnMemoria implements SolicitudRepository {

    private final List<Solicitud> solicitudes = new ArrayList<>();
    private int contador = 0;
    private boolean fallarEnGuardar = false;

    @Override
    public void guardar(Solicitud solicitud) throws repository.PersistenciaException {
        if (fallarEnGuardar) {
            throw new repository.PersistenciaException("Fallo simulado de persistencia (test)");
        }
        solicitudes.add(solicitud);
        contador++;
    }

    @Override
    public List<Solicitud> listarTodas() {
        return new ArrayList<>(solicitudes);
    }

    @Override
    public Solicitud buscarPorId(String id) {
        for (Solicitud s : solicitudes) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Solicitud solicitud) {
    }

    @Override
    public String generarSiguienteId() {
        return "SOL-" + (contador + 1);
    }

    public void forzarFalloDePersistencia() {
        this.fallarEnGuardar = true;
    }

    public int cantidadGuardada() {
        return solicitudes.size();
    }
}
