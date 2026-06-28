package plataformajuegos.controlador;

import java.util.*;

import plataformajuegos.modelo.juegos.*;
import plataformajuegos.modelo.partidas.*;
import plataformajuegos.modelo.usuarios.*;

public class ControladorJuego {
    private final List<Usuario> jugadores;
    private Juego juego;
    private final ControladorPrincipal controladorPrincipal;
    private Partida partida;
    private final ControladorFicheros controladorFicheros;
    private boolean resultadoRegistrado;

    public ControladorJuego(Usuario jugador, String nombreJuego,
            ControladorPrincipal controladorPrincipal,
            ControladorFicheros controladorFicheros) {
        this(Collections.singletonList(jugador), nombreJuego,
                controladorPrincipal, controladorFicheros);
    }

    public ControladorJuego(List<Usuario> jugadores, String nombreJuego,
            ControladorPrincipal controladorPrincipal,
            ControladorFicheros controladorFicheros) {
        if (jugadores == null || jugadores.isEmpty()) {
            throw new IllegalArgumentException("Se necesita al menos un jugador.");
        }
        this.jugadores = new ArrayList<>(jugadores);
        this.controladorPrincipal = controladorPrincipal;
        this.controladorFicheros = controladorFicheros == null
                ? new ControladorFicheros()
                : controladorFicheros;
        this.juego = crearJuego(nombreJuego);
        this.partida = crearPartida(this.jugadores, juego);
        this.partida.iniciar();
        this.resultadoRegistrado = false;
    }

    public void procesarJugada(String input) {
        if (partida.getEstado() != EstadoPartida.EN_CURSO || juego.esFinalizado()) {
            return;
        }

        int puntuacionAnterior = juego.obtenerPuntuacion();
        juego.procesarJugada(input);
        int diferencia = juego.obtenerPuntuacion() - puntuacionAnterior;
        partida.registrarPuntuacionTurno(diferencia);

        if (juego.esFinalizado()) {
            terminarPartida();
        } else {
            partida.avanzarTurno();
        }
    }

    public void pausarPartida() {
        if (partida.getEstado() == EstadoPartida.EN_CURSO) {
            partida.pausar();
            controladorFicheros.guardarEstado(getJugadorPrincipal().getUsername(),
                    juego.getNombreJuego(), partida.serializarEstado());
        }
    }

    public boolean reanudarPartida() {
        return reanudarPartida(getJugadorPrincipal().getUsername(),
                juego.getNombreJuego());
    }

    public boolean reanudarPartida(String username, String nombreJuego) {
        String estadoGuardado = controladorFicheros.cargarEstado(username, nombreJuego);
        if (estadoGuardado == null) {
            return false;
        }

        juego = crearJuego(nombreJuego);
        partida = crearPartida(jugadores, juego);
        try {
            partida.deserializarEstado(estadoGuardado);
            partida.reanudar();
            resultadoRegistrado = false;
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void terminarPartida() {
        if (resultadoRegistrado) {
            return;
        }
        for (Usuario jugador : partida.getJugadores()) {
            RegistroPartida registro = new RegistroPartida(
                    jugador.getUsername(),
                    juego.getNombreJuego(),
                    partida.getFechaInicio(),
                    partida.getPuntuacionDe(jugador.getUsername()));
            controladorFicheros.registrarPartida(registro);
        }
        controladorFicheros.eliminarGuardada(getJugadorPrincipal().getUsername(),
                juego.getNombreJuego());
        partida.setEstado(EstadoPartida.TERMINADA);
        resultadoRegistrado = true;
    }

    public int obtenerPuntuacionActual() {
        return partida.getPuntuacionDe(partida.getJugadorActual().getUsername());
    }

    public String obtenerEstadoJuego() {
        return partida.obtenerEstadoVisual();
    }

    public Usuario getJugadorPrincipal() {
        return jugadores.get(0);
    }

    public Juego getJuego() {
        return juego;
    }

    public Partida getPartida() {
        return partida;
    }

    public ControladorPrincipal getControladorPrincipal() {
        return controladorPrincipal;
    }

    private Juego crearJuego(String nombreJuego) {
        if ("Ahorcado".equalsIgnoreCase(nombreJuego)) {
            return new Ahorcado();
        }
        if ("Pasapalabra".equalsIgnoreCase(nombreJuego)) {
            return new Pasapalabra();
        }
        throw new IllegalArgumentException("Juego no soportado: " + nombreJuego);
    }

    private Partida crearPartida(List<Usuario> jugadoresPartida, Juego juegoPartida) {
        if (juegoPartida instanceof Ahorcado) {
            return new PartidaAhorcado(jugadoresPartida, juegoPartida);
        }
        return new PartidaPasapalabra(jugadoresPartida, juegoPartida);
    }
}
