package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.ICarne;
import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IMaterialEntregable;

//fabrica concreta de la familia virtual: licencia de plataforma + carne digital
public class FabricaVirtual implements MaterialEntregaFactory {

    @Override
    public IMaterialEntregable crearMaterial() {
        return new MaterialVirtual();
    }

    @Override
    public ICarne crearCarne() {
        return new CarneDigital();
    }
}
