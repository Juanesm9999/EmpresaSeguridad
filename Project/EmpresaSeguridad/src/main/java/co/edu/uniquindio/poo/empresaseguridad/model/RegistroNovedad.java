package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDateTime;

public record RegistroNovedad(LocalDateTime fechaHora, String tipo, String descripcion,String responsable, String entidadAfectada) {
}
