package co.edu.uniquindio.poo.empresaseguridad.viewController;

import co.edu.uniquindio.poo.empresaseguridad.App;
import co.edu.uniquindio.poo.empresaseguridad.controller.EmpleadoController;
import co.edu.uniquindio.poo.empresaseguridad.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SupervisorViewController {
    private EmpleadoController empleadoController;
    private ObservableList<Supervisor> listSupervisores = FXCollections.observableArrayList();
    private Supervisor selectedSupervisor;
    private App app;

    @FXML private TextField txtDocumento;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Turno> cmbTurno;
    @FXML private TextField txtSalarioBase;
    @FXML private TextField txtHorasExtras;
    @FXML private TextField txtBonoCoordinacion;
    @FXML private TextField txtNumeroVigilantes;

    @FXML private TableView<Supervisor> tblSupervisores;
    @FXML private TableColumn<Supervisor, String> tbcDocumento;
    @FXML private TableColumn<Supervisor, String> tbcNombre;
    @FXML private TableColumn<Supervisor, String> tbcTurno;
    @FXML private TableColumn<Supervisor, String> tbcSalarioBase;
    @FXML private TableColumn<Supervisor, String> tbcBono;
    @FXML private TableColumn<Supervisor, String> tbcVigilantes;
    @FXML private TableColumn<Supervisor, String> tbcSalarioTotal;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @FXML
    void initialize() {
        cmbTurno.setItems(FXCollections.observableArrayList(Turno.values()));
    }

    public void setApp(App app) {
        this.app = app;
        empleadoController = new EmpleadoController(app.getSistemaSeguridad());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerSupervisores();
        tblSupervisores.setItems(listSupervisores);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcDocumento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDocumento()));
        tbcNombre.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNombre()));
        tbcTurno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTurno().toString()));
        tbcSalarioBase.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getSalarioBase())));
        tbcBono.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getBonoCoordinacion())));
        tbcVigilantes.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getVigilantesSupervisa().size())));
        tbcSalarioTotal.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().calcularSalarioTotal())));
    }

    private void obtenerSupervisores() {
        listSupervisores.clear();
        listSupervisores.addAll(empleadoController.obtenerListaSupervisores());
    }

    private void listenerSelection() {
        tblSupervisores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedSupervisor = newSelection;
            mostrarInformacion(selectedSupervisor);
        });
    }

    private void mostrarInformacion(Supervisor supervisor) {
        if (supervisor != null) {
            txtDocumento.setText(supervisor.getDocumento());
            txtNombre.setText(supervisor.getNombre());
            cmbTurno.setValue(supervisor.getTurno());
            txtSalarioBase.setText(String.valueOf(supervisor.getSalarioBase()));
            txtHorasExtras.setText(String.valueOf(supervisor.getHorasExtras()));
            txtBonoCoordinacion.setText(String.valueOf(supervisor.getBonoCoordinacion()));
            txtNumeroVigilantes.setText(String.valueOf(supervisor.getVigilantesSupervisa().size()));
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            Supervisor supervisor = buildSupervisor();
            if (empleadoController.crearSupervisor(supervisor)) {
                listSupervisores.add(supervisor);
                limpiarCampos();
                mostrarAlerta("Éxito", "Supervisor creado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo crear el supervisor", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedSupervisor != null && validarCampos()) {
            Supervisor supervisorNuevo = buildSupervisor();
            if (empleadoController.actualizarEmpleado(selectedSupervisor.getDocumento(), supervisorNuevo)) {
                int index = listSupervisores.indexOf(selectedSupervisor);
                if (index >= 0) {
                    listSupervisores.set(index, supervisorNuevo);
                }
                tblSupervisores.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Supervisor actualizado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un supervisor de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedSupervisor != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este supervisor?");
            confirmacion.setContentText(selectedSupervisor.getNombre());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (empleadoController.eliminarEmpleado(selectedSupervisor.getDocumento())) {
                    listSupervisores.remove(selectedSupervisor);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Supervisor eliminado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un supervisor de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void onRegresar() {
        app.openViewPrincipal();
    }

    private Supervisor buildSupervisor() {
        return new Supervisor(
                txtDocumento.getText(),
                txtNombre.getText(),
                cmbTurno.getValue(),
                Double.parseDouble(txtSalarioBase.getText()),
                Double.parseDouble(txtHorasExtras.getText()),
                Double.parseDouble(txtBonoCoordinacion.getText())
        );
    }

    private boolean validarCampos() {
        if (txtDocumento.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                cmbTurno.getValue() == null || txtSalarioBase.getText().isEmpty() ||
                txtHorasExtras.getText().isEmpty() || txtBonoCoordinacion.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        try {
            Double.parseDouble(txtSalarioBase.getText());
            Double.parseDouble(txtHorasExtras.getText());
            Double.parseDouble(txtBonoCoordinacion.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Los valores numéricos deben ser válidos", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarSeleccion() {
        tblSupervisores.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        cmbTurno.setValue(null);
        txtSalarioBase.clear();
        txtHorasExtras.clear();
        txtBonoCoordinacion.clear();
        txtNumeroVigilantes.clear();
        selectedSupervisor = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
