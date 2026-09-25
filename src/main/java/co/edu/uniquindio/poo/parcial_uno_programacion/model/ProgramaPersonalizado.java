package co.edu.uniquindio.poo.parcial_uno_programacion.model;

// el valor de recargo también lo escogí yo, ya que el archivo no mencionaba uno específico, nuevamente hecho constante para que no afecte el resto del sistema
public class ProgramaPersonalizado extends ProgramaFormacion {

    private static final double RECARGO_POR_SESION = 25000.0;

    private int cantidadSesionesTutor;
    private String nivelIdiomaRequerido;
    private String objetivosEstudiante;

    public ProgramaPersonalizado(String codigo, String nombre, String idioma, String descripcion, int duracionMeses, double valorMensual, Modalidad modalidad, int cantidadSesionesTutor,String nivelIdiomaRequerido, String objetivosEstudiante) {
        super(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, modalidad);
        setCantidadSesionesTutor(cantidadSesionesTutor);
        this.nivelIdiomaRequerido = nivelIdiomaRequerido;
        this.objetivosEstudiante = objetivosEstudiante;
    }

    @Override
    public double calcularValorFinal(){
        double base = getValorMensual() * getDuracionMeses();
        double recargoSesiones = cantidadSesionesTutor * RECARGO_POR_SESION;
        return base + recargoSesiones;
    }

    public int getCantidadSesionesTutor() {
        return cantidadSesionesTutor;
    }

    public void setCantidadSesionesTutor(int cantidadSesionesTutor) {
        if (cantidadSesionesTutor < 0){
            throw new IllegalArgumentException("La cantidad de sesiones no puede ser negativa");
        }
        this.cantidadSesionesTutor = cantidadSesionesTutor;
    }

    public String getNivelIdiomaRequerido() {
        return nivelIdiomaRequerido;
    }

    public void setNivelIdiomaRequerido(String nivelIdiomaRequerido) {
        this.nivelIdiomaRequerido = nivelIdiomaRequerido;
    }

    public String getObjetivosEstudiante() {
        return objetivosEstudiante;
    }

    public void setObjetivosEstudiante(String objetivosEstudiante) {
        this.objetivosEstudiante = objetivosEstudiante;
    }
}
