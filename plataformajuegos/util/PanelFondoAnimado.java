package plataformajuegos.util;

import javax.swing.*;
import java.awt.*;

public class PanelFondoAnimado extends JPanel {
    private Image imagenFondo;
    private boolean animacionActiva = true;

    // El constructor recibe la ruta de la imagen (ej: "src/imagenes/mcqueen.gif")
    public PanelFondoAnimado(String rutaImagen) {
        // Cargar la imagen usando Toolkit para que Java procese el GIF correctamente
        imagenFondo = Toolkit.getDefaultToolkit().createImage(rutaImagen);

        // Configurar un Timer para que detenga el movimiento después de unos segundos
        // Cambia 3000 (3 segundos) por la duración real de tu GIF
        int duracionDeLaAnimacion = 3000;

        Timer timer = new Timer(duracionDeLaAnimacion, e -> {
            animacionActiva = false; // Al volverse false, detiene el bucle de repintado
            repaint(); // Un último repintado para asegurar que quede estático en el último frame
        });

        timer.setRepeats(false); // IMPORTANTE: Para que el timer solo corra una vez
        timer.start(); // Inicia la cuenta regresiva al crear el panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Si la imagen ya cargó, la dibujamos en todo el ancho y alto del panel
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);

            // Si la animación sigue activa, le pedimos a Swing que vuelva a dibujar
            // inmediatamente
            if (animacionActiva) {
                repaint();
            }
        }
    }
}
