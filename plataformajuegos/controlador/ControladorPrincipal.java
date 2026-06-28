package plataformajuegos.controlador;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import plataformajuegos.modelo.juegos.Ahorcado;
import plataformajuegos.modelo.juegos.Juego;
import plataformajuegos.modelo.juegos.Pasapalabra;
import plataformajuegos.modelo.sistema.SistemaJuegos;
import plataformajuegos.modelo.usuarios.Administrador;
import plataformajuegos.modelo.usuarios.Jugador;
import plataformajuegos.modelo.usuarios.Usuario;
import plataformajuegos.vista.PanelAdmin;
import plataformajuegos.vista.PanelAhorcado;
import plataformajuegos.vista.PanelEstadisticas;
import plataformajuegos.vista.PanelMenu;
import plataformajuegos.vista.PanelPasapalabra;
import plataformajuegos.vista.VentanaPrincipal;

public class ControladorPrincipal {
    private final SistemaJuegos sistemaJuegos = new SistemaJuegos();
    private final ControladorFicheros controladorFicheros =
            sistemaJuegos.getControladorFicheros();
    private Usuario usuarioActual;
    private VentanaPrincipal ventana;
    private PanelMenu panelMenu;
    private ControladorJuego controladorJuegoActual;

    public void iniciar() {
        ventana = new VentanaPrincipal(this);
        ventana.setVisible(true);
    }

    public boolean login(String username, String password) {
        Usuario usuario = sistemaJuegos.login(username, password);
        if (usuario == null) {
            return false;
        }

        usuarioActual = usuario;
        if (panelMenu == null) {
            panelMenu = new PanelMenu(this);
            ventana.agregarPanel(panelMenu, "MENU");
        }
        panelMenu.mostrarVistaSegunRol();
        ventana.mostrarPanel("MENU");
        return true;
    }

    public String registrar(String username, String regPass1, String regPass2) {
        if (!regPass1.equals(regPass2)) {
            return "No coinciden";
        }
        if (!sistemaJuegos.registrar(username, regPass1)) {
            return "Usuario existente";
        }
        return "Usuario creado";
    }

    public void seleccionarJuego(String nombreJuego) {
        if (usuarioActual == null) {
            mostrarLogin();
            return;
        }

        boolean reanudar = false;
        if (controladorFicheros.tieneEstadoGuardado(
                usuarioActual.getUsername(), nombreJuego)) {
            Object[] opciones = {"Continuar", "Nueva partida", "Cancelar"};
            int opcion = JOptionPane.showOptionDialog(ventana,
                    "Hay una partida guardada de " + nombreJuego + ".",
                    "Partida guardada", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (opcion == 2 || opcion == JOptionPane.CLOSED_OPTION) {
                return;
            }
            reanudar = opcion == 0;
        }

        List<Usuario> jugadores = new ArrayList<>();
        jugadores.add(usuarioActual);
        if (!reanudar) {
            Usuario segundoJugador = solicitarSegundoJugador();
            if (segundoJugador != null) {
                jugadores.add(segundoJugador);
            }
        }

        controladorJuegoActual = new ControladorJuego(jugadores, nombreJuego,
                this, controladorFicheros);
        if (reanudar && !controladorJuegoActual.reanudarPartida()) {
            JOptionPane.showMessageDialog(ventana,
                    "No se pudo recuperar la partida guardada.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        mostrarJuego(controladorJuegoActual);
    }

    public void mostrarJuego(ControladorJuego controladorJuego) {
        if (controladorJuego.getJuego() instanceof Ahorcado) {
            ventana.reemplazarPanel(new PanelAhorcado(this, controladorJuego), "JUEGO");
        } else if (controladorJuego.getJuego() instanceof Pasapalabra) {
            ventana.reemplazarPanel(new PanelPasapalabra(this, controladorJuego), "JUEGO");
        }
        ventana.mostrarPanel("JUEGO");
    }

    public void pausarYVolverAlMenu() {
        if (controladorJuegoActual != null
                && !controladorJuegoActual.getJuego().esFinalizado()) {
            controladorJuegoActual.pausarPartida();
        }
        volverAlMenu();
    }

    public void volverAlMenu() {
        if (panelMenu != null) {
            panelMenu.mostrarVistaSegunRol();
        }
        ventana.mostrarPanel("MENU");
    }

    public void mostrarEstadisticas() {
        if (usuarioActual == null) {
            return;
        }
        PanelEstadisticas panel = new PanelEstadisticas(this,
                sistemaJuegos.obtenerEstadisticasJugador(usuarioActual.getUsername()));
        ventana.reemplazarPanel(panel, "ESTADISTICAS");
        ventana.mostrarPanel("ESTADISTICAS");
    }

    public void mostrarAdmin() {
        if (!(usuarioActual instanceof Administrador)) {
            JOptionPane.showMessageDialog(ventana,
                    "Esta seccion es solo para administradores.");
            return;
        }
        ventana.reemplazarPanel(new PanelAdmin(this, controladorFicheros), "ADMIN");
        ventana.mostrarPanel("ADMIN");
    }

    public void mostrarLogin() {
        ventana.mostrarPanel("LOGIN");
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public ControladorFicheros getControladorFicheros() {
        return controladorFicheros;
    }

    public SistemaJuegos getSistemaJuegos() {
        return sistemaJuegos;
    }

    public void logout() {
        usuarioActual = null;
        controladorJuegoActual = null;
        mostrarLogin();
    }

    private Usuario solicitarSegundoJugador() {
        int respuesta = JOptionPane.showConfirmDialog(ventana,
                "¿Quieres jugar por turnos con un segundo jugador?",
                "Modo de juego", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return null;
        }

        JTextField usuario = new JTextField();
        JPasswordField password = new JPasswordField();
        JPanel formulario = new JPanel(new GridLayout(0, 1, 4, 4));
        formulario.add(new JLabel("Usuario del segundo jugador:"));
        formulario.add(usuario);
        formulario.add(new JLabel("Contraseña:"));
        formulario.add(password);

        int confirmar = JOptionPane.showConfirmDialog(ventana, formulario,
                "Identificar segundo jugador", JOptionPane.OK_CANCEL_OPTION);
        if (confirmar != JOptionPane.OK_OPTION) {
            return null;
        }

        Usuario segundo = controladorFicheros.validarCredenciales(
                usuario.getText(), new String(password.getPassword()));
        if (segundo == null || segundo instanceof Administrador
                || segundo.getUsername().equalsIgnoreCase(usuarioActual.getUsername())) {
            JOptionPane.showMessageDialog(ventana,
                    "Las credenciales del segundo jugador no son validas.",
                    "No se pudo añadir", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return segundo instanceof Jugador ? segundo
                : new Jugador(segundo.getUsername(), segundo.getPassword());
    }
}
