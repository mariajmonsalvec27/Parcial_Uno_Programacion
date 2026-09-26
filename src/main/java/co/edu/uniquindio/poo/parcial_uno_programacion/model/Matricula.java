package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.ICarne;
import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IMaterialEntregable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//matricula de un estudiante en un programa.
//patron Builder: tiene 3 datos obligatorios y varios opcionales, el Builder evita un constructor
//gigante y valida TODAS las reglas antes de crear el objeto
public class Matricula {

    public static final double DESCUENTO_MAXIMO = 30.0;

    private final long numero;
    private final Estudiante estudiante;
    private final ProgramaFormacion programa;
    private final LocalDate fechaInicio;
    private final Docente docenteTutor;
    private final Asignacion asignacion;
    private final List<ServicioAdicional> servicios;
    private double porcentajeDescuento;
    private String observaciones;
    private final IMaterialEntregable material;
    private final ICarne carne;

    private Matricula(Builder builder) {
        this.estudiante = builder.estudiante;
        this.programa = builder.programa;
        this.fechaInicio = builder.fechaInicio;
        this.docenteTutor = builder.docenteTutor;
        this.servicios = new ArrayList<>(builder.servicios);
        this.porcentajeDescuento = builder.porcentajeDescuento;
        this.observaciones = builder.observaciones;
        //la asignacion identifica la relacion estudiante - programa - docente
        this.asignacion = docenteTutor == null ? null : new Asignacion(estudiante, programa, docenteTutor);
        //Abstract Factory: el material y el carne siempre son de la misma modalidad del programa
        MaterialEntregaFactory fabrica = MaterialEntregaFactory.segunModalidad(programa.getModalidad());
        this.material = fabrica.crearMaterial();
        this.carne = fabrica.crearCarne();
        //Singleton: el numero se pide al final, cuando ya se validaron las reglas, para no gastar numeros
        this.numero = GeneradorConsecutivo.getInstance().siguiente();
    }

    //regla de negocio: el descuento va de 0 % a 30 % del valor del programa
    public static void validarDescuento(double porcentaje) {
        if (porcentaje < 0 || porcentaje > DESCUENTO_MAXIMO) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 % y " + DESCUENTO_MAXIMO + " %");
        }
    }

    public double calcularValorPrograma() {
        return programa.calcularValorFinal();
    }

    public double calcularValorServicios() {
        double total = 0;
        for (ServicioAdicional servicio : servicios) {
            total += servicio.getPrecio();
        }
        return total;
    }

    //el descuento se aplica solo sobre el valor del programa
    public double calcularDescuento() {
        return calcularValorPrograma() * porcentajeDescuento / 100;
    }

    //valor final a pagar = programa + servicios - descuento
    public double calcularTotal() {
        return calcularValorPrograma() + calcularValorServicios() - calcularDescuento();
    }

    public long getNumero() {
        return numero;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public ProgramaFormacion getPrograma() {
        return programa;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public Docente getDocenteTutor() {
        return docenteTutor;
    }

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public List<ServicioAdicional> getServicios() {
        return new ArrayList<>(servicios);
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        validarDescuento(porcentajeDescuento);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public IMaterialEntregable getMaterial() {
        return material;
    }

    public ICarne getCarne() {
        return carne;
    }

    //Builder: los obligatorios van en el constructor, los opcionales en metodos encadenables
    public static class Builder {

        private final Estudiante estudiante;
        private final ProgramaFormacion programa;
        private final LocalDate fechaInicio;
        private Docente docenteTutor;
        private final List<ServicioAdicional> servicios = new ArrayList<>();
        private double porcentajeDescuento;
        private String observaciones = "";

        public Builder(Estudiante estudiante, ProgramaFormacion programa, LocalDate fechaInicio) {
            this.estudiante = estudiante;
            this.programa = programa;
            this.fechaInicio = fechaInicio;
        }

        public Builder docenteTutor(Docente docente) {
            this.docenteTutor = docente;
            return this;
        }

        public Builder agregarServicio(ServicioAdicional servicio) {
            if (servicio == null) {
                throw new IllegalArgumentException("El servicio no puede ser nulo");
            }

            this.servicios.add(servicio);
            return this;
        }

        public Builder descuento(double porcentaje) {
            this.porcentajeDescuento = porcentaje;
            return this;
        }

        public Builder observaciones(String observaciones) {
            this.observaciones = observaciones;
            return this;
        }

        public Matricula build() {
            if (programa == null) {
                throw new IllegalArgumentException("No puede existir una matricula sin programa");
            }
            if (estudiante == null) {
                throw new IllegalArgumentException("La matricula necesita un estudiante");
            }
            if (fechaInicio == null) {
                throw new IllegalArgumentException("La matricula necesita una fecha de inicio");
            }
            if (programa.getEstado() != EstadoPrograma.ACTIVO) {
                throw new IllegalArgumentException("Solo se puede matricular en programas activos");
            }
            if (docenteTutor != null && !(programa instanceof ProgramaPersonalizado)) {
                throw new IllegalArgumentException("Solo los programas personalizados tienen docente tutor");
            }
            for (ServicioAdicional servicio : servicios) {
                if (!servicio.isDisponible()) {
                    throw new IllegalArgumentException("El servicio " + servicio.getNombre() + " no esta disponible");
                }
            }
            validarDescuento(porcentajeDescuento);
            return new Matricula(this);
        }
    }
}
