package plataformajuegos.modelo.juegos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import plataformajuegos.modelo.partidas.Partida;
import plataformajuegos.modelo.usuarios.Usuario;

public class Pasapalabra extends Juego {
    private List<Pregunta> preguntas;
    private int indiceActual;
    private Set<Integer> respuestasCorrectas;
    private int puntuacion;
    private Map<Integer, String> respuestasUsuario;

    public Pasapalabra() {
        this.respuestasCorrectas = new HashSet<>();
        this.respuestasUsuario = new HashMap<>();
        this.puntuacion = 0;
        this.indiceActual = 0;
        cargarPreguntas();
    }

    public String getNombreJuego() {
        return "Pasapalabra";
    }

    public void iniciar() {
    }

    public void procesarJugada(String input) {
    }

    public boolean esFinalizado() {
        return false;
    }

    public int obtenerPuntuacion() {
        return 0;
    }

    public Pregunta getPreguntaActual() {
        return null;
    }

    public int getRespuestasCorrectas() {
        return respuestasCorrectas.size();
    }

    public int getTotalPreguntas() {
        return preguntas.size();
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public void avanzarPregunta() {
        indiceActual++;
    }

    private void cargarPreguntas() {
    }

}
