package co.edu.uniquindio.poo.empresaseguridad.model;

import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ServicioSeguridad implements IAuditable, IAgendable {
    private String codigoContrato;
    private String cliente;
    private double tarifaMensual;
    private EstadoServicio estado;
    private List<Empleado> empleadosAsignados;
    private List<Equipo> equiposAsignados;
    private List<RegistroNovedad> novedades;
    private List<AgendaItem> agenda;

    public ServicioSeguridad(String codigoContrato, String cliente,
                    double tarifaMensual, EstadoServicio estado) {
        this.codigoContrato = codigoContrato;
        this.cliente = cliente;
        this.tarifaMensual = tarifaMensual;
        this.estado = estado;
        this.empleadosAsignados = new ArrayList<>();
        this.equiposAsignados = new ArrayList<>();
        this.novedades = new ArrayList<>();
        this.agenda = new ArrayList<>();
    }



    public abstract double calcularCostoMensual();

    public void asignarEmpleado(Empleado empleado) {
        empleadosAsignados.add(empleado);
    }

    public void asignarEquipo(Equipo equipo) {
        equiposAsignados.add(equipo);
    }

    @Override
    public void registrarNovedad(RegistroNovedad novedad) {
        novedades.add(novedad);
    }

    @Override
    public List<RegistroNovedad> obtenerNovedades(LocalDateTime desde, LocalDateTime hasta) {
        return novedades.stream()
                .filter(n -> !n.fecha().isBefore(desde) && !n.fecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public void programar(LocalDate fecha, String descripcion) {
        agenda.add(new AgendaItem(fecha, descripcion, "Servicio"));
    }

    @Override
    public List<AgendaItem> obtenerAgenda(LocalDate desde, LocalDate hasta) {
        return agenda.stream()
                .filter(a -> !a.fecha().isBefore(desde) && !a.fecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    // Getters y Setters
    public String getCodigoContrato() { return codigoContrato; }
    public void setCodigoContrato(String codigoContrato) { this.codigoContrato = codigoContrato; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public double getTarifaMensual() { return tarifaMensual; }
    public void setTarifaMensual(double tarifaMensual) { this.tarifaMensual = tarifaMensual; }
    public EstadoServicio getEstado() { return estado; }
    public void setEstado(EstadoServicio estado) { this.estado = estado; }
    public List<Empleado> getEmpleadosAsignados() { return empleadosAsignados; }
    public List<Equipo> getEquiposAsignados() { return equiposAsignados; }
}
