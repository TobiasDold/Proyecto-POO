package plataformajuegos.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

import javax.swing.*;

import plataformajuegos.controlador.*;
import plataformajuegos.modelo.juegos.*;

public class PanelPasapalabra extends JPanel {
    private final ControladorPrincipal controladorPrincipal;
    private final ControladorJuego controladorJuego;
    private final Pasapalabra pasapalabra;
    private final JLabel labelTurno = new JLabel();
    private final JLabel labelRosco = new JLabel("", SwingConstants.CENTER);
    private final JLabel labelLetra = new JLabel("", SwingConstants.CENTER);
    private final JLabel labelDefinicion = new JLabel("", SwingConstants.CENTER);
    private final JLabel labelProgreso = new JLabel("", SwingConstants.CENTER);
    private final JLabel labelPuntuaciones = new JLabel("", SwingConstants.CENTER);
    private final JTextField campoRespuesta = new JTextField(20);
    private final JButton botonResponder = new JButton("Responder");
    private final JButton botonPasapalabra = new JButton("Pasapalabra");
    private final JButton botonSalir = new JButton("Guardar y salir");
    private boolean finalMostrado;

    public PanelPasapalabra(ControladorPrincipal controladorPrincipal,
            ControladorJuego controladorJuego) {
        this.controladorPrincipal = controladorPrincipal;
        this.controladorJuego = controladorJuego;
        this.pasapalabra = (Pasapalabra) controladorJuego.getJuego();

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(24, 36, 30, 36));
        setBackground(new Color(247, 248, 252));
        add(crearCabecera(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearControles(), BorderLayout.SOUTH);
        actualizarVista();
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
        JLabel titulo = new JLabel("PASAPALABRA");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(new Color(220, 35, 100));
        labelTurno.setFont(new Font("Arial", Font.BOLD, 16));
        cabecera.add(titulo, BorderLayout.WEST);
        cabecera.add(labelTurno, BorderLayout.EAST);
        return cabecera;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new GridLayout(5, 1, 6, 6));
        contenido.setOpaque(false);
        labelRosco.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        labelLetra.setFont(new Font("Arial", Font.BOLD, 44));
        labelLetra.setForeground(new Color(220, 35, 100));
        labelDefinicion.setFont(new Font("Arial", Font.PLAIN, 18));
        labelProgreso.setFont(new Font("Arial", Font.BOLD, 16));
        labelPuntuaciones.setFont(new Font("Arial", Font.BOLD, 16));
        contenido.add(labelRosco);
        contenido.add(labelLetra);
        contenido.add(labelDefinicion);
        contenido.add(labelProgreso);
        contenido.add(labelPuntuaciones);
        return contenido;
    }

    private JPanel crearControles() {
        JPanel contenedor = new JPanel(new BorderLayout(10, 10));
        contenedor.setOpaque(false);
        campoRespuesta.setFont(new Font("Arial", Font.PLAIN, 18));
        contenedor.add(campoRespuesta, BorderLayout.NORTH);

        JPanel jugada = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        jugada.setOpaque(false);
        jugada.add(botonResponder);
        jugada.add(botonPasapalabra);
        contenedor.add(jugada, BorderLayout.CENTER);

        JPanel salida = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        salida.setOpaque(false);
        salida.add(botonSalir);
        contenedor.add(salida, BorderLayout.SOUTH);

        ActionListener responder = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                procesarRespuesta();
            }
        };
        botonResponder.addActionListener(responder);
        campoRespuesta.addActionListener(responder);
        botonPasapalabra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controladorJuego.procesarJugada("PASAPALABRA");
                campoRespuesta.setText("");
                actualizarVista();
            }
        });
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (pasapalabra.esFinalizado()) {
                    controladorPrincipal.volverAlMenu();
                } else {
                    controladorPrincipal.pausarYVolverAlMenu();
                }
            }
        });
        return contenedor;
    }

    private void procesarRespuesta() {
        String respuesta = campoRespuesta.getText().trim();
        if (respuesta.isEmpty()) {
            return;
        }
        controladorJuego.procesarJugada(respuesta);
        campoRespuesta.setText("");
        actualizarVista();
    }

    private void actualizarVista() {
        labelTurno.setText("Turno: "
                + controladorJuego.getPartida().getJugadorActual().getUsername());
        labelRosco.setText(crearResumenRosco());
        Pregunta actual = pasapalabra.getPreguntaActual();
        if (actual != null) {
            labelLetra.setText(actual.getLetra());
            labelDefinicion.setText("<html><div style='text-align:center;width:700px'>"
                    + actual.getTipo() + ": " + actual.getDefinicion() + "</div></html>");
        } else {
            labelLetra.setText("FIN");
            labelDefinicion.setText("Rosco completado");
        }
        labelProgreso.setText("Aciertos: " + pasapalabra.getRespuestasCorrectas()
                + " de " + pasapalabra.getTotalPreguntas());
        labelPuntuaciones.setText(formatearPuntuaciones(
                controladorJuego.getPartida().getPuntuaciones()));

        if (pasapalabra.esFinalizado()) {
            botonResponder.setEnabled(false);
            botonPasapalabra.setEnabled(false);
            campoRespuesta.setEnabled(false);
            botonSalir.setText("Volver al menu");
            if (!finalMostrado) {
                finalMostrado = true;
                JOptionPane.showMessageDialog(this,
                        "Rosco completado con " + pasapalabra.getRespuestasCorrectas()
                                + " aciertos.",
                        "Fin de partida", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private String crearResumenRosco() {
        StringBuilder resumen = new StringBuilder("<html>");
        for (int i = 0; i < pasapalabra.getPreguntas().size(); i++) {
            Pregunta pregunta = pasapalabra.getPreguntas().get(i);
            resumen.append(pregunta.getLetra()).append('[')
                    .append(pasapalabra.getEstadoPregunta(i)).append("] ");
            if ((i + 1) % 9 == 0) {
                resumen.append("<br>");
            }
        }
        return resumen.append("</html>").toString();
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
