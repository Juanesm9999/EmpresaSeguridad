package co.edu.uniquindio.poo.empresaseguridad.model;

public class MonitoreoRemoto extends ServicioSeguridad {
    private int numeroDispositivos;

    public MonitoreoRemoto(String codigoContrato, String cliente,
                           double tarifaMensual, EstadoServicio estado,
                           int numeroDispositivos) {
        super(codigoContrato, cliente, tarifaMensual, estado);
        this.numeroDispositivos = numeroDispositivos;
    }

    @Override
    public double calcularCostoMensual() {
        double costoBase = getTarifaMensual();
        double costoDispositivos = numeroDispositivos * 150000;
        return costoBase + costoDispositivos;
    }

    // Getters y Setters
    public int getNumeroDispositivos() { return numeroDispositivos; }
    public void setNumeroDispositivos(int numeroDispositivos) {
        this.numeroDispositivos = numeroDispositivos;
    }
}
