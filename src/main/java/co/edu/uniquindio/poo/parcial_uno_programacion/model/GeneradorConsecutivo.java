package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

//patron Singleton: un unico generador de numeros de matricula para toda la aplicacion.
//- thread-safe: getInstance() y siguiente() son synchronized, dos hilos nunca reciben el mismo numero
//- persistido: el ultimo numero se guarda en un archivo, asi al reiniciar la app no se repiten numeros.
//  si varias sedes comparten ese archivo (por ejemplo en una carpeta de red), comparten el consecutivo
public final class GeneradorConsecutivo {

    private static final Path ARCHIVO = Path.of("datos", "consecutivo.txt");
    private static GeneradorConsecutivo instancia;

    private long ultimoNumero;

    //constructor privado: nadie puede hacer "new GeneradorConsecutivo()"
    private GeneradorConsecutivo() {
        this.ultimoNumero = leerUltimoNumero();
    }

    public static synchronized GeneradorConsecutivo getInstance() {
        if (instancia == null) {
            instancia = new GeneradorConsecutivo();
        }
        return instancia;
    }

    public synchronized long siguiente() {
        ultimoNumero++;
        guardarUltimoNumero();
        return ultimoNumero;
    }

    private long leerUltimoNumero() {
        try {
            if (Files.exists(ARCHIVO)) {
                return Long.parseLong(Files.readString(ARCHIVO).trim());
            }
            return 0;
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer el consecutivo", e);
        }
    }

    private void guardarUltimoNumero() {
        try {
            Files.createDirectories(ARCHIVO.getParent());
            Files.writeString(ARCHIVO, String.valueOf(ultimoNumero));
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo guardar el consecutivo", e);
        }
    }
}
