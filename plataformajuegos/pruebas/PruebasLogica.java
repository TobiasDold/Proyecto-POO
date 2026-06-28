package plataformajuegos.pruebas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingUtilities;

import plataformajuegos.controlador.ControladorFicheros;
import plataformajuegos.controlador.ControladorJuego;
import plataformajuegos.modelo.juegos.Ahorcado;
import plataformajuegos.modelo.juegos.Pasapalabra;
import plataformajuegos.modelo.usuarios.Jugador;
import plataformajuegos.modelo.usuarios.RegistroPartida;
import plataformajuegos.modelo.usuarios.Usuario;
import plataformajuegos.util.Normalizador;
import plataformajuegos.vista.PanelAdmin;
import plataformajuegos.vista.PanelAhorcado;
import plataformajuegos.vista.PanelEstadisticas;
import plataformajuegos.vista.PanelMenu;
import plataformajuegos.vista.PanelPasapalabra;

public class PruebasLogica {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "true");
        probarNormalizador();
        probarAhorcado();
        probarPasapalabra();
        probarMultijugadorYReanudacion();
        probarPersistenciaReal();
        probarConstruccionPaneles();
        System.out.println("Todas las pruebas de logica han pasado.");
        System.exit(0);
    }

    private static void probarNormalizador() {
        comprobar("PROGRAMACION".equals(Normalizador.normalizar(" programación ")),
                "El normalizador debe ignorar espacios, acentos y mayusculas.");
    }

    private static void probarAhorcado() {
        Ahorcado ahorcado = new Ahorcado();
        ahorcado.deserializarEstado("JAVA;____;;;6;0");
        ahorcado.procesarJugada("j");
        comprobar(ahorcado.getPalabraActual().startsWith("J"),
                "Ahorcado debe descubrir letras correctas.");
        comprobar(ahorcado.obtenerPuntuacion() == 10,
                "Una letra correcta debe sumar puntos.");

        ahorcado.procesarJugada("x");
        comprobar(ahorcado.getIntentosRestantes() == 5,
                "Una letra incorrecta debe restar un intento.");

        Ahorcado restaurado = new Ahorcado();
        restaurado.deserializarEstado(ahorcado.serializarEstado());
        comprobar(restaurado.getPalabraActual().equals(ahorcado.getPalabraActual()),
                "El estado restaurado de Ahorcado debe conservar la palabra visible.");
        comprobar(restaurado.getIntentosRestantes() == 5,
                "El estado restaurado de Ahorcado debe conservar los intentos.");
    }

    private static void probarPasapalabra() {
        Pasapalabra pasapalabra = new Pasapalabra();
        comprobar(pasapalabra.getTotalPreguntas() >= 20,
                "Pasapalabra debe cargar las preguntas del fichero.");
        comprobar("A".equals(pasapalabra.getPreguntaActual().getLetra()),
                "El rosco debe empezar por la primera pregunta.");

        pasapalabra.procesarJugada("volar");
        comprobar(pasapalabra.getRespuestasCorrectas() == 1,
                "Pasapalabra debe reconocer una respuesta correcta.");
        comprobar(pasapalabra.obtenerPuntuacion() == 10,
                "Una respuesta correcta debe sumar puntos.");

        Pasapalabra restaurado = new Pasapalabra();
        restaurado.deserializarEstado(pasapalabra.serializarEstado());
        comprobar(restaurado.getRespuestasCorrectas() == 1,
                "La reanudacion debe conservar las respuestas correctas.");
    }

    private static void probarMultijugadorYReanudacion() {
        Usuario ana = new Jugador("Ana", "1234");
        Usuario bruno = new Jugador("Bruno", "1234");
        List<Usuario> jugadores = Arrays.asList(ana, bruno);
        FicherosMemoria ficheros = new FicherosMemoria();

        ControladorJuego controlador = new ControladorJuego(
                jugadores, "Ahorcado", null, ficheros);
        ((Ahorcado) controlador.getJuego())
                .deserializarEstado("JAVA;____;;;6;0");

        controlador.procesarJugada("j");
        comprobar(controlador.getPartida().getPuntuacionDe("Ana") == 10,
                "La puntuacion debe asignarse al jugador de su turno.");
        comprobar("Bruno".equals(
                controlador.getPartida().getJugadorActual().getUsername()),
                "Tras una jugada debe avanzar el turno.");

        controlador.procesarJugada("x");
        comprobar(controlador.getPartida().getPuntuacionDe("Bruno") == -2,
                "El segundo jugador debe tener su propia puntuacion.");
        controlador.pausarPartida();
        comprobar(ficheros.estado != null,
                "Pausar debe guardar el estado de la partida.");

        ControladorJuego reanudado = new ControladorJuego(
                jugadores, "Ahorcado", null, ficheros);
        comprobar(reanudado.reanudarPartida(),
                "Debe poder reanudarse una partida guardada.");
        comprobar(reanudado.getPartida().getPuntuacionDe("Ana") == 10,
                "La reanudacion debe conservar las puntuaciones.");
        comprobar("Ana".equals(
                reanudado.getPartida().getJugadorActual().getUsername()),
                "La reanudacion debe conservar el turno.");

        reanudado.procesarJugada("JAVA");
        comprobar(ficheros.registros.size() == 2,
                "Una partida multijugador debe registrar un resultado por usuario.");
    }

    private static void probarConstruccionPaneles() throws Exception {
        final FicherosMemoria ficheros = new FicherosMemoria();
        final Usuario jugador = new Jugador("Prueba", "1234");
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ControladorJuego ahorcado = new ControladorJuego(
                        jugador, "Ahorcado", null, ficheros);
                ControladorJuego pasapalabra = new ControladorJuego(
                        jugador, "Pasapalabra", null, ficheros);
                comprobar(new PanelAhorcado(null, ahorcado).getComponentCount() > 0,
                        "PanelAhorcado debe construirse con contenido.");
                comprobar(new PanelPasapalabra(null, pasapalabra).getComponentCount() > 0,
                        "PanelPasapalabra debe construirse con contenido.");
                comprobar(new PanelMenu(null).getComponentCount() > 0,
                        "PanelMenu debe construirse con contenido.");
                comprobar(new PanelEstadisticas(null,
                        new ArrayList<RegistroPartida>()).getComponentCount() > 0,
                        "PanelEstadisticas debe construirse con contenido.");
                comprobar(new PanelAdmin(null, ficheros).getComponentCount() > 0,
                        "PanelAdmin debe construirse con contenido.");
            }
        });
    }

    private static void probarPersistenciaReal() {
        ControladorFicheros ficheros = new ControladorFicheros();
        String usuario = "__PRUEBA_AUTOMATICA__";
        String estado = "estado|con;separadores y e\u00f1e";
        ficheros.guardarEstado(usuario, "Ahorcado", estado);
        comprobar(estado.equals(ficheros.cargarEstado(usuario, "Ahorcado")),
                "La persistencia debe conservar el estado completo.");
        ficheros.eliminarGuardada(usuario, "Ahorcado");
        comprobar(ficheros.cargarEstado(usuario, "Ahorcado") == null,
                "Eliminar una partida guardada debe retirarla del fichero.");
    }

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static class FicherosMemoria extends ControladorFicheros {
        private String estado;
        private final List<RegistroPartida> registros = new ArrayList<>();

        @Override
        public void guardarEstado(String username, String juego, String estado) {
            this.estado = estado;
        }

        @Override
        public String cargarEstado(String username, String juego) {
            return estado;
        }

        @Override
        public void eliminarGuardada(String username, String juego) {
            estado = null;
        }

        @Override
        public void registrarPartida(RegistroPartida partida) {
            registros.add(partida);
        }
    }
}
