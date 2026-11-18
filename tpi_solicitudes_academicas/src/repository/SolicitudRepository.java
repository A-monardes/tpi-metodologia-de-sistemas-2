package repository;

import java.util.List;

import model.Solicitud;

public interface SolicitudRepository {

    void guardar(Solicitud solicitud) throws PersistenciaException;

    List<Solicitud> listarTodas();

    Solicitud buscarPorId(String id);

    void actualizar(Solicitud solicitud) throws PersistenciaException;

    String generarSiguienteId();
}
