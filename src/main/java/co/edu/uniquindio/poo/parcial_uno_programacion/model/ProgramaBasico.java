package co.edu.uniquindio.poo.parcial_uno_programacion.model;

public class ProgramaBasico extends ProgramaFormacion {

    public ProgramaBasico(String codigo, String nombre, String idioma, String descripcion, int duracionMeses, double valorMensual, Modalidad modalidad) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, modalidad);

    }

    @Override
    public double calcularValorFinal(){
        return getValorMensual() * getDuracionMeses();
    }
}
