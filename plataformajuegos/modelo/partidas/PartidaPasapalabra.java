package plataformajuegos.modelo.partidas;

import java.util.List;

import plataformajuegos.modelo.juegos.Juego;
import plataformajuegos.modelo.usuarios.Usuario;

public class PartidaPasapalabra extends Partida {
    public PartidaPasapalabra(Usuario jugador, Juego juego) {
        super(jugador, juego);
    }

    public PartidaPasapalabra(List<Usuario> jugadores, Juego juego) {
        super(jugadores, juego);
    }
}
