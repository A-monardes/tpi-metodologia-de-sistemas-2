package service;

/** Totales de un reporte de solicitudes. */
public class ReporteSolicitudes {
    private final int total;
    private final int pendientes;
    private final int enProgreso;
    private final int resueltas;
    private final int rechazadas;

    public ReporteSolicitudes(int total, int pendientes, int enProgreso, int resueltas, int rechazadas) {
        this.total = total;
        this.pendientes = pendientes;
        this.enProgreso = enProgreso;
        this.resueltas = resueltas;
        this.rechazadas = rechazadas;
    }

    public int getTotal() { return total; }
    public int getPendientes() { return pendientes; }
    public int getEnProgreso() { return enProgreso; }
    public int getResueltas() { return resueltas; }
    public int getRechazadas() { return rechazadas; }
}
