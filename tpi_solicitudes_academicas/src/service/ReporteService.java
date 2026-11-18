package service;

import java.util.List;

import model.Solicitud;

public class ReporteService {

    public ReporteSolicitudes generar(List<Solicitud> solicitudes) {
        int pendientes = 0, enProgreso = 0, resueltas = 0, rechazadas = 0;
        for (Solicitud s : solicitudes) {
            switch (s.getEstado()) {
                case PENDIENTE: pendientes++; break;
                case EN_PROGRESO: enProgreso++; break;
                case RESUELTA: resueltas++; break;
                case RECHAZADA: rechazadas++; break;
            }
        }
        return new ReporteSolicitudes(solicitudes.size(), pendientes, enProgreso, resueltas, rechazadas);
    }
}
