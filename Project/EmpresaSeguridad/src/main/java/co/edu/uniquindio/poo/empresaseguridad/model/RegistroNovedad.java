package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDateTime;

public record RegistroNovedad(
        String id,
        LocalDateTime fecha,
        String tipo,
        String descripcion,
        String responsable
) {}
