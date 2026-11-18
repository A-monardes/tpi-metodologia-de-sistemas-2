package service;

import model.SolicitudRequest;

public class ValidadorSolicitud {

    public void validar(SolicitudRequest datos) throws SolicitudInvalidaException {
        if (datos.getNombreEstudiante() == null || datos.getNombreEstudiante().trim().isEmpty()) {
            throw new SolicitudInvalidaException("El nombre del estudiante no puede estar vacio");
        }
        if (datos.getLegajo() == null || datos.getLegajo().trim().isEmpty()) {
            throw new SolicitudInvalidaException("El legajo no puede estar vacio");
        }
    }
}
