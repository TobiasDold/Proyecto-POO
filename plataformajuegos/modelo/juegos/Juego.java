package plataformajuegos.modelo.juegos;

import plataformajuegos.modelo.Reanudable;

public abstract class Juego implements Reanudable {
    private final String id;
    private final String nombreJuego;
    private final String descripcion;
    private final int numJugadoresMinimo;
    private final int numJugadoresMaximo;

    protected Juego(String id, String nombreJuego, String descripcion,
            int numJugadoresMinimo, int numJugadoresMaximo) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El id del juego es obligatorio.");
        }
        if (nombreJuego == null || nombreJuego.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del juego es obligatorio.");
        }
        if (numJugadoresMinimo < 1 || numJugadoresMaximo < numJugadoresMinimo) {
            throw new IllegalArgumentException("El numero de jugadores no es valido.");
        }

        this.id = id.trim();
        this.nombreJuego = nombreJuego.trim();
        this.descripcion = descripcion == null ? "" : descripcion.trim();
        this.numJugadoresMinimo = numJugadoresMinimo;
        this.numJugadoresMaximo = numJugadoresMaximo;
    }

    public abstract void iniciar();
    public abstract void procesarJugada(String input);
    public abstract boolean esFinalizado();
    public abstract int obtenerPuntuacion();
    public abstract String obtenerEstadoVisual();

    public String getNombreJuego() {
        return nombreJuego;
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

    @Override
    public String toString() {
        return nombreJuego;
    }
}
