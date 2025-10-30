module co.edu.uniquindio.poo.empresaseguridad {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.empresaseguridad to javafx.fxml;
    exports co.edu.uniquindio.poo.empresaseguridad;
}