package co.edu.uniquindio.poo.empresaseguridad;

import co.edu.uniquindio.poo.empresaseguridad.model.*;
import co.edu.uniquindio.poo.empresaseguridad.viewController.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Stage primaryStage;
    private SistemaSeguridad sistemaSeguridad;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.sistemaSeguridad = new SistemaSeguridad();
        inicializarDatosPrueba();
        openViewPrincipal();
    }

    public void openViewPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewPrincipal.fxml"));
            Parent root = loader.load();

            ViewPrincipalController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sistema de Seguridad SEGURCOL S.A.");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openVigilanteView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VigilanteView.fxml"));
            Parent root = loader.load();

            VigilanteViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Vigilantes");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSupervisorView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SupervisorView.fxml"));
            Parent root = loader.load();

            SupervisorViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Supervisores");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openOperadorView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OperadorMonitoreoView.fxml"));
            Parent root = loader.load();

            OperadorMonitoreoViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Operadores de Monitoreo");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openServicioView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ServicioView.fxml"));
            Parent root = loader.load();

            ServicioViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Servicios");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openEquipoView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EquipoView.fxml"));
            Parent root = loader.load();

            EquipoViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión de Equipos");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openReportesView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportesView.fxml"));
            Parent root = loader.load();

            ReportesViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Reportes e Informes");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAsignarPersonalView(ServicioSeguridad servicio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AsignarPersonalView.fxml"));
            Parent root = loader.load();

            AsignarPersonalViewController controller = loader.getController();
            controller.setApp(this);
            controller.setServicio(servicio);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Asignar Personal al Servicio");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openAsignarEquipoView(ServicioSeguridad servicio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AsignarEquipoView.fxml"));
            Parent root = loader.load();

            AsignarEquipoViewController controller = loader.getController();
            controller.setApp(this);
            controller.setServicio(servicio);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Asignar Equipos al Servicio");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inicializarDatosPrueba() {
        // Crear vigilantes de prueba
        Vigilante v1 = new Vigilante("1001", "Juan Pérez", Turno.DIA, 2000000, 0, "P-001", TipoArma.NO_LETAL);
        Vigilante v2 = new Vigilante("1002", "María López", Turno.NOCHE, 2200000, 20, "P-002", TipoArma.LETAL);
        sistemaSeguridad.agregarEmpleado(v1);
        sistemaSeguridad.agregarEmpleado(v2);

        // Crear supervisor de prueba
        Supervisor s1 = new Supervisor("2001", "Carlos Ramírez", Turno.DIA, 3000000, 0, 500000);
        s1.agregarVigilante(v1);
        sistemaSeguridad.agregarEmpleado(s1);

        // Crear operador de prueba
        OperadorMonitoreo o1 = new OperadorMonitoreo("3001", "Ana Martínez", Turno.NOCHE, 2500000, 10, 15);
        sistemaSeguridad.agregarEmpleado(o1);

        // Crear servicios de prueba
        CustodiaFija cf1 = new CustodiaFija("C-001", "Edificio Central", 5000000, EstadoServicio.ACTIVO);
        PatrullajeMovil pm1 = new PatrullajeMovil("P-001", "Conjunto Residencial Norte", 3500000, EstadoServicio.ACTIVO, 3, 120.5);
        MonitoreoRemoto mr1 = new MonitoreoRemoto("M-001", "Bodega Industrial", 4000000, EstadoServicio.ACTIVO, 25);

        sistemaSeguridad.agregarServicio(cf1);
        sistemaSeguridad.agregarServicio(pm1);
        sistemaSeguridad.agregarServicio(mr1);

        // Crear equipos de prueba
        Equipo eq1 = new Equipo("EQ-001", TipoEquipo.RADIO, EstadoEquipo.DISPONIBLE, 500000);
        Equipo eq2 = new Equipo("EQ-002", TipoEquipo.ARMA, EstadoEquipo.DISPONIBLE, 3000000);
        Equipo eq3 = new Equipo("EQ-003", TipoEquipo.UNIFORME, EstadoEquipo.DISPONIBLE, 200000);
        Equipo eq4 = new Equipo("EQ-004", TipoEquipo.VEHICULO, EstadoEquipo.DISPONIBLE, 50000000);

        sistemaSeguridad.agregarEquipo(eq1);
        sistemaSeguridad.agregarEquipo(eq2);
        sistemaSeguridad.agregarEquipo(eq3);
        sistemaSeguridad.agregarEquipo(eq4);
    }

    public SistemaSeguridad getSistemaSeguridad() {
        return sistemaSeguridad;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
