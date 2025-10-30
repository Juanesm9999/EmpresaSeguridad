package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public class CustodiaFija extends ServicioSeguridad {

    public CustodiaFija(String codigoContrato, String id, int tarifaMensual, EstadoServicio estadoServicio) {
        super(codigoContrato, id, tarifaMensual, estadoServicio);
    }

    public void asignarVigilantePuestoPermanente(){
        
    }


    @Override
    public void Programar(LocalDate fecha, String id) {

    }

    @Override
    public List<AgendaItem> ObtenerAgenda(LocalDate desde, LocalDate hasta) {
        return List.of();
    }
}
