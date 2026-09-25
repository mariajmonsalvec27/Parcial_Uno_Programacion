package co.edu.uniquindio.poo.parcial_uno_programacion.controller;

import co.edu.uniquindio.poo.parcial_uno_programacion.model.Academia;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.ConsultasAcademia;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Optional;

//controlador de las consultas: busqueda por telefono (numero perfecto) e ingresos por periodo
public class ConsultasController {

    @FXML private TextField txtTelefono;
    @FXML private Label lblResultadoTelefono;
    @FXML private DatePicker dpDesde;
    @FXML private DatePicker dpHasta;
    @FXML private Label lblIngresos;

    //se inyectan los repositorios de la academia en el servicio de consultas (DIP)
    private final ConsultasAcademia consultas = new ConsultasAcademia(
            Academia.getInstance().getEstudiantes(), Academia.getInstance().getMatriculas());

    @FXML
    private void onBuscarTelefono() {
        String telefono = txtTelefono.getText();
        if (telefono == null || telefono.isBlank()) {
            Alertas.error("Ingrese un número de teléfono");
            return;
        }
        Optional<Estudiante> estudiante = consultas.buscarPorTelefono(telefono);
        String perfecto = consultas.telefonoEsPerfecto(telefono) ? "SÍ es un número perfecto" : "NO es un número perfecto";
        lblResultadoTelefono.setText(estudiante
                .map(e -> "Estudiante: " + e.getNombreCompleto() + " (" + e.getDocumentoIdentidad() + ")")
                .orElse("No hay ningún estudiante con ese teléfono") + "\nEl teléfono " + perfecto);
    }

    @FXML
    private void onCalcularIngresos() {
        try {
            double total = consultas.calcularIngresos(dpDesde.getValue(), dpHasta.getValue());
            lblIngresos.setText(String.format("Ingresos del periodo: $%,.0f", total));
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }
}
