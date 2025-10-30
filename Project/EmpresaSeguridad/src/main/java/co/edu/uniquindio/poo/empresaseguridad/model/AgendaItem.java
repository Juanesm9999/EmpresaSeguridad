package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDate;
import java.util.List;

public class AgendaItem {
    private LocalDate fecha;
    private String descripcion;
    private String tipo;

    public AgendaItem() {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    //-----------------------------getters y setters---------------------------------------------


    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Empresa> getTheempresa() {
        return theempresa;
    }

    public void setTheempresa(List<Empresa> theempresa) {
        this.theempresa = theempresa;
    }
}
