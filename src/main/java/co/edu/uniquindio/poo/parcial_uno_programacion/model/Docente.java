package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.util.Objects;

//docente de la academia, puede ser asignado como tutor en programas personalizados
public class Docente {

    private String identificacion;
    private String nombre;
    private String idiomaEspecialidad;
    private String telefono;
    private double tarifaSesion;

    public Docente(String identificacion, String nombre, String idiomaEspecialidad, String telefono, double tarifaSesion) {
        setIdentificacion(identificacion);
        setNombre(nombre);
        setIdiomaEspecialidad(idiomaEspecialidad);
        setTelefono(telefono);
        setTarifaSesion(tarifaSesion);
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        if (identificacion == null || identificacion.isBlank()) {
            throw new IllegalArgumentException("La identificacion del docente es obligatoria");
        }
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del docente es obligatorio");
        }
        this.nombre = nombre;
    }

    public String getIdiomaEspecialidad() {
        return idiomaEspecialidad;
    }

    public void setIdiomaEspecialidad(String idiomaEspecialidad) {
        if (idiomaEspecialidad == null || idiomaEspecialidad.isBlank()) {
            throw new IllegalArgumentException("El idioma de especialidad es obligatorio");
        }
        this.idiomaEspecialidad = idiomaEspecialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getTarifaSesion() {
        return tarifaSesion;
    }

    public void setTarifaSesion(double tarifaSesion) {
        if (tarifaSesion < 0) {
            throw new IllegalArgumentException("La tarifa por sesion no puede ser negativa");
        }
        this.tarifaSesion = tarifaSesion;
    }

    //dos docentes son el mismo si tienen la misma identificacion
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Docente)) return false;
        Docente otro = (Docente) o;
        return Objects.equals(identificacion, otro.identificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identificacion);
    }

    @Override
    public String toString() {
        return nombre + " (" + idiomaEspecialidad + ")";
    }
}
