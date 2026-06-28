package plataformajuegos.modelo.sistema;

import java.util.List;

import plataformajuegos.controlador.ControladorFicheros;
import plataformajuegos.modelo.partidas.Puntuacion;
import plataformajuegos.modelo.usuarios.Jugador;
import plataformajuegos.modelo.usuarios.RegistroPartida;
import plataformajuegos.modelo.usuarios.Usuario;

public class SistemaJuegos {
    private final ControladorFicheros controladorFicheros;

    public SistemaJuegos() {
        controladorFicheros = new ControladorFicheros();
    }

    public boolean registrar(String username, String password) {
        String nombre = username == null ? "" : username.trim();
        if (nombre.length() < 3 || nombre.contains("|") || nombre.contains(":")
                || nombre.contains(",")) {
            return false;
        }
        if (password == null || password.length() < 4
                || controladorFicheros.existeUsuario(nombre)) {
            return false;
        }
        controladorFicheros.guardarUsuarios(new Jugador(nombre, password));
        return true;
    }

    public Usuario login(String username, String password) {
        return controladorFicheros.validarCredenciales(
                username == null ? "" : username.trim(), password);
    }

    public List<RegistroPartida> obtenerEstadisticasJugador(String username) {
        return controladorFicheros.cargarPartidasDe(username);
    }

    public List<Puntuacion> obtenerEstadisticasJuego(String nombreJuego) {
        return controladorFicheros.cargarRanking(nombreJuego);
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return controladorFicheros.cargarUsuarios();
    }

    public ControladorFicheros getControladorFicheros() {
        return controladorFicheros;
    }
}
