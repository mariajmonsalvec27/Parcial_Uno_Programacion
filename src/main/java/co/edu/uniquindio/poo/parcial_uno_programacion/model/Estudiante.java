package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

public class Estudiante {

    /**
     * Patrón para validar el formato básico de un correo
     * que significa cada parte:
     *   ^ inicio del texto
     *   [\w.+-]+ parte antes del @, letras, números como ".", "+" o "-" 1 o mas veces
     *   @ arroba obligatoria y literal
     *   [\w-]+ dominio, letras, números o "-" 1 o mas veces
     *   \. punto literal
     *   [a-zA-Z]{2,} es la extensión, solo letras, mínimo 2 como co, com, edu
     *   $ fin del texto
     *   esto no confirma correos reales porsiacasoxd
     */

    private static final Pattern PATRON_CORREO =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    //verifica si lo escrito tiene el formato de correo
    private static boolean esFormatoCorreoValido(String correo) {
        return correo != null && PATRON_CORREO.matcher(correo).matches();
    }

    private String nombreCompleto;
    private String documentoIdentidad;
    private String telefono;
    private String correo;
    private int edad;
    private LocalDate fechaRegistro;

    /**
     * Constructor corto sirve para indicar que si la fecha de registro no se pone, se asume
     * que es hoy, como para crear a un estudiante nuevo
     * Delega en el constructor completo para no repetir validaciones.
     */
    public Estudiante(String nombreCompleto, String documentoIdentidad, String telefono, String correo, int edad) {
        this(nombreCompleto, documentoIdentidad, telefono, correo, edad, LocalDate.now());
    }

    public Estudiante(String nombreCompleto, String documentoIdentidad, String telefono, String correo, int edad, LocalDate fechaRegistro) {
        setNombreCompleto(nombreCompleto);
        setDocumentoIdentidad(documentoIdentidad);
        setTelefono(telefono);
        setCorreo(correo);
        setEdad(edad);
        this.fechaRegistro = fechaRegistro;
    }


    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.isBlank()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio");
        }
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        if (documentoIdentidad == null || documentoIdentidad.isBlank()) {
            throw new IllegalArgumentException("El documento de identidad es obligatorio");
        }
        this.documentoIdentidad = documentoIdentidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("El teléfono es obligatorio");
        }
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (!esFormatoCorreoValido(correo)) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido");
        }
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad <= 0 || edad > 80) {
            throw new IllegalArgumentException("La edad debe estar en un rango válido.");
        }
        this.edad = edad;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    //dos estudiantes son el mismo si tienen el mismo documento (equals y hashCode deben ir juntos)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estudiante)) return false;
        Estudiante otro = (Estudiante) o;
        return Objects.equals(documentoIdentidad, otro.documentoIdentidad);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(documentoIdentidad);
    }

    @Override
    public String toString(){
        return nombreCompleto + "(" + documentoIdentidad + ") - " + telefono;
    }
}
