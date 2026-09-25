package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatriculaTest {

    private final Estudiante estudiante = new Estudiante("Ana Gomez", "1094", "3001234567", "ana@correo.com", 20);
    private final ProgramaFormacion programa = new FabricaProgramaBasico()
            .crear("P1", "Ingles A1", "Ingles", "Nivel inicial", 2, 100_000, Modalidad.VIRTUAL);

    @Test
    void aceptaDescuentoDeTreintaPorCiento() {
        Matricula matricula = new Matricula.Builder(estudiante, programa, LocalDate.now()).descuento(30).build();
        //programa 200.000 - 30 % = 140.000
        assertEquals(140_000, matricula.calcularTotal(), 0.01);
    }

    @Test
    void rechazaDescuentoMayorATreintaPorCiento() {
        assertThrows(IllegalArgumentException.class,
                () -> new Matricula.Builder(estudiante, programa, LocalDate.now()).descuento(30.5).build());
    }

    @Test
    void rechazaMatriculaSinPrograma() {
        assertThrows(IllegalArgumentException.class,
                () -> new Matricula.Builder(estudiante, null, LocalDate.now()).build());
    }

    @Test
    void rechazaTutorEnProgramaQueNoEsPersonalizado() {
        Docente docente = new Docente("D1", "Laura", "Ingles", "3100000000", 50_000);
        assertThrows(IllegalArgumentException.class,
                () -> new Matricula.Builder(estudiante, programa, LocalDate.now()).docenteTutor(docente).build());
    }

    @Test
    void entregaMaterialYCarneDeLaMismaModalidad() {
        Matricula matricula = new Matricula.Builder(estudiante, programa, LocalDate.now()).build();
        assertInstanceOf(MaterialVirtual.class, matricula.getMaterial());
        assertInstanceOf(CarneDigital.class, matricula.getCarne());
    }
}
