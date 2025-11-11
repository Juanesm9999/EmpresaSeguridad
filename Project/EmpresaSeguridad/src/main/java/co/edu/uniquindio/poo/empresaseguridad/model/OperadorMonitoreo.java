package co.edu.uniquindio.poo.empresaseguridad.model;

import javafx.beans.value.ObservableValue;

public class OperadorMonitoreo extends Empleado {
    private int numeroZonasMonitoreo;

    public OperadorMonitoreo(String documento, String nombre, Turno turno,
                             double salarioBase, double horasExtras,
                             int numeroZonasMonitoreo) {
        super(documento, nombre, turno, salarioBase, horasExtras);
        this.numeroZonasMonitoreo = numeroZonasMonitoreo;
    }

    @Override
    public double calcularSalarioTotal() {
        double valorHoraExtra = getSalarioBase() / 240 * 1.5;
        double bonoZonas = numeroZonasMonitoreo * 50000;
        return getSalarioBase() + (getHorasExtras() * valorHoraExtra) + bonoZonas;
    }

    @Override
    public ObservableValue<String> turno() {
        return null;
    }

    @Override
    public ObservableValue<String> documento() {
        return null;
    }

    // Getters y Setters
    public int getNumeroZonasMonitoreo() { return numeroZonasMonitoreo; }
    public void setNumeroZonasMonitoreo(int numeroZonasMonitoreo) {
        this.numeroZonasMonitoreo = numeroZonasMonitoreo;
    }
}
