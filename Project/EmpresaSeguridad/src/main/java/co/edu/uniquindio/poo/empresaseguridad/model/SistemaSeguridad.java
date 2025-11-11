package co.edu.uniquindio.poo.empresaseguridad.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaSeguridad {
    private List<Empleado> empleados;
    private List<ServicioSeguridad> servicios;
    private List<Equipo> equipos;

    public SistemaSeguridad() {
        this.empleados = new ArrayList<>();
        this.servicios = new ArrayList<>();
        this.equipos = new ArrayList<>();
    }

    // ==================== GESTIÓN DE EMPLEADOS ====================

    public boolean agregarEmpleado(Empleado empleado) {
        if (empleado == null || buscarEmpleado(empleado.getDocumento()) != null) {
            return false;
        }
        return empleados.add(empleado);
    }

    public boolean eliminarEmpleado(String documento) {
        Empleado empleado = buscarEmpleado(documento);
        if (empleado != null) {
            return empleados.remove(empleado);
        }
        return false;
    }

    public boolean actualizarEmpleado(String documentoAntiguo, Empleado empleadoNuevo) {
        Empleado empleadoAntiguo = buscarEmpleado(documentoAntiguo);
        if (empleadoAntiguo != null) {
            int index = empleados.indexOf(empleadoAntiguo);
            empleados.set(index, empleadoNuevo);
            return true;
        }
        return false;
    }

    public Empleado buscarEmpleado(String documento) {
        return empleados.stream()
                .filter(e -> e.getDocumento().equals(documento))
                .findFirst()
                .orElse(null);
    }

    public List<Empleado> getEmpleados() {
        return new ArrayList<>(empleados);
    }

    public double calcularGastoTotalNomina() {
        return empleados.stream()
                .mapToDouble(Empleado::calcularSalarioTotal)
                .sum();
    }

    // ==================== GESTIÓN DE SERVICIOS ====================

    public boolean agregarServicio(ServicioSeguridad servicio) {
        if (servicio == null || buscarServicio(servicio.getCodigoContrato()) != null) {
            return false;
        }
        return servicios.add(servicio);
    }

    public boolean eliminarServicio(String codigoContrato) {
        ServicioSeguridad servicio = buscarServicio(codigoContrato);
        if (servicio != null) {
            return servicios.remove(servicio);
        }
        return false;
    }

    public boolean actualizarServicio(String codigoAntiguo, ServicioSeguridad servicioNuevo) {
        ServicioSeguridad servicioAntiguo = buscarServicio(codigoAntiguo);
        if (servicioAntiguo != null) {
            int index = servicios.indexOf(servicioAntiguo);
            servicios.set(index, servicioNuevo);
            return true;
        }
        return false;
    }

    public ServicioSeguridad buscarServicio(String codigoContrato) {
        return servicios.stream()
                .filter(s -> s.getCodigoContrato().equals(codigoContrato))
                .findFirst()
                .orElse(null);
    }

    public List<ServicioSeguridad> getServicios() {
        return new ArrayList<>(servicios);
    }

    public List<ServicioSeguridad> getServiciosActivos() {
        return servicios.stream()
                .filter(s -> s.getEstado() == EstadoServicio.ACTIVO)
                .toList();
    }

    public double calcularCostoTotalServicios() {
        return servicios.stream()
                .mapToDouble(ServicioSeguridad::calcularCostoMensual)
                .sum();
    }

    // ==================== GESTIÓN DE EQUIPOS ====================

    public boolean agregarEquipo(Equipo equipo) {
        if (equipo == null || buscarEquipo(equipo.getCodigo()) != null) {
            return false;
        }
        return equipos.add(equipo);
    }

    public boolean eliminarEquipo(String codigo) {
        Equipo equipo = buscarEquipo(codigo);
        if (equipo != null) {
            return equipos.remove(equipo);
        }
        return false;
    }

    public boolean actualizarEquipo(String codigoAntiguo, Equipo equipoNuevo) {
        Equipo equipoAntiguo = buscarEquipo(codigoAntiguo);
        if (equipoAntiguo != null) {
            int index = equipos.indexOf(equipoAntiguo);
            equipos.set(index, equipoNuevo);
            return true;
        }
        return false;
    }

    public Equipo buscarEquipo(String codigo) {
        return equipos.stream()
                .filter(e -> e.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<Equipo> getEquipos() {
        return new ArrayList<>(equipos);
    }

    public List<Equipo> getEquiposDisponibles() {
        return equipos.stream()
                .filter(e -> e.getEstado() == EstadoEquipo.DISPONIBLE)
                .toList();
    }

    public double calcularValorTotalRecursos() {
        return equipos.stream()
                .mapToDouble(Equipo::getValorReposicion)
                .sum();
    }

    // ==================== OPERACIONES ESPECIALES ====================

    public boolean asignarEquipoAEmpleado(String codigoEquipo, String documentoEmpleado) {
        Equipo equipo = buscarEquipo(codigoEquipo);
        Empleado empleado = buscarEmpleado(documentoEmpleado);

        if (equipo != null && empleado != null && equipo.getEstado() == EstadoEquipo.DISPONIBLE) {
            empleado.asignarEquipo(equipo);
            equipo.setEstado(EstadoEquipo.EN_USO);
            return true;
        }
        return false;
    }

    public boolean retirarEquipoDeEmpleado(String codigoEquipo, String documentoEmpleado) {
        Equipo equipo = buscarEquipo(codigoEquipo);
        Empleado empleado = buscarEmpleado(documentoEmpleado);

        if (equipo != null && empleado != null) {
            empleado.retirarEquipo(equipo);
            equipo.setEstado(EstadoEquipo.DISPONIBLE);
            return true;
        }
        return false;
    }

    public boolean asignarPersonalAServicio(String codigoServicio, String documentoEmpleado) {
        ServicioSeguridad servicioSeguridad = buscarServicio(codigoServicio);
        Empleado empleado = buscarEmpleado(documentoEmpleado);

        if (servicioSeguridad != null && empleado != null) {
            servicioSeguridad.asignarEmpleado(empleado);
            return true;
        }
        return false;
    }

    public boolean asignarEquipoAServicio(String codigoServicio, String codigoEquipo) {
        ServicioSeguridad servicioSeguridad = buscarServicio(codigoServicio);
        Equipo equipo = buscarEquipo(codigoEquipo);

        if (servicioSeguridad != null && equipo != null) {
            servicioSeguridad.asignarEquipo(equipo);
            return true;
        }
        return false;
    }

    // ==================== REPORTES ====================

    public String generarInformeDotacionEmpleado(String documento) {
        Empleado empleado = buscarEmpleado(documento);
        if (empleado == null) {
            return "Empleado no encontrado";
        }

        StringBuilder informe = new StringBuilder();
        informe.append("INFORME DE DOTACIÓN\n");
        informe.append("====================\n");
        informe.append("Empleado: ").append(empleado.getNombre()).append("\n");
        informe.append("Documento: ").append(empleado.getDocumento()).append("\n");
        informe.append("\nEquipos Asignados:\n");

        List<Equipo> equiposAsignados = empleado.getEquiposAsignados();
        if (equiposAsignados.isEmpty()) {
            informe.append("  Sin equipos asignados\n");
        } else {
            for (Equipo equipo : equiposAsignados) {
                informe.append(String.format("  - %s (%s): $%.2f\n",
                        equipo.getCodigo(),
                        equipo.getTipo(),
                        equipo.getValorReposicion()));
            }
        }

        informe.append(String.format("\nValor Total: $%.2f\n",
                empleado.calcularValorTotalEquipos()));

        return informe.toString();
    }

    public String generarResumenFinanciero() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("RESUMEN FINANCIERO\n");
        resumen.append("==================\n");
        resumen.append(String.format("Gasto Nómina:          $%.2f\n", calcularGastoTotalNomina()));
        resumen.append(String.format("Ingreso Servicios:     $%.2f\n", calcularCostoTotalServicios()));
        resumen.append(String.format("Valor Recursos:        $%.2f\n", calcularValorTotalRecursos()));
        resumen.append(String.format("\nTotal Empleados:       %d\n", empleados.size()));
        resumen.append(String.format("Total Servicios:       %d\n", servicios.size()));
        resumen.append(String.format("Total Equipos:         %d\n", equipos.size()));

        return resumen.toString();
    }
}