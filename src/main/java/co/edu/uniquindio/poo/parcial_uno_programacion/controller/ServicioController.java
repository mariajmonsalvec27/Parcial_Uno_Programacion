package co.edu.uniquindio.poo.parcial_uno_programacion.controller;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Academia;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.ServicioAdicional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

//controlador del CRUD de servicios adicionales
public class ServicioController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private CheckBox chkDisponible;
    @FXML private TableView<ServicioAdicional> tabla;
    @FXML private TableColumn<ServicioAdicional, String> colCodigo;
    @FXML private TableColumn<ServicioAdicional, String> colNombre;
    @FXML private TableColumn<ServicioAdicional, String> colDescripcion;
    @FXML private TableColumn<ServicioAdicional, String> colPrecio;
    @FXML private TableColumn<ServicioAdicional, String> colDisponible;

    private final IRepositorio<ServicioAdicional> repositorio = Academia.getInstance().getServicios();
    private final ObservableList<ServicioAdicional> listaServicios = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colDescripcion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescripcion()));
        colPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.0f", c.getValue().getPrecio())));
        colDisponible.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().isDisponible() ? "Sí" : "No"));
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
        ServicioAdicional seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alertas.error("Seleccione un servicio de la tabla");
            return;
        }
        try {
            ServicioAdicional datos = leerFormulario();
            if (!datos.getCodigo().equals(seleccionado.getCodigo())) {
                throw new IllegalArgumentException("El codigo no se puede modificar");
            }
            seleccionado.setNombre(datos.getNombre());
            seleccionado.setDescripcion(datos.getDescripcion());
            seleccionado.setPrecio(datos.getPrecio());
            seleccionado.setDisponible(datos.isDisponible());
            repositorio.actualizar(seleccionado);
            refrescar();
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        ServicioAdicional seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado != null && Alertas.confirmar("¿Eliminar el servicio " + seleccionado.getNombre() + "?")) {
            repositorio.eliminar(seleccionado.getCodigo());
            refrescar();
            onLimpiar();
        }
    }

    @FXML
    private void onLimpiar() {
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        chkDisponible.setSelected(true);
        tabla.getSelectionModel().clearSelection();
    }

    private ServicioAdicional leerFormulario() {
        return new ServicioAdicional(txtCodigo.getText(), txtNombre.getText(), txtDescripcion.getText(),
                Double.parseDouble(txtPrecio.getText().trim()), chkDisponible.isSelected());
    }

    private void mostrar(ServicioAdicional servicio) {
        if (servicio == null) {
            return;
        }
        txtCodigo.setText(servicio.getCodigo());
        txtNombre.setText(servicio.getNombre());
        txtDescripcion.setText(servicio.getDescripcion());
        txtPrecio.setText(String.valueOf(servicio.getPrecio()));
        chkDisponible.setSelected(servicio.isDisponible());
    }

    private void refrescar() {
        listaServicios.setAll(repositorio.listar());
        tabla.setItems(listaServicios);
        tabla.refresh();
    }
}
