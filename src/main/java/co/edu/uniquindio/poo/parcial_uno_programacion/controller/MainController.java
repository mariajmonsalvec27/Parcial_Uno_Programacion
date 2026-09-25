package co.edu.uniquindio.poo.parcial_uno_programacion.controller;

import co.edu.uniquindio.poo.parcial_uno_programacion.model.Academia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

//controlador de la ventana principal: muestra los datos basicos de la academia
public class MainController {

    @FXML
    private Label lblAcademia;

    @FXML
    private void initialize() {
        Academia academia = Academia.getInstance();
        lblAcademia.setText(academia.getNombre() + "  |  NIT " + academia.getNit() + "  |  "
                + academia.getDireccion() + "  |  " + academia.getTelefono() + "  |  "
                + academia.getCorreo() + "  |  " + academia.getPaginaWeb());
    }
}
