package plataformajuegos.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import plataformajuegos.controlador.*;
import plataformajuegos.modelo.juegos.Ahorcado;
import plataformajuegos.util.BotonRedondeado;

public class PanelAhorcado extends JPanel {
    private static final int INTENTOS_TOTALES = 6;
    private static final Color ROSA = new Color(230, 40, 106);
    private static final Color ROSA_CLARO = new Color(252, 222, 232);
    private static final Color GRIS_TEXTO = new Color(120, 120, 130);
    private static final Color BORDE = new Color(225, 225, 232);
    private static final String[] FILAS_TECLADO = {
            "ABCDEFGHI", "JKLMNÑOPQ", "RSTUVWXYZ"
    };

    private final ControladorPrincipal controladorPrincipal;
    private final ControladorJuego controladorJuego;
    private final Ahorcado ahorcado;

    private final PanelDibujo panelDibujo = new PanelDibujo();
    private final PanelIntentos panelIntentos = new PanelIntentos();
    private final JLabel labelPalabra = new JLabel("", SwingConstants.CENTER);
    private final JLabel labelMensaje = new JLabel(" ", SwingConstants.CENTER);
    private final JLabel labelPuntuacion = new JLabel("0");
    private final Map<Character, JButton> botonesLetra = new HashMap<>();

    private Set<Character> letrasPrevias = new HashSet<>();
    private boolean finalMostrado;

    public PanelAhorcado(ControladorPrincipal controladorPrincipal,
            ControladorJuego controladorJuego) {
        this.controladorPrincipal = controladorPrincipal;
        this.controladorJuego = controladorJuego;
        this.ahorcado = (Ahorcado) controladorJuego.getJuego();
        this.letrasPrevias = ahorcado.getLetrasUsadas();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(crearLateral(), BorderLayout.WEST);
        add(crearCentro(), BorderLayout.CENTER);
        actualizarVista();
    }

    // ---------- Columna lateral rosa ----------
    private JPanel crearLateral() {
        JPanel lateral = new JPanel(null);
        lateral.setPreferredSize(new Dimension(320, 0));
        lateral.setBackground(ROSA);

        JLabel sistema = new JLabel("SISTEMA JUEGOS");
        sistema.setFont(new Font("Arial", Font.BOLD, 13));
        sistema.setForeground(new Color(255, 255, 255, 200));
        sistema.setBounds(36, 45, 250, 20);
        lateral.add(sistema);

        JLabel titulo = new JLabel("Ahorcado");
        titulo.setFont(new Font("Arial", Font.BOLD, 46));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(34, 70, 280, 60);
        lateral.add(titulo);

        JLabel descripcion = new JLabel("<html>Adivina la palabra oculta letra a letra "
                + "antes de que se complete el dibujo.</html>");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 15));
        descripcion.setForeground(new Color(255, 255, 255, 220));
        descripcion.setBounds(36, 140, 250, 60);
        lateral.add(descripcion);

        JLabel catTitulo = etiquetaLateral("CATEGORÍA", 12, new Color(255, 255, 255, 200));
        catTitulo.setBounds(36, 630, 250, 18);
        lateral.add(catTitulo);
        JLabel categoria = etiquetaLateral("Informática", 18, Color.WHITE);
        categoria.setFont(new Font("Arial", Font.BOLD, 18));
        categoria.setBounds(36, 650, 250, 26);
        lateral.add(categoria);

        JLabel puntTitulo = etiquetaLateral("PUNTUACIÓN", 12, new Color(255, 255, 255, 200));
        puntTitulo.setBounds(36, 695, 250, 18);
        lateral.add(puntTitulo);
        labelPuntuacion.setFont(new Font("Arial", Font.BOLD, 38));
        labelPuntuacion.setForeground(Color.WHITE);
        labelPuntuacion.setBounds(34, 712, 250, 46);
        lateral.add(labelPuntuacion);

        return lateral;
    }

    private JLabel etiquetaLateral(String texto, int tam, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.BOLD, tam));
        l.setForeground(color);
        return l;
    }

    // ---------- Zona central ----------
    private JPanel crearCentro() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(Color.WHITE);
        centro.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        centro.add(crearCabecera(), BorderLayout.NORTH);
        centro.add(crearJuego(), BorderLayout.CENTER);
        return centro;
    }

    private JPanel crearCabecera() {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);

        JPanel intentos = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        intentos.setOpaque(false);
        JLabel titulo = new JLabel("INTENTOS RESTANTES");
        titulo.setFont(new Font("Arial", Font.BOLD, 12));
        titulo.setForeground(GRIS_TEXTO);
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        fila.setOpaque(false);
        fila.add(panelIntentos);
        JPanel cont = new JPanel();
        cont.setOpaque(false);
        cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
        cont.add(titulo);
        cont.add(fila);
        cabecera.add(cont, BorderLayout.WEST);

        JButton salir = new BotonRedondeado("PAUSAR Y GUARDAR", 20);
        salir.setFont(new Font("Arial", Font.BOLD, 12));
        salir.setForeground(ROSA);
        salir.setBackground(Color.WHITE);
        salir.setFocusPainted(false);
        salir.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1, true),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)));
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ahorcado.esFinalizado()) {
                    controladorPrincipal.volverAlMenu();
                } else {
                    controladorPrincipal.pausarYVolverAlMenu();
                }
            }
        });
        JPanel este = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        este.setOpaque(false);
        este.add(salir);
        cabecera.add(este, BorderLayout.EAST);
        return cabecera;
    }

    private JPanel crearJuego() {
        JPanel juego = new JPanel();
        juego.setOpaque(false);
        juego.setLayout(new BoxLayout(juego, BoxLayout.Y_AXIS));

        panelDibujo.setAlignmentX(CENTER_ALIGNMENT);
        panelDibujo.setMaximumSize(new Dimension(260, 280));
        panelDibujo.setPreferredSize(new Dimension(260, 280));
        juego.add(Box.createVerticalStrut(10));
        juego.add(panelDibujo);

        labelPalabra.setAlignmentX(CENTER_ALIGNMENT);
        labelPalabra.setFont(new Font(Font.MONOSPACED, Font.BOLD, 34));
        labelPalabra.setForeground(ROSA);
        juego.add(Box.createVerticalStrut(15));
        juego.add(labelPalabra);

        labelMensaje.setAlignmentX(CENTER_ALIGNMENT);
        labelMensaje.setFont(new Font("Arial", Font.ITALIC, 13));
        labelMensaje.setForeground(ROSA);
        juego.add(Box.createVerticalStrut(8));
        juego.add(labelMensaje);

        JPanel teclado = crearTeclado();
        teclado.setAlignmentX(CENTER_ALIGNMENT);
        juego.add(Box.createVerticalStrut(12));
        juego.add(teclado);
        juego.add(Box.createVerticalGlue());
        return juego;
    }

    private JPanel crearTeclado() {
        JPanel teclado = new JPanel();
        teclado.setOpaque(false);
        teclado.setLayout(new BoxLayout(teclado, BoxLayout.Y_AXIS));
        for (String filaLetras : FILAS_TECLADO) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
            fila.setOpaque(false);
            for (char letra : filaLetras.toCharArray()) {
                fila.add(crearBotonLetra(letra));
            }
            teclado.add(fila);
        }
        return teclado;
    }

    private JButton crearBotonLetra(final char letra) {
        final JButton boton = new BotonRedondeado(String.valueOf(letra), 20);
        boton.setPreferredSize(new Dimension(50, 50));
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setFocusPainted(false);
        boton.setBackground(Color.WHITE);
        boton.setForeground(new Color(40, 40, 50));
        boton.setBorder(BorderFactory.createLineBorder(BORDE, 1, true));
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jugarLetra(letra);
            }
        });
        botonesLetra.put(letra, boton);
        return boton;
    }

    private void jugarLetra(char letra) {
        if (ahorcado.esFinalizado()) {
            return;
        }
        letrasPrevias = ahorcado.getLetrasUsadas();
        controladorJuego.procesarJugada(String.valueOf(letra));
        actualizarVista();
    }

    // ---------- Actualización ----------
    private void actualizarVista() {
        labelPalabra.setText(ahorcado.getPalabraActual());
        labelPuntuacion.setText(String.valueOf(controladorJuego.obtenerPuntuacionActual()));

        int fallos = INTENTOS_TOTALES - ahorcado.getIntentosRestantes();
        panelIntentos.setFallos(fallos);
        panelDibujo.setFallos(fallos);

        actualizarTeclado();
        actualizarMensaje();

        panelIntentos.repaint();
        panelDibujo.repaint();

        if (ahorcado.esFinalizado() && !finalMostrado) {
            finalMostrado = true;
            for (JButton b : botonesLetra.values()) {
                b.setEnabled(false);
            }
            String mensaje = ahorcado.hasGanado()
                    ? "¡Palabra completada!"
                    : "Sin intentos. La palabra era " + ahorcado.getPalabraSecreta() + ".";
            JOptionPane.showMessageDialog(this, mensaje, "Fin de partida",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void actualizarTeclado() {
        Set<Character> usadas = ahorcado.getLetrasUsadas();
        String secreta = ahorcado.getPalabraSecreta();
        for (Map.Entry<Character, JButton> entrada : botonesLetra.entrySet()) {
            char letra = entrada.getKey();
            JButton boton = entrada.getValue();
            if (!usadas.contains(letra)) {
                boton.setBackground(Color.WHITE);
                boton.setForeground(new Color(40, 40, 50));
                continue;
            }
            boton.setEnabled(false);
            if (secreta != null && secreta.indexOf(letra) >= 0) {
                boton.setBackground(ROSA);            // acierto
                boton.setForeground(Color.WHITE);
            } else {
                boton.setBackground(ROSA_CLARO);      // fallo
                boton.setForeground(new Color(200, 120, 150));
            }
        }
    }

    private void actualizarMensaje() {
        Set<Character> usadas = ahorcado.getLetrasUsadas();
        String secreta = ahorcado.getPalabraSecreta();
        Set<Character> nuevas = new HashSet<>(usadas);
        nuevas.removeAll(letrasPrevias);
        for (char letra : nuevas) {
            if (secreta != null && secreta.indexOf(letra) < 0) {
                labelMensaje.setText("La letra «" + letra + "» no está en la palabra.");
                return;
            }
        }
        if (!ahorcado.esFinalizado()) {
            labelMensaje.setText(" ");
        }
    }

    // ---------- Círculos de intentos ----------
    private class PanelIntentos extends JComponent {
        private int fallos;

        PanelIntentos() {
            setPreferredSize(new Dimension(INTENTOS_TOTALES * 22, 18));
        }

        void setFallos(int fallos) {
            this.fallos = fallos;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            int d = 14;
            for (int i = 0; i < INTENTOS_TOTALES; i++) {
                int x = i * 22;
                if (i < fallos) {
                    g2.setColor(ROSA);
                    g2.fillOval(x, 2, d, d);
                } else {
                    g2.setColor(BORDE);
                    g2.drawOval(x, 2, d, d);
                }
            }
            g2.dispose();
        }
    }

    // ---------- Dibujo del ahorcado ----------
    private class PanelDibujo extends JComponent {
        private int fallos;

        void setFallos(int fallos) {
            this.fallos = fallos;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(ROSA);

            int baseX = 60, baseY = 250, topX = 60, topY = 30, ropeX = 150;
            // Horca (siempre visible)
            g2.drawLine(baseX, baseY, baseX + 110, baseY);   // suelo
            g2.drawLine(topX, baseY, topX, topY);            // poste
            g2.drawLine(topX, topY, ropeX, topY);            // viga
            g2.drawLine(ropeX, topY, ropeX, topY + 30);      // cuerda

            // Partes del cuerpo según fallos (gris las que aún no aparecen no se dibujan)
            int headY = topY + 30, headR = 22, cx = ropeX;
            int neckY = headY + headR * 2, hipY = neckY + 55;

            if (fallos >= 1) g2.drawOval(cx - headR, headY, headR * 2, headR * 2); // cabeza
            if (fallos >= 2) g2.drawLine(cx, neckY, cx, hipY);                     // tronco
            if (fallos >= 3) g2.drawLine(cx, neckY + 12, cx - 30, neckY + 40);     // brazo izq
            if (fallos >= 4) g2.drawLine(cx, neckY + 12, cx + 30, neckY + 40);     // brazo der
            if (fallos >= 5) g2.drawLine(cx, hipY, cx - 28, hipY + 45);            // pierna izq
            if (fallos >= 6) g2.drawLine(cx, hipY, cx + 28, hipY + 45);            // pierna der

            g2.dispose();
        }
    }
}