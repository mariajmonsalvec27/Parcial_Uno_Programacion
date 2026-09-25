package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//periodo academico con su oferta de programas
public class PeriodoAcademico {

    private final String nombre;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final List<OfertaPrograma> ofertas = new ArrayList<>();

    public PeriodoAcademico(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null || fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("Las fechas del periodo no son validas");
        }
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public void agregarOferta(OfertaPrograma oferta) {
        ofertas.add(oferta);
    }

    //abre un periodo nuevo con la misma oferta: solo cambian las fechas y los cupos empiezan libres.
    //clonacion profunda: se clona cada oferta, no se comparte la lista ni sus elementos
    public PeriodoAcademico clonar(String nuevoNombre, LocalDate nuevoInicio, LocalDate nuevoFin) {
        PeriodoAcademico nuevo = new PeriodoAcademico(nuevoNombre, nuevoInicio, nuevoFin);
        for (OfertaPrograma oferta : ofertas) {
            nuevo.agregarOferta(oferta.clonar());
        }
        return nuevo;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public List<OfertaPrograma> getOfertas() {
        return new ArrayList<>(ofertas);
    }
}
