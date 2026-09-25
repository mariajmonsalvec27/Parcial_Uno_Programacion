package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.util.List;

//patron Factory Method: esta clase define COMO se registra un programa (crearlo y darle sus beneficios),
//pero deja que cada subclase decida QUE tipo de programa se crea (basico, intensivo o personalizado).
//si mañana aparece un nuevo tipo, se agrega otra fabrica sin modificar esta clase (OCP)
public abstract class ProgramaFactory {

    protected static final Beneficio PLATAFORMA =
            new Beneficio("B01", "Acceso a la plataforma virtual", "Contenidos y ejercicios en linea");
    protected static final Beneficio CLUB_CONVERSACION =
            new Beneficio("B02", "Club de conversacion", "Practica oral semanal con otros estudiantes");
    protected static final Beneficio TUTOR =
            new Beneficio("B03", "Acompañamiento de tutor", "Sesiones uno a uno con un docente");

    public ProgramaFormacion crear(String codigo, String nombre, String idioma, String descripcion,
                                   int duracionMeses, double valorMensual, Modalidad modalidad) {
        ProgramaFormacion programa = crearPrograma(codigo, nombre, idioma, descripcion, duracionMeses, valorMensual, modalidad);
        for (Beneficio beneficio : beneficiosDelTipo()) {
            programa.agregarBeneficio(beneficio);
        }
        return programa;
    }

    //factory method: cada subclase crea su tipo de programa
    protected abstract ProgramaFormacion crearPrograma(String codigo, String nombre, String idioma, String descripcion,
                                                       int duracionMeses, double valorMensual, Modalidad modalidad);

    //cada tipo de programa incluye beneficios diferentes
    protected abstract List<Beneficio> beneficiosDelTipo();
}
