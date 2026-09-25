package co.edu.uniquindio.poo.parcial_uno_programacion.model;

//clase propia para la relacion estudiante - programa - docente tutor.
//asi se puede saber que docente atiende a que estudiante y en que programa
public class Asignacion {

    private final Estudiante estudiante;
    private final ProgramaFormacion programa;
    private final Docente docente;

    public Asignacion(Estudiante estudiante, ProgramaFormacion programa, Docente docente) {
        if (estudiante == null || programa == null || docente == null) {
            throw new IllegalArgumentException("La asignacion necesita estudiante, programa y docente");
        }
        this.estudiante = estudiante;
        this.programa = programa;
        this.docente = docente;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public ProgramaFormacion getPrograma() {
        return programa;
    }

    public Docente getDocente() {
        return docente;
    }

    @Override
    public String toString() {
        return docente.getNombre() + " -> " + estudiante.getNombreCompleto() + " en " + programa.getNombre();
    }
}
