package plataformajuegos.modelo.juegos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import plataformajuegos.util.Normalizador;

public class Pasapalabra extends Juego {
    private static final Path RUTA_PREGUNTAS =
            Paths.get("plataformajuegos", "datos", "preguntas_pasapalabra.txt");
    private static final Path RUTA_PREGUNTAS_ANTIGUA =
            Paths.get("plataformajuegos", "datos", "preguntas_pasaplabras.txt");

    private final List<Pregunta> preguntas;
    private int indiceActual;
    private final Set<Integer> respuestasCorrectas;
    private int puntuacion;
    private final Map<Integer, String> respuestasUsuario;

    public Pasapalabra() {
        super("PASAPALABRA", "Pasapalabra",
                "Completa el rosco respondiendo preguntas de la A a la Z.", 1, 2);
        preguntas = new ArrayList<>();
        respuestasCorrectas = new HashSet<>();
        respuestasUsuario = new HashMap<>();
        cargarPreguntas();
        iniciar();
    }

    @Override
    public void iniciar() {
        indiceActual = 0;
        puntuacion = 0;
        respuestasCorrectas.clear();
        respuestasUsuario.clear();
        for (int i = 0; i < preguntas.size(); i++) {
            respuestasUsuario.put(i, "P");
        }
    }

    @Override
    public void procesarJugada(String input) {
        if (esFinalizado() || preguntas.isEmpty()) {
            return;
        }

        String respuesta = input == null ? "" : input.trim();
        if (respuesta.isEmpty() || "PASAPALABRA".equals(Normalizador.normalizar(respuesta))) {
            avanzarPregunta();
            return;
        }

        Pregunta pregunta = getPreguntaActual();
        if (pregunta.verificarRespuesta(respuesta)) {
            respuestasUsuario.put(indiceActual, "C");
            respuestasCorrectas.add(indiceActual);
            puntuacion += 10;
        } else {
            respuestasUsuario.put(indiceActual, "F");
            puntuacion -= 2;
        }
        avanzarPregunta();
    }

    @Override
    public boolean esFinalizado() {
        if (preguntas.isEmpty()) {
            return true;
        }
        for (int i = 0; i < preguntas.size(); i++) {
            if ("P".equals(respuestasUsuario.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int obtenerPuntuacion() {
        return puntuacion;
    }

    public Pregunta getPreguntaActual() {
        if (indiceActual < 0 || indiceActual >= preguntas.size() || esFinalizado()) {
            return null;
        }
        return preguntas.get(indiceActual);
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

    public String getEstadoPregunta(int indice) {
        String estado = respuestasUsuario.get(indice);
        return estado == null ? "P" : estado;
    }

    public List<Pregunta> getPreguntas() {
        return new ArrayList<>(preguntas);
    }

    public void avanzarPregunta() {
        if (preguntas.isEmpty() || esFinalizado()) {
            indiceActual = preguntas.size();
            return;
        }

        for (int desplazamiento = 1; desplazamiento <= preguntas.size(); desplazamiento++) {
            int candidato = (indiceActual + desplazamiento) % preguntas.size();
            if ("P".equals(respuestasUsuario.get(candidato))) {
                indiceActual = candidato;
                return;
            }
        }
    }

    private void cargarPreguntas() {
        preguntas.clear();
        Path ruta = Files.exists(RUTA_PREGUNTAS) ? RUTA_PREGUNTAS : RUTA_PREGUNTAS_ANTIGUA;

        if (Files.exists(ruta)) {
            try {
                for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
                    if (linea.trim().isEmpty()) {
                        continue;
                    }
                    String[] partes = linea.split("\\|", 4);
                    if (partes.length == 4) {
                        preguntas.add(new Pregunta(partes[0], partes[1], partes[2], partes[3]));
                    }
                }
            } catch (IOException e) {
                System.err.println("No se pudieron cargar las preguntas: " + e.getMessage());
            }
        }

        if (preguntas.isEmpty()) {
            preguntas.add(new Pregunta("A", "EMPIEZA",
                    "Lenguaje de programacion de Android.", "JAVA"));
            preguntas.add(new Pregunta("C", "EMPIEZA",
                    "Molde utilizado para crear objetos.", "CLASE"));
            preguntas.add(new Pregunta("H", "EMPIEZA",
                    "Relacion entre una clase padre y una hija.", "HERENCIA"));
        }
    }

    @Override
    public String serializarEstado() {
        StringBuilder estados = new StringBuilder();
        for (int i = 0; i < preguntas.size(); i++) {
            if (i > 0) {
                estados.append(',');
            }
            estados.append(getEstadoPregunta(i));
        }
        return indiceActual + ";" + puntuacion + ";" + estados;
    }

    @Override
    public void deserializarEstado(String estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado de Pasapalabra no puede ser null.");
        }
        String[] partes = estado.split(";", -1);
        if (partes.length != 3) {
            throw new IllegalArgumentException("El estado de Pasapalabra no es valido.");
        }

        indiceActual = Integer.parseInt(partes[0]);
        puntuacion = Integer.parseInt(partes[1]);
        respuestasCorrectas.clear();
        respuestasUsuario.clear();

        String[] estados = partes[2].split(",", -1);
        for (int i = 0; i < preguntas.size(); i++) {
            String valor = i < estados.length ? estados[i] : "P";
            respuestasUsuario.put(i, valor);
            if ("C".equals(valor)) {
                respuestasCorrectas.add(i);
            }
        }

        if (!esFinalizado() && (indiceActual < 0 || indiceActual >= preguntas.size()
                || !"P".equals(respuestasUsuario.get(indiceActual)))) {
            indiceActual = 0;
            if (!"P".equals(respuestasUsuario.get(indiceActual))) {
                avanzarPregunta();
            }
        }
    }

    @Override
    public String obtenerEstadoVisual() {
        Pregunta actual = getPreguntaActual();
        if (actual == null) {
            return "Rosco completado | Aciertos: " + getRespuestasCorrectas()
                    + " | Puntos: " + puntuacion;
        }
        return "Letra " + actual.getLetra() + " | " + actual.getDefinicion()
                + " | Aciertos: " + getRespuestasCorrectas()
                + "/" + getTotalPreguntas() + " | Puntos: " + puntuacion;
    }
}
