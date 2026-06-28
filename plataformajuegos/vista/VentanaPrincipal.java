package plataformajuegos.vista;

import java.awt.*;

import javax.swing.*;

import plataformajuegos.controlador.ControladorPrincipal;

public class VentanaPrincipal extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel contenedor = new JPanel(layout);

    public VentanaPrincipal(ControladorPrincipal controladorPrincipal) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setTitle("Proyecto POO");
        setResizable(false);
        add(contenedor);

        agregarPanel(new PanelLogin(controladorPrincipal), "LOGIN");
        mostrarPanel("LOGIN");
    }

    public void agregarPanel(JPanel panel, String clavePanel) {
        panel.setName(clavePanel);
        contenedor.add(panel, clavePanel);
    }

    public void reemplazarPanel(JPanel panel, String clavePanel) {
        for (Component componente : contenedor.getComponents()) {
            if (clavePanel.equals(componente.getName())) {
                contenedor.remove(componente);
                break;
            }
        }
        agregarPanel(panel, clavePanel);
        contenedor.revalidate();
        contenedor.repaint();
    }

    public void mostrarPanel(String clavePanel) {
        layout.show(contenedor, clavePanel);
    }
}
