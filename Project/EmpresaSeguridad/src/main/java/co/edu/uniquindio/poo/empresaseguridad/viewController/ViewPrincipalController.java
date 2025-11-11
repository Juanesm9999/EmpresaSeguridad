package co.edu.uniquindio.poo.empresaseguridad.viewController;

import co.edu.uniquindio.poo.empresaseguridad.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ViewPrincipalController {
    private App app;

    @FXML private Button btnVigilantes;
    @FXML private Button btnSupervisores;
    @FXML private Button btnOperadores;
    @FXML private Button btnServicios;
    @FXML private Button btnEquipos;
    @FXML private Button btnReportes;

    @FXML
    void initialize() {
        // Configuraciones adicionales si son necesarias
    }

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    void onGestionarVigilantes() {
        app.openVigilanteView();
    }

    @FXML
    void onGestionarSupervisores() {
        app.openSupervisorView();
    }

    @FXML
    void onGestionarOperadores() {
        app.openOperadorView();
    }

    @FXML
    void onGestionarServicios() {
        app.openServicioView();
    }

    @FXML
    void onGestionarEquipos() {
        app.openEquipoView();
    }

    @FXML
    void onGenerarReportes() {
        app.openReportesView();
    }
}
