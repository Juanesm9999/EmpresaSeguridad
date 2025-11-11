package co.edu.uniquindio.poo.empresaseguridad.viewController;

import co.edu.uniquindio.poo.empresaseguridad.App;
import co.edu.uniquindio.poo.empresaseguridad.controller.EquipoController;
import co.edu.uniquindio.poo.empresaseguridad.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EquipoViewController {
    private EquipoController equipoController;
    private ObservableList<Equipo> listEquipos = FXCollections.observableArrayList();
    private Equipo selectedEquipo;
    private App app;

    @FXML private TextField txtCodigo;
    @FXML private ComboBox<TipoEquipo> cmbTipo;
    @FXML private ComboBox<EstadoEquipo> cmbEstado;
    @FXML private TextField txtValorReposicion;

    @FXML private TableView<Equipo> tblEquipos;
    @FXML private TableColumn<Equipo, String> tbcCodigo;
    @FXML private TableColumn<Equipo, String> tbcTipo;
    @FXML private TableColumn<Equipo, String> tbcEstado;
    @FXML private TableColumn<Equipo, String> tbcValor;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;
    @FXML private Button btnAsignarEmpleado;

    @FXML
    void initialize() {
        cmbTipo.setItems(FXCollections.observableArrayList(TipoEquipo.values()));
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoEquipo.values()));
    }

    public void setApp(App app) {
        this.app = app;
        equipoController = new EquipoController(app.getSistemaSeguridad());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerEquipos();
        tblEquipos.setItems(listEquipos);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCodigo()));
        tbcTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipo().toString()));
        tbcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado().toString()));
        tbcValor.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getValorReposicion())));
    }

    private void obtenerEquipos() {
        listEquipos.clear();
        listEquipos.addAll(equipoController.obtenerListaEquipos());
    }

    private void listenerSelection() {
        tblEquipos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedEquipo = newSelection;
            mostrarInformacion(selectedEquipo);
        });
    }

    private void mostrarInformacion(Equipo equipo) {
        if (equipo != null) {
            txtCodigo.setText(equipo.getCodigo());
            cmbTipo.setValue(equipo.getTipo());
            cmbEstado.setValue(equipo.getEstado());
            txtValorReposicion.setText(String.valueOf(equipo.getValorReposicion()));
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            Equipo equipo = buildEquipo();
            if (equipoController.crearEquipo(equipo)) {
                listEquipos.add(equipo);
                limpiarCampos();
                mostrarAlerta("Éxito", "Equipo creado correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo crear el equipo", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedEquipo != null && validarCampos()) {
            Equipo equipoNuevo = buildEquipo();
            if (equipoController.actualizarEquipo(selectedEquipo.getCodigo(), equipoNuevo)) {
                int index = listEquipos.indexOf(selectedEquipo);
                if (index >= 0) {
                    listEquipos.set(index, equipoNuevo);
                }
                tblEquipos.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Equipo actualizado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un equipo de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedEquipo != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este equipo?");
            confirmacion.setContentText(selectedEquipo.getCodigo());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (equipoController.eliminarEquipo(selectedEquipo.getCodigo())) {
                    listEquipos.remove(selectedEquipo);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Equipo eliminado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un equipo de la tabla", Alert.AlertType.WARNING);
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

    @FXML
    void onAsignarEmpleado() {
        if (selectedEquipo != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Asignar Equipo");
            dialog.setHeaderText("Asignar equipo a empleado");
            dialog.setContentText("Ingrese el documento del empleado:");

            dialog.showAndWait().ifPresent(documento -> {
                if (equipoController.asignarEquipoAEmpleado(selectedEquipo.getCodigo(), documento)) {
                    obtenerEquipos();
                    tblEquipos.refresh();
                    mostrarAlerta("Éxito", "Equipo asignado correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "No se pudo asignar el equipo", Alert.AlertType.ERROR);
                }
            });
        } else {
            mostrarAlerta("Advertencia", "Seleccione un equipo", Alert.AlertType.WARNING);
        }
    }

    private Equipo buildEquipo() {
        return new Equipo(
                txtCodigo.getText(),
                cmbTipo.getValue(),
                cmbEstado.getValue(),
                Double.parseDouble(txtValorReposicion.getText())
        );
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().isEmpty() || cmbTipo.getValue() == null ||
                cmbEstado.getValue() == null || txtValorReposicion.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        try {
            Double.parseDouble(txtValorReposicion.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El valor de reposición debe ser numérico", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarSeleccion() {
        tblEquipos.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        cmbTipo.setValue(null);
        cmbEstado.setValue(null);
        txtValorReposicion.clear();
        selectedEquipo = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
