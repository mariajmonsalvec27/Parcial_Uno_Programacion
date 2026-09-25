package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;
import co.edu.uniquindio.poo.parcial_uno_programacion.repositorio.RepositorioMemoria;

//patron Singleton: existe una sola academia en el sistema, con sus datos basicos y sus registros.
//todas las pantallas trabajan sobre la misma instancia, por eso ven los mismos datos
public final class Academia {

    private static Academia instancia;

    private String nombre = "LinguaPlus";
    private String nit = "900.123.456-7";
    private String direccion = "Calle 21 # 14-35, Armenia, Quindio";
    private String telefono = "6067412345";
    private String correo = "contacto@linguaplus.edu.co";
    private String paginaWeb = "www.linguaplus.edu.co";

    //la academia depende de la interfaz IRepositorio y no de la clase concreta (DIP)
    private final IRepositorio<Estudiante> estudiantes = new RepositorioMemoria<>(Estudiante::getDocumentoIdentidad);
    private final IRepositorio<Docente> docentes = new RepositorioMemoria<>(Docente::getIdentificacion);
    private final IRepositorio<ProgramaFormacion> programas = new RepositorioMemoria<>(ProgramaFormacion::getCodigo);
    private final IRepositorio<ServicioAdicional> servicios = new RepositorioMemoria<>(ServicioAdicional::getCodigo);
    private final IRepositorio<Matricula> matriculas = new RepositorioMemoria<>(m -> String.valueOf(m.getNumero()));

    private Academia() {
    }

    public static synchronized Academia getInstance() {
        if (instancia == null) {
            instancia = new Academia();
        }
        return instancia;
    }

    public IRepositorio<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public IRepositorio<Docente> getDocentes() {
        return docentes;
    }

    public IRepositorio<ProgramaFormacion> getProgramas() {
        return programas;
    }

    public IRepositorio<ServicioAdicional> getServicios() {
        return servicios;
    }

    public IRepositorio<Matricula> getMatriculas() {
        return matriculas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
}
