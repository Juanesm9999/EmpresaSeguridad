package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;

public record AgendaItem(
        LocalDate fecha,
        String descripcion,
        String tipo
) {}
