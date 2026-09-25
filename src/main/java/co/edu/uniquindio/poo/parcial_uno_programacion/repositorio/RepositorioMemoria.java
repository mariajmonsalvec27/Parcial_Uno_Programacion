package co.edu.uniquindio.poo.parcial_uno_programacion.repositorio;

import co.edu.uniquindio.poo.parcial_uno_programacion.interfaces.IRepositorio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

//repositorio generico en memoria: sirve para estudiantes, docentes, programas, servicios y matriculas.
//recibe una funcion que dice cual es el identificador de cada objeto (documento, codigo, numero...)
public class RepositorioMemoria<T> implements IRepositorio<T> {

    //LinkedHashMap conserva el orden en que se registraron los datos
    private final Map<String, T> datos = new LinkedHashMap<>();
    private final Function<T, String> obtenerId;

    public RepositorioMemoria(Function<T, String> obtenerId) {
        this.obtenerId = obtenerId;
    }

    @Override
    public void agregar(T elemento) {
        String id = obtenerId.apply(elemento);
        if (datos.containsKey(id)) {
            throw new IllegalArgumentException("Ya existe un registro con el identificador " + id);
        }
        datos.put(id, elemento);
    }

    @Override
    public void actualizar(T elemento) {
        String id = obtenerId.apply(elemento);
        if (!datos.containsKey(id)) {
            throw new IllegalArgumentException("No existe un registro con el identificador " + id);
        }
        datos.put(id, elemento);
    }

    @Override
    public boolean eliminar(String id) {
        return datos.remove(id) != null;
    }

    @Override
    public Optional<T> buscarPorId(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(datos.values());
    }
}
