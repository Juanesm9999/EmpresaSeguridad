package co.edu.uniquindio.poo.empresaseguridad.viewController;

import co.edu.uniquindio.poo.empresaseguridad.App;
import co.edu.uniquindio.poo.empresaseguridad.controller.ServicioController;
import co.edu.uniquindio.poo.empresaseguridad.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ServicioViewController {
    private ServicioController servicioController;
    private ObservableList<ServicioSeguridad> listServicios = FXCollections.observableArrayList();
    private ServicioSeguridad selectedServicio;
    private App app;

    @FXML private TextField txtCodigoContrato;
    @FXML private TextField txtCliente;
    @FXML private TextField txtTarifaMensual;
    @FXML private ComboBox<EstadoServicio> cmbEstado;
    @FXML private ComboBox<String> cmbTipoServicio;

    // Campos específicos
    @FXML private TextField txtNumeroVigilantes;
    @FXML private TextField txtNumeroRutas;
    @FXML private TextField txtKilometros;
    @FXML private TextField txtNumeroDispositivos;

    @FXML private TableView<ServicioSeguridad> tblServicios;
    @FXML private TableColumn<ServicioSeguridad, String> tbcCodigo;
    @FXML private TableColumn<ServicioSeguridad, String> tbcCliente;
    @FXML private TableColumn<ServicioSeguridad, String> tbcTarifa;
    @FXML private TableColumn<ServicioSeguridad, String> tbcEstado;
    @FXML private TableColumn<ServicioSeguridad, String> tbcTipo;
    @FXML private TableColumn<ServicioSeguridad, String> tbcCostoTotal;

    @FXML private Button btnAgregar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnRegresar;
    @FXML private Button btnAsignarPersonal;
    @FXML private Button btnAsignarEquipo;

    @FXML
    void initialize() {
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoServicio.values()));
        cmbTipoServicio.setItems(FXCollections.observableArrayList(
                "Custodia Fija", "Patrullaje Móvil", "Monitoreo Remoto"
        ));

        cmbTipoServicio.valueProperty().addListener((obs, oldVal, newVal) -> {
            actualizarCamposSegunTipo(newVal);
        });
    }

    public void setApp(App app) {
        this.app = app;
        servicioController = new ServicioController(app.getSistemaSeguridad());
        initView();
    }

    private void initView() {
        initDataBinding();
        obtenerServicios();
        tblServicios.setItems(listServicios);
        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCodigoContrato()));
        tbcCliente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCliente()));
        tbcTarifa.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().getTarifaMensual())));
        tbcEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado().toString()));
        tbcTipo.setCellValueFactory(cellData -> {
            String tipo = "";
            if (cellData.getValue() instanceof CustodiaFija) tipo = "Custodia Fija";
            else if (cellData.getValue() instanceof PatrullajeMovil) tipo = "Patrullaje Móvil";
            else if (cellData.getValue() instanceof MonitoreoRemoto) tipo = "Monitoreo Remoto";
            return new SimpleStringProperty(tipo);
        });
        tbcCostoTotal.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.format("%.2f", cellData.getValue().calcularCostoMensual())));
    }

    private void obtenerServicios() {
        listServicios.clear();
        listServicios.addAll(servicioController.obtenerListaServicios());
    }

    private void listenerSelection() {
        tblServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedServicio = newSelection;
            mostrarInformacion(selectedServicio);
        });
    }

    private void mostrarInformacion(ServicioSeguridad servicio) {
        if (servicio != null) {
            txtCodigoContrato.setText(servicio.getCodigoContrato());
            txtCliente.setText(servicio.getCliente());
            txtTarifaMensual.setText(String.valueOf(servicio.getTarifaMensual()));
            cmbEstado.setValue(servicio.getEstado());

            if (servicio instanceof CustodiaFija) {
                cmbTipoServicio.setValue("Custodia Fija");
                CustodiaFija custodia = (CustodiaFija) servicio;
                txtNumeroVigilantes.setText(String.valueOf(custodia.getVigilantesAsignados().size()));
            } else if (servicio instanceof PatrullajeMovil) {
                cmbTipoServicio.setValue("Patrullaje Móvil");
                PatrullajeMovil patrullaje = (PatrullajeMovil) servicio;
                txtNumeroRutas.setText(String.valueOf(patrullaje.getNumeroRutas()));
                txtKilometros.setText(String.valueOf(patrullaje.getKilometrosRecorridos()));
            } else if (servicio instanceof MonitoreoRemoto) {
                cmbTipoServicio.setValue("Monitoreo Remoto");
                MonitoreoRemoto monitoreo = (MonitoreoRemoto) servicio;
                txtNumeroDispositivos.setText(String.valueOf(monitoreo.getNumeroDispositivos()));
            }
        }
    }

    private void actualizarCamposSegunTipo(String tipo) {
        txtNumeroVigilantes.setDisable(true);
        txtNumeroRutas.setDisable(true);
        txtKilometros.setDisable(true);
        txtNumeroDispositivos.setDisable(true);

        if ("Custodia Fija".equals(tipo)) {
            txtNumeroVigilantes.setDisable(false);
        } else if ("Patrullaje Móvil".equals(tipo)) {
            txtNumeroRutas.setDisable(false);
            txtKilometros.setDisable(false);
        } else if ("Monitoreo Remoto".equals(tipo)) {
            txtNumeroDispositivos.setDisable(false);
        }
    }

    @FXML
    void onAgregar() {
        if (validarCampos()) {
            ServicioSeguridad servicio = buildServicio();
            if (servicio != null) {
                boolean exito = false;
                if (servicio instanceof CustodiaFija) {
                    exito = servicioController.crearCustodiaFija((CustodiaFija) servicio);
                } else if (servicio instanceof PatrullajeMovil) {
                    exito = servicioController.crearPatrullajeMovil((PatrullajeMovil) servicio);
                } else if (servicio instanceof MonitoreoRemoto) {
                    exito = servicioController.crearMonitoreoRemoto((MonitoreoRemoto) servicio);
                }

                if (exito) {
                    listServicios.add(servicio);
                    limpiarCampos();
                    mostrarAlerta("Éxito", "Servicio creado correctamente", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "No se pudo crear el servicio", Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    void onActualizar() {
        if (selectedServicio != null && validarCampos()) {
            ServicioSeguridad servicioNuevo = buildServicio();
            if (servicioNuevo != null && servicioController.actualizarServicio(
                    selectedServicio.getCodigoContrato(), servicioNuevo)) {
                int index = listServicios.indexOf(selectedServicio);
                if (index >= 0) {
                    listServicios.set(index, servicioNuevo);
                }
                tblServicios.refresh();
                limpiarSeleccion();
                mostrarAlerta("Éxito", "Servicio actualizado correctamente", Alert.AlertType.INFORMATION);
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un servicio de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onEliminar() {
        if (selectedServicio != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este servicio?");
            confirmacion.setContentText(selectedServicio.getCliente());

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (servicioController.eliminarServicio(selectedServicio.getCodigoContrato())) {
                    listServicios.remove(selectedServicio);
                    limpiarSeleccion();
                    mostrarAlerta("Éxito", "Servicio eliminado correctamente", Alert.AlertType.INFORMATION);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Seleccione un servicio de la tabla", Alert.AlertType.WARNING);
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
    void onAsignarPersonal() {
        if (selectedServicio != null) {
            app.openAsignarPersonalView(selectedServicio);
        } else {
            mostrarAlerta("Advertencia", "Seleccione un servicio", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void onAsignarEquipo() {
        if (selectedServicio != null) {
            app.openAsignarEquipoView(selectedServicio);
        } else {
            mostrarAlerta("Advertencia", "Seleccione un servicio", Alert.AlertType.WARNING);
        }
    }

    private ServicioSeguridad buildServicio() {
        String tipo = cmbTipoServicio.getValue();
        String codigo = txtCodigoContrato.getText();
        String cliente = txtCliente.getText();
        double tarifa = Double.parseDouble(txtTarifaMensual.getText());
        EstadoServicio estado = cmbEstado.getValue();

        if ("Custodia Fija".equals(tipo)) {
            return new CustodiaFija(codigo, cliente, tarifa, estado);
        } else if ("Patrullaje Móvil".equals(tipo)) {
            int rutas = Integer.parseInt(txtNumeroRutas.getText());
            double km = Double.parseDouble(txtKilometros.getText());
            return new PatrullajeMovil(codigo, cliente, tarifa, estado, rutas, km);
        } else if ("Monitoreo Remoto".equals(tipo)) {
            int dispositivos = Integer.parseInt(txtNumeroDispositivos.getText());
            return new MonitoreoRemoto(codigo, cliente, tarifa, estado, dispositivos);
        }
        return null;
    }

    private boolean validarCampos() {
        if (txtCodigoContrato.getText().isEmpty() || txtCliente.getText().isEmpty() ||
                txtTarifaMensual.getText().isEmpty() || cmbEstado.getValue() == null ||
                cmbTipoServicio.getValue() == null) {
            mostrarAlerta("Error", "Los campos básicos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }

        try {
            Double.parseDouble(txtTarifaMensual.getText());

            String tipo = cmbTipoServicio.getValue();
            if ("Custodia Fija".equals(tipo)) {
                // No se requiere validación adicional
            } else if ("Patrullaje Móvil".equals(tipo)) {
                if (txtNumeroRutas.getText().isEmpty() || txtKilometros.getText().isEmpty()) {
                    mostrarAlerta("Error", "Complete los campos de patrullaje", Alert.AlertType.ERROR);
                    return false;
                }
                Integer.parseInt(txtNumeroRutas.getText());
                Double.parseDouble(txtKilometros.getText());
            } else if ("Monitoreo Remoto".equals(tipo)) {
                if (txtNumeroDispositivos.getText().isEmpty()) {
                    mostrarAlerta("Error", "Complete el campo de dispositivos", Alert.AlertType.ERROR);
                    return false;
                }
                Integer.parseInt(txtNumeroDispositivos.getText());
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Los valores numéricos deben ser válidos", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void limpiarSeleccion() {
        tblServicios.getSelectionModel().clearSelection();
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtCodigoContrato.clear();
        txtCliente.clear();
        txtTarifaMensual.clear();
        cmbEstado.setValue(null);
        cmbTipoServicio.setValue(null);
        txtNumeroVigilantes.clear();
        txtNumeroRutas.clear();
        txtKilometros.clear();
        txtNumeroDispositivos.clear();
        selectedServicio = null;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
