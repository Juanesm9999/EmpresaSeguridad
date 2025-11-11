package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDateTime;

public record RegistroNovedad(
        String id,
        String tipo,
        LocalDateTime fecha
) {
    public RegistroNovedad(String tipo, String descripcion) {
        this(java.util.UUID.randomUUID().toString(), tipo, LocalDateTime.now());
    }
}
