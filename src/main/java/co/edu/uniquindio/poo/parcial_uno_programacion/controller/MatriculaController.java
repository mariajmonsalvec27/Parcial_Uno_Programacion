package co.edu.uniquindio.poo.parcial_uno_programacion.controller;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IGeneradorComprobable;
import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Academia;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Docente;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Estudiante;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.GeneradorComprobanteExcel;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.GeneradorComprobantePDF;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.Matricula;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.ProgramaFormacion;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.ServicioAdicional;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

//controlador de matriculas: registra con el Builder y genera el comprobante en el formato elegido
public class MatriculaController {

    @FXML private ComboBox<Estudiante> cbEstudiante;
    @FXML private ComboBox<ProgramaFormacion> cbPrograma;
    @FXML private DatePicker dpFechaInicio;
    @FXML private ComboBox<Docente> cbDocente;
    @FXML private ComboBox<ServicioAdicional> cbServicio;
    @FXML private ListView<ServicioAdicional> lvServicios;
    @FXML private TextField txtDescuento;
    @FXML private TextArea txtObservaciones;
    @FXML private ComboBox<IGeneradorComprobable> cbFormato;
    @FXML private TableView<Matricula> tabla;
    @FXML private TableColumn<Matricula, String> colNumero;
    @FXML private TableColumn<Matricula, String> colEstudiante;
    @FXML private TableColumn<Matricula, String> colPrograma;
    @FXML private TableColumn<Matricula, String> colFecha;
    @FXML private TableColumn<Matricula, String> colTutor;
    @FXML private TableColumn<Matricula, String> colEntrega;
    @FXML private TableColumn<Matricula, String> colTotal;

    private final Academia academia = Academia.getInstance();
    private final IRepositorio<Matricula> repositorio = academia.getMatriculas();

    //formatos disponibles: para agregar un tercer formato solo se añade su clase a esta lista (OCP)
    private final List<IGeneradorComprobable> generadores =
            List.of(new GeneradorComprobantePDF(), new GeneradorComprobanteExcel());

    @FXML
    private void initialize() {
        //las listas se recargan al abrir cada combo, asi aparecen los datos nuevos de las otras pestañas
        cbEstudiante.setOnShowing(e -> cbEstudiante.setItems(FXCollections.observableArrayList(academia.getEstudiantes().listar())));
        cbPrograma.setOnShowing(e -> cbPrograma.setItems(FXCollections.observableArrayList(academia.getProgramas().listar())));
        cbDocente.setOnShowing(e -> cbDocente.setItems(FXCollections.observableArrayList(academia.getDocentes().listar())));
        cbServicio.setOnShowing(e -> cbServicio.setItems(FXCollections.observableArrayList(academia.getServicios().listar())));

        cbFormato.setItems(FXCollections.observableArrayList(generadores));
        cbFormato.setConverter(new StringConverter<>() {
            @Override
            public String toString(IGeneradorComprobable generador) {
                return generador == null ? "" : generador.getFormato();
            }

            @Override
            public IGeneradorComprobable fromString(String texto) {
                return null;
            }
        });
        cbFormato.setValue(generadores.get(0));

        colNumero.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getNumero())));
        colEstudiante.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstudiante().getNombreCompleto()));
        colPrograma.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrograma().getNombre()));
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaInicio().toString()));
        colTutor.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getDocenteTutor() == null ? "-" : c.getValue().getDocenteTutor().getNombre()));
        colEntrega.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getMaterial().getDescripcion() + " + " + c.getValue().getCarne().getDescripcion()));
        colTotal.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%,.0f", c.getValue().calcularTotal())));
        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, actual) -> mostrar(actual));
        refrescar();
    }

    @FXML
    private void onAgregarServicio() {
        ServicioAdicional servicio = cbServicio.getValue();
        if (servicio != null && !lvServicios.getItems().contains(servicio)) {
            lvServicios.getItems().add(servicio);
        }
    }

    @FXML
    private void onQuitarServicio() {
        lvServicios.getItems().remove(lvServicios.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onSinTutor() {
        cbDocente.setValue(null);
    }

    @FXML
    private void onRegistrar() {
        try {
            Matricula.Builder builder = new Matricula.Builder(cbEstudiante.getValue(), cbPrograma.getValue(), dpFechaInicio.getValue())
                    .docenteTutor(cbDocente.getValue())
                    .descuento(leerDescuento())
                    .observaciones(txtObservaciones.getText());
            for (ServicioAdicional servicio : lvServicios.getItems()) {
                builder.agregarServicio(servicio);
            }
            Matricula matricula = builder.build();
            repositorio.agregar(matricula);
            refrescar();
            onLimpiar();
            Alertas.info(String.format("Matrícula N° %d registrada. Total a pagar: $%,.0f",
                    matricula.getNumero(), matricula.calcularTotal()));
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    //en una matricula ya registrada solo se pueden cambiar el descuento y las observaciones
    @FXML
    private void onActualizar() {
        Matricula seleccionada = tabla.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alertas.error("Seleccione una matrícula de la tabla");
            return;
        }
        try {
            seleccionada.setPorcentajeDescuento(leerDescuento());
            seleccionada.setObservaciones(txtObservaciones.getText());
            repositorio.actualizar(seleccionada);
            refrescar();
        } catch (IllegalArgumentException e) {
            Alertas.error(e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        Matricula seleccionada = tabla.getSelectionModel().getSelectedItem();
        if (seleccionada != null && Alertas.confirmar("¿Eliminar la matrícula N° " + seleccionada.getNumero() + "?")) {
            repositorio.eliminar(String.valueOf(seleccionada.getNumero()));
            refrescar();
            onLimpiar();
        }
    }

    @FXML
    private void onLimpiar() {
        cbEstudiante.setValue(null);
        cbPrograma.setValue(null);
        dpFechaInicio.setValue(null);
        cbDocente.setValue(null);
        cbServicio.setValue(null);
        lvServicios.getItems().clear();
        txtDescuento.clear();
        txtObservaciones.clear();
        tabla.getSelectionModel().clearSelection();
    }

    @FXML
    private void onGenerarComprobante() {
        Matricula seleccionada = tabla.getSelectionModel().getSelectedItem();
        IGeneradorComprobable generador = cbFormato.getValue();
        if (seleccionada == null || generador == null) {
            Alertas.error("Seleccione una matrícula y un formato");
            return;
        }
        try {
            Path carpeta = Path.of("comprobantes");
            Files.createDirectories(carpeta);
            Path destino = carpeta.resolve("comprobante-" + seleccionada.getNumero() + "." + generador.getExtension());
            generador.generar(seleccionada, destino);
            Alertas.info("Comprobante generado en:\n" + destino.toAbsolutePath());
        } catch (IOException e) {
            Alertas.error("No se pudo generar el comprobante: " + e.getMessage());
        }
    }

    private double leerDescuento() {
        String texto = txtDescuento.getText().trim().replace(',', '.');
        return texto.isEmpty() ? 0 : Double.parseDouble(texto);
    }

    private void mostrar(Matricula matricula) {
        if (matricula == null) {
            return;
        }
        cbEstudiante.setValue(matricula.getEstudiante());
        cbPrograma.setValue(matricula.getPrograma());
        dpFechaInicio.setValue(matricula.getFechaInicio());
        cbDocente.setValue(matricula.getDocenteTutor());
        lvServicios.setItems(FXCollections.observableArrayList(matricula.getServicios()));
        txtDescuento.setText(String.valueOf(matricula.getPorcentajeDescuento()));
        txtObservaciones.setText(matricula.getObservaciones());
    }

    private void refrescar() {
        tabla.setItems(FXCollections.observableArrayList(repositorio.listar()));
    }
}
