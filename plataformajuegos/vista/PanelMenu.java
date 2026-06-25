package plataformajuegos.vista;

import javax.swing.*;

public class PanelMenu extends JPanel{

    VentanaPrincipal ventana;

    public PanelMenu(){
        setLayout(null);
        JLabel label1 = new JLabel("Bienvenido al Menu");
        label1.setBounds(500, 400, 150, 75);
        add(label1);
    }
    
}
