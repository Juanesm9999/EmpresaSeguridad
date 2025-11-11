package co.edu.uniquindio.poo.empresaseguridad.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if (!verificarEquipo(equipo.getDocumento())) {
            listEquipos.add(equipo);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarEquipo(String id) {
        boolean centinela = false;
        for (Empleado equipo : listEquipos) {
            if (equipo.getDocumento().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }


    public boolean eliminarEquipo(String id){
        boolean centinela = false;
        for (Empleado equipo: listEquipos) {
            if (equipo.getDocumento().equals(id)) {
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
        if (!verificarVigilante(vigilante.getDocumento())) {
            listEmpleados.add(vigilante);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarVigilante(String id) {
        boolean centinela = false;
        for (Empleado vigilante : listEmpleados) {
            if (vigilante.getDocumento().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    public boolean registrarSupervisor(Empleado supervisor) {
        boolean centinela = false;
        if (!verificarSupervisor(supervisor.getDocumento())) {
            listEmpleados.add(supervisor);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarSupervisor(String id) {
        boolean centinela = false;
        for (Empleado supervisor : listEmpleados) {
            if (supervisor.getDocumento().equals(id)) {
                centinela = true;
            }
        }
        return centinela;
    }

    public boolean registrarOperador (Empleado operador) {
        boolean centinela = false;
        if (!verificarOperador(operador.getDocumento())) {
            listEmpleados.add(operador);
            centinela = true;
        }
        return centinela;
    }

    public boolean verificarOperador(String id) {
        boolean centinela = false;
        for (Empleado operador : listEmpleados) {
            if (operador.getDocumento().equals(id)) {
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

    public void asignarPersonalAServicio(ServicioSeguridad servicio, Empleado empleado) {
        if (servicio == null || empleado == null) {
            throw new IllegalArgumentException("Servicio o empleado no pueden ser nulos");
        }

        servicio.asignarEmpleado(empleado); // Usa el método ya definido en ServicioSeguridad

        registrarNovedad(new RegistroNovedad (
                "Asignación de personal",
                "Empleado " + empleado.getNombre() + " asignado al servicio " + servicio.getCodigoContrato(),
                LocalDateTime.now()
        ));
    }

    public double calcularCostoTotalServicios() {
        return listServiciosSeguridad.stream()
                .filter(servicio -> servicio.getEstado().toString().equalsIgnoreCase("ACTIVO"))
                .mapToDouble(ServicioSeguridad::calcularCostoMensual)
                .sum();
    }

    public String generarInformeDotacion(Empleado empleado) {
        if (empleado == null) return "Empleado no válido.";

        StringBuilder sb = new StringBuilder();
        sb.append("Informe de Dotación - ").append(empleado.getNombre()).append("\n");
        sb.append("-------------------------------------------------\n");

        double total = 0;
        for (Equipo equipo : empleado.getEquiposAsignados()) {
            sb.append("Código: ").append(equipo.getCodigo())
                    .append(" | Tipo: ").append(equipo.getTipo())
                    .append(" | Estado: ").append(equipo.getEstado())
                    .append(" | Valor: ").append(equipo.getValorReposicion()).append("\n");
            total += equipo.getValorReposicion();
        }

        sb.append("-------------------------------------------------\n");
        sb.append("Valor total de dotación: ").append(total);
        return sb.toString();
    }

    public double calcularGastoTotal() {
        double gastoNomina = listEmpleados.stream()
                .mapToDouble(Empleado::calcularSalarioTotal)
                .sum();

        double gastoEquipos = listEquipos.stream()
                .mapToDouble(Equipo::getValorReposicion)
                .sum();

        return gastoNomina + gastoEquipos;
    }

    private List<RegistroNovedad> listNovedades = new ArrayList<>();

    public void registrarNovedad(RegistroNovedad registroNovedad) {
        if (registroNovedad != null) {
            listNovedades.add(registroNovedad);
        }
    }

    public List<RegistroNovedad> obtenerNovedades(LocalDateTime desde, LocalDateTime hasta) {
        return listNovedades.stream()
                .filter(n -> !n.fecha().isBefore(desde) && !n.fecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    public List<ServicioSeguridad> obtenerServiciosActivos() {
        return listServiciosSeguridad.stream()
                .filter(s -> s.getEstado().toString().equalsIgnoreCase("ACTIVO"))
                .collect(Collectors.toList());
    }

    public List<Empleado> obtenerEmpleadosSinAsignacion() {
        return listEmpleados.stream()
                .filter(emp -> listServiciosSeguridad.stream()
                        .noneMatch(serv -> serv.getEmpleadosAsignados().contains(emp)))
                .collect(Collectors.toList());
    }

    public List<Empleado> obtenerEmpleados() {
        return listEmpleados;
    }

    public List<Empleado> obtenerEquiposDisponibles() {
        List<Empleado> disponibles = new ArrayList<>();

        for (Empleado equipo : listEquipos) {
            boolean asignado = false;
            for (Empleado empleado : listEmpleados) {
                if (empleado.getEquiposAsignados().contains(equipo)) {
                    asignado = true;
                    break;
                }
            }
            if (!asignado) {
                disponibles.add(equipo);
            }
        }
        return disponibles;
    }


}
