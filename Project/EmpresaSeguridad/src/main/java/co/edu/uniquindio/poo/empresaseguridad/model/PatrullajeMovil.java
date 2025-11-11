package co.edu.uniquindio.poo.empresaseguridad.model;

public class PatrullajeMovil extends ServicioSeguridad {
    private int numeroRutas;
    private double kilometrosRecorridos;

    public PatrullajeMovil(String codigoContrato, String cliente,
                           double tarifaMensual, EstadoServicio estado,
                           int numeroRutas, double kilometrosRecorridos) {
        super(codigoContrato, cliente, tarifaMensual, estado);
        this.numeroRutas = numeroRutas;
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    @Override
    public double calcularCostoMensual() {
        double costoBase = getTarifaMensual();
        double costoRutas = numeroRutas * 300000;
        double costoKilometros = kilometrosRecorridos * 2000;
        return costoBase + costoRutas + costoKilometros;
    }

    // Getters y Setters
    public int getNumeroRutas() { return numeroRutas; }
    public void setNumeroRutas(int numeroRutas) { this.numeroRutas = numeroRutas; }
    public double getKilometrosRecorridos() { return kilometrosRecorridos; }
    public void setKilometrosRecorridos(double kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }
}
