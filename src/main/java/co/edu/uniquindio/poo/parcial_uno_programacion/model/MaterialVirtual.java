package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IMaterialEntregable;

//material de la modalidad virtual
public class MaterialVirtual implements IMaterialEntregable {

    @Override
    public String getDescripcion() {
        return "Licencia de acceso a la plataforma virtual";
    }
}
