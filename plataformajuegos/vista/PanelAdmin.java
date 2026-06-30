package plataformajuegos.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import plataformajuegos.controlador.*;
import plataformajuegos.modelo.partidas.Puntuacion;
import plataformajuegos.modelo.usuarios.*;
import plataformajuegos.util.FechaUtil;

public class PanelAdmin extends JPanel {
    private static final Color ROSA = new Color(228, 30, 90);
    private static final Color ROSA_CLARO = new Color(252, 222, 232);
    private static final Color FONDO = new Color(247, 248, 252);
    private static final Color GRIS_TEXTO = new Color(150, 150, 160);
    private static final Color TEXTO_OSCURO = new Color(40, 40, 50);
    private static final Color BORDE = new Color(228, 228, 235);

    public PanelAdmin(final ControladorPrincipal controladorPrincipal,
            ControladorFicheros controladorFicheros) {
        setLayout(new BorderLayout());
        setBackground(FONDO);

        add(crearCabecera(controladorPrincipal), BorderLayout.NORTH);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.setBackground(FONDO);
        pestanas.setBorder(BorderFactory.createEmptyBorder(8, 24, 24, 24));
        pestanas.addTab("Rankings", crearRankings(controladorFicheros));
        pestanas.addTab("Usuarios", crearUsuarios(controladorFicheros));
        add(pestanas, BorderLayout.CENTER);
    }

    // ---------- Cabecera rosa ----------
    private JPanel crearCabecera(final ControladorPrincipal controladorPrincipal) {
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setBackground(ROSA);
        cabecera.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        izquierda.setOpaque(false);
        JLabel logo = new JLabel("S", SwingConstants.CENTER);
        logo.setOpaque(true);
        logo.setBackground(Color.WHITE);
        logo.setForeground(ROSA);
        logo.setFont(new Font("Arial Black", Font.BOLD, 20));
        logo.setPreferredSize(new Dimension(44, 44));
        JLabel titulo = new JLabel("PANEL DE ADMINISTRACIÓN");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        izquierda.add(logo);
        izquierda.add(titulo);
        cabecera.add(izquierda, BorderLayout.WEST);

        JButton volver = new JButton("‹ Volver al menú");
        volver.setFont(new Font("Arial", Font.BOLD, 14));
        volver.setForeground(ROSA);
        volver.setBackground(Color.WHITE);
        volver.setFocusPainted(false);
        volver.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        volver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controladorPrincipal.volverAlMenu();
            }
        });
        JPanel wrapVolver = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 4));
        wrapVolver.setOpaque(false);
        wrapVolver.add(volver);
        cabecera.add(wrapVolver, BorderLayout.EAST);

        return cabecera;
    }

    // ---------- Pestaña Rankings ----------
    private JPanel crearRankings(ControladorFicheros controladorFicheros) {
        JPanel rankings = new JPanel(new GridLayout(1, 2, 24, 0));
        rankings.setBackground(FONDO);
        rankings.setBorder(BorderFactory.createEmptyBorder(20, 24, 28, 24));
        rankings.add(crearTarjetaRanking("Ahorcado",
                controladorFicheros.cargarRanking("Ahorcado")));
        rankings.add(crearTarjetaRanking("Pasapalabra",
                controladorFicheros.cargarRanking("Pasapalabra")));
        return rankings;
    }

    private JPanel crearTarjetaRanking(String juego, List<Puntuacion> puntuaciones) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1, true),
                BorderFactory.createEmptyBorder(22, 26, 22, 26)));

        // Título + subtítulo
        JPanel cabeceraTarjeta = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        cabeceraTarjeta.setOpaque(false);
        cabeceraTarjeta.setAlignmentX(LEFT_ALIGNMENT);
        JLabel labelJuego = new JLabel(juego);
        labelJuego.setFont(new Font("Arial", Font.BOLD, 19));
        labelJuego.setForeground(TEXTO_OSCURO);
        JLabel labelSub = new JLabel("Ranking de jugadores");
        labelSub.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSub.setForeground(GRIS_TEXTO);
        cabeceraTarjeta.add(labelJuego);
        cabeceraTarjeta.add(labelSub);
        tarjeta.add(cabeceraTarjeta);

        JLabel orden = new JLabel("Ordenado de mayor a menor");
        orden.setFont(new Font("Arial", Font.PLAIN, 11));
        orden.setForeground(GRIS_TEXTO);
        orden.setAlignmentX(LEFT_ALIGNMENT);
        tarjeta.add(orden);
        tarjeta.add(Box.createVerticalStrut(14));

        // Cabecera de columnas
        JPanel filaCabecera = new JPanel(new BorderLayout());
        filaCabecera.setOpaque(false);
        filaCabecera.setAlignmentX(LEFT_ALIGNMENT);
        filaCabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel colPos = etiquetaColumna("POS");
        JLabel colJug = etiquetaColumna("JUGADOR");
        JLabel colPunt = etiquetaColumna("MEJOR PUNT.");
        colPunt.setHorizontalAlignment(SwingConstants.RIGHT);
        JPanel izqCab = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        izqCab.setOpaque(false);
        colPos.setPreferredSize(new Dimension(40, 18));
        izqCab.add(colPos);
        izqCab.add(colJug);
        filaCabecera.add(izqCab, BorderLayout.WEST);
        filaCabecera.add(colPunt, BorderLayout.EAST);
        tarjeta.add(filaCabecera);

        JSeparator sep = new JSeparator();
        sep.setForeground(ROSA);
        sep.setAlignmentX(LEFT_ALIGNMENT);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        tarjeta.add(sep);
        tarjeta.add(Box.createVerticalStrut(6));

        if (puntuaciones.isEmpty()) {
            JLabel vacio = new JLabel("Todavía no hay partidas registradas.");
            vacio.setFont(new Font("Arial", Font.ITALIC, 13));
            vacio.setForeground(GRIS_TEXTO);
            vacio.setAlignmentX(LEFT_ALIGNMENT);
            vacio.setBorder(BorderFactory.createEmptyBorder(16, 4, 16, 4));
            tarjeta.add(vacio);
        } else {
            for (int i = 0; i < puntuaciones.size(); i++) {
                tarjeta.add(crearFilaRanking(i + 1, puntuaciones.get(i)));
            }
        }

        tarjeta.add(Box.createVerticalGlue());
        return tarjeta;
    }

    private JLabel etiquetaColumna(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", Font.BOLD, 11));
        l.setForeground(ROSA);
        return l;
    }

    private JPanel crearFilaRanking(int posicion, Puntuacion puntuacion) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);
        fila.setAlignmentX(LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        fila.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 244)));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        izquierda.setOpaque(false);
        izquierda.add(crearBadge(posicion));
        JLabel jugador = new JLabel(puntuacion.getUsername());
        jugador.setFont(new Font("Arial", Font.PLAIN, 15));
        jugador.setForeground(TEXTO_OSCURO);
        izquierda.add(jugador);
        fila.add(izquierda, BorderLayout.WEST);

        JLabel puntos = new JLabel(String.valueOf(puntuacion.getPuntos()));
        puntos.setFont(new Font("Arial", Font.BOLD, 16));
        puntos.setForeground(TEXTO_OSCURO);
        puntos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
        fila.add(puntos, BorderLayout.EAST);

        return fila;
    }

    // Badge circular con el número de posición
    private JComponent crearBadge(final int posicion) {
        final boolean primero = posicion == 1;
        JComponent badge = new JComponent() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(primero ? ROSA : ROSA_CLARO);
                g2.fillOval(0, 0, 30, 30);
                g2.setColor(primero ? Color.WHITE : ROSA);
                g2.setFont(new Font("Arial", Font.BOLD, 13));
                String texto = String.valueOf(posicion);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (30 - fm.stringWidth(texto)) / 2;
                int ty = (30 - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(texto, tx, ty);
                g2.dispose();
            }
        };
        badge.setPreferredSize(new Dimension(30, 30));
        return badge;
    }

    // ---------- Pestaña Usuarios ----------
    private JPanel crearUsuarios(ControladorFicheros controladorFicheros) {
        java.util.List<Usuario> usuarios = controladorFicheros.cargarUsuarios();

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1, true),
                BorderFactory.createEmptyBorder(22, 26, 22, 26)));

        // Título + recuento
        JPanel cabeceraTarjeta = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        cabeceraTarjeta.setOpaque(false);
        cabeceraTarjeta.setAlignmentX(LEFT_ALIGNMENT);
        JLabel labelTitulo = new JLabel("Usuarios registrados");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 19));
        labelTitulo.setForeground(TEXTO_OSCURO);
        JLabel labelCuenta = new JLabel(usuarios.size()
                + (usuarios.size() == 1 ? " cuenta" : " cuentas"));
        labelCuenta.setFont(new Font("Arial", Font.PLAIN, 14));
        labelCuenta.setForeground(GRIS_TEXTO);
        cabeceraTarjeta.add(labelTitulo);
        cabeceraTarjeta.add(labelCuenta);
        tarjeta.add(cabeceraTarjeta);
        tarjeta.add(Box.createVerticalStrut(14));

        // Cabecera de columnas
        tarjeta.add(crearCabeceraUsuarios());

        JSeparator sep = new JSeparator();
        sep.setForeground(ROSA);
        sep.setAlignmentX(LEFT_ALIGNMENT);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        tarjeta.add(sep);
        tarjeta.add(Box.createVerticalStrut(6));

        if (usuarios.isEmpty()) {
            JLabel vacio = new JLabel("No hay usuarios registrados.");
            vacio.setFont(new Font("Arial", Font.ITALIC, 13));
            vacio.setForeground(GRIS_TEXTO);
            vacio.setAlignmentX(LEFT_ALIGNMENT);
            vacio.setBorder(BorderFactory.createEmptyBorder(16, 4, 16, 4));
            tarjeta.add(vacio);
        } else {
            for (Usuario usuario : usuarios) {
                java.util.List<RegistroPartida> partidas =
                        controladorFicheros.cargarPartidasDe(usuario.getUsername());
                RegistroPartida ultima = partidas.isEmpty() ? null : partidas.get(0);
                tarjeta.add(crearFilaUsuario(usuario, ultima));
            }
        }
        tarjeta.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(tarjeta);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(FONDO);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 24, 28, 24));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearCabeceraUsuarios() {
        JPanel fila = new JPanel(new GridBagLayout());
        fila.setOpaque(false);
        fila.setAlignmentX(LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));

        anadirCelda(fila, etiquetaColumna("USUARIO"), 0, 0.26, SwingConstants.LEFT);
        anadirCelda(fila, etiquetaColumna("ROL"), 1, 0.20, SwingConstants.LEFT);
        anadirCelda(fila, etiquetaColumna("ÚLTIMO JUEGO"), 2, 0.22, SwingConstants.LEFT);
        anadirCelda(fila, etiquetaColumna("FECHA"), 3, 0.18, SwingConstants.LEFT);
        JLabel punt = etiquetaColumna("PUNTUACIÓN");
        punt.setHorizontalAlignment(SwingConstants.RIGHT);
        anadirCelda(fila, punt, 4, 0.14, SwingConstants.RIGHT);
        return fila;
    }

    private JPanel crearFilaUsuario(Usuario usuario, RegistroPartida ultima) {
        JPanel fila = new JPanel(new GridBagLayout());
        fila.setOpaque(false);
        fila.setAlignmentX(LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        fila.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
                new Color(240, 240, 244)));

        JLabel nombre = new JLabel(usuario.getUsername());
        nombre.setFont(new Font("Arial", Font.BOLD, 15));
        nombre.setForeground(TEXTO_OSCURO);
        anadirCelda(fila, nombre, 0, 0.26, SwingConstants.LEFT);

        JPanel wrapRol = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapRol.setOpaque(false);
        wrapRol.add(crearBadgeRol(usuario instanceof Administrador));
        anadirCelda(fila, wrapRol, 1, 0.20, SwingConstants.LEFT);

        anadirCelda(fila, celdaTexto(ultima == null ? "—" : ultima.getNombreJuego(),
                false), 2, 0.22, SwingConstants.LEFT);
        anadirCelda(fila, celdaTexto(ultima == null ? "—"
                : FechaUtil.formatear(ultima.getFecha()), false),
                3, 0.18, SwingConstants.LEFT);

        JLabel puntos = celdaTexto(ultima == null ? "—"
                : String.valueOf(ultima.getPuntuacion()), true);
        puntos.setHorizontalAlignment(SwingConstants.RIGHT);
        puntos.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
        anadirCelda(fila, puntos, 4, 0.14, SwingConstants.RIGHT);

        return fila;
    }

    private JLabel celdaTexto(String texto, boolean negrita) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Arial", negrita ? Font.BOLD : Font.PLAIN, 15));
        l.setForeground(negrita ? TEXTO_OSCURO : new Color(90, 90, 100));
        return l;
    }

    // Pill de rol: gris para Jugador, rosa claro para Administrador
    private JComponent crearBadgeRol(final boolean admin) {
        final String texto = admin ? "Administrador" : "Jugador";
        JLabel badge = new JLabel(texto, SwingConstants.CENTER) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(admin ? ROSA_CLARO : new Color(232, 233, 238));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setOpaque(false);
        badge.setForeground(admin ? ROSA : new Color(90, 90, 100));
        badge.setFont(new Font("Arial", Font.BOLD, 12));
        badge.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        return badge;
    }

    private void anadirCelda(JPanel fila, JComponent componente, int columna,
            double peso, int alineacion) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = columna;
        gbc.gridy = 0;
        gbc.weightx = peso;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = alineacion == SwingConstants.RIGHT
                ? GridBagConstraints.EAST : GridBagConstraints.WEST;
        fila.add(componente, gbc);
    }
}