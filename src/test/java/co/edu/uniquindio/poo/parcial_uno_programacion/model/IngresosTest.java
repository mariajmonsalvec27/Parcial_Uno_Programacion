package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;
import co.edu.uniquindio.poo.parcial_uno_programacion.repositorio.RepositorioMemoria;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngresosTest {

    @Test
    void sumaSoloLasMatriculasDelPeriodo() {
        //repositorios propios para la prueba (inyeccion de dependencias)
        IRepositorio<Estudiante> estudiantes = new RepositorioMemoria<>(Estudiante::getDocumentoIdentidad);
        IRepositorio<Matricula> matriculas = new RepositorioMemoria<>(m -> String.valueOf(m.getNumero()));
        ConsultasAcademia consultas = new ConsultasAcademia(estudiantes, matriculas);

        Estudiante estudiante = new Estudiante("Luis Perez", "1095", "3159876543", "luis@correo.com", 25);
        ProgramaFormacion programa = new FabricaProgramaBasico()
                .crear("P3", "Ingles A2", "Ingles", "Basico", 1, 100_000, Modalidad.PRESENCIAL);
        ServicioAdicional examen = new ServicioAdicional("S1", "Examen de nivelacion", "", 50_000, true);

        //en marzo: 100.000 + 50.000 - 10 % de 100.000 = 140.000
        matriculas.agregar(new Matricula.Builder(estudiante, programa, LocalDate.of(2026, 3, 10))
                .agregarServicio(examen).descuento(10).build());
        //en abril: 100.000
        matriculas.agregar(new Matricula.Builder(estudiante, programa, LocalDate.of(2026, 4, 5)).build());
        //en agosto: fuera del periodo consultado
        matriculas.agregar(new Matricula.Builder(estudiante, programa, LocalDate.of(2026, 8, 1)).build());

        double ingresos = consultas.calcularIngresos(LocalDate.of(2026, 3, 1), LocalDate.of(2026, 4, 30));
        assertEquals(240_000, ingresos, 0.01);
    }
}
