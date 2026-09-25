package co.edu.uniquindio.poo.parcial_uno_programacion.view;

import co.edu.uniquindio.poo.parcial_uno_programacion.model.Academia;
import co.edu.uniquindio.poo.parcial_uno_programacion.model.DatosIniciales;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// ventana principal: solo carga la vista FXML, no tiene logica de negocio (MVC)
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        DatosIniciales.cargar(Academia.getInstance());
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(loader.load(), 1100, 650);
        stage.setTitle("LinguaPlus - Gestion academica");
        stage.setScene(scene);
        stage.show();
    }
}
