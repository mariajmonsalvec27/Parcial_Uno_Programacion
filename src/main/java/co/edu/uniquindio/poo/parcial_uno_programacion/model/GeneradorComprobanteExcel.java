package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IGeneradorComprobable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

//genera el comprobante como archivo CSV separado por ";" que Excel abre directamente
public class GeneradorComprobanteExcel implements IGeneradorComprobable {

    @Override
    public String getFormato() {
        return "Excel";
    }

    @Override
    public String getExtension() {
        return "csv";
    }

    @Override
    public void generar(Matricula matricula, Path destino) throws IOException {
        Academia academia = Academia.getInstance();
        //el caracter ﻿ al inicio le indica a Excel que el archivo esta en UTF-8 (tildes y ñ)
        StringBuilder csv = new StringBuilder("﻿");
        csv.append("Comprobante de pago;").append(academia.getNombre()).append('\n');
        csv.append("NIT;").append(academia.getNit()).append('\n');
        csv.append("Concepto;Valor\n");
        for (String[] fila : filasComprobante(matricula)) {
            csv.append(fila[0]).append(';').append(fila[1]).append('\n');
        }
        Files.writeString(destino, csv.toString(), StandardCharsets.UTF_8);
    }
}
