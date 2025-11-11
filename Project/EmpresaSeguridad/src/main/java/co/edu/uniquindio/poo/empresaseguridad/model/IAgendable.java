package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public interface IAgendable {
    void programar(LocalDate fecha, String descripcion);
    List<AgendaItem> obtenerAgenda(LocalDate desde, LocalDate hasta);
}
