package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public class Vigilante extends Empleado {

    public Vigilante(String nombre, String id, boolean isDia, int salarioBase) {
        super(nombre, id, isDia, salarioBase);
    }


    @Override
    public void Programar(LocalDate fecha, String id) {

    }

    @Override
    public List<AgendaItem> ObtenerAgenda(LocalDate desde, LocalDate hasta) {
        return List.of();
    }
}
