package co.edu.uniquindio.poo.empresaseguridad.viewController;

import co.edu.uniquindio.poo.empresaseguridad.App;
import co.edu.uniquindio.poo.empresaseguridad.controller.EmpleadoController;
import co.edu.uniquindio.poo.empresaseguridad.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OperadorMonitoreoViewController {
    private EmpleadoController empleadoController;
    private ObservableList<OperadorMonitoreo> listOperadores = FXCollections.observableArrayList();
    private OperadorMonitoreo selectedOperador;
    private App app;

    @FXML private TextField txtDocumento;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Turno> cmbTurno;
    @FXML private TextField txtSalarioBase;
    @FXML private TextField txtHorasExtras;
    @FXML private TextField txtNumeroZonas;

    @FXML private TableView<OperadorMonitoreo> tblOperadores;
    @FXML private TableColumn<OperadorMonitoreo, String> tbcDocumento;
    @FXML private TableColumn<OperadorMonitoreo, String> tbcNombre;
    @FXML private TableColumn<OperadorMonitoreo, String> tbcTurno;
    @FXML private TableColumn<OperadorMonitoreo, String> tbcSalarioBase;
    @FXML private TableColumn<OperadorMonitoreo, String> tbcZonas;
    @FXML private TableColumn<OperadorMonitoreo, String> tbcSalarioTotal;

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
        obtenerOperadores();
        tblOperadores.setItems(listOperadores);
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
        tbcZonas.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getNumeroZonasMonitoreo())));
        tbcSalarioTotal.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().calcularSalarioTotal())));
    }

    private void obtenerOperadores() {
        listOperadores.clear();
        listOperadores.addAll(empleadoController.obtenerListaOperadores());
    }

    private void listenerSelection() {
        tblOperadores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedOperador = newSelection;
            mostrarInformacion(selectedOperador);
        });
    }

    private void mostrarInformacion(OperadorMonitoreo operador) {
        if (operador != null) {
            txtDocumento.setText(operador.getDocumento());
            txtNombre.setText(operador.getNombre());
            cmbTurno.setValue(operador.getTurno());
            txtSalarioBase.setText(String.valueOf(operador.getSalarioBase()));
            txtHorasExtras.setText(String.valueOf(operador.getHorasExtras()));
            txtNumeroZonas.setText(String.valueOf(operador.getNumeroZonasMonitoreo()));
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            OperadorMonitoreo operador = buildOperador();
            if (empleadoController.crearOperadorMonitoreo(operador)) {
                listOperadores.add(operador);
                limpiarCampos();
                mostrarAlerta("Éxito", "Operador creado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo crear el operador", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedOperador != null && validarCampos()) {
            OperadorMonitoreo operadorNuevo = buildOperador();
            if (empleadoController.actualizarEmpleado(selectedOperador.getDocumento(), operadorNuevo)) {
                int index = listOperadores.indexOf(selectedOperador);
                if (index >= 0) {
                    listOperadores.set(index, operadorNuevo);
                }
                tblOperadores.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Operador actualizado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un operador de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedOperador != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este operador?");
            confirmacion.setContentText(selectedOperador.getNombre());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (empleadoController.eliminarEmpleado(selectedOperador.getDocumento())) {
                    listOperadores.remove(selectedOperador);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Operador eliminado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un operador de la tabla", Alert.AlertType.WARNING);
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

    private OperadorMonitoreo buildOperador() {
        return new OperadorMonitoreo(
                txtDocumento.getText(),
                txtNombre.getText(),
                cmbTurno.getValue(),
                Double.parseDouble(txtSalarioBase.getText()),
                Double.parseDouble(txtHorasExtras.getText()),
                Integer.parseInt(txtNumeroZonas.getText())
        );
    }

    private boolean validarCampos() {
        if (txtDocumento.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                cmbTurno.getValue() == null || txtSalarioBase.getText().isEmpty() ||
                txtHorasExtras.getText().isEmpty() || txtNumeroZonas.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        try {
            Double.parseDouble(txtSalarioBase.getText());
            Double.parseDouble(txtHorasExtras.getText());
            Integer.parseInt(txtNumeroZonas.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Los valores numéricos deben ser válidos", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarSeleccion() {
        tblOperadores.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        cmbTurno.setValue(null);
        txtSalarioBase.clear();
        txtHorasExtras.clear();
        txtNumeroZonas.clear();
        selectedOperador = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
