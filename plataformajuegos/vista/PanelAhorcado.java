package plataformajuegos.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import plataformajuegos.controlador.ControladorJuego;
import plataformajuegos.controlador.ControladorPrincipal;
import plataformajuegos.modelo.juegos.Ahorcado;

public class PanelAhorcado extends JPanel {
    private final ControladorPrincipal controladorPrincipal;
    private final ControladorJuego controladorJuego;
    private final Ahorcado ahorcado;
    private final JLabel labelTurno = new JLabel();
    private final JLabel labelPalabra = new JLabel("", SwingConstants.CENTER);
    private final JLabel labelIntentos = new JLabel("", SwingConstants.CENTER);
    private final JLabel labelLetras = new JLabel("", SwingConstants.CENTER);
    private final JLabel labelPuntuaciones = new JLabel("", SwingConstants.CENTER);
    private final JTextField campoJugada = new JTextField(16);
    private final JButton botonProbar = new JButton("Probar");
    private final JButton botonSalir = new JButton("Guardar y salir");
    private boolean finalMostrado;

    public PanelAhorcado(ControladorPrincipal controladorPrincipal,
            ControladorJuego controladorJuego) {
        this.controladorPrincipal = controladorPrincipal;
        this.controladorJuego = controladorJuego;
        this.ahorcado = (Ahorcado) controladorJuego.getJuego();

        setLayout(new BorderLayout(24, 24));
        setBorder(BorderFactory.createEmptyBorder(28, 40, 32, 40));
        setBackground(new Color(247, 248, 252));

        add(crearCabecera(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearControles(), BorderLayout.SOUTH);
        actualizarVista();
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
        JLabel titulo = new JLabel("AHORCADO");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(220, 35, 100));
        labelTurno.setFont(new Font("Arial", Font.BOLD, 16));
        cabecera.add(titulo, BorderLayout.WEST);
        cabecera.add(labelTurno, BorderLayout.EAST);
        return cabecera;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new GridLayout(4, 1, 8, 8));
        contenido.setOpaque(false);

        labelPalabra.setFont(new Font(Font.MONOSPACED, Font.BOLD, 38));
        labelIntentos.setFont(new Font("Arial", Font.BOLD, 21));
        labelLetras.setFont(new Font("Arial", Font.PLAIN, 17));
        labelPuntuaciones.setFont(new Font("Arial", Font.BOLD, 17));

        contenido.add(labelPalabra);
        contenido.add(labelIntentos);
        contenido.add(labelLetras);
        contenido.add(labelPuntuaciones);
        return contenido;
    }

    private JPanel crearControles() {
        JPanel contenedor = new JPanel(new BorderLayout(12, 12));
        contenedor.setOpaque(false);

        JLabel ayuda = new JLabel("Introduce una letra o intenta resolver la palabra:");
        ayuda.setFont(new Font("Arial", Font.PLAIN, 15));
        contenedor.add(ayuda, BorderLayout.NORTH);

        JPanel entrada = new JPanel(new BorderLayout(10, 0));
        entrada.setOpaque(false);
        campoJugada.setFont(new Font("Arial", Font.PLAIN, 18));
        entrada.add(campoJugada, BorderLayout.CENTER);
        entrada.add(botonProbar, BorderLayout.EAST);
        contenedor.add(entrada, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        acciones.setOpaque(false);
        acciones.add(botonSalir);
        contenedor.add(acciones, BorderLayout.SOUTH);

        ActionListener jugar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                procesarJugada();
            }
        };
        botonProbar.addActionListener(jugar);
        campoJugada.addActionListener(jugar);
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (ahorcado.esFinalizado()) {
                    controladorPrincipal.volverAlMenu();
                } else {
                    controladorPrincipal.pausarYVolverAlMenu();
                }
            }
        });
        return contenedor;
    }

    private void procesarJugada() {
        String jugada = campoJugada.getText().trim();
        if (jugada.isEmpty()) {
            return;
        }
        controladorJuego.procesarJugada(jugada);
        campoJugada.setText("");
        actualizarVista();
    }

    private void actualizarVista() {
        labelTurno.setText("Turno: "
                + controladorJuego.getPartida().getJugadorActual().getUsername());
        labelPalabra.setText(ahorcado.getPalabraActual());
        labelIntentos.setText("Intentos restantes: " + ahorcado.getIntentosRestantes());
        labelLetras.setText("Letras usadas: "
                + new TreeSet<Character>(ahorcado.getLetrasUsadas()));
        labelPuntuaciones.setText(formatearPuntuaciones(
                controladorJuego.getPartida().getPuntuaciones()));

        if (ahorcado.esFinalizado()) {
            botonProbar.setEnabled(false);
            campoJugada.setEnabled(false);
            botonSalir.setText("Volver al menu");
            if (!finalMostrado) {
                finalMostrado = true;
                String mensaje = ahorcado.hasGanado()
                        ? "¡Palabra completada!"
                        : "Sin intentos. La palabra era " + ahorcado.getPalabraSecreta() + ".";
                JOptionPane.showMessageDialog(this, mensaje, "Fin de partida",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private String formatearPuntuaciones(Map<String, Integer> puntuaciones) {
        StringBuilder texto = new StringBuilder("Puntuaciones: ");
        boolean primera = true;
        for (Map.Entry<String, Integer> entrada : puntuaciones.entrySet()) {
            if (!primera) {
                texto.append("  |  ");
            }
            texto.append(entrada.getKey()).append(": ").append(entrada.getValue());
            primera = false;
        }
        return texto.toString();
    }
}
