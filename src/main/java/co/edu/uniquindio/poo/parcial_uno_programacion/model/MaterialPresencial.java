package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IMaterialEntregable;

//material de la modalidad presencial
public class MaterialPresencial implements IMaterialEntregable {

    @Override
    public String getDescripcion() {
        return "Material impreso";
    }
}
