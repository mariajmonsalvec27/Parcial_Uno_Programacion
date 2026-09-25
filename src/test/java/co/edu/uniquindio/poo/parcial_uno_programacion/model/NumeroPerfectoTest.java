package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumeroPerfectoTest {

    @Test
    void reconoceNumerosPerfectos() {
        assertTrue(ConsultasAcademia.esNumeroPerfecto(6));
        assertTrue(ConsultasAcademia.esNumeroPerfecto(28));
        assertTrue(ConsultasAcademia.esNumeroPerfecto(496));
        assertTrue(ConsultasAcademia.esNumeroPerfecto(8128));
    }

    @Test
    void rechazaNumerosNoPerfectos() {
        assertFalse(ConsultasAcademia.esNumeroPerfecto(12));
        assertFalse(ConsultasAcademia.esNumeroPerfecto(100));
        assertFalse(ConsultasAcademia.esNumeroPerfecto(1));
    }
}
