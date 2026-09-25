package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.util.Objects;

public class Beneficio {

    private String codigo;
    private String nombre;
    private String descripcion;

    public Beneficio(String codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // esta parte del codigo se encarga de que dos beneficios se consideren iguales si tienen el mismo codigo
    //con esto se puede usar List#contains sin la preocupacion de obtener
    //duplicados en ProgramaFormacion.agregarBeneficio()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Beneficio)) return false;
        Beneficio beneficio = (Beneficio) o;
        return Objects.equals(codigo, beneficio.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return nombre + "( " + codigo + ")";
    }
}
