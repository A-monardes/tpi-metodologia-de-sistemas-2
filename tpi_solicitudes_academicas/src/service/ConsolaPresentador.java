package service;

import java.util.List;

import model.Solicitud;

public class ConsolaPresentador {

    public void mostrarResultadosBusqueda(List<Solicitud> solicitudes) {
        for (Solicitud s : solicitudes) {
            System.out.println(s.getId() + " | " + s.getNombreEstudiante() + " | "
                    + s.getEstado() + " | " + s.getPrioridad());
        }
    }

    public void mostrarReporte(ReporteSolicitudes reporte) {
        System.out.println("===== REPORTE DE SOLICITUDES =====");
        System.out.println("Total: " + reporte.getTotal());
        System.out.println("Pendientes: " + reporte.getPendientes());
        System.out.println("En progreso: " + reporte.getEnProgreso());
        System.out.println("Resueltas: " + reporte.getResueltas());
        System.out.println("Rechazadas: " + reporte.getRechazadas());
        System.out.println("===================================");
    }
}
