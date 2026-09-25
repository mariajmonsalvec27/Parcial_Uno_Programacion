module co.edu.uniquindio.poo.parcial_uno_programacion {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.poo.parcial_uno_programacion to javafx.fxml;
    exports co.edu.uniquindio.poo.parcial_uno_programacion;
}