package co.edu.uniquindio.poo.parcial_uno_programacion.model;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneradorConsecutivoTest {

    @Test
    void siempreEsLaMismaInstancia() {
        assertSame(GeneradorConsecutivo.getInstance(), GeneradorConsecutivo.getInstance());
    }

    @Test
    void numerosConsecutivosSinRepetir() {
        GeneradorConsecutivo generador = GeneradorConsecutivo.getInstance();
        long primero = generador.siguiente();
        long segundo = generador.siguiente();
        assertEquals(primero + 1, segundo);
    }

    @Test
    void noRepiteNumerosConVariosHilos() throws InterruptedException {
        Set<Long> numeros = ConcurrentHashMap.newKeySet();
        ExecutorService hilos = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 200; i++) {
            hilos.submit(() -> numeros.add(GeneradorConsecutivo.getInstance().siguiente()));
        }
        hilos.shutdown();
        assertTrue(hilos.awaitTermination(10, TimeUnit.SECONDS));
        //si algun numero se hubiera repetido, el Set tendria menos de 200 elementos
        assertEquals(200, numeros.size());
    }
}
