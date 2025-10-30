package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public class PatrullajeMovil extends ServicioSeguridad {

    private String ruta;
    private int kmRecorridos;

    public PatrullajeMovil(String codigoContrato, String id, int tarifaMensual, EstadoServicio estadoServicio, String ruta, int kmRecorridos) {
        super(codigoContrato, id, tarifaMensual, estadoServicio);
        this.ruta = ruta;
        this.kmRecorridos = kmRecorridos;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(int kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public void patrullajeMovil (String ruta, int kmRecorridos) {

    }

    @Override
    public void Programar(LocalDate fecha, String id) {

    }

    @Override
    public List<AgendaItem> ObtenerAgenda(LocalDate desde, LocalDate hasta) {
        return null;
    }
}
