module co.edu.uniquindio.poo.parcial_uno_programacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.github.librepdf.openpdf;

    // la vista (JavaFX) necesita acceso por reflexion a la clase de la aplicacion
    opens co.edu.uniquindio.poo.parcial_uno_programacion.view to javafx.graphics, javafx.fxml;

    exports co.edu.uniquindio.poo.parcial_uno_programacion;
    exports co.edu.uniquindio.poo.parcial_uno_programacion.model;
    exports co.edu.uniquindio.poo.parcial_uno_programacion.interfaces;
    exports co.edu.uniquindio.poo.parcial_uno_programacion.view;
}
