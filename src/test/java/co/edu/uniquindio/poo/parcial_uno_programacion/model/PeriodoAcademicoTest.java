package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

class PeriodoAcademicoTest {

    @Test
    void clonarNoCompartePlazasEntrePeriodos() {
        ProgramaFormacion programa = new FabricaProgramaIntensivo()
                .crear("P2", "Frances B1", "Frances", "Intermedio", 3, 150_000, Modalidad.PRESENCIAL);
        PeriodoAcademico periodo1 = new PeriodoAcademico("2026-1", LocalDate.of(2026, 2, 1), LocalDate.of(2026, 6, 30));
        periodo1.agregarOferta(new OfertaPrograma(programa, null, "Lunes 6pm", "Salon 101", 20));

        PeriodoAcademico periodo2 = periodo1.clonar("2026-2", LocalDate.of(2026, 8, 1), LocalDate.of(2026, 12, 15));
        OfertaPrograma original = periodo1.getOfertas().get(0);
        OfertaPrograma copia = periodo2.getOfertas().get(0);

        //clonacion profunda: la oferta es otro objeto, pero con la misma configuracion
        assertNotSame(original, copia);
        assertSame(original.getPrograma(), copia.getPrograma());
        assertEquals(original.getHorario(), copia.getHorario());

        //ocupar cupos en el periodo 2 no afecta al periodo 1
        copia.ocuparCupo();
        copia.ocuparCupo();
        assertEquals(18, copia.getCuposDisponibles());
        assertEquals(20, original.getCuposDisponibles());
    }
}
