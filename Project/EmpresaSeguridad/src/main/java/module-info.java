module co.edu.uniquindio.poo.empresaseguridad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens co.edu.uniquindio.poo.empresaseguridad to javafx.fxml;
    exports co.edu.uniquindio.poo.empresaseguridad;


    exports co.edu.uniquindio.poo.empresaseguridad.viewController;
    opens co.edu.uniquindio.poo.empresaseguridad.viewController to javafx.fxml;


    exports co.edu.uniquindio.poo.empresaseguridad.controller;
    opens co.edu.uniquindio.poo.empresaseguridad.controller to javafx.fxml;
}
