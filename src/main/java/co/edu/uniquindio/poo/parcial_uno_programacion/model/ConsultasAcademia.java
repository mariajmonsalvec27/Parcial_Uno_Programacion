package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;

import java.time.LocalDate;
import java.util.Optional;

//consultas que pide la academia: buscar estudiante por telefono, numero perfecto e ingresos por periodo.
//SRP: esta clase solo hace consultas, no registra ni modifica datos.
//DIP: recibe los repositorios por el constructor (inyeccion), no los crea ella misma
public class ConsultasAcademia {

    private final IRepositorio<Estudiante> estudiantes;
    private final IRepositorio<Matricula> matriculas;

    public ConsultasAcademia(IRepositorio<Estudiante> estudiantes, IRepositorio<Matricula> matriculas) {
        this.estudiantes = estudiantes;
        this.matriculas = matriculas;
    }

    //recorre los estudiantes y devuelve el que tenga ese telefono
    public Optional<Estudiante> buscarPorTelefono(String telefono) {
        for (Estudiante estudiante : estudiantes.listar()) {
            if (estudiante.getTelefono().equals(telefono.trim())) {
                return Optional.of(estudiante);
            }
        }
        return Optional.empty();
    }

    //convierte el telefono a numero (solo los digitos) y revisa si es perfecto
    public boolean telefonoEsPerfecto(String telefono) {
        String digitos = telefono.replaceAll("\\D", "");
        if (digitos.isEmpty() || digitos.length() > 18) {
            return false;
        }
        return esNumeroPerfecto(Long.parseLong(digitos));
    }

    //un numero es perfecto si la suma de sus divisores propios es igual al numero. ej: 28 = 1+2+4+7+14.
    //solo se recorre hasta la raiz cuadrada: cada divisor i encontrado trae a su pareja n / i
    public static boolean esNumeroPerfecto(long numero) {
        if (numero < 2) {
            return false;
        }
        long suma = 1; //el 1 siempre es divisor propio
        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                suma += i;
                long pareja = numero / i;
                if (pareja != i) {
                    suma += pareja;
                }
            }
        }
        return suma == numero;
    }

    //recorre las matriculas, toma las que iniciaron dentro del periodo y acumula su valor total
    public double calcularIngresos(LocalDate desde, LocalDate hasta) {
        if (desde == null || hasta == null || hasta.isBefore(desde)) {
            throw new IllegalArgumentException("El periodo consultado no es valido");
        }
        double total = 0;
        for (Matricula matricula : matriculas.listar()) {
            LocalDate fecha = matricula.getFechaInicio();
            if (!fecha.isBefore(desde) && !fecha.isAfter(hasta)) {
                total += matricula.calcularTotal();
            }
        }
        return total;
    }
}
