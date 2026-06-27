package plataformajuegos.modelo.usuarios;

import java.time.LocalDateTime;

public class RegistroPartida {
    String nombreJugador;
    String nombreJuego;
    LocalDateTime fecha;
    int puntuacion;

    public RegistroPartida(String nombreJugador, String nombreJuego, LocalDateTime fecha, int puntuacion) {
        this.nombreJugador = nombreJugador;
        this.nombreJuego = nombreJuego;
        this.fecha = fecha;
        this.puntuacion = puntuacion;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    @Override
    public String toString() {
        return nombreJugador + "|" + nombreJuego + "|" + fecha + "|" + puntuacion;
    }
}
