package co.edu.uniquindio.poo.parcial_uno_programacion.interfaces;

import java.util.List;
import java.util.Optional;

//interfaz pequeña (ISP) con solo las operaciones CRUD que necesita el sistema.
//las demas clases dependen de esta interfaz y no de como se guardan los datos (DIP)
public interface IRepositorio<T> {

    void agregar(T elemento);

    void actualizar(T elemento);

    boolean eliminar(String id);

    Optional<T> buscarPorId(String id);

    List<T> listar();
}
