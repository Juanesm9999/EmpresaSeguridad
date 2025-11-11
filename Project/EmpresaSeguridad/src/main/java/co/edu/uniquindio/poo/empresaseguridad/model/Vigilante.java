package co.edu.uniquindio.poo.empresaseguridad.model;

import javafx.beans.value.ObservableValue;

public class Vigilante extends Empleado {
    private String numeroPuesto;
    private TipoArma tipoArma;

    public Vigilante(String documento, String nombre, Turno turno,
                     double salarioBase, double horasExtras,
                     String numeroPuesto, TipoArma tipoArma) {
        super(documento, nombre, turno, salarioBase, horasExtras);
        this.numeroPuesto = numeroPuesto;
        this.tipoArma = tipoArma;
    }

    @Override
    public double calcularSalarioTotal() {
        double valorHoraExtra = getSalarioBase() / 240 * 1.5;
        return getSalarioBase() + (getHorasExtras() * valorHoraExtra);
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
    public String getNumeroPuesto() { return numeroPuesto; }
    public void setNumeroPuesto(String numeroPuesto) { this.numeroPuesto = numeroPuesto; }
    public TipoArma getTipoArma() { return tipoArma; }
    public void setTipoArma(TipoArma tipoArma) { this.tipoArma = tipoArma; }
}
