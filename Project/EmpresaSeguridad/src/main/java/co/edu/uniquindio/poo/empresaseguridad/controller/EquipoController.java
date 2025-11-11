package co.edu.uniquindio.poo.empresaseguridad.controller;

import co.edu.uniquindio.poo.empresaseguridad.model.*;
import java.util.List;

public class EquipoController {
    private SistemaSeguridad sistemaSeguridad;

    public EquipoController(SistemaSeguridad sistemaSeguridad) {
        this.sistemaSeguridad = sistemaSeguridad;
    }

    public boolean crearEquipo(Equipo equipo) {
        return sistemaSeguridad.agregarEquipo(equipo);
    }

    public boolean eliminarEquipo(String codigo) {
        return sistemaSeguridad.eliminarEquipo(codigo);
    }

    public boolean actualizarEquipo(String codigoAntiguo, Equipo equipoNuevo) {
        return sistemaSeguridad.actualizarEquipo(codigoAntiguo, equipoNuevo);
    }

    public List<Equipo> obtenerListaEquipos() {
        return sistemaSeguridad.getEquipos();
    }

    public Equipo buscarEquipo(String codigo) {
        return sistemaSeguridad.buscarEquipo(codigo);
    }

    public boolean asignarEquipoAEmpleado(String codigoEquipo, String documentoEmpleado) {
        Equipo equipo = buscarEquipo(codigoEquipo);
        Empleado empleado = sistemaSeguridad.buscarEmpleado(documentoEmpleado);

        if (equipo != null && empleado != null && equipo.getEstado() == EstadoEquipo.DISPONIBLE) {
            empleado.asignarEquipo(equipo);
            equipo.setEstado(EstadoEquipo.EN_USO);
            return true;
        }
        return false;
    }

    public boolean retirarEquipoDeEmpleado(String codigoEquipo, String documentoEmpleado) {
        Equipo equipo = buscarEquipo(codigoEquipo);
        Empleado empleado = sistemaSeguridad.buscarEmpleado(documentoEmpleado);

        if (equipo != null && empleado != null) {
            empleado.retirarEquipo(equipo);
            equipo.setEstado(EstadoEquipo.DISPONIBLE);
            return true;
        }
        return false;
    }

    public double calcularValorTotalEquipos() {
        return sistemaSeguridad.calcularValorTotalRecursos();
    }

    public double calcularValorEquiposEmpleado(String documentoEmpleado) {
        Empleado empleado = sistemaSeguridad.buscarEmpleado(documentoEmpleado);
        if (empleado != null) {
            return empleado.calcularValorTotalEquipos();
        }
        return 0.0;
    }

    public List<Equipo> obtenerEquiposDisponibles() {
        return sistemaSeguridad.getEquipos().stream()
                .filter(e -> e.getEstado() == EstadoEquipo.DISPONIBLE)
                .toList();
    }
}
