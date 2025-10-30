package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public interface IAgendable {
    void Programar(LocalDate fecha,String id);
    List<AgendaItem> ObtenerAgenda(LocalDate desde, LocalDate hasta);

}
