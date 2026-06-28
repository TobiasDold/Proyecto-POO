package plataformajuegos.modelo.partidas;

import java.util.List;

import plataformajuegos.modelo.juegos.Juego;
import plataformajuegos.modelo.usuarios.Usuario;

public class PartidaAhorcado extends Partida {
    public PartidaAhorcado(Usuario jugador, Juego juego) {
        super(jugador, juego);
    }

    public PartidaAhorcado(List<Usuario> jugadores, Juego juego) {
        super(jugadores, juego);
    }
}
