package co.edu.uniquindio.poo.parcial_uno_programacion.interfaces;

//patron Prototype: el objeto sabe crear una copia de si mismo
public interface IPrototipo<T> {

    T clonar();
}
