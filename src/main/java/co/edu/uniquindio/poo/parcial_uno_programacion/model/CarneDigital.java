package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.ICarne;

//carne de la modalidad virtual
public class CarneDigital implements ICarne {

    @Override
    public String getDescripcion() {
        return "Carne digital";
    }
}
