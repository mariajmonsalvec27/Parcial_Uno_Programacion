package co.edu.uniquindio.poo.parcial_uno_programacion.interfaces;

import co.edu.uniquindio.poo.parcial_uno_programacion.model.Matricula;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

//principio abierto/cerrado (OCP): para un tercer formato de comprobante se crea una clase nueva
//que implemente esta interfaz, sin modificar las que ya existen
public interface IGeneradorComprobable {

    String getFormato();

    String getExtension();

    void generar(Matricula matricula, Path destino) throws IOException;

    //contenido comun a todos los formatos: pares (concepto, valor)
    default List<String[]> filasComprobante(Matricula m) {
        List<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"Numero de matricula", String.valueOf(m.getNumero())});
        filas.add(new String[]{"Estudiante", m.getEstudiante().getNombreCompleto()});
        filas.add(new String[]{"Documento", m.getEstudiante().getDocumentoIdentidad()});
        filas.add(new String[]{"Programa", m.getPrograma().getNombre() + " (" + m.getPrograma().getModalidad() + ")"});
        filas.add(new String[]{"Fecha de inicio", m.getFechaInicio().toString()});
        filas.add(new String[]{"Entrega", m.getMaterial().getDescripcion() + " + " + m.getCarne().getDescripcion()});
        if (m.getDocenteTutor() != null) {
            filas.add(new String[]{"Docente tutor", m.getDocenteTutor().getNombre()});
        }
        filas.add(new String[]{"Valor del programa", String.format("%.2f", m.calcularValorPrograma())});
        for (var servicio : m.getServicios()) {
            filas.add(new String[]{"Servicio: " + servicio.getNombre(), String.format("%.2f", servicio.getPrecio())});
        }
        filas.add(new String[]{"Descuento (" + m.getPorcentajeDescuento() + " %)", String.format("-%.2f", m.calcularDescuento())});
        filas.add(new String[]{"TOTAL A PAGAR", String.format("%.2f", m.calcularTotal())});
        return filas;
    }
}
