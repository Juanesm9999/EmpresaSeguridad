package co.edu.uniquindio.poo.empresaseguridad.viewController;

import co.edu.uniquindio.poo.empresaseguridad.App;
import co.edu.uniquindio.poo.empresaseguridad.controller.*;
import co.edu.uniquindio.poo.empresaseguridad.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.util.List;

public class ReportesViewController {
    private App app;
    private EmpleadoController empleadoController;
    private ServicioController servicioController;
    private EquipoController equipoController;

    @FXML private TextArea txtReporte;
    @FXML private ComboBox<String> cmbTipoReporte;
    @FXML private Button btnGenerar;
    @FXML private Button btnRegresar;
    @FXML private TextField txtDocumentoEmpleado;
    @FXML private Label lblGastoTotal;
    @FXML private Label lblCostoServicios;
    @FXML private Label lblValorEquipos;

    @FXML
    void initialize() {
        cmbTipoReporte.setItems(FXCollections.observableArrayList(
                "Listado de Empleados por Tipo",
                "Servicios Activos",
                "Dotación por Empleado",
                "Gasto Total Empresa",
                "Costo Total Servicios",
                "Valor Total Equipos"
        ));
    }

    public void setApp(App app) {
        this.app = app;
        empleadoController = new EmpleadoController(app.getSistemaSeguridad());
        servicioController = new ServicioController(app.getSistemaSeguridad());
        equipoController = new EquipoController(app.getSistemaSeguridad());
        actualizarResumen();
    }

    private void actualizarResumen() {
        double gastoNomina = empleadoController.calcularGastoNomina();
        double costoServicios = servicioController.calcularCostoTotalMensual();
        double valorEquipos = equipoController.calcularValorTotalEquipos();

        lblGastoTotal.setText(String.format("Gasto Nómina: $%.2f", gastoNomina));
        lblCostoServicios.setText(String.format("Costo Servicios: $%.2f", costoServicios));
        lblValorEquipos.setText(String.format("Valor Equipos: $%.2f", valorEquipos));
    }

    @FXML
    void onGenerar() {
        String tipoReporte = cmbTipoReporte.getValue();
        if (tipoReporte == null) {
            mostrarAlerta("Advertencia", "Seleccione un tipo de reporte", Alert.AlertType.WARNING);
            return;
        }

        txtReporte.clear();
        StringBuilder reporte = new StringBuilder();
        reporte.append("=".repeat(80)).append("\n");
        reporte.append("SEGURCOL S.A. - ").append(tipoReporte).append("\n");
        reporte.append("Fecha: ").append(LocalDateTime.now()).append("\n");
        reporte.append("=".repeat(80)).append("\n\n");

        switch (tipoReporte) {
            case "Listado de Empleados por Tipo":
                generarReporteEmpleados(reporte);
                break;
            case "Servicios Activos":
                generarReporteServiciosActivos(reporte);
                break;
            case "Dotación por Empleado":
                generarReporteDotacion(reporte);
                break;
            case "Gasto Total Empresa":
                generarReporteGastoTotal(reporte);
                break;
            case "Costo Total Servicios":
                generarReporteCostoServicios(reporte);
                break;
            case "Valor Total Equipos":
                generarReporteValorEquipos(reporte);
                break;
        }

        txtReporte.setText(reporte.toString());
    }

    private void generarReporteEmpleados(StringBuilder reporte) {
        reporte.append("VIGILANTES:\n");
        reporte.append("-".repeat(80)).append("\n");
        List<Vigilante> vigilantes = empleadoController.obtenerListaVigilantes();
        for (Vigilante v : vigilantes) {
            reporte.append(String.format("Doc: %s | Nombre: %s | Turno: %s | Puesto: %s | Salario: $%.2f\n",
                    v.getDocumento(), v.getNombre(), v.getTurno(), v.getNumeroPuesto(), v.calcularSalarioTotal()));
        }

        reporte.append("\n\nSUPERVISORES:\n");
        reporte.append("-".repeat(80)).append("\n");
        List<Supervisor> supervisores = empleadoController.obtenerListaSupervisores();
        for (Supervisor s : supervisores) {
            reporte.append(String.format("Doc: %s | Nombre: %s | Turno: %s | Vigilantes: %d | Salario: $%.2f\n",
                    s.getDocumento(), s.getNombre(), s.getTurno(),
                    s.getVigilantesSupervisa().size(), s.calcularSalarioTotal()));
        }

        reporte.append("\n\nOPERADORES DE MONITOREO:\n");
        reporte.append("-".repeat(80)).append("\n");
        List<OperadorMonitoreo> operadores = empleadoController.obtenerListaOperadores();
        for (OperadorMonitoreo o : operadores) {
            reporte.append(String.format("Doc: %s | Nombre: %s | Turno: %s | Zonas: %d | Salario: $%.2f\n",
                    o.getDocumento(), o.getNombre(), o.getTurno(),
                    o.getNumeroZonasMonitoreo(), o.calcularSalarioTotal()));
        }
    }

    private void generarReporteServiciosActivos(StringBuilder reporte) {
        List<ServicioSeguridad> servicios = servicioController.obtenerServiciosActivos();
        reporte.append("SERVICIOS ACTIVOS:\n");
        reporte.append("-".repeat(80)).append("\n");

        for (ServicioSeguridad s : servicios) {
            String tipo = "";
            if (s instanceof CustodiaFija) tipo = "Custodia Fija";
            else if (s instanceof PatrullajeMovil) tipo = "Patrullaje Móvil";
            else if (s instanceof MonitoreoRemoto) tipo = "Monitoreo Remoto";

            reporte.append(String.format("Código: %s | Cliente: %s | Tipo: %s | Costo: $%.2f\n",
                    s.getCodigoContrato(), s.getCliente(), tipo, s.calcularCostoMensual()));
            reporte.append(String.format("  Personal asignado: %d | Equipos asignados: %d\n",
                    s.getEmpleadosAsignados().size(), s.getEquiposAsignados().size()));
            reporte.append("\n");
        }
    }

    private void generarReporteDotacion(StringBuilder reporte) {
        String documento = txtDocumentoEmpleado.getText();
        if (documento.isEmpty()) {
            reporte.append("ERROR: Debe ingresar un documento de empleado\n");
            return;
        }

        Empleado empleado = empleadoController.buscarEmpleado(documento);
        if (empleado == null) {
            reporte.append("ERROR: Empleado no encontrado\n");
            return;
        }

        reporte.append("DOTACIÓN DEL EMPLEADO:\n");
        reporte.append("-".repeat(80)).append("\n");
        reporte.append(String.format("Documento: %s\n", empleado.getDocumento()));
        reporte.append(String.format("Nombre: %s\n", empleado.getNombre()));
        reporte.append(String.format("Turno: %s\n\n", empleado.getTurno()));

        reporte.append("EQUIPOS ASIGNADOS:\n");
        List<Equipo> equipos = empleado.getEquiposAsignados();
        if (equipos.isEmpty()) {
            reporte.append("  No tiene equipos asignados\n");
        } else {
            for (Equipo eq : equipos) {
                reporte.append(String.format("  - %s | Tipo: %s | Valor: $%.2f\n",
                        eq.getCodigo(), eq.getTipo(), eq.getValorReposicion()));
            }
        }

        double valorTotal = empleado.calcularValorTotalEquipos();
        reporte.append(String.format("\nVALOR TOTAL DE DOTACIÓN: $%.2f\n", valorTotal));
    }

    private void generarReporteGastoTotal(StringBuilder reporte) {
        double gastoNomina = empleadoController.calcularGastoNomina();
        double valorEquipos = equipoController.calcularValorTotalEquipos();

        reporte.append("GASTO TOTAL DE LA EMPRESA:\n");
        reporte.append("-".repeat(80)).append("\n");
        reporte.append(String.format("Gasto en Nómina:          $%.2f\n", gastoNomina));
        reporte.append(String.format("Valor Total en Equipos:   $%.2f\n", valorEquipos));
        reporte.append("-".repeat(80)).append("\n");
        reporte.append(String.format("TOTAL:                    $%.2f\n", gastoNomina + valorEquipos));
    }

    private void generarReporteCostoServicios(StringBuilder reporte) {
        double costoTotal = servicioController.calcularCostoTotalMensual();

        reporte.append("COSTO TOTAL DE SERVICIOS MENSUALES:\n");
        reporte.append("-".repeat(80)).append("\n");

        List<ServicioSeguridad> servicios = servicioController.obtenerListaServicios();
        for (ServicioSeguridad s : servicios) {
            reporte.append(String.format("%s - %s: $%.2f\n",
                    s.getCodigoContrato(), s.getCliente(), s.calcularCostoMensual()));
        }

        reporte.append("-".repeat(80)).append("\n");
        reporte.append(String.format("COSTO TOTAL MENSUAL: $%.2f\n", costoTotal));
    }

    private void generarReporteValorEquipos(StringBuilder reporte) {
        double valorTotal = equipoController.calcularValorTotalEquipos();

        reporte.append("VALOR TOTAL DE EQUIPOS:\n");
        reporte.append("-".repeat(80)).append("\n");

        List<Equipo> equipos = equipoController.obtenerListaEquipos();
        for (Equipo eq : equipos) {
            reporte.append(String.format("%s - %s [%s]: $%.2f\n",
                    eq.getCodigo(), eq.getTipo(), eq.getEstado(), eq.getValorReposicion()));
        }

        reporte.append("-".repeat(80)).append("\n");
        reporte.append(String.format("VALOR TOTAL: $%.2f\n", valorTotal));
    }

    @FXML
    void onRegresar() {
        app.openViewPrincipal();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}