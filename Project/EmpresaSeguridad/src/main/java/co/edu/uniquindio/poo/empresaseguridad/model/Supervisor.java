package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public class Supervisor extends Empleado{

    private int bonoCoordinacion;

    public Supervisor(String nombre, String id, boolean isDia, int salarioBase, int bonoCoordinacion) {
        super(nombre, id, isDia, salarioBase);
        this.bonoCoordinacion = bonoCoordinacion;
    }

    public int getBonoCoordinacion() {
        return bonoCoordinacion;
    }

    public void setBonoCoordinacion(int bonoCoordinacion) {
        this.bonoCoordinacion = bonoCoordinacion;
    }

    @Override
    public void Programar(LocalDate fecha, String id) {

    }

    @Override
    public List<AgendaItem> ObtenerAgenda(LocalDate desde, LocalDate hasta) {
        return List.of();
    }
}
