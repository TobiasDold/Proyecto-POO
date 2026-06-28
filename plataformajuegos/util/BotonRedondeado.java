package plataformajuegos.util;

import javax.swing.*;
import java.awt.*;

public class BotonRedondeado extends JButton {
    private int radio;

    public BotonRedondeado(String texto, int radio) {
        super(texto);
        this.radio = radio;
        setContentAreaFilled(false); // Quita el fondo rectangular por defecto
        setBorderPainted(false); // Quita el borde cuadrado por defecto
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Activa el suavizado para que las esquinas no se vean pixeladas
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Cambia el color dependiendo de si está activo o deshabilitado
        if (!isEnabled()) {
            g2.setColor(new Color(240, 240, 240)); // Color gris cuando está deshabilitado
        } else if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }

        // Dibuja el fondo redondeado
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
        g2.dispose();

        super.paintComponent(g);
    }
}