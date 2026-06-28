package plataformajuegos.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import plataformajuegos.controlador.ControladorPrincipal;
import plataformajuegos.modelo.usuarios.RegistroPartida;
import plataformajuegos.util.FechaUtil;

public class PanelEstadisticas extends JPanel {
    public PanelEstadisticas(final ControladorPrincipal controladorPrincipal,
            List<RegistroPartida> partidas) {
        setLayout(new BorderLayout(16, 16));
        setBorder(BorderFactory.createEmptyBorder(28, 36, 32, 36));
        setBackground(new Color(247, 248, 252));

        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
        JLabel titulo = new JLabel("MIS ESTADISTICAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
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

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[] {"Fecha", "Juego", "Puntuacion"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        for (RegistroPartida partida : partidas) {
            modelo.addRow(new Object[] {
                    FechaUtil.formatear(partida.getFecha()),
                    partida.getNombreJuego(),
                    partida.getPuntuacion()
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setReorderingAllowed(false);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JLabel resumen = new JLabel(partidas.isEmpty()
                ? "Todavia no hay partidas terminadas."
                : "Partidas terminadas: " + partidas.size());
        resumen.setFont(new Font("Arial", Font.BOLD, 15));
        add(resumen, BorderLayout.SOUTH);
    }
}
