package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.time.LocalDate;

//datos de ejemplo para que la aplicacion no arranque vacia (la persistencia es en memoria)
public final class DatosIniciales {

    private DatosIniciales() {
    }

    public static void cargar(Academia academia) {
        if (!academia.getEstudiantes().listar().isEmpty()) {
            return;
        }
        //el telefono 33550336 es un numero perfecto, sirve para probar la consulta
        Estudiante ana = new Estudiante("Ana Gómez", "1094000001", "33550336", "ana.gomez@gmail.com", 21);
        Estudiante luis = new Estudiante("Luis Pérez", "1094000002", "3001234567", "luis.perez@gmail.com", 30);
        academia.getEstudiantes().agregar(ana);
        academia.getEstudiantes().agregar(luis);

        Docente laura = new Docente("D001", "Laura Restrepo", "Inglés", "3104567890", 60_000);
        academia.getDocentes().agregar(laura);
        academia.getDocentes().agregar(new Docente("D002", "Pierre Martin", "Francés", "3119876543", 70_000));

        ProgramaFormacion ingles = new FabricaProgramaBasico()
                .crear("ING-B1", "Inglés básico", "Inglés", "Nivel A1-A2", 4, 180_000, Modalidad.VIRTUAL);
        ProgramaFormacion frances = new FabricaProgramaIntensivo()
                .crear("FRA-I1", "Francés intensivo", "Francés", "Nivel B1 en poco tiempo", 3, 250_000, Modalidad.PRESENCIAL);
        ProgramaFormacion ielts = new FabricaProgramaPersonalizado(8, "B1", "Preparar el examen IELTS")
                .crear("ING-P1", "Preparación IELTS", "Inglés", "Programa uno a uno", 2, 300_000, Modalidad.PRESENCIAL);
        academia.getProgramas().agregar(ingles);
        academia.getProgramas().agregar(frances);
        academia.getProgramas().agregar(ielts);

        ServicioAdicional examen = new ServicioAdicional("S01", "Examen de nivelación", "Ubica al estudiante en su nivel", 80_000, true);
        academia.getServicios().agregar(examen);
        academia.getServicios().agregar(new ServicioAdicional("S02", "Simulacro de certificación", "Simulacro de examen internacional", 150_000, true));
        academia.getServicios().agregar(new ServicioAdicional("S03", "Material de estudio", "Libro y cuadernillo de ejercicios", 60_000, true));
        academia.getServicios().agregar(new ServicioAdicional("S04", "Taller especial", "Taller de pronunciación", 45_000, false));

        academia.getMatriculas().agregar(new Matricula.Builder(ana, ingles, LocalDate.of(2026, 8, 3))
                .agregarServicio(examen).descuento(10).build());
        academia.getMatriculas().agregar(new Matricula.Builder(luis, ielts, LocalDate.of(2026, 9, 1))
                .docenteTutor(laura).observaciones("Prefiere clases en la tarde").build());
    }
}
