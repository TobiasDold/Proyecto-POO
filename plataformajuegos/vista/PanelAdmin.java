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
    public PanelAdmin(final ControladorPrincipal controladorPrincipal,
            ControladorFicheros controladorFicheros) {
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 28, 32));
        setBackground(new Color(247, 248, 252));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
        JLabel titulo = new JLabel("PANEL DE ADMINISTRACION");
        titulo.setFont(new Font("Arial", Font.BOLD, 27));
        titulo.setForeground(new Color(220, 35, 100));
        JButton volver = new JButton("Volver al menu");
        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                controladorPrincipal.volverAlMenu();
            }
        });
        cabecera.add(titulo, BorderLayout.WEST);
        cabecera.add(volver, BorderLayout.EAST);
        add(cabecera, BorderLayout.NORTH);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Rankings", crearRankings(controladorFicheros));
        pestanas.addTab("Usuarios y ultimas partidas",
                crearUsuarios(controladorFicheros));
        add(pestanas, BorderLayout.CENTER);
    }

    private JPanel crearRankings(ControladorFicheros controladorFicheros) {
        JPanel rankings = new JPanel(new GridLayout(1, 2, 14, 0));
        rankings.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        rankings.add(crearTablaRanking("Ahorcado",
                controladorFicheros.cargarRanking("Ahorcado")));
        rankings.add(crearTablaRanking("Pasapalabra",
                controladorFicheros.cargarRanking("Pasapalabra")));
        return rankings;
    }

    private JPanel crearTablaRanking(String juego, List<Puntuacion> puntuaciones) {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JLabel titulo = new JLabel(juego, JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titulo, BorderLayout.NORTH);

        DefaultTableModel modelo = modeloNoEditable(
                new Object[] { "Posicion", "Jugador", "Mejor puntuacion" });
        for (int i = 0; i < puntuaciones.size(); i++) {
            Puntuacion puntuacion = puntuaciones.get(i);
            modelo.addRow(new Object[] {
                    i + 1, puntuacion.getUsername(), puntuacion.getPuntos()
            });
        }
        panel.add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearUsuarios(ControladorFicheros controladorFicheros) {
        DefaultTableModel modelo = modeloNoEditable(new Object[] {
                "Usuario", "Rol", "Ultimo juego", "Fecha", "Puntuacion"
        });
        for (Usuario usuario : controladorFicheros.cargarUsuarios()) {
            List<RegistroPartida> partidas = controladorFicheros.cargarPartidasDe(usuario.getUsername());
            RegistroPartida ultima = partidas.isEmpty() ? null : partidas.get(0);
            modelo.addRow(new Object[] {
                    usuario.getUsername(),
                    usuario.getRol(),
                    ultima == null ? "-" : ultima.getNombreJuego(),
                    ultima == null ? "-" : FechaUtil.formatear(ultima.getFecha()),
                    ultima == null ? "-" : ultima.getPuntuacion()
            });
        }
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private DefaultTableModel modeloNoEditable(Object[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }
}
