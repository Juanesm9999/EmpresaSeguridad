package co.edu.uniquindio.poo.empresaseguridad.controller;

import co.edu.uniquindio.poo.empresaseguridad.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class EmpleadoController {
    private SistemaSeguridad sistemaSeguridad;

    public EmpleadoController(SistemaSeguridad sistemaSeguridad) {
        this.sistemaSeguridad = sistemaSeguridad;
    }

    public boolean crearVigilante(Vigilante vigilante) {
        return sistemaSeguridad.agregarEmpleado(vigilante);
    }

    public boolean crearSupervisor(Supervisor supervisor) {
        return sistemaSeguridad.agregarEmpleado(supervisor);
    }

    public boolean crearOperadorMonitoreo(OperadorMonitoreo operador) {
        return sistemaSeguridad.agregarEmpleado(operador);
    }

    public boolean eliminarEmpleado(String documento) {
        return sistemaSeguridad.eliminarEmpleado(documento);
    }

    public boolean actualizarEmpleado(String documentoAntiguo, Empleado empleadoNuevo) {
        return sistemaSeguridad.actualizarEmpleado(documentoAntiguo, empleadoNuevo);
    }

    public List<Empleado> obtenerListaEmpleados() {
        return sistemaSeguridad.getEmpleados();
    }

    public List<Vigilante> obtenerListaVigilantes() {
        return sistemaSeguridad.getEmpleados().stream()
                .filter(e -> e instanceof Vigilante)
                .map(e -> (Vigilante) e)
                .collect(Collectors.toList());
    }

    public List<Supervisor> obtenerListaSupervisores() {
        return sistemaSeguridad.getEmpleados().stream()
                .filter(e -> e instanceof Supervisor)
                .map(e -> (Supervisor) e)
                .collect(Collectors.toList());
    }

    public List<OperadorMonitoreo> obtenerListaOperadores() {
        return sistemaSeguridad.getEmpleados().stream()
                .filter(e -> e instanceof OperadorMonitoreo)
                .map(e -> (OperadorMonitoreo) e)
                .collect(Collectors.toList());
    }

    public Empleado buscarEmpleado(String documento) {
        return sistemaSeguridad.buscarEmpleado(documento);
    }

    public double calcularGastoNomina() {
        return sistemaSeguridad.calcularGastoTotalNomina();
    }
}
