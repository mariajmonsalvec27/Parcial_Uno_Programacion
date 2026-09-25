package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IPrototipo;

//un programa ofrecido en un periodo: con su docente, horario, salon y cupos.
//patron Prototype: armar la oferta desde cero es costoso (consultar docentes, salones, tarifas),
//por eso cada periodo nuevo se obtiene clonando la oferta del anterior
public class OfertaPrograma implements IPrototipo<OfertaPrograma> {

    private final ProgramaFormacion programa;
    private final Docente docente;
    private final String horario;
    private final String salon;
    private final int cuposTotales;
    private int cuposOcupados;

    public OfertaPrograma(ProgramaFormacion programa, Docente docente, String horario, String salon, int cuposTotales) {
        if (programa == null) {
            throw new IllegalArgumentException("La oferta necesita un programa");
        }
        if (cuposTotales <= 0) {
            throw new IllegalArgumentException("Los cupos deben ser mayores a 0");
        }
        this.programa = programa;
        this.docente = docente;
        this.horario = horario;
        this.salon = salon;
        this.cuposTotales = cuposTotales;
        this.cuposOcupados = 0;
    }

    //la copia es un objeto NUEVO con la misma configuracion y los cupos libres,
    //por eso ocupar cupos en un periodo no afecta los cupos del otro.
    //programa y docente se comparten porque son datos del catalogo, no del periodo
    @Override
    public OfertaPrograma clonar() {
        return new OfertaPrograma(programa, docente, horario, salon, cuposTotales);
    }

    public void ocuparCupo() {
        if (getCuposDisponibles() == 0) {
            throw new IllegalStateException("No hay cupos disponibles para " + programa.getNombre());
        }
        cuposOcupados++;
    }

    public int getCuposDisponibles() {
        return cuposTotales - cuposOcupados;
    }

    public ProgramaFormacion getPrograma() {
        return programa;
    }

    public Docente getDocente() {
        return docente;
    }

    public String getHorario() {
        return horario;
    }

    public String getSalon() {
        return salon;
    }

    public int getCuposTotales() {
        return cuposTotales;
    }

    public int getCuposOcupados() {
        return cuposOcupados;
    }
}
