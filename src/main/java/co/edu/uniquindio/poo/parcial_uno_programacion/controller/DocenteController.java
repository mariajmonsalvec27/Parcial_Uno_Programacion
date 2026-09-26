package co.edu.uniquindio.poo.parcial_uno_programacion.controller;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Academia;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Docente;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

//controlador del CRUD de docentes
public class DocenteController {

    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtIdioma;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtTarifa;
    @FXML
    private TableView<Docente> tabla;
    @FXML
    private TableColumn<Docente, String> colIdentificacion;
    @FXML
    private TableColumn<Docente, String> colNombre;
    @FXML
    private TableColumn<Docente, String> colIdioma;
    @FXML
    private TableColumn<Docente, String> colTelefono;
    @FXML
    private TableColumn<Docente, String> colTarifa;

    private final IRepositorio<Docente> repositorio = Academia.getInstance().getDocentes();
    private final ObservableList<Docente> listaDocentes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colIdentificacion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentificacion()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colIdioma.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdiomaEspecialidad()));
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colTarifa.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.0f", c.getValue().getTarifaSesion())));
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
        Docente seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alertas.error("Seleccione un docente de la tabla");
            return;
        }
        try {
            Docente datos = leerFormulario();
            if (!datos.getIdentificacion().equals(seleccionado.getIdentificacion())) {
                throw new IllegalArgumentException("La identificacion no se puede modificar");
            }
            seleccionado.setNombre(datos.getNombre());
            seleccionado.setIdiomaEspecialidad(datos.getIdiomaEspecialidad());
            seleccionado.setTelefono(datos.getTelefono());
            seleccionado.setTarifaSesion(datos.getTarifaSesion());
            repositorio.actualizar(seleccionado);
            refrescar();
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        Docente seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado != null && Alertas.confirmar("¿Eliminar a " + seleccionado.getNombre() + "?")) {
            repositorio.eliminar(seleccionado.getIdentificacion());
            refrescar();
            onLimpiar();
        }
    }

    @FXML
    private void onLimpiar() {
        txtIdentificacion.clear();
        txtNombre.clear();
        txtIdioma.clear();
        txtTelefono.clear();
        txtTarifa.clear();
        tabla.getSelectionModel().clearSelection();
    }

    private Docente leerFormulario() {
        return new Docente(txtIdentificacion.getText(), txtNombre.getText(), txtIdioma.getText(),
                txtTelefono.getText(), Double.parseDouble(txtTarifa.getText().trim()));
    }

    private void mostrar(Docente docente) {
        if (docente == null) {
            return;
        }
        txtIdentificacion.setText(docente.getIdentificacion());
        txtNombre.setText(docente.getNombre());
        txtIdioma.setText(docente.getIdiomaEspecialidad());
        txtTelefono.setText(docente.getTelefono());
        txtTarifa.setText(String.valueOf(docente.getTarifaSesion()));
    }

    private void refrescar() {
        listaDocentes.setAll(repositorio.listar());
        tabla.setItems(listaDocentes);
        tabla.refresh();
    }

}
