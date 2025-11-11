package co.edu.uniquindio.poo.empresaseguridad.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Empleado {
    private String documento;
    private String nombre;
    private Turno turno;
    private double salarioBase;
    private double horasExtras;
    private List<Equipo> equiposAsignados;

    public Empleado(String documento, String nombre, Turno turno,
                    double salarioBase, double horasExtras) {
        this.documento = documento;
        this.nombre = nombre;
        this.turno = turno;
        this.salarioBase = salarioBase;
        this.horasExtras = horasExtras;
        this.equiposAsignados = new ArrayList<>();
    }

    public abstract double calcularSalarioTotal();

    public void asignarEquipo(Equipo equipo) {
        equiposAsignados.add(equipo);
    }

    public void retirarEquipo(Equipo equipo) {
        equiposAsignados.remove(equipo);
    }

    public double calcularValorTotalEquipos() {
        return equiposAsignados.stream()
                .mapToDouble(Equipo::getValorReposicion)
                .sum();
    }

    // Getters y Setters
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
    public double getHorasExtras() { return horasExtras; }
    public void setHorasExtras(double horasExtras) { this.horasExtras = horasExtras; }
    public List<Equipo> getEquiposAsignados() { return equiposAsignados; }
}
