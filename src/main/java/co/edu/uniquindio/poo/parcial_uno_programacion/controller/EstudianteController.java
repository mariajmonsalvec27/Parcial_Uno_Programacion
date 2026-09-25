package co.edu.uniquindio.poo.parcial_uno_programacion.controller;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Academia;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Estudiante;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;

//controlador del CRUD de estudiantes: lee el formulario, llama al modelo y refresca la tabla
public class EstudianteController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtEdad;
    @FXML private DatePicker dpFechaRegistro;
    @FXML private TableView<Estudiante> tabla;
    @FXML private TableColumn<Estudiante, String> colNombre;
    @FXML private TableColumn<Estudiante, String> colDocumento;
    @FXML private TableColumn<Estudiante, String> colTelefono;
    @FXML private TableColumn<Estudiante, String> colCorreo;
    @FXML private TableColumn<Estudiante, String> colEdad;
    @FXML private TableColumn<Estudiante, String> colFecha;

    private final IRepositorio<Estudiante> repositorio = Academia.getInstance().getEstudiantes();

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreCompleto()));
        colDocumento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDocumentoIdentidad()));
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        colEdad.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEdad())));
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getFechaRegistro())));
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> mostrar(actual));
        refrescar();
    }

    @FXML
    private void onAgregar() {
        try {
            repositorio.agregar(leerFormulario());
            refrescar();
            onLimpiar();
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onActualizar() {
        Estudiante seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alertas.error("Seleccione un estudiante de la tabla");
            return;
        }
        try {
            //primero se valida todo creando un estudiante temporal, luego se copian los datos
            Estudiante datos = leerFormulario();
            if (!datos.getDocumentoIdentidad().equals(seleccionado.getDocumentoIdentidad())) {
                throw new IllegalArgumentException("El documento no se puede modificar");
            }
            seleccionado.setNombreCompleto(datos.getNombreCompleto());
            seleccionado.setTelefono(datos.getTelefono());
            seleccionado.setCorreo(datos.getCorreo());
            seleccionado.setEdad(datos.getEdad());
            seleccionado.setFechaRegistro(datos.getFechaRegistro());
            repositorio.actualizar(seleccionado);
            refrescar();
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        Estudiante seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado != null && Alertas.confirmar("¿Eliminar a " + seleccionado.getNombreCompleto() + "?")) {
            repositorio.eliminar(seleccionado.getDocumentoIdentidad());
            refrescar();
            onLimpiar();
        }
    }

    @FXML
    private void onLimpiar() {
        txtNombre.clear();
        txtDocumento.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtEdad.clear();
        dpFechaRegistro.setValue(null);
        tabla.getSelectionModel().clearSelection();
    }

    private Estudiante leerFormulario() {
        LocalDate fecha = dpFechaRegistro.getValue() == null ? LocalDate.now() : dpFechaRegistro.getValue();
        return new Estudiante(txtNombre.getText(), txtDocumento.getText(), txtTelefono.getText(),
                txtCorreo.getText(), Integer.parseInt(txtEdad.getText().trim()), fecha);
    }

    private void mostrar(Estudiante estudiante) {
        if (estudiante == null) {
            return;
        }
        txtNombre.setText(estudiante.getNombreCompleto());
        txtDocumento.setText(estudiante.getDocumentoIdentidad());
        txtTelefono.setText(estudiante.getTelefono());
        txtCorreo.setText(estudiante.getCorreo());
        txtEdad.setText(String.valueOf(estudiante.getEdad()));
        dpFechaRegistro.setValue(estudiante.getFechaRegistro());
    }

    private void refrescar() {
        tabla.setItems(FXCollections.observableArrayList(repositorio.listar()));
    }
}
