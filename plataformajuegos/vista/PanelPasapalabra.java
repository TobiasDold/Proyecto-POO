package plataformajuegos.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import plataformajuegos.controlador.*;
import plataformajuegos.modelo.juegos.*;
import plataformajuegos.util.BotonRedondeado;
import plataformajuegos.util.TextFieldRedondeado;

public class PanelPasapalabra extends JPanel {
    private static final Color ROSA = new Color(228, 30, 90);
    private static final Color VERDE = new Color(38, 166, 91);
    private static final Color ROJO = new Color(217, 30, 41);
    private static final Color GRIS = new Color(230, 230, 235);
    private static final Color GRIS_TEXTO = new Color(150, 150, 160);
    private static final Color FONDO_DEF = new Color(252, 235, 240);

    private final ControladorPrincipal controladorPrincipal;
    private final ControladorJuego controladorJuego;
    private final Pasapalabra pasapalabra;

    private final Rosco rosco = new Rosco();
    private final JLabel labelDefinicion = new JLabel();
    private final JLabel labelAciertos = new JLabel("0");
    private final JLabel labelFallos = new JLabel("0");
    private final JLabel labelPuntuacion = new JLabel("0");
    private final JLabel labelTiempo = new JLabel("00:00");
    private final JLabel labelTurno = new JLabel();
    private final JTextField campoRespuesta = new TextFieldRedondeado(0, 20);
    private final JButton botonResponder = new BotonRedondeado("RESPONDER", 20);
    private final JButton botonPasapalabra = new BotonRedondeado("PASAPALABRA", 20);
    private final JButton botonSalir = new BotonRedondeado("PAUSAR Y GUARDAR", 20);

    private final Timer cronometro;
    private int segundos;
    private boolean finalMostrado;

    public PanelPasapalabra(ControladorPrincipal controladorPrincipal,
            ControladorJuego controladorJuego) {
        this.controladorPrincipal = controladorPrincipal;
        this.controladorJuego = controladorJuego;
        this.pasapalabra = (Pasapalabra) controladorJuego.getJuego();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(crearLateral(), BorderLayout.WEST);
        add(crearCentro(), BorderLayout.CENTER);

        cronometro = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundos++;
                actualizarTiempo();
            }
        });
        cronometro.start();
        actualizarVista();
    }

    private JPanel crearLateral() {
        JPanel lateral = new JPanel();
        lateral.setLayout(null);
        lateral.setBackground(ROSA);
        lateral.setPreferredSize(new Dimension(320, 0));

        JLabel marca = new JLabel("SISTEMA JUEGOS");
        marca.setFont(new Font("Arial", Font.BOLD, 13));
        marca.setForeground(new Color(255, 255, 255, 200));
        marca.setBounds(40, 30, 250, 20);
        lateral.add(marca);

        JLabel titulo = new JLabel("Pasapalabra");
        titulo.setFont(new Font("Arial", Font.BOLD, 42));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(38, 50, 280, 55);
        lateral.add(titulo);

        JLabel desc = new JLabel("<html>Recorre el rosco respondiendo una "
                + "definici\u00f3n por cada letra del abecedario.</html>");
        desc.setFont(new Font("Arial", Font.PLAIN, 15));
        desc.setForeground(new Color(255, 255, 255, 220));
        desc.setBounds(40, 115, 250, 70);
        lateral.add(desc);

        // Stats inferiores
        JLabel tagAciertos = etiquetaStat("ACIERTOS", 40, 560);
        JLabel tagFallos = etiquetaStat("FALLOS", 160, 560);
        lateral.add(tagAciertos);
        lateral.add(tagFallos);
        valorStat(labelAciertos, 40, 580);
        valorStat(labelFallos, 160, 580);
        lateral.add(labelAciertos);
        lateral.add(labelFallos);

        JLabel tagPunt = etiquetaStat("PUNTUACI\u00d3N", 40, 645);
        lateral.add(tagPunt);
        labelPuntuacion.setFont(new Font("Arial", Font.BOLD, 40));
        labelPuntuacion.setForeground(Color.WHITE);
        labelPuntuacion.setBounds(40, 665, 250, 50);
        lateral.add(labelPuntuacion);

        labelTurno.setFont(new Font("Arial", Font.BOLD, 13));
        labelTurno.setForeground(new Color(255, 255, 255, 200));
        labelTurno.setBounds(40, 730, 250, 20);
        lateral.add(labelTurno);

        return lateral;
    }

    private JLabel etiquetaStat(String texto, int x, int y) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.BOLD, 12));
        l.setForeground(new Color(255, 255, 255, 200));
        l.setBounds(x, y, 120, 18);
        return l;
    }

    private void valorStat(JLabel label, int x, int y) {
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, 120, 40);
    }

    private JPanel crearCentro() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Color.WHITE);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        // Barra superior: tiempo + botón pausar
        JPanel barra = new JPanel(new BorderLayout());
        barra.setOpaque(false);

        JPanel bloqueTiempo = new JPanel();
        bloqueTiempo.setLayout(new BoxLayout(bloqueTiempo, BoxLayout.Y_AXIS));
        bloqueTiempo.setOpaque(false);
        JLabel tagTiempo = new JLabel("\u25cf TIEMPO");
        tagTiempo.setFont(new Font("Arial", Font.BOLD, 12));
        tagTiempo.setForeground(ROSA);
        labelTiempo.setFont(new Font("Arial", Font.BOLD, 26));
        labelTiempo.setForeground(new Color(40, 40, 40));
        bloqueTiempo.add(tagTiempo);
        bloqueTiempo.add(labelTiempo);
        barra.add(bloqueTiempo, BorderLayout.WEST);

        botonSalir.setFont(new Font("Arial", Font.BOLD, 13));
        botonSalir.setForeground(new Color(40, 40, 40));
        botonSalir.setBackground(Color.WHITE);
        botonSalir.setFocusPainted(false);
        botonSalir.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 225)),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)));
        JPanel wrapSalir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        wrapSalir.setOpaque(false);
        wrapSalir.add(botonSalir);
        barra.add(wrapSalir, BorderLayout.EAST);
        centro.add(barra, BorderLayout.NORTH);

        centro.add(rosco, BorderLayout.CENTER);
        centro.add(crearInferior(), BorderLayout.SOUTH);

        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cronometro.stop();
                if (pasapalabra.esFinalizado()) {
                    controladorPrincipal.volverAlMenu();
                } else {
                    controladorPrincipal.pausarYVolverAlMenu();
                }
            }
        });
        return centro;
    }

    private JPanel crearInferior() {
        JPanel inferior = new JPanel(new BorderLayout(0, 14));
        inferior.setOpaque(false);

        labelDefinicion.setFont(new Font("Arial", Font.PLAIN, 16));
        labelDefinicion.setForeground(new Color(70, 70, 75));
        labelDefinicion.setOpaque(true);
        labelDefinicion.setBackground(FONDO_DEF);
        labelDefinicion.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        inferior.add(labelDefinicion, BorderLayout.NORTH);

        JPanel controles = new JPanel(new BorderLayout(10, 0));
        controles.setOpaque(false);

        campoRespuesta.setFont(new Font("Arial", Font.PLAIN, 16));
        campoRespuesta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 230)),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));
        controles.add(campoRespuesta, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botones.setOpaque(false);
        estiloPrimario(botonResponder);
        estiloSecundario(botonPasapalabra);
        botones.add(botonResponder);
        botones.add(botonPasapalabra);
        controles.add(botones, BorderLayout.EAST);

        inferior.add(controles, BorderLayout.SOUTH);

        ActionListener responder = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRespuesta();
            }
        };
        botonResponder.addActionListener(responder);
        campoRespuesta.addActionListener(responder);
        botonPasapalabra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controladorJuego.procesarJugada("PASAPALABRA");
                campoRespuesta.setText("");
                actualizarVista();
            }
        });
        return inferior;
    }

    private void estiloPrimario(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setBackground(ROSA);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(13, 26, 13, 26));
        boton.setOpaque(true);
    }

    private void estiloSecundario(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setForeground(ROSA);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSA),
                BorderFactory.createEmptyBorder(12, 22, 12, 22)));
        boton.setOpaque(true);
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

    private void actualizarTiempo() {
        labelTiempo.setText(String.format("%02d:%02d", segundos / 60, segundos % 60));
    }

    private void actualizarVista() {
        labelTurno.setText("Turno: "
                + controladorJuego.getPartida().getJugadorActual().getUsername());
        labelAciertos.setText(String.valueOf(pasapalabra.getRespuestasCorrectas()));
        labelFallos.setText(String.valueOf(contarFallos()));
        labelPuntuacion.setText(String.valueOf(pasapalabra.obtenerPuntuacion()));

        Pregunta actual = pasapalabra.getPreguntaActual();
        if (actual != null) {
            labelDefinicion.setText("<html><div style='width:560px'>"
                    + actual.getDefinicion() + "</div></html>");
        } else {
            labelDefinicion.setText("Rosco completado.");
        }

        rosco.repaint();

        if (pasapalabra.esFinalizado()) {
            cronometro.stop();
            botonResponder.setEnabled(false);
            botonPasapalabra.setEnabled(false);
            campoRespuesta.setEnabled(false);
            botonSalir.setText("Volver al men\u00fa");
            if (!finalMostrado) {
                finalMostrado = true;
                JOptionPane.showMessageDialog(this,
                        "Rosco completado con " + pasapalabra.getRespuestasCorrectas()
                                + " aciertos.",
                        "Fin de partida", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private int contarFallos() {
        int fallos = 0;
        for (int i = 0; i < pasapalabra.getPreguntas().size(); i++) {
            if ("F".equals(pasapalabra.getEstadoPregunta(i))) {
                fallos++;
            }
        }
        return fallos;
    }

    // Panel custom que dibuja el rosco circular
    private class Rosco extends JPanel {
        private static final long serialVersionUID = 1L;

        Rosco() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            List<Pregunta> preguntas = pasapalabra.getPreguntas();
            int n = preguntas.size();
            if (n == 0) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int cx = w / 2;
            int cy = h / 2;
            int radio = Math.min(w, h) / 2 - 50;
            int d = 46; // diámetro de cada letra

            int actualIdx = pasapalabra.getIndiceActual();

            for (int i = 0; i < n; i++) {
                double ang = -Math.PI / 2 + (2 * Math.PI * i / n);
                int x = cx + (int) (radio * Math.cos(ang)) - d / 2;
                int y = cy + (int) (radio * Math.sin(ang)) - d / 2;

                String estado = pasapalabra.getEstadoPregunta(i);
                Color fondo;
                Color texto = Color.WHITE;
                if (i == actualIdx && !pasapalabra.esFinalizado()) {
                    fondo = ROSA;
                } else if ("C".equals(estado)) {
                    fondo = VERDE;
                } else if ("F".equals(estado)) {
                    fondo = ROJO;
                } else {
                    fondo = GRIS;
                    texto = GRIS_TEXTO;
                }

                g2.setColor(fondo);
                g2.fillOval(x, y, d, d);

                g2.setColor(texto);
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                String letra = preguntas.get(i).getLetra();
                FontMetrics fm = g2.getFontMetrics();
                int tx = x + (d - fm.stringWidth(letra)) / 2;
                int ty = y + (d - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(letra, tx, ty);
            }

            // Letra central grande + "EMPIEZA POR X"
            Pregunta actual = pasapalabra.getPreguntaActual();
            if (actual != null) {
                g2.setColor(ROSA);
                g2.setFont(new Font("Arial", Font.BOLD, 80));
                FontMetrics fmC = g2.getFontMetrics();
                String l = actual.getLetra();
                g2.drawString(l, cx - fmC.stringWidth(l) / 2,
                        cy + fmC.getAscent() / 3);

                g2.setColor(GRIS_TEXTO);
                g2.setFont(new Font("Arial", Font.BOLD, 14));
                String tipo = ("EMPIEZA".equals(actual.getTipo())
                        ? "EMPIEZA POR " : "CONTIENE LA ") + l;
                FontMetrics fmT = g2.getFontMetrics();
                g2.drawString(tipo, cx - fmT.stringWidth(tipo) / 2, cy + 55);
            }

            g2.dispose();
        }
    }
}