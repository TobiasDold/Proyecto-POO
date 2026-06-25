package plataformajuegos.vista;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import plataformajuegos.controlador.ControladorPrincipal;
import plataformajuegos.modelo.usuarios.Administrador;
import plataformajuegos.modelo.usuarios.Usuario;
import plataformajuegos.util.*;

public class PanelMenu extends JPanel implements ActionListener{

    private JPanel contenedorDer;
    private CardLayout cardLayoutDer;
    private JButton decoNombre;
    private BotonRedondeado botonPasapalabra;
    private BotonRedondeado botonAhorcado;
    private BotonRedondeado botonEstadisticas;
    private ControladorPrincipal cp;
    private Usuario user;
    
    public PanelMenu(ControladorPrincipal cp){
        this.cp=cp;

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        JPanel panelIzq = new JPanel();
        panelIzq.setLayout(null);
        panelIzq.setBackground(new Color(230, 40, 106));
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.4;

        decoNombre = new BotonRedondeado("S", 30);
        decoNombre.setText("S");
        decoNombre.setFont(new Font("Arial Black", Font.BOLD, 20));
        decoNombre.setBounds(40, 60, 50, 50);
        decoNombre.setForeground(new Color(255, 105, 180));
        decoNombre.setEnabled(false);
        panelIzq.add(decoNombre);

        JLabel labelLogo = new JLabel("Sistema Juegos");
        labelLogo.setFont(new Font("Arial", Font.BOLD, 32));
        labelLogo.setForeground(Color.WHITE);
        labelLogo.setBounds(100, 60, 300, 50);
        panelIzq.add(labelLogo);

        JLabel labelTitulo = new JLabel("<html>Juega, compite<br>y adivina.</html>");
        labelTitulo.setFont(new Font("Arial Black", Font.BOLD, 36));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setBounds(50, 200, 400, 120);
        panelIzq.add(labelTitulo);

        JLabel labelSubTitulo = new JLabel("<html>Dos clásicos de palabras en una<br>sola app de escritorio.</html>");
        labelSubTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelSubTitulo.setForeground(Color.WHITE);
        labelSubTitulo.setBounds(50, 520, 400, 60);
        panelIzq.add(labelSubTitulo);

        this.add(panelIzq, gbc);

        cardLayoutDer = new CardLayout();
        contenedorDer = new JPanel(cardLayoutDer);

        JPanel vistaUsuario = crearVistaUsuario();
        JPanel vistaAdmin = crearVistaAdmin();

        contenedorDer.add(crearVistaUsuario(), "VistaUsuario");
        contenedorDer.add(crearVistaAdmin(), "VistaAdmin");
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.weightx = 0.6;

        
        this.add(contenedorDer, gbc);
    }

    public JPanel crearVistaUsuario(){
        JPanel vistaUsuario = new JPanel();
        vistaUsuario.setLayout(null);
        vistaUsuario.setBackground(Color.WHITE);

        JLabel labelTitulo = new JLabel("MENU PRINCIPAL");
        labelTitulo.setBounds(60, 160, 380, 20);
        labelTitulo.setForeground(new Color(255, 0, 127));
        vistaUsuario.add(labelTitulo);

        JLabel labelPregunta = new JLabel("¿A que jugamos?");
        labelPregunta.setFont(new Font("Arial", Font.BOLD, 32));
        labelPregunta.setBounds(60, 190, 380, 40);
        vistaUsuario.add(labelPregunta);

        JLabel labelDescripcion = new JLabel("<html>Elige un juego para empezar una partida o revisa cómo vas.</html>");
        labelDescripcion.setBounds(60, 240, 470, 50);
        labelDescripcion.setFont(new Font("Arial", Font.BOLD, 16));
        labelDescripcion.setForeground(Color.LIGHT_GRAY);
        vistaUsuario.add(labelDescripcion);

        botonPasapalabra = new BotonRedondeado("<html><font size='5'><b>Pasapalabra</b></font><br><font size='3'><b>Completa el rosco<br>respondiendo de la<br>A a la Z.</b></font></html>" , 20);
        botonPasapalabra.setBounds(60, 300, 220, 150);
        botonPasapalabra.setBackground(Color.WHITE);
        botonPasapalabra.setFocusPainted(false);
        vistaUsuario.add(botonPasapalabra);

        botonAhorcado = new BotonRedondeado("<html><font size='5'><b>Ahorcado</b></font><br><font size='3'><b>Adivina la palabra antes<br>de agotar los intentos.</b></font></html>" , 20);
        botonAhorcado.setBounds(290, 300, 220, 150);
        botonAhorcado.setBackground(Color.WHITE);
        vistaUsuario.add(botonAhorcado);

        botonEstadisticas = new BotonRedondeado("<html><font size='5'><b>Estadisticas</b></font></html>" , 20);
        botonEstadisticas.setBounds(60, 460, 450, 50);
        botonEstadisticas.setBackground(Color.WHITE);
        vistaUsuario.add(botonEstadisticas);

        this.user = cp.getUsuarioActual();
        JLabel labelNombre = new JLabel(user.getUsername());
        labelNombre.setBounds(500, 10, 300, 50);
        labelNombre.setFont(new Font("Arial", Font.BOLD, 16));
        labelNombre.setForeground(Color.BLACK);
        vistaUsuario.add(labelNombre);

        JLabel labelRol = new JLabel(user.getRol());
        labelRol.setBounds(500, 30, 300, 35);
        labelRol.setFont(new Font("Arial", Font.BOLD, 10));
        labelRol.setForeground(new Color(255, 0, 127));
        vistaUsuario.add(labelRol);

        JLabel labelCerrarSesion = new JLabel("Cerrar Sesion");
        labelCerrarSesion.setForeground(new Color(230, 40, 106));
        labelCerrarSesion.setFont(labelCerrarSesion.getFont().deriveFont(Font.BOLD));
        labelCerrarSesion.setBounds(40, 650, 100, 30);
        labelCerrarSesion.setHorizontalAlignment(SwingConstants.LEFT);
        vistaUsuario.add(labelCerrarSesion);

        //Hago que el cursor se ponga como una manito
        labelCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Le añado la accion del clic solo a la palabra "Registrate"
        labelCerrarSesion.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                cp.mostrarLogin();
            }
        });
        vistaUsuario.add(labelCerrarSesion);

        return vistaUsuario;
    }
    public JPanel crearVistaAdmin(){
        JPanel vistaAdmin = new JPanel();
        vistaAdmin.setLayout(null);
        vistaAdmin.setBackground(Color.WHITE);

        JLabel labelTitulo = new JLabel("MENU PRINCIPAL");
        labelTitulo.setBounds(60, 160, 380, 20);
        labelTitulo.setForeground(new Color(255, 0, 127));
        vistaAdmin.add(labelTitulo);

        JLabel labelPregunta = new JLabel("¿A que jugamos?");
        labelPregunta.setFont(new Font("Arial", Font.BOLD, 32));
        labelPregunta.setBounds(60, 190, 380, 40);
        vistaAdmin.add(labelPregunta);

        JLabel labelDescripcion = new JLabel("<html>Elige un juego para empezar una partida o revisa cómo vas.</html>");
        labelDescripcion.setBounds(60, 240, 470, 50);
        labelDescripcion.setFont(new Font("Arial", Font.BOLD, 16));
        labelDescripcion.setForeground(Color.LIGHT_GRAY);
        vistaAdmin.add(labelDescripcion);

        botonPasapalabra = new BotonRedondeado("<html><font size='5'><b>Pasapalabra</b></font><br><font size='3'><b>Completa el rosco<br>respondiendo de la<br>A a la Z.</b></font></html>" , 20);
        botonPasapalabra.setBounds(60, 300, 220, 150);
        botonPasapalabra.setBackground(Color.WHITE);
        botonPasapalabra.setFocusPainted(false);
        vistaAdmin.add(botonPasapalabra);

        botonAhorcado = new BotonRedondeado("<html><font size='5'><b>Ahorcado</b></font><br><font size='3'><b>Adivina la palabra antes<br>de agotar los intentos.</b></font></html>" , 20);
        botonAhorcado.setBounds(290, 300, 220, 150);
        botonAhorcado.setBackground(Color.WHITE);
        vistaAdmin.add(botonAhorcado);

        botonEstadisticas = new BotonRedondeado("<html><font size='5'><b>Estadisticas</b></font></html>" , 20);
        botonEstadisticas.setBounds(60, 460, 225, 50);
        botonEstadisticas.setBackground(Color.WHITE);
        vistaAdmin.add(botonEstadisticas);

        botonEstadisticas = new BotonRedondeado("<html><font size='5'><b>Panel Admin</b></font></html>" , 20);
        botonEstadisticas.setBounds(60, 690, 225, 50);
        botonEstadisticas.setBackground(Color.WHITE);
        vistaAdmin.add(botonEstadisticas);

        this.user = cp.getUsuarioActual();
        JLabel labelNombre = new JLabel(user.getUsername());
        labelNombre.setBounds(500, 10, 300, 50);
        labelNombre.setFont(new Font("Arial", Font.BOLD, 16));
        labelNombre.setForeground(Color.BLACK);
        vistaAdmin.add(labelNombre);

        JLabel labelRol = new JLabel(user.getRol());
        labelRol.setBounds(500, 30, 300, 35);
        labelRol.setFont(new Font("Arial", Font.BOLD, 10));
        labelRol.setForeground(new Color(255, 0, 127));
        vistaAdmin.add(labelRol);

        JLabel labelCerrarSesion = new JLabel("Cerrar Sesion");
        labelCerrarSesion.setForeground(new Color(230, 40, 106));
        labelCerrarSesion.setFont(labelCerrarSesion.getFont().deriveFont(Font.BOLD));
        labelCerrarSesion.setBounds(40, 650, 100, 30);
        labelCerrarSesion.setHorizontalAlignment(SwingConstants.LEFT);
        vistaAdmin.add(labelCerrarSesion);

        //Hago que el cursor se ponga como una manito
        labelCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Le añado la accion del clic solo a la palabra "Registrate"
        labelCerrarSesion.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                cp.mostrarLogin();
            }
        });
        vistaAdmin.add(labelCerrarSesion);

        return vistaAdmin;
    }

    public void actionPerformed(ActionEvent event){}

    public void mostrarVistaSegunRol(){
    Usuario user = cp.getUsuarioActual();
    if(user instanceof Administrador){
        cardLayoutDer.show(contenedorDer, "VISTA_ADMIN");
    } else {
        cardLayoutDer.show(contenedorDer, "VISTA_USUARIO");
    }
}
}
