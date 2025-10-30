package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nombre, nit;
    private List<AgendaItem> listItems;
    private List<Empleado> listEmpleados;
    private List<ServicioSeguridad> listServiciosSeguridad;
    private List<Equipo> listEquipos;
    
    public Empresa(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
        this.listItems = new ArrayList<>();
        this.listEmpleados = new ArrayList<>();
        this.listServiciosSeguridad = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<AgendaItem> getListItems() {
        return listItems;
    }

    public void setListItems(List<AgendaItem> listItems) {
        this.listItems = listItems;
    }

    public List<Empleado> getListEmpleados() {
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleado> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<ServicioSeguridad> getListServiciosSeguridad() {
        return listServiciosSeguridad;
    }

    public void setListServiciosSeguridad(List<ServicioSeguridad> listServiciosSeguridad) {
        this.listServiciosSeguridad = listServiciosSeguridad;
    }

    //--------------------------------------------- EQUIPO --------------------------------------------------------

    public boolean agregarEquipo(Equipo equipo) {
        boolean centinela = false;
        if (!verificarEquipo(equipo.getId())) {
            listEquipos.add(equipo);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarEquipo(String id) {
        boolean centinela = false;
        for (Equipo equipo : listEquipos) {
            if (equipo.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }


    public boolean eliminarEquipo(String id){
        boolean centinela = false;
        for (Equipo equipo: listEquipos) {
            if (equipo.getId().equals(id)) {
                // CORREGIDO: debe eliminar de listConsultas, NO de listMascotas
                listEquipos.remove(equipo);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public void calcularValorTotalEquipos(){

    }

    //---------------------------------------------------------- REGISTRO DE EMPLEADOS -------------------------------------------------------



    public boolean registrarVigilante(Empleado vigilante) {
        boolean centinela = false;
        if (!verificarVigilante(vigilante.getId())) {
            listEmpleados.add(vigilante);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarVigilante(String id) {
        boolean centinela = false;
        for (Empleado vigilante : listEmpleados) {
            if (vigilante.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    public boolean agregarSupervisor(Empleado supervisor) {
        boolean centinela = false;
        if (!verificarSupervisor(supervisor.getId())) {
            listEquipos.add(supervisor);
            centinela = true;
        }
        return centinela;
    }

    

    public boolean registrarOperador(OperadorMonitoreo operador) {
        return false;
    }

    public List<Vigilante> listarVigilantes() {
        return null;
    }

    public List<Supervisor> listarSupervisores() {
        return null;
    }

    public List<OperadorMonitoreo> listarOperadores() {
        return null;
    }

    public  List<Empleado> listarTodosEmpleados(){
        return null;
    }

    public void asignarPersonalAServicio(ServicioSeguridad servicio, Empleado empleado){

    }

    public double calcularCostoTotalServicios() {
        return 0;
    }

    public String generarInformeDotacion(Empleado empleado){
        return "";
    }

    public double calcularGastoTotal(){
        return 0;
    }
    public void registrarNovedad(RegistroNovedad registroNovedad){

    }


    public List<RegistroNovedad> obtenerNovedades(LocalDateTime desde, LocalDateTime hasta){
        return null;
    }



}
