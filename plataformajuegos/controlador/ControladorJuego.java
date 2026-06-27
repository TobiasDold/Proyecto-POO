package plataformajuegos.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;

import plataformajuegos.modelo.juegos.*;
import plataformajuegos.modelo.partidas.*;
import plataformajuegos.modelo.usuarios.*;

public class ControladorJuego {
    private Usuario jugador;
    private Juego juego;
    private ControladorPrincipal cp;
    private Partida partida;
    private ControladorFicheros cf;

    public ControladorJuego(Usuario jugador, String nombreJuego, ControladorPrincipal cp, ControladorFicheros cf) {
        this.jugador = jugador;
        this.cp = cp;
        this.cf = cf;

        if (nombreJuego.equals("Ahorcado")) {
            this.juego = new Ahorcado();
            partida = new PartidaAhorcado(jugador, this.juego);
        } else if (nombreJuego.equals("Pasapalabra")) {
            this.juego = new Pasapalabra();
            partida = new PartidaPasapalabra(jugador, this.juego);
        } else {
            return;
        }

        partida.iniciar();
        cp.iniciarPartida(this.juego);
    }

    public void procesarJugada(String input) {
        juego.procesarJugada(input);

        if (juego.esFinalizado()) {
            partida.setPuntuacionFinal(juego.obtenerPuntuacion());
            String username = jugador.getUsername();
            String juegoJugado = juego.getNombreJuego();
            terminarPartida(username, juegoJugado);
        }
    }

    public void pausarPartida() {
        if (partida.getEstado() != EstadoPartida.PAUSADA) {
            partida.pausar();
            String estadoSerializado = partida.serializarEstado();
            cf.guardarEstado(jugador.getUsername(), juego.getNombreJuego(), estadoSerializado);
        }
    }

    public void reanudarPartida(String username, String nombreJuego) {
        String estado = cf.cargarEstado(username, nombreJuego);

        if (estado == null) {
            return;
        }

        if (nombreJuego.equals("Ahorcado")) {
            this.juego = new Ahorcado();
            partida = new PartidaAhorcado(jugador, this.juego);
        } else if (nombreJuego.equals("Pasapalabra")) {
            this.juego = new Pasapalabra();
            partida = new PartidaPasapalabra(jugador, this.juego);
        } else {
            return;
        }

        partida.deserializarEstado(estado);
        partida.setEstado(EstadoPartida.EN_CURSO);
    }

    public void terminarPartida(String username, String juego) {
        int puntuacion = obtenerPuntuacionActual();

        RegistroPartida registro = new RegistroPartida(username, juego, (partida.getFechaInicio()), puntuacion);

        cf.registrarPartida(registro);

        cf.eliminarGuardada(username, juego);

        partida.setEstado(EstadoPartida.TERMINADA);
    }

    public int obtenerPuntuacionActual() {
        return partida.getPuntuacionFinal();
    }

    public String obtenerEstadoJuego() {
        return partida.obtenerEstadoVisual(); // Solo desde Partida
    }
}
