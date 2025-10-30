package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public class OperadorMonitoreo extends Empleado{

    private int numZonas;

    public OperadorMonitoreo(String nombre, String id, boolean isDia, int salarioBase, int numZonas) {
        super(nombre, id, isDia, salarioBase);
        this.numZonas = numZonas;
    }

    public int getNumZonas() {
        return numZonas;
    }

    public void setNumZonas(int numZonas) {
        this.numZonas = numZonas;
    }

    @Override
    public void Programar(LocalDate fecha, String id) {

    }

    @Override
    public List<AgendaItem> ObtenerAgenda(LocalDate desde, LocalDate hasta) {
        return List.of();
    }
}
