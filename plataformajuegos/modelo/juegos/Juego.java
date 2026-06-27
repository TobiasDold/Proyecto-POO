package plataformajuegos.modelo.juegos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import plataformajuegos.modelo.partidas.*;
import plataformajuegos.modelo.usuarios.*;

public abstract class Juego {
    private String id;
    private String nombreJuego;
    private String descripcion;
    private int numJugadoresMinimo;
    private int numJugadoresMaximo;

    public abstract void procesarJugada(String input);

    public abstract boolean esFinalizado();

    public abstract int obtenerPuntuacion();

    public String getNombreJuego() {
        return this.getClass().getSimpleName();
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getNumJugadoresMax() {
        return numJugadoresMaximo;
    }

    public int getNumJugadoresMin() {
        return numJugadoresMinimo;
    }

}
