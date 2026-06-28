package plataformajuegos.modelo.partidas;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import plataformajuegos.modelo.Reanudable;
import plataformajuegos.modelo.juegos.Juego;
import plataformajuegos.modelo.usuarios.*;

public abstract class Partida implements Reanudable {
    protected Juego juego;
    protected List<Usuario> jugadores;
    protected Map<String, Integer> puntuaciones;
    protected EstadoPartida estado;
    protected LocalDateTime fechaInicio;
    protected int turnoActual;

    public Partida(Usuario jugador, Juego juego) {
        this(Collections.singletonList(jugador), juego);
    }

    public Partida(List<Usuario> jugadores, Juego juego) {
        if (jugadores == null || jugadores.isEmpty()) {
            throw new IllegalArgumentException("La partida necesita al menos un jugador.");
        }
        if (juego == null) {
            throw new IllegalArgumentException("La partida necesita un juego.");
        }
        this.juego = juego;
        this.jugadores = new ArrayList<>(jugadores);
        this.puntuaciones = new LinkedHashMap<>();
        for (Usuario jugador : jugadores) {
            this.puntuaciones.put(jugador.getUsername(), 0);
        }
        estado = EstadoPartida.EN_CURSO;
        fechaInicio = LocalDateTime.now();
        turnoActual = 0;
    }

    public void iniciar() {
        juego.iniciar();
        estado = EstadoPartida.EN_CURSO;
    }

    public void pausar() {
        if (estado == EstadoPartida.EN_CURSO) {
            estado = EstadoPartida.PAUSADA;
        }
    }

    public void reanudar() {
        if (estado == EstadoPartida.PAUSADA) {
            estado = EstadoPartida.EN_CURSO;
        }
    }

    public void registrarPuntuacionTurno(int puntos) {
        String username = getJugadorActual().getUsername();
        puntuaciones.put(username, getPuntuacionDe(username) + puntos);
    }

    public void avanzarTurno() {
        if (jugadores.size() > 1 && !juego.esFinalizado()) {
            turnoActual = (turnoActual + 1) % jugadores.size();
        }
    }

    public Usuario getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public List<Usuario> getJugadores() {
        return Collections.unmodifiableList(jugadores);
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public Map<String, Integer> getPuntuaciones() {
        return Collections.unmodifiableMap(puntuaciones);
    }

    public int getPuntuacionDe(String username) {
        Integer puntuacion = puntuaciones.get(username);
        return puntuacion == null ? 0 : puntuacion.intValue();
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartida estado) {
        this.estado = estado;
    }

    public Juego getJuego() {
        return juego;
    }

    public int getPuntuacionFinal() {
        int total = 0;
        for (Integer puntos : puntuaciones.values()) {
            total += puntos.intValue();
        }
        return total;
    }

    public void setPuntuacionFinal(int puntuacion) {
        puntuaciones.put(getJugadorActual().getUsername(), puntuacion);
    }

    @Override
    public String serializarEstado() {
        StringBuilder jugadoresSerializados = new StringBuilder();
        for (Usuario jugador : jugadores) {
            if (jugadoresSerializados.length() > 0) {
                jugadoresSerializados.append(',');
            }
            jugadoresSerializados.append(jugador.getUsername()).append(':')
                    .append(getPuntuacionDe(jugador.getUsername()));
        }
        String estadoJuego = Base64.getEncoder().encodeToString(
                juego.serializarEstado().getBytes(StandardCharsets.UTF_8));
        return fechaInicio + "|" + turnoActual + "|" + estado.name() + "|"
                + jugadoresSerializados + "|" + estadoJuego;
    }

    @Override
    public void deserializarEstado(String estadoSerializado) {
        String[] partes = estadoSerializado.split("\\|", 5);
        if (partes.length != 5) {
            throw new IllegalArgumentException("El estado de la partida no es valido.");
        }

        fechaInicio = LocalDateTime.parse(partes[0]);
        turnoActual = Integer.parseInt(partes[1]);
        estado = EstadoPartida.valueOf(partes[2]);
        jugadores.clear();
        puntuaciones.clear();

        for (String jugadorSerializado : partes[3].split(",")) {
            String[] datos = jugadorSerializado.split(":", 2);
            if (datos.length == 2) {
                jugadores.add(new Usuario(datos[0], ""));
                puntuaciones.put(datos[0], Integer.parseInt(datos[1]));
            }
        }
        if (jugadores.isEmpty()) {
            throw new IllegalArgumentException("El estado no contiene jugadores.");
        }
        if (turnoActual < 0 || turnoActual >= jugadores.size()) {
            turnoActual = 0;
        }

        String estadoJuego = new String(Base64.getDecoder().decode(partes[4]),
                StandardCharsets.UTF_8);
        juego.deserializarEstado(estadoJuego);
    }

    public String obtenerEstadoVisual() {
        return "Turno: " + getJugadorActual().getUsername() + " | "
                + juego.obtenerEstadoVisual();
    }
}
