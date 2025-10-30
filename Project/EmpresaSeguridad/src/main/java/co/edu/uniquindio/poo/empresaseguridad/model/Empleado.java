package co.edu.uniquindio.poo.empresaseguridad.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Empleado implements IAgendable {
    
    protected String nombre, id;
    protected boolean isDia;
    protected int salarioBase;

    private List<Equipo> listEquipos;


    public Empleado(String nombre, String id, boolean isDia, int salarioBase) {
        this.nombre = nombre;
        this.id = id;
        this.isDia = isDia;
        this.salarioBase = salarioBase;
        this.listEquipos = new ArrayList<Equipo>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDia() {
        return isDia;
    }

    public void setDia(boolean dia) {
        isDia = dia;
    }

    public int getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(int salarioBase) {
        this.salarioBase = salarioBase;
    }

    public List<Equipo> getListEquipos() {
        return listEquipos;
    }

    public void setListEquipos(List<Equipo> listEquipos) {
        this.listEquipos = listEquipos;
    }

    public int calcularSalarioTotal() {
        return 0;
    }

    public void calcularValorEquipos(){

    }
}
