package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.util.List;

public class FabricaProgramaIntensivo extends ProgramaFactory {

    @Override
    protected ProgramaFormacion crearPrograma(String codigo, String nombre, String idioma, String descripcion,
                                              int duracionMeses, double valorMensual, Modalidad modalidad) {
        return new ProgramaIntensivo(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, modalidad);
    }

    @Override
    protected List<Beneficio> beneficiosDelTipo() {
        return List.of(PLATAFORMA, CLUB_CONVERSACION);
    }
}
