package co.edu.uniquindio.poo.empresaseguridad.model;

import javafx.beans.value.ObservableValue;

import java.util.ArrayList;
import java.util.List;

public class Supervisor extends Empleado {
    private double bonoCoordinacion;
    private List<Vigilante> vigilantesSupervisa;

    public Supervisor(String documento, String nombre, Turno turno,
                      double salarioBase, double horasExtras, double bonoCoordinacion) {
        super(documento, nombre, turno, salarioBase, horasExtras);
        this.bonoCoordinacion = bonoCoordinacion;
        this.vigilantesSupervisa = new ArrayList<>();
    }

    @Override
    public double calcularSalarioTotal() {
        double valorHoraExtra = getSalarioBase() / 240 * 1.5;
        return getSalarioBase() + (getHorasExtras() * valorHoraExtra) + bonoCoordinacion;
    }

    @Override
    public ObservableValue<String> turno() {
        return null;
    }

    @Override
    public ObservableValue<String> documento() {
        return null;
    }

    public void agregarVigilante(Vigilante vigilante) {
        vigilantesSupervisa.add(vigilante);
    }

    public void removerVigilante(Vigilante vigilante) {
        vigilantesSupervisa.remove(vigilante);
    }

    // Getters y Setters
    public double getBonoCoordinacion() { return bonoCoordinacion; }
    public void setBonoCoordinacion(double bonoCoordinacion) {
        this.bonoCoordinacion = bonoCoordinacion;
    }
    public List<Vigilante> getVigilantesSupervisa() { return vigilantesSupervisa; }
}
