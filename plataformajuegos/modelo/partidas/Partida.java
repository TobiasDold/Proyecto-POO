package plataformajuegos.modelo.partidas;

import java.time.LocalDateTime;
import java.util.List;

import plataformajuegos.modelo.juegos.*;
import plataformajuegos.modelo.usuarios.*;

public abstract class Partida {
    protected Juego juego;
    protected Usuario jugador;
    protected EstadoPartida estado;
    protected LocalDateTime fechaInicio;
    protected int puntuacionFinal;

    public Partida(Usuario jugador, Juego juego) {
        this.jugador = jugador;
        this.juego = juego;
        this.estado = EstadoPartida.EN_CURSO;
        this.fechaInicio = LocalDateTime.now();
        this.puntuacionFinal = 0;
    }

    public abstract void iniciar();

    public abstract void pausar();

    public abstract String serializarEstado();

    public abstract void deserializarEstado(String estado);

    public abstract String obtenerEstadoVisual();

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartida estado) {
        this.estado = estado;
    }

    public int getPuntuacionFinal() {
        return puntuacionFinal;
    }

    public void setPuntuacionFinal(int puntuacion) {
        this.puntuacionFinal = puntuacion;
    }

}
