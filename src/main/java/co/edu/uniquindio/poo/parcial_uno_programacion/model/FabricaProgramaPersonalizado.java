package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.util.List;

//el programa personalizado necesita datos extra, por eso esta fabrica los recibe en su constructor
public class FabricaProgramaPersonalizado extends ProgramaFactory {

    private final int cantidadSesionesTutor;
    private final String nivelIdiomaRequerido;
    private final String objetivosEstudiante;

    public FabricaProgramaPersonalizado(int cantidadSesionesTutor, String nivelIdiomaRequerido, String objetivosEstudiante) {
        this.cantidadSesionesTutor = cantidadSesionesTutor;
        this.nivelIdiomaRequerido = nivelIdiomaRequerido;
        this.objetivosEstudiante = objetivosEstudiante;
    }

    @Override
    protected ProgramaFormacion crearPrograma(String codigo, String nombre, String idioma, String descripcion,
                                              int duracionMeses, double valorMensual, Modalidad modalidad) {
        return new ProgramaPersonalizado(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, modalidad,
                cantidadSesionesTutor, nivelIdiomaRequerido, objetivosEstudiante);
    }

    @Override
    protected List<Beneficio> beneficiosDelTipo() {
        return List.of(PLATAFORMA, TUTOR);
    }
}
