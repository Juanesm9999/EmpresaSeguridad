package co.edu.uniquindio.poo.empresaseguridad.viewController;

import java.util.List;

import co.edu.uniquindio.poo.empresaseguridad.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import co.edu.uniquindio.poo.empresaseguridad.model.*;

public class AsignarEquipoViewController {

    @FXML
    private ComboBox<Empleado> comboEmpleados;

    @FXML
    private TableView<Equipo> tablaEquiposDisponibles;

    @FXML
    private TableColumn<Equipo, String> colCodigo;

    @FXML
    private TableColumn<Equipo, String> colTipo;

    @FXML
    private TableColumn<Equipo, String> colEstado;

    @FXML
    private Button btnAsignar;

    private Empresa empresa; // referencia al modelo principal
    private ObservableList<Empleado> empleados;
    private ObservableList<Equipo> equiposDisponibles;

    public void inicializar(Empresa empresa) {
        this.empresa = empresa;
        cargarEmpleados();
        cargarEquiposDisponibles();
    }

    private void cargarEmpleados() {
        List<Empleado> lista = empresa.obtenerEmpleados();
        empleados = FXCollections.observableArrayList(lista);
        comboEmpleados.setItems(empleados);
    }

    private void cargarEquiposDisponibles() {
        List<Empleado> libres = empresa.obtenerEquiposDisponibles();
        equiposDisponibles = FXCollections.observableArrayList();

        colCodigo.setCellValueFactory(cell -> cell.getValue().codigoProperty());
        colTipo.setCellValueFactory(cell -> cell.getValue().tipoProperty());
        colEstado.setCellValueFactory(cell -> cell.getValue().estadoProperty());

        tablaEquiposDisponibles.setItems(equiposDisponibles);
    }

    @FXML
    private void asignarEquipo() {
        Empleado empleado = comboEmpleados.getSelectionModel().getSelectedItem();
        Equipo equipo = tablaEquiposDisponibles.getSelectionModel().getSelectedItem();

        if (empleado == null || equipo == null) {
            mostrarAlerta("Debe seleccionar un empleado y un equipo.");
            return;
        }

        empleado.asignarEquipo(equipo);
        empresa.registrarNovedad(new RegistroNovedad(
                "Asignación de equipo",
                "Equipo " + equipo.getCodigo() + " (" + equipo.getTipo() + ") asignado a " + empleado.getNombre()
        ));

        equiposDisponibles.remove(equipo);
        mostrarAlerta("Equipo asignado correctamente.");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setServicio(ServicioSeguridad servicio) {
    }

    public void setApp(App app) {
    }
}

