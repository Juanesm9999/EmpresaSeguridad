package co.edu.uniquindio.poo.empresaseguridad.viewController;

import co.edu.uniquindio.poo.empresaseguridad.App;
import co.edu.uniquindio.poo.empresaseguridad.controller.EmpleadoController;
import co.edu.uniquindio.poo.empresaseguridad.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class VigilanteViewController {
    private EmpleadoController empleadoController;
    private ObservableList<Vigilante> listVigilantes = FXCollections.observableArrayList();
    private Vigilante selectedVigilante;
    private App app;

    @FXML private TextField txtDocumento;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Turno> cmbTurno;
    @FXML private TextField txtSalarioBase;
    @FXML private TextField txtHorasExtras;
    @FXML private TextField txtNumeroPuesto;
    @FXML private ComboBox<TipoArma> cmbTipoArma;

    @FXML private TableView<Vigilante> tblVigilantes;
    @FXML private TableColumn<Vigilante, String> tbcDocumento;
    @FXML private TableColumn<Vigilante, String> tbcNombre;
    @FXML private TableColumn<Vigilante, String> tbcTurno;
    @FXML private TableColumn<Vigilante, String> tbcSalarioBase;
    @FXML private TableColumn<Vigilante, String> tbcPuesto;
    @FXML private TableColumn<Vigilante, String> tbcTipoArma;
    @FXML private TableColumn<Vigilante, String> tbcSalarioTotal;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;

    @FXML
    void initialize() {
        cmbTurno.setItems(FXCollections.observableArrayList(Turno.values()));
        cmbTipoArma.setItems(FXCollections.observableArrayList(TipoArma.values()));
    }

    public void setApp(App app) {
        this.app = app;
        empleadoController = new EmpleadoController(app.getSistemaSeguridad());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerVigilantes();
        tblVigilantes.setItems(listVigilantes);
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
        tbcPuesto.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getNumeroPuesto()));
        tbcTipoArma.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoArma().toString()));
        tbcSalarioTotal.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().calcularSalarioTotal())));
    }

    private void obtenerVigilantes() {
        listVigilantes.clear();
        listVigilantes.addAll(empleadoController.obtenerListaVigilantes());
    }

    private void listenerSelection() {
        tblVigilantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedVigilante = newSelection;
            mostrarInformacion(selectedVigilante);
        });
    }

    private void mostrarInformacion(Vigilante vigilante) {
        if (vigilante != null) {
            txtDocumento.setText(vigilante.getDocumento());
            txtNombre.setText(vigilante.getNombre());
            cmbTurno.setValue(vigilante.getTurno());
            txtSalarioBase.setText(String.valueOf(vigilante.getSalarioBase()));
            txtHorasExtras.setText(String.valueOf(vigilante.getHorasExtras()));
            txtNumeroPuesto.setText(vigilante.getNumeroPuesto());
            cmbTipoArma.setValue(vigilante.getTipoArma());
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            Vigilante vigilante = buildVigilante();
            if (empleadoController.crearVigilante(vigilante)) {
                listVigilantes.add(vigilante);
                limpiarCampos();
                mostrarAlerta("Éxito", "Vigilante creado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo crear el vigilante", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedVigilante != null && validarCampos()) {
            Vigilante vigilanteNuevo = buildVigilante();
            if (empleadoController.actualizarEmpleado(selectedVigilante.getDocumento(), vigilanteNuevo)) {
                int index = listVigilantes.indexOf(selectedVigilante);
                if (index >= 0) {
                    listVigilantes.set(index, vigilanteNuevo);
                }
                tblVigilantes.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Vigilante actualizado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un vigilante de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedVigilante != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este vigilante?");
            confirmacion.setContentText(selectedVigilante.getNombre());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (empleadoController.eliminarEmpleado(selectedVigilante.getDocumento())) {
                    listVigilantes.remove(selectedVigilante);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Vigilante eliminado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un vigilante de la tabla", Alert.AlertType.WARNING);
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

    private Vigilante buildVigilante() {
        return new Vigilante(
                txtDocumento.getText(),
                txtNombre.getText(),
                cmbTurno.getValue(),
                Double.parseDouble(txtSalarioBase.getText()),
                Double.parseDouble(txtHorasExtras.getText()),
                txtNumeroPuesto.getText(),
                cmbTipoArma.getValue()
        );
    }

    private boolean validarCampos() {
        if (txtDocumento.getText().isEmpty() || txtNombre.getText().isEmpty() ||
                cmbTurno.getValue() == null || txtSalarioBase.getText().isEmpty() ||
                txtHorasExtras.getText().isEmpty() || txtNumeroPuesto.getText().isEmpty() ||
                cmbTipoArma.getValue() == null) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        try {
            Double.parseDouble(txtSalarioBase.getText());
            Double.parseDouble(txtHorasExtras.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Salario y horas extras deben ser números válidos", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarSeleccion() {
        tblVigilantes.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtDocumento.clear();
        txtNombre.clear();
        cmbTurno.setValue(null);
        txtSalarioBase.clear();
        txtHorasExtras.clear();
        txtNumeroPuesto.clear();
        cmbTipoArma.setValue(null);
        selectedVigilante = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
