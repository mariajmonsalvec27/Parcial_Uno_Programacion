package co.edu.uniquindio.poo.parcial_uno_programacion.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

//ventanas de mensaje reutilizadas por todos los controladores
public final class Alertas {

    private Alertas() {
    }

    public static void error(String mensaje) {
        mostrar(Alert.AlertType.ERROR, "Error", mensaje);
    }

    public static void info(String mensaje) {
        mostrar(Alert.AlertType.INFORMATION, "Información", mensaje);
    }

    public static boolean confirmar(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, mensaje);
        alerta.setHeaderText(null);
        return alerta.showAndWait().filter(boton -> boton == ButtonType.OK).isPresent();
    }

    private static void mostrar(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo, mensaje);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
