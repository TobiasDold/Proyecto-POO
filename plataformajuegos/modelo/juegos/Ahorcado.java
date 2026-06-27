package plataformajuegos.modelo.juegos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import plataformajuegos.modelo.partidas.Partida;
import plataformajuegos.modelo.usuarios.Usuario;

public class Ahorcado extends Juego {
    private String palabraSecreta;
    private StringBuilder palabraActual;
    private Set<Character> letrasUsadas;
    private Set<Character> letrasCorrectas;
    private int intentosRestantes;
    private int puntuacion;
    private static final int INTENTOS_INICIALES = 6;
    private List<String> palabras;

    public Ahorcado() {
        this.intentosRestantes = INTENTOS_INICIALES;
        this.letrasUsadas = new HashSet<>();
        this.letrasCorrectas = new HashSet<>();
        this.puntuacion = 0;
        cargarPalabras();
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

    public String getPalabraActual() {
        return palabraActual.toString();
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public Set<Character> getLetrasUsadas() {
        return new HashSet<>(letrasUsadas);
    }

    public boolean hasGanado() {
        return palabraActual.toString().equals(palabraSecreta);
    }

    private void actualizarPalabraActual(char letra) {
    }

    private void seleccionarPalabraAleatoria() {
    }

    private void cargarPalabras() {
    }

}
