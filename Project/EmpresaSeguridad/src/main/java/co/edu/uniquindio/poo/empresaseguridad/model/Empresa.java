package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nombre, nit;
    private List<AgendaItem> listItems;
    private List<Empleado> listEmpleados;
    private List<ServicioSeguridad> listServiciosSeguridad;
    private List<Empleado> listEquipos;
    
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

    public boolean agregarEquipo(Empleado equipo) {
        boolean centinela = false;
        if (!verificarEquipo(equipo.getId())) {
            listEquipos.add(equipo);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarEquipo(String id) {
        boolean centinela = false;
        for (Empleado equipo : listEquipos) {
            if (equipo.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }


    public boolean eliminarEquipo(String id){
        boolean centinela = false;
        for (Empleado equipo: listEquipos) {
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

    public boolean registrarSupervisor(Empleado supervisor) {
        boolean centinela = false;
        if (!verificarSupervisor(supervisor.getId())) {
            listEmpleados.add(supervisor);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarSupervisor(String id) {
        boolean centinela = false;
        for (Empleado supervisor : listEmpleados) {
            if (supervisor.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    public boolean registrarOperador (Empleado operador) {
        boolean centinela = false;
        if (!verificarOperador(operador.getId())) {
            listEmpleados.add(operador);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarOperador(String id) {
        boolean centinela = false;
        for (Empleado operador : listEmpleados) {
            if (operador.getId().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    //-----------------------------------------------------------------------------------------------------------------

    public List<Vigilante> listarVigilantes() {
        List<Vigilante> vigilantes = new ArrayList<>();

        for (Empleado empleado : listEmpleados) {
            if (empleado instanceof Vigilante) {
                vigilantes.add((Vigilante) empleado);
            }
        }

        return vigilantes;
    }

    public List<OperadorMonitoreo> listarOperadores() {
        List<OperadorMonitoreo> operadores = new ArrayList<>();

        for (Empleado empleado : listEmpleados) {
            if (empleado instanceof OperadorMonitoreo) {
                operadores.add((OperadorMonitoreo) empleado);
            }
        }

        return operadores;
    }

    public List<Supervisor> listarSupervisores() {
        List<Supervisor> supervisores = new ArrayList<>();

        for (Empleado empleado : listEmpleados) {
            if (empleado instanceof Supervisor) {
                supervisores.add((Supervisor) empleado);
            }
        }

        return supervisores;
    }

    public List<Empleado> listarTodosEmpleados() {
        return new ArrayList<>(listEmpleados);
    }

    //------------------------------------------------------------------------------------------------------------------------------

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
