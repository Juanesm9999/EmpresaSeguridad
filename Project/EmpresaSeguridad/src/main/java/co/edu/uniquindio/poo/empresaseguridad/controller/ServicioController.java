package co.edu.uniquindio.poo.empresaseguridad.controller;

import co.edu.uniquindio.poo.empresaseguridad.model.*;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioController {
    private SistemaSeguridad sistemaSeguridad;

    public ServicioController(SistemaSeguridad sistemaSeguridad) {
        this.sistemaSeguridad = sistemaSeguridad;
    }

    public boolean crearCustodiaFija(CustodiaFija custodia) {
        return sistemaSeguridad.agregarServicio(custodia);
    }

    public boolean crearPatrullajeMovil(PatrullajeMovil patrullaje) {
        return sistemaSeguridad.agregarServicio(patrullaje);
    }

    public boolean crearMonitoreoRemoto(MonitoreoRemoto monitoreo) {
        return sistemaSeguridad.agregarServicio(monitoreo);
    }

    public boolean eliminarServicio(String codigoContrato) {
        return sistemaSeguridad.eliminarServicio(codigoContrato);
    }

    public boolean actualizarServicio(String codigoAntiguo, ServicioSeguridad servicioNuevo) {
        return sistemaSeguridad.actualizarServicio(codigoAntiguo, servicioNuevo);
    }

    public List<ServicioSeguridad> obtenerListaServicios() {
        return sistemaSeguridad.getServicios();
    }

    public List<ServicioSeguridad> obtenerServiciosActivos() {
        return sistemaSeguridad.getServicios().stream()
                .filter(s -> s.getEstado() == EstadoServicio.ACTIVO)
                .collect(Collectors.toList());
    }

    public ServicioSeguridad buscarServicio(String codigoContrato) {
        return sistemaSeguridad.buscarServicio(codigoContrato);
    }

    public double calcularCostoTotalMensual() {
        return sistemaSeguridad.calcularCostoTotalServicios();
    }

    public boolean asignarEmpleadoAServicio(String codigoServicio, String documentoEmpleado) {
        ServicioSeguridad servicio = buscarServicio(codigoServicio);
        Empleado empleado = sistemaSeguridad.buscarEmpleado(documentoEmpleado);

        if (servicio != null && empleado != null) {
            servicio.asignarEmpleado(empleado);
            return true;
        }
        return false;
    }

    public boolean asignarEquipoAServicio(String codigoServicio, String codigoEquipo) {
        ServicioSeguridad servicio = buscarServicio(codigoServicio);
        Equipo equipo = sistemaSeguridad.buscarEquipo(codigoEquipo);

        if (servicio != null && equipo != null) {
            servicio.asignarEquipo(equipo);
            return true;
        }
        return false;
    }
}
