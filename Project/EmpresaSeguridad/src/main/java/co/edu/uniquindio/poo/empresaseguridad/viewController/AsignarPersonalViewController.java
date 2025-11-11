package co.edu.uniquindio.poo.empresaseguridad.viewController;

import java.util.List;

import co.edu.uniquindio.poo.empresaseguridad.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import co.edu.uniquindio.poo.empresaseguridad.model.*;

public class AsignarPersonalViewController {

    @FXML
    private ComboBox<ServicioSeguridad> comboServiciosActivos;

    @FXML
    private TableView<Empleado> tablaEmpleadosDisponibles;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, String> colDocumento;

    @FXML
    private TableColumn<Empleado, String> colTurno;

    @FXML
    private Button btnAsignar;

    private ObservableList<ServicioSeguridad> serviciosActivos;
    private ObservableList<Empleado> empleadosDisponibles;

    private Empresa empresa; // referencia al modelo principal

    public void inicializar(Empresa empresa) {
        this.empresa = empresa;
        cargarServiciosActivos();
        cargarEmpleadosDisponibles();
    }

    private void cargarServiciosActivos() {
        List<ServicioSeguridad> activos = empresa.obtenerServiciosActivos();
        serviciosActivos = FXCollections.observableArrayList(activos);
        comboServiciosActivos.setItems(serviciosActivos);
    }

    private void cargarEmpleadosDisponibles() {
        List<Empleado> libres = empresa.obtenerEmpleadosSinAsignacion();
        empleadosDisponibles = FXCollections.observableArrayList(libres);

        colNombre.setCellValueFactory(e -> e.getValue().nombre());
        colDocumento.setCellValueFactory(e -> e.getValue().documento());
        colTurno.setCellValueFactory(e -> e.getValue().turno());

        tablaEmpleadosDisponibles.setItems(empleadosDisponibles);
    }

    @FXML
    private void asignarPersonal() {
        ServicioSeguridad servicio = comboServiciosActivos.getSelectionModel().getSelectedItem();
        Empleado empleado = tablaEmpleadosDisponibles.getSelectionModel().getSelectedItem();

        if (servicio == null || empleado == null) {
            mostrarAlerta("Debe seleccionar un servicio y un empleado.");
            return;
        }

        servicio.asignarEmpleado(empleado);
        empresa.registrarNovedad(new RegistroNovedad(
                "Asignación de personal",
                empleado.getNombre() + " asignado al servicio " + servicio.getCodigoContrato()
        ));

        empleadosDisponibles.remove(empleado);
        mostrarAlerta("Empleado asignado correctamente.");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public void setApp(App app) {
    }

    public void setServicio(ServicioSeguridad servicio) {
    }
}
