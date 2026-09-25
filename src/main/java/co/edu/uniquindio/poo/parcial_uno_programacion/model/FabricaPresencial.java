package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.ICarne;
import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IMaterialEntregable;

//fabrica concreta de la familia presencial: material impreso + carne fisico
public class FabricaPresencial implements MaterialEntregaFactory {

    @Override
    public IMaterialEntregable crearMaterial() {
        return new MaterialPresencial();
    }

    @Override
    public ICarne crearCarne() {
        return new CarneFisico();
    }
}
