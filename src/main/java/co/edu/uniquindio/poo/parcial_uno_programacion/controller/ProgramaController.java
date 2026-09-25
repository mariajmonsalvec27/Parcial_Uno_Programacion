package co.edu.uniquindio.poo.parcial_uno_programacion.controller;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Academia;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.EstadoPrograma;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.FabricaProgramaBasico;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.FabricaProgramaIntensivo;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.FabricaProgramaPersonalizado;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Modalidad;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.ProgramaFactory;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.ProgramaFormacion;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.ProgramaIntensivo;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.ProgramaPersonalizado;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

//controlador del CRUD de programas. Para crear usa las fabricas (Factory Method)
public class ProgramaController {

    private static final String BASICO = "Básico";
    private static final String INTENSIVO = "Intensivo";
    private static final String PERSONALIZADO = "Personalizado";

    @FXML private ComboBox<String> cbTipo;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtIdioma;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtValorMensual;
    @FXML private ComboBox<Modalidad> cbModalidad;
    @FXML private ComboBox<EstadoPrograma> cbEstado;
    @FXML private TextField txtSesiones;
    @FXML private TextField txtNivel;
    @FXML private TextField txtObjetivos;
    @FXML private TableView<ProgramaFormacion> tabla;
    @FXML private TableColumn<ProgramaFormacion, String> colCodigo;
    @FXML private TableColumn<ProgramaFormacion, String> colNombre;
    @FXML private TableColumn<ProgramaFormacion, String> colTipo;
    @FXML private TableColumn<ProgramaFormacion, String> colIdioma;
    @FXML private TableColumn<ProgramaFormacion, String> colModalidad;
    @FXML private TableColumn<ProgramaFormacion, String> colEstado;
    @FXML private TableColumn<ProgramaFormacion, String> colValor;

    private final IRepositorio<ProgramaFormacion> repositorio = Academia.getInstance().getProgramas();
    private final ObservableList<ProgramaFormacion> listaProgramas = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        cbTipo.setItems(FXCollections.observableArrayList(BASICO, INTENSIVO, PERSONALIZADO));
        cbModalidad.setItems(FXCollections.observableArrayList(Modalidad.values()));
        cbEstado.setItems(FXCollections.observableArrayList(EstadoPrograma.values()));
        //los campos del programa personalizado solo se habilitan si se elige ese tipo
        cbTipo.valueProperty().addListener((obs, anterior, actual) -> {
            boolean personalizado = PERSONALIZADO.equals(actual);
            txtSesiones.setDisable(!personalizado);
            txtNivel.setDisable(!personalizado);
            txtObjetivos.setDisable(!personalizado);
        });
        colCodigo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCodigo()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(tipoDe(c.getValue())));
        colIdioma.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdioma()));
        colModalidad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getModalidad().toString()));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().toString()));
        colValor.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.0f", c.getValue().calcularValorFinal())));
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> mostrar(actual));
        onLimpiar();
        refrescar();
    }

    @FXML
    private void onAgregar() {
        try {
            if (cbTipo.getValue() == null || cbModalidad.getValue() == null) {
                throw new IllegalArgumentException("Seleccione el tipo y la modalidad del programa");
            }
            ProgramaFormacion programa = fabricaSegunTipo().crear(txtCodigo.getText(), txtNombre.getText(),
                    txtIdioma.getText(), txtDescripcion.getText(), Integer.parseInt(txtDuracion.getText().trim()),
                    Double.parseDouble(txtValorMensual.getText().trim()), cbModalidad.getValue());
            programa.setEstado(cbEstado.getValue());
            repositorio.agregar(programa);
            refrescar();
            onLimpiar();
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onActualizar() {
        ProgramaFormacion seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            Alertas.error("Seleccione un programa de la tabla");
            return;
        }
        try {
            if (!txtCodigo.getText().equals(seleccionado.getCodigo()) || !tipoDe(seleccionado).equals(cbTipo.getValue())) {
                throw new IllegalArgumentException("El codigo y el tipo del programa no se pueden modificar");
            }
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            double valor = Double.parseDouble(txtValorMensual.getText().trim());
            seleccionado.setNombre(txtNombre.getText());
            seleccionado.setIdioma(txtIdioma.getText());
            seleccionado.setDescripcion(txtDescripcion.getText());
            seleccionado.setDuracionMeses(duracion);
            seleccionado.setValorMensual(valor);
            seleccionado.setModalidad(cbModalidad.getValue());
            seleccionado.setEstado(cbEstado.getValue());
            if (seleccionado instanceof ProgramaPersonalizado personalizado) {
                personalizado.setCantidadSesionesTutor(Integer.parseInt(txtSesiones.getText().trim()));
                personalizado.setNivelIdiomaRequerido(txtNivel.getText());
                personalizado.setObjetivosEstudiante(txtObjetivos.getText());
            }
            repositorio.actualizar(seleccionado);
            refrescar();
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        ProgramaFormacion seleccionado = tabla.getSelectionModel().getSelectedItem();
        if (seleccionado != null && Alertas.confirmar("¿Eliminar el programa " + seleccionado.getNombre() + "?")) {
            repositorio.eliminar(seleccionado.getCodigo());
            refrescar();
            onLimpiar();
        }
    }

    @FXML
    private void onLimpiar() {
        cbTipo.setValue(BASICO);
        txtCodigo.clear();
        txtNombre.clear();
        txtIdioma.clear();
        txtDescripcion.clear();
        txtDuracion.clear();
        txtValorMensual.clear();
        cbModalidad.setValue(Modalidad.PRESENCIAL);
        cbEstado.setValue(EstadoPrograma.ACTIVO);
        txtSesiones.clear();
        txtNivel.clear();
        txtObjetivos.clear();
        tabla.getSelectionModel().clearSelection();
    }

    //elige la fabrica concreta segun el tipo que selecciono el usuario
    private ProgramaFactory fabricaSegunTipo() {
        return switch (cbTipo.getValue()) {
            case INTENSIVO -> new FabricaProgramaIntensivo();
            case PERSONALIZADO -> new FabricaProgramaPersonalizado(
                    Integer.parseInt(txtSesiones.getText().trim()), txtNivel.getText(), txtObjetivos.getText());
            default -> new FabricaProgramaBasico();
        };
    }

    private String tipoDe(ProgramaFormacion programa) {
        if (programa instanceof ProgramaPersonalizado) {
            return PERSONALIZADO;
        }
        if (programa instanceof ProgramaIntensivo) {
            return INTENSIVO;
        }
        return BASICO;
    }

    private void mostrar(ProgramaFormacion programa) {
        if (programa == null) {
            return;
        }
        cbTipo.setValue(tipoDe(programa));
        txtCodigo.setText(programa.getCodigo());
        txtNombre.setText(programa.getNombre());
        txtIdioma.setText(programa.getIdioma());
        txtDescripcion.setText(programa.getDescripcion());
        txtDuracion.setText(String.valueOf(programa.getDuracionMeses()));
        txtValorMensual.setText(String.valueOf(programa.getValorMensual()));
        cbModalidad.setValue(programa.getModalidad());
        cbEstado.setValue(programa.getEstado());
        if (programa instanceof ProgramaPersonalizado personalizado) {
            txtSesiones.setText(String.valueOf(personalizado.getCantidadSesionesTutor()));
            txtNivel.setText(personalizado.getNivelIdiomaRequerido());
            txtObjetivos.setText(personalizado.getObjetivosEstudiante());
        } else {
            txtSesiones.clear();
            txtNivel.clear();
            txtObjetivos.clear();
        }
    }

    private void refrescar() {
        listaProgramas.setAll(repositorio.listar());
        tabla.setItems(listaProgramas);
        tabla.refresh();
    }
}
