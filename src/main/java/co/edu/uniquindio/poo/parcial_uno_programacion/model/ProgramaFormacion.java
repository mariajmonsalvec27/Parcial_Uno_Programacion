package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.util.ArrayList;
import java.util.List;

//clase que cumple con el principio de responsabilidad unica (SRP), ya que solo manejara datos
// y reglas propias del programa
//principio abierto cerrado (OCP)
public abstract class ProgramaFormacion {

    private String codigo;
    private String nombre;
    private String idioma;
    private String descripcion;
    private int duracionMeses;
    private double valorMensual;
    private EstadoPrograma estado;
    private Modalidad modalidad;
    private List<Beneficio> beneficios;

    protected ProgramaFormacion(String codigo, String nombre, String idioma, String descripcion, int duracionMeses, double valorMensual, Modalidad modalidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idioma = idioma;
        setDuracionMeses(duracionMeses);
        setValorMensual(valorMensual);
        this.modalidad = modalidad;
        this.estado = EstadoPrograma.ACTIVO;
        this.beneficios = new ArrayList<Beneficio>();

    }

    //metodo que calcula valor final del programa segun el tipo y duracion
    //no incluye servicios adicionales ni descuestos (principio SRP)
    public abstract double calcularValorFinal();

    public void agregarBeneficio(Beneficio beneficio) {
        if (beneficio == null){
            throw new IllegalArgumentException("El Beneficio no puede ser nulo.");
        }
        if (!beneficios.contains(beneficio)) {
            beneficios.add(beneficio);
        }
    }

    public void removerBeneficio(Beneficio beneficio) {
        beneficios.remove(beneficio);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionMeses() {
        return duracionMeses;
    }

    public void setDuracionMeses(int duracionMeses) {
        if (duracionMeses <= 0){
            throw new IllegalArgumentException("La duracion debe ser mayor a 0 meses");
        }
        this.duracionMeses = duracionMeses;
    }

    public double getValorMensual() {
        return valorMensual;
    }

    public void setValorMensual(double valorMensual) {
        if (valorMensual < 0){
            throw new IllegalArgumentException("La valor mensual no puede ser negativo");
        }
        this.valorMensual = valorMensual;
    }

    public EstadoPrograma getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrograma estado) {
        this.estado = estado;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    //este metodo evita que el codigo externo modifique la lista interna.
    public List<Beneficio> getBeneficios() {
        return new ArrayList<>(beneficios);
    }

    public void setBeneficios(List<Beneficio> beneficios) {
        this.beneficios = beneficios;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] - %s (%s, %d meses) - $%.2f/mes",
                nombre, codigo, idioma, modalidad, duracionMeses, valorMensual);
    }
}
