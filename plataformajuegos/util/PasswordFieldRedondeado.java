package plataformajuegos.util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PasswordFieldRedondeado extends JPasswordField {
    private int radio;
    private Color colorBorde = new Color(220, 220, 230); // Gris suave de tu foto

    public PasswordFieldRedondeado(int columnas, int radio) {
        super(columnas);
        this.radio = radio;
        setOpaque(false); // Clave para que no se pinte el fondo cuadrado por defecto
        
        // Añade un margen interno (padding) para que el texto no toque el borde izquierdo
        setBorder(new EmptyBorder(0, 15, 0, 15)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Pintar el fondo blanco/claro
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);
        
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Pintar el contorno gris fino
        g2.setColor(colorBorde);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);
        
        g2.dispose();
    }
}

