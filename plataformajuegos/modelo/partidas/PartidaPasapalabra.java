package plataformajuegos.modelo.partidas;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import plataformajuegos.modelo.juegos.*;
import plataformajuegos.modelo.usuarios.*;

public class PartidaPasapalabra extends Partida{

    public PartidaPasapalabra(Usuario jugador, Juego juego) {
        super(jugador, juego);
    }

    public void iniciar(){}
    public void pausar(){}
    public String serializarEstado(){return null;}
    public void deserializarEstado(String estado){}

    @Override
    public String obtenerEstadoVisual() {
        return null;
    }
}
