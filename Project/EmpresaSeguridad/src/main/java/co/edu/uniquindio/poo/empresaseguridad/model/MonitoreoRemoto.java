package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public class MonitoreoRemoto extends ServicioSeguridad{

    private int numDispositivosVigilados;

    public MonitoreoRemoto(String codigoContrato, String id, int tarifaMensual, EstadoServicio estadoServicio, int dispositivosVigilados) {
        super(codigoContrato, id, tarifaMensual, estadoServicio);
    }

    public int getNumDispositivosVigilados() {
        return numDispositivosVigilados;
    }

    public void setNumDispositivosVigilados(int numDispositivosVigilados) {
        this.numDispositivosVigilados = numDispositivosVigilados;
    }

    @Override
    public void Programar(LocalDate fecha, String id) {

    }

    @Override
    public List<AgendaItem> ObtenerAgenda(LocalDate desde, LocalDate hasta) {
        return List.of();
    }
}
