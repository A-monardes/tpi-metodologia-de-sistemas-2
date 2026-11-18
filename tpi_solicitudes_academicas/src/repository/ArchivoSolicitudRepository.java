package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Solicitud;

/** Implementación de SolicitudRepository basada en un archivo de texto plano. */
public class ArchivoSolicitudRepository implements SolicitudRepository {

    private final String archivo;
    private final List<Solicitud> solicitudes = new ArrayList<>();
    private int contador = 0;

    public ArchivoSolicitudRepository(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public void guardar(Solicitud solicitud) throws PersistenciaException {
        solicitudes.add(solicitud);
        contador++;
        reescribirArchivo();
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
    public void actualizar(Solicitud solicitud) throws PersistenciaException {
        reescribirArchivo();
    }

    @Override
    public String generarSiguienteId() {
        return "SOL-" + (contador + 1);
    }

    private void reescribirArchivo() throws PersistenciaException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
            for (Solicitud s : solicitudes) {
                bw.write(s.toLineaArchivo());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new PersistenciaException(
                    "No se pudo escribir el archivo de solicitudes: " + archivo, e);
        }
    }
}
