package co.edu.uniquindio.poo.parcial_uno_programacion.model;

//programa intensivo, recargo del 20% sobre valor base (aunque no dicen porcentaje exacto, escogí el 20% como constante para poder justificarla sin afectar otras clases)
public class ProgramaIntensivo extends ProgramaFormacion {

    private static final double FACTOR_INTENSIVO = 1.20;
    public ProgramaIntensivo(String codigo, String nombre, String idioma, String descripcion, int duracionMeses, double valorMensual, Modalidad modalidad) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, modalidad);
    }

    @Override
    public double calcularValorFinal(){
        return getValorMensual() * getDuracionMeses() * FACTOR_INTENSIVO;
    }
}
