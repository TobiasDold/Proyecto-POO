package plataformajuegos.modelo.juegos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

import plataformajuegos.util.Normalizador;

public class Ahorcado extends Juego {
    private static final int INTENTOS_INICIALES = 6;
    private static final Path RUTA_PALABRAS = Paths.get("plataformajuegos", "datos", "palabras_ahorcado.txt");

    private String palabraSecreta;
    private StringBuilder palabraActual;
    private Set<Character> letrasUsadas;
    private Set<Character> letrasCorrectas;
    private int intentosRestantes;
    private int puntuacion;
    private final List<String> palabras;
    private final Random random;

    public Ahorcado() {
        super("AHORCADO", "Ahorcado",
                "Adivina la palabra antes de agotar los intentos.", 1, 2);
        letrasUsadas = new HashSet<>();
        letrasCorrectas = new HashSet<>();
        palabras = new ArrayList<>();
        random = new Random();
        cargarPalabras();
        iniciar();
    }

    @Override
    public void iniciar() {
        intentosRestantes = INTENTOS_INICIALES;
        puntuacion = 0;
        letrasUsadas.clear();
        letrasCorrectas.clear();
        seleccionarPalabraAleatoria();

        palabraActual = new StringBuilder();
        for (int i = 0; i < palabraSecreta.length(); i++) {
            char caracter = palabraSecreta.charAt(i);
            palabraActual.append(Character.isLetter(caracter) ? '_' : caracter);
        }
    }

    @Override
    public void procesarJugada(String input) {
        if (esFinalizado() || input == null) {
            return;
        }

        String jugada = Normalizador.normalizar(input);
        if (jugada.isEmpty()) {
            return;
        }

        if (jugada.length() > 1) {
            if (jugada.equals(palabraSecreta)) {
                for (int i = 0; i < palabraSecreta.length(); i++) {
                    palabraActual.setCharAt(i, palabraSecreta.charAt(i));
                }
                puntuacion += intentosRestantes * 10;
            } else {
                intentosRestantes--;
                puntuacion -= 2;
            }
            return;
        }

        char letra = jugada.charAt(0);
        if (!Character.isLetter(letra) || !letrasUsadas.add(letra)) {
            return;
        }

        if (palabraSecreta.indexOf(letra) >= 0) {
            letrasCorrectas.add(letra);
            puntuacion += actualizarPalabraActual(letra) * 10;
        } else {
            intentosRestantes--;
            puntuacion -= 2;
        }
    }

    @Override
    public boolean esFinalizado() {
        return hasGanado() || intentosRestantes <= 0;
    }

    @Override
    public int obtenerPuntuacion() {
        return puntuacion;
    }

    public String getPalabraActual() {
        if (palabraActual == null) {
            return "";
        }
        StringBuilder visible = new StringBuilder();
        for (int i = 0; i < palabraActual.length(); i++) {
            if (i > 0) {
                visible.append(' ');
            }
            visible.append(palabraActual.charAt(i));
        }
        return visible.toString();
    }

    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    public Set<Character> getLetrasUsadas() {
        return new HashSet<>(letrasUsadas);
    }

    public boolean hasGanado() {
        return palabraActual != null && palabraSecreta != null
                && palabraActual.toString().equals(palabraSecreta);
    }

    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    private int actualizarPalabraActual(char letra) {
        int aciertos = 0;
        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra && palabraActual.charAt(i) == '_') {
                palabraActual.setCharAt(i, letra);
                aciertos++;
            }
        }
        return aciertos;
    }

    private void seleccionarPalabraAleatoria() {
        if (palabras.isEmpty()) {
            throw new IllegalStateException("No hay palabras disponibles para jugar.");
        }
        palabraSecreta = palabras.get(random.nextInt(palabras.size()));
    }

    private void cargarPalabras() {
        palabras.clear();
        if (Files.exists(RUTA_PALABRAS)) {
            try {
                for (String linea : Files.readAllLines(RUTA_PALABRAS, StandardCharsets.UTF_8)) {
                    String palabra = Normalizador.normalizar(linea);
                    if (!palabra.isEmpty()) {
                        palabras.add(palabra);
                    }
                }
            } catch (IOException e) {
                System.err.println("No se pudieron cargar las palabras: " + e.getMessage());
            }
        }

        if (palabras.isEmpty()) {
            Collections.addAll(palabras, "PROGRAMACION", "HERENCIA", "POLIMORFISMO",
                    "CLASE", "OBJETO", "INTERFAZ", "VARIABLE", "COMPILADOR");
        }
    }

    @Override
    public String serializarEstado() {
        return palabraSecreta + ";" + palabraActual + ";"
                + caracteresAString(letrasUsadas) + ";"
                + caracteresAString(letrasCorrectas) + ";"
                + intentosRestantes + ";" + puntuacion;
    }

    @Override
    public void deserializarEstado(String estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado de Ahorcado no puede ser null.");
        }
        String[] partes = estado.split(";", -1);
        if (partes.length != 6) {
            throw new IllegalArgumentException("El estado de Ahorcado no es valido.");
        }

        palabraSecreta = partes[0];
        palabraActual = new StringBuilder(partes[1]);
        letrasUsadas = stringACaracteres(partes[2]);
        letrasCorrectas = stringACaracteres(partes[3]);
        intentosRestantes = Integer.parseInt(partes[4]);
        puntuacion = Integer.parseInt(partes[5]);
    }

    @Override
    public String obtenerEstadoVisual() {
        return getPalabraActual() + " | Intentos: " + intentosRestantes
                + " | Letras: " + caracteresAString(letrasUsadas)
                + " | Puntos: " + puntuacion;
    }

    private String caracteresAString(Set<Character> caracteres) {
        List<Character> ordenados = new ArrayList<>(caracteres);
        Collections.sort(ordenados);
        StringBuilder resultado = new StringBuilder();
        for (Character caracter : ordenados) {
            resultado.append(caracter.charValue());
        }
        return resultado.toString();
    }

    private Set<Character> stringACaracteres(String texto) {
        Set<Character> resultado = new HashSet<>();
        for (char caracter : texto.toCharArray()) {
            resultado.add(caracter);
        }
        return resultado;
    }
}
