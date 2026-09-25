package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.ICarne;

//carne de la modalidad presencial
public class CarneFisico implements ICarne {

    @Override
    public String getDescripcion() {
        return "Carne fisico";
    }
}
