package co.edu.uniquindio.poo.empresaseguridad.model;

import java.util.List;

public abstract class ServicioSeguridad implements IAgendable{
    protected String codigoContrato;
    protected String id;
    protected int tarifaMensual;
    protected  EstadoServicio estadoServicio;

    public ServicioSeguridad(String codigoContrato, String id, int tarifaMensual, EstadoServicio estadoServicio) {
        this.codigoContrato = codigoContrato;
        this.id = id;
        this.tarifaMensual = tarifaMensual;
        this.estadoServicio = estadoServicio;
    }
    //----------------------------getters y setters------------------------------------------------------------


    public String getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(String codigoContrato) {
        this.codigoContrato = codigoContrato;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTarifaMensual() {
        return tarifaMensual;
    }

    public void setTarifaMensual(int tarifaMensual) {
        this.tarifaMensual = tarifaMensual;
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public int calcularCostoMensual(){
        return 0;
    }
}
