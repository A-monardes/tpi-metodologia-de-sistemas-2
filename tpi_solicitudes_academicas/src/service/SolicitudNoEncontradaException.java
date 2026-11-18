package service;

public class SolicitudNoEncontradaException extends Exception {
    public SolicitudNoEncontradaException(String idSolicitud) {
        super("No se encontro la solicitud con id " + idSolicitud);
    }
}
