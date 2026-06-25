package plataformajuegos.vista;

import javax.swing.*;

import plataformajuegos.controlador.ControladorPrincipal;

import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel contenedor = new JPanel(layout);

    public VentanaPrincipal(ControladorPrincipal cp) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setTitle("Proyecto POO");
        add(contenedor);

        agregarPanel(new PanelLogin(cp), "LOGIN");
        agregarPanel(new PanelMenu(),"MENU");

        mostrarPanel("LOGIN");
    }

    public void agregarPanel(JPanel panel, String clavePanel) {
        contenedor.add(panel, clavePanel);
    }

    public void mostrarPanel(String clavePanel) {
        layout.show(contenedor, clavePanel);
    }

}
