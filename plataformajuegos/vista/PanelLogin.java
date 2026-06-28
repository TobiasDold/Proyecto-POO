package plataformajuegos.vista;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import plataformajuegos.controlador.ControladorPrincipal;
import plataformajuegos.util.*;

public class PanelLogin extends JPanel implements ActionListener {

    private PanelFondoAnimado contenedorDer;
    private CardLayout cardLayoutDer;
    private JButton decoPasapalabra;
    private JButton decoAhorcado;
    private JButton decoLogo;
    private JButton botonInicioSesion;
    private JButton botonCrearCuenta;
    private JLabel labelMensajeLogin;
    private JLabel labelMensajeRegistro;
    private JTextField textUsuarioLogin;
    private JPasswordField textContraseñaLogin;
    private JTextField textUsuarioRegistro;
    private JPasswordField regPass1;
    private JPasswordField regPass2;
    private ControladorPrincipal cp;

    public PanelLogin(ControladorPrincipal cp) {
        this.cp = cp;

        this.setLayout(new GridLayout(1, 2));

        JPanel panelIzq = new JPanel();
        panelIzq.setLayout(null);
        panelIzq.setBackground(new Color(230, 40, 106));

        decoLogo = new BotonRedondeado("S", 30);
        decoLogo.setText("S");
        decoLogo.setFont(new Font("Arial Black", Font.BOLD, 20));
        decoLogo.setBounds(40, 60, 50, 50);
        decoLogo.setForeground(new Color(255, 105, 180));
        decoLogo.setEnabled(false);
        panelIzq.add(decoLogo);

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

        JLabel labelSubTitulo = new JLabel("<html>Dos clásicos de palabras en una sola app de<br>escritorio.</html>");
        labelSubTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelSubTitulo.setForeground(Color.WHITE);
        labelSubTitulo.setBounds(50, 400, 400, 60);
        panelIzq.add(labelSubTitulo);

        decoPasapalabra = new BotonRedondeado("Pasapalabra", 30);
        decoPasapalabra.setBounds(50, 520, 130, 40);
        decoPasapalabra.setEnabled(false);
        decoPasapalabra.setFocusable(false);
        decoPasapalabra.setForeground(Color.WHITE);
        panelIzq.add(decoPasapalabra);

        decoAhorcado = new BotonRedondeado("Ahorcado", 30);
        decoAhorcado.setBounds(195, 520, 110, 40);
        decoAhorcado.setEnabled(false);
        decoAhorcado.setFocusable(false);
        panelIzq.add(decoAhorcado);

        cardLayoutDer = new CardLayout();
        contenedorDer = new PanelFondoAnimado("plataformajuegos/datos/giffondo/gifToystory.gif");
        contenedorDer.setLayout(cardLayoutDer);

        JPanel vistaLogin = crearVistaLogin();
        JPanel vistaRegistro = crearVistaRegistro();

        contenedorDer.add(vistaLogin, "VistaLogin");
        contenedorDer.add(vistaRegistro, "VistaRegistro");

        this.add(panelIzq);
        this.add(contenedorDer);
    }

    public JPanel crearVistaLogin() {
        JPanel vistaLogin = new JPanel();
        vistaLogin.setLayout(null);
        vistaLogin.setBackground(Color.WHITE);
        vistaLogin.setOpaque(false);

        JLabel labelTitulo = new JLabel("BIENVENIDO DE NUEVO");
        labelTitulo.setBounds(60, 160, 380, 20);
        labelTitulo.setForeground(new Color(255, 0, 127));
        vistaLogin.add(labelTitulo);

        JLabel labelIniciarSesion = new JLabel("Iniciar Sesion");
        labelIniciarSesion.setFont(new Font("Arial", Font.BOLD, 32));
        labelIniciarSesion.setBounds(60, 190, 380, 40);
        vistaLogin.add(labelIniciarSesion);

        JLabel labelDescripcion = new JLabel("<html>Introduce tus credenciales para acceder a tus<br>partidas.</html>");
        labelDescripcion.setBounds(60, 240, 380, 50);
        labelDescripcion.setFont(new Font("Arial", Font.BOLD, 14));
        labelDescripcion.setForeground(Color.LIGHT_GRAY);
        vistaLogin.add(labelDescripcion);

        JLabel labelUsuario = new JLabel("USUARIO");
        labelUsuario.setBounds(60, 310, 380, 20);
        labelUsuario.setForeground(Color.GRAY);
        vistaLogin.add(labelUsuario);

        textUsuarioLogin = new TextFieldRedondeado(0, 15);
        textUsuarioLogin.setBounds(60, 340, 380, 45);
        vistaLogin.add(textUsuarioLogin);

        JLabel labelContraseña = new JLabel("CONTRASEÑA");
        labelContraseña.setBounds(60, 410, 380, 20);
        labelContraseña.setForeground(Color.GRAY);
        vistaLogin.add(labelContraseña);

        textContraseñaLogin = new PasswordFieldRedondeado(0, 15);
        textContraseñaLogin.setBounds(60, 440, 380, 45);
        vistaLogin.add(textContraseñaLogin);

        botonInicioSesion = new BotonRedondeado("Iniciar Sesion", 20);
        botonInicioSesion.setBounds(60, 530, 380, 50);
        botonInicioSesion.setBackground(new Color(227, 47, 114));
        botonInicioSesion.setFocusPainted(false);
        vistaLogin.add(botonInicioSesion);
        botonInicioSesion.addActionListener(this);

        labelMensajeLogin = new JLabel(" ");
        labelMensajeLogin.setBounds(60, 595, 380, 30);
        labelMensajeLogin.setForeground(Color.RED);
        vistaLogin.add(labelMensajeLogin);

        JLabel labelSinCuenta = new JLabel("¿No tienes cuenta? ");
        labelSinCuenta.setForeground(Color.GRAY);
        labelSinCuenta.setBounds(140, 650, 130, 30);
        labelSinCuenta.setHorizontalAlignment((SwingConstants.RIGHT));
        vistaLogin.add(labelSinCuenta);

        JLabel labelRegistrate = new JLabel("Registrate");
        labelRegistrate.setForeground(new Color(230, 40, 106));
        labelRegistrate.setFont(labelRegistrate.getFont().deriveFont(Font.BOLD));
        labelRegistrate.setBounds(275, 650, 100, 30);
        labelRegistrate.setHorizontalAlignment(SwingConstants.LEFT);
        vistaLogin.add(labelRegistrate);

        // Hago que el cursor se ponga como una manito
        labelRegistrate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Le añado la accion del clic solo a la palabra "Registrate"
        labelRegistrate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                cardLayoutDer.show(contenedorDer, "VistaRegistro");
            }
        });
        vistaLogin.add(labelRegistrate);

        return vistaLogin;
    }

    public JPanel crearVistaRegistro() {
        JPanel vistaRegistro = new JPanel();
        vistaRegistro.setLayout(null);
        vistaRegistro.setBackground(Color.WHITE);
        vistaRegistro.setOpaque(false);

        JLabel labelTitulo = new JLabel("CREAR CUENTA");
        labelTitulo.setBounds(60, 90, 380, 20);
        labelTitulo.setForeground(new Color(255, 0, 127));
        vistaRegistro.add(labelTitulo);

        JLabel labelCrearCuenta = new JLabel("Crear una cuenta");
        labelCrearCuenta.setFont(new Font("Arial", Font.BOLD, 32));
        labelCrearCuenta.setForeground(Color.BLACK);
        labelCrearCuenta.setBounds(60, 120, 380, 40);
        vistaRegistro.add(labelCrearCuenta);

        JLabel labelDescripcion = new JLabel("<html>Elige un usuario y una contraseña para empezar a<br>jugar.</html>");
        labelDescripcion.setBounds(60, 170, 380, 40);
        labelDescripcion.setFont(new Font("Arial", Font.BOLD, 14));
        labelDescripcion.setForeground(Color.LIGHT_GRAY);
        vistaRegistro.add(labelDescripcion);

        JLabel labelUsuario = new JLabel("USUARIO");
        labelUsuario.setBounds(60, 240, 380, 20);
        labelUsuario.setForeground(Color.GRAY);
        vistaRegistro.add(labelUsuario);

        textUsuarioRegistro = new TextFieldRedondeado(0, 20);
        textUsuarioRegistro.setBounds(60, 270, 380, 45);
        vistaRegistro.add(textUsuarioRegistro);

        JLabel labelContraseña = new JLabel("CONTRASEÑA");
        labelContraseña.setBounds(60, 340, 380, 20);
        labelContraseña.setForeground(Color.GRAY);
        vistaRegistro.add(labelContraseña);

        regPass1 = new PasswordFieldRedondeado(0, 15);
        regPass1.setBounds(60, 370, 380, 45);
        vistaRegistro.add(regPass1);

        JLabel labelContraseña2 = new JLabel("CONFIRMAR CONTRASEÑA");
        labelContraseña2.setBounds(60, 440, 380, 20);
        labelContraseña2.setForeground(Color.GRAY);
        vistaRegistro.add(labelContraseña2);

        regPass2 = new PasswordFieldRedondeado(0, 15);
        regPass2.setBounds(60, 470, 380, 45);
        vistaRegistro.add(regPass2);

        botonCrearCuenta = new BotonRedondeado("Crear Cuenta", 15);
        botonCrearCuenta.setBounds(60, 560, 380, 50);
        botonCrearCuenta.setBackground(new Color(227, 47, 114));
        botonCrearCuenta.setFocusPainted(false);
        vistaRegistro.add(botonCrearCuenta);
        botonCrearCuenta.addActionListener(this);

        labelMensajeRegistro = new JLabel(" ");
        labelMensajeRegistro.setBounds(60, 610, 380, 30);
        labelMensajeRegistro.setForeground(Color.RED);
        vistaRegistro.add(labelMensajeRegistro);

        JLabel labelCuenta = new JLabel("¿Ya tienes cuenta? ");
        labelCuenta.setForeground(Color.GRAY);
        labelCuenta.setBounds(135, 650, 140, 30);
        labelCuenta.setHorizontalAlignment((SwingConstants.RIGHT));
        vistaRegistro.add(labelCuenta);

        JLabel labelIniciaSesion = new JLabel("Inicia Sesion");
        labelIniciaSesion.setForeground(new Color(230, 40, 106));
        labelIniciaSesion.setFont(labelIniciaSesion.getFont().deriveFont(Font.BOLD));
        labelIniciaSesion.setBounds(280, 650, 100, 30);
        labelIniciaSesion.setHorizontalAlignment(SwingConstants.LEFT);
        vistaRegistro.add(labelIniciaSesion);

        // Hago que el cursor se ponga como una manito
        labelIniciaSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Le añado la accion del clic solo a la palabra "Registrate"
        labelIniciaSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                cardLayoutDer.show(contenedorDer, "VistaLogin");
            }
        });
        vistaRegistro.add(labelIniciaSesion);

        return vistaRegistro;
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == botonInicioSesion) {
            String username = textUsuarioLogin.getText();
            String password = new String(textContraseñaLogin.getPassword());
            if (!cp.login(username, password)) {
                labelMensajeLogin.setText("Usuario o contraseña incorrectos.");
            }
            textUsuarioLogin.setText("");
            textContraseñaLogin.setText("");
        }
        if (event.getSource() == botonCrearCuenta) {
            String username = textUsuarioRegistro.getText();
            String regPass1 = new String(this.regPass1.getPassword());
            String regPass2 = new String(this.regPass2.getPassword());
            String usuario = cp.registrar(username, regPass1, regPass2);
            if (usuario.equals("No coinciden")) {
                labelMensajeRegistro.setText("Las contraseñas no coinciden.");
            }
            if (usuario.equals("Usuario existente")) {
                labelMensajeRegistro.setText("El usuario ya existe.");
            }
        }
    }
}
