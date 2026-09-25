package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.ICarne;
import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IMaterialEntregable;

//patron Abstract Factory: cada fabrica crea una FAMILIA completa (material + carne) de una sola modalidad.
//asi es imposible entregar material impreso con carne digital o licencia de plataforma con carne fisico
public interface MaterialEntregaFactory {

    IMaterialEntregable crearMaterial();

    ICarne crearCarne();

    //devuelve la fabrica que corresponde a la modalidad del programa
    static MaterialEntregaFactory segunModalidad(Modalidad modalidad) {
        return switch (modalidad) {
            case PRESENCIAL -> new FabricaPresencial();
            case VIRTUAL -> new FabricaVirtual();
        };
    }
}
