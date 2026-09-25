package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.util.List;

public class FabricaProgramaBasico extends ProgramaFactory {

    @Override
    protected ProgramaFormacion crearPrograma(String codigo, String nombre, String idioma, String descripcion,
                                              int duracionMeses, double valorMensual, Modalidad modalidad) {
        return new ProgramaBasico(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, modalidad);
    }

    @Override
    protected List<Beneficio> beneficiosDelTipo() {
        return List.of(PLATAFORMA);
    }
}
