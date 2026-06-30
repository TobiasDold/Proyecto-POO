package plataformajuegos.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import plataformajuegos.controlador.ControladorPrincipal;
import plataformajuegos.modelo.usuarios.RegistroPartida;
import plataformajuegos.util.FechaUtil;

public class PanelEstadisticas extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final Color ROSA = new Color(228, 30, 100);
    private static final Color ROSA_CLARO = new Color(253, 230, 240);
    private static final Color FONDO = new Color(247, 248, 252);
    private static final Color GRIS_TEXTO = new Color(150, 150, 160);
    private static final Color GRIS_BADGE = new Color(228, 228, 234);
    private static final Color GRIS_BADGE_TEXTO = new Color(90, 90, 100);
    private static final Color TEXTO_OSCURO = new Color(40, 40, 50);
    private static final Color BORDE = new Color(235, 235, 240);

    public PanelEstadisticas(final ControladorPrincipal controladorPrincipal,
            List<RegistroPartida> partidas) {
        setLayout(new BorderLayout());
        setBackground(FONDO);

        add(crearCabecera(controladorPrincipal), BorderLayout.NORTH);
        add(crearCentro(partidas), BorderLayout.CENTER);
    }

    // ---------- Cabecera rosa con logo y boton volver ----------
    private JPanel crearCabecera(final ControladorPrincipal controladorPrincipal) {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(ROSA);
        cabecera.setBorder(BorderFactory.createEmptyBorder(18, 28, 18, 28));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        izquierda.setOpaque(false);

        JLabel logo = new JLabel("S", SwingConstants.CENTER);
        logo.setOpaque(true);
        logo.setBackground(new Color(255, 90, 160));
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial Black", Font.BOLD, 20));
        logo.setPreferredSize(new Dimension(44, 44));
        logo.setBorder(BorderFactory.createEmptyBorder());
        izquierda.add(logo);

        JLabel titulo = new JLabel("MIS ESTADISTICAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        izquierda.add(titulo);

        cabecera.add(izquierda, BorderLayout.WEST);

        JButton volver = new JButton("\u2039 Volver al menu");
        volver.setFont(new Font("Arial", Font.BOLD, 14));
        volver.setForeground(ROSA);
        volver.setBackground(Color.WHITE);
        volver.setFocusPainted(false);
        volver.setBorder(BorderFactory.createEmptyBorder(11, 22, 11, 22));
        volver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controladorPrincipal.volverAlMenu();
            }
        });
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        derecha.setOpaque(false);
        derecha.add(volver);
        cabecera.add(derecha, BorderLayout.EAST);

        return cabecera;
    }

    // ---------- Zona central: tarjeta blanca con historial ----------
    private JPanel crearCentro(List<RegistroPartida> partidas) {
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createLineBorder(BORDE, 1, true));

        tarjeta.add(crearEncabezadoTarjeta(), BorderLayout.NORTH);
        tarjeta.add(crearTabla(partidas), BorderLayout.CENTER);
        tarjeta.add(crearFooter(partidas.size()), BorderLayout.SOUTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        centro.add(tarjeta, gbc);
        return centro;
    }

    private JPanel crearEncabezadoTarjeta() {
        JPanel encabezado = new JPanel();
        encabezado.setLayout(new BoxLayout(encabezado, BoxLayout.Y_AXIS));
        encabezado.setOpaque(false);
        encabezado.setBorder(BorderFactory.createEmptyBorder(22, 28, 6, 28));

        JLabel titulo = new JLabel("Historial de partidas");
        titulo.setFont(new Font("Arial", Font.BOLD, 19));
        titulo.setForeground(TEXTO_OSCURO);
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        encabezado.add(titulo);

        JLabel subtitulo = new JLabel("Partidas terminadas \u00b7 mas recientes primero");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(GRIS_TEXTO);
        subtitulo.setAlignmentX(LEFT_ALIGNMENT);
        encabezado.add(Box.createVerticalStrut(2));
        encabezado.add(subtitulo);

        return encabezado;
    }

    private JComponent crearTabla(List<RegistroPartida> partidas) {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 28, 16, 28));

        contenedor.add(crearFilaCabecera());
        contenedor.add(separador());

        if (partidas.isEmpty()) {
            JLabel vacio = new JLabel("Todavia no hay partidas terminadas.");
            vacio.setFont(new Font("Arial", Font.PLAIN, 14));
            vacio.setForeground(GRIS_TEXTO);
            vacio.setBorder(BorderFactory.createEmptyBorder(20, 4, 20, 4));
            vacio.setAlignmentX(LEFT_ALIGNMENT);
            contenedor.add(vacio);
        } else {
            for (RegistroPartida partida : partidas) {
                contenedor.add(crearFila(partida));
                contenedor.add(separador());
            }
        }

        JScrollPane scroll = new JScrollPane(contenedor,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel crearFilaCabecera() {
        JPanel fila = filaBase();
        fila.add(celda("FECHA", ROSA, Font.BOLD, 12, SwingConstants.LEFT), pesoFecha());
        fila.add(celda("JUEGO", ROSA, Font.BOLD, 12, SwingConstants.LEFT), pesoJuego());
        fila.add(celda("PUNTUACION", ROSA, Font.BOLD, 12, SwingConstants.RIGHT), pesoPuntos());
        return fila;
    }

    private JPanel crearFila(RegistroPartida partida) {
        JPanel fila = filaBase();

        JLabel fecha = new JLabel(FechaUtil.formatear(partida.getFecha()));
        fecha.setFont(new Font("Arial", Font.PLAIN, 14));
        fecha.setForeground(TEXTO_OSCURO);
        fila.add(fecha, pesoFecha());

        JPanel celdaJuego = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        celdaJuego.setOpaque(false);
        celdaJuego.add(crearBadge(partida.getNombreJuego()));
        fila.add(celdaJuego, pesoJuego());

        JLabel puntos = new JLabel(String.valueOf(partida.getPuntuacion()));
        puntos.setFont(new Font("Arial", Font.BOLD, 16));
        puntos.setForeground(TEXTO_OSCURO);
        puntos.setHorizontalAlignment(SwingConstants.RIGHT);
        fila.add(puntos, pesoPuntos());

        return fila;
    }

    // Badge con color segun el juego: Pasapalabra -> rosa, resto -> gris
    private JLabel crearBadge(String juego) {
        boolean esPasapalabra = juego != null
                && juego.equalsIgnoreCase("Pasapalabra");
        JLabel badge = new JLabel(juego == null ? "-" : juego);
        badge.setOpaque(true);
        badge.setFont(new Font("Arial", Font.BOLD, 12));
        badge.setBorder(BorderFactory.createEmptyBorder(5, 14, 5, 14));
        badge.setBackground(esPasapalabra ? ROSA_CLARO : GRIS_BADGE);
        badge.setForeground(esPasapalabra ? ROSA : GRIS_BADGE_TEXTO);
        return badge;
    }

    // ---------- Footer rosa con contador ----------
    private JPanel crearFooter(int total) {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(ROSA_CLARO);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 28, 20, 28));

        JLabel etiqueta = new JLabel("PARTIDAS TERMINADAS");
        etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        etiqueta.setForeground(ROSA);
        footer.add(etiqueta, BorderLayout.WEST);

        JLabel valor = new JLabel(String.valueOf(total));
        valor.setFont(new Font("Arial", Font.BOLD, 30));
        valor.setForeground(ROSA);
        valor.setHorizontalAlignment(SwingConstants.RIGHT);
        footer.add(valor, BorderLayout.EAST);

        return footer;
    }

    // ---------- Utilidades de layout de filas ----------
    private JPanel filaBase() {
        JPanel fila = new JPanel(new GridBagLayout());
        fila.setOpaque(false);
        fila.setBorder(BorderFactory.createEmptyBorder(12, 4, 12, 4));
        fila.setAlignmentX(LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        return fila;
    }

    private JComponent separador() {
        JPanel linea = new JPanel();
        linea.setBackground(BORDE);
        linea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        linea.setPreferredSize(new Dimension(0, 1));
        linea.setAlignmentX(LEFT_ALIGNMENT);
        return linea;
    }

    private JLabel celda(String texto, Color color, int estilo, int tam, int alineacion) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", estilo, tam));
        label.setForeground(color);
        label.setHorizontalAlignment(alineacion);
        return label;
    }

    private GridBagConstraints pesoFecha() {
        return restriccion(0.30, GridBagConstraints.WEST);
    }

    private GridBagConstraints pesoJuego() {
        return restriccion(0.45, GridBagConstraints.WEST);
    }

    private GridBagConstraints pesoPuntos() {
        return restriccion(0.25, GridBagConstraints.EAST);
    }

    private GridBagConstraints restriccion(double pesoX, int anclaje) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = pesoX;
        gbc.anchor = anclaje;
        return gbc;
    }
}