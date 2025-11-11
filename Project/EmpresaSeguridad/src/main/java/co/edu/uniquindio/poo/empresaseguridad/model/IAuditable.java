package co.edu.uniquindio.poo.empresaseguridad.model;
import java.time.LocalDateTime;
import java.util.List;

public interface IAuditable {
    void registrarNovedad(RegistroNovedad novedad);
    List<RegistroNovedad> obtenerNovedades(LocalDateTime desde, LocalDateTime hasta);
}
