package plataformajuegos.vista;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import plataformajuegos.controlador.ControladorPrincipal;

public class PanelLogin extends JPanel implements ActionListener{

    private JPanel contenedorDer;
    private CardLayout cardLayoutDer;
    private JButton decoPasapalabra;
    private JButton decoAhorcado;
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

    
    public PanelLogin(ControladorPrincipal cp){
        this.cp=cp;

        this.setLayout(new GridLayout(1, 2));

        JPanel panelIzq = new JPanel();
        panelIzq.setLayout(null);
        panelIzq.setBackground(new Color(230, 40, 106));
        JLabel labelLogo = new JLabel("Sistema Juegos");
        labelLogo.setFont(labelLogo.getFont().deriveFont(Font.BOLD));
        labelLogo.setBounds(40, 60, 300, 50);
        panelIzq.add(labelLogo);

        JLabel labelTitulo = new JLabel("<html>Juega, compite<br>y adivina.</html>");
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(Font.BOLD));
        labelTitulo.setBounds(50, 300, 400, 120);
        panelIzq.add(labelTitulo);

        JLabel labelSubTitulo = new JLabel("<html>Dos clásicos de palabras en una sola app de<br>escritorio.</html>");
        labelSubTitulo.setBounds(50, 430, 400, 60);
        panelIzq.add(labelSubTitulo);

        decoPasapalabra = new JButton("Pasapalabra");
        decoPasapalabra.setBounds(50, 520, 130, 40);
        decoPasapalabra.setEnabled(false);
        decoPasapalabra.setFocusable(false);
        panelIzq.add(decoPasapalabra);

        decoAhorcado = new JButton("Ahorcado");
        decoAhorcado.setBounds(195, 520, 110, 40);
        decoAhorcado.setEnabled(false);
        decoAhorcado.setFocusable(false);
        panelIzq.add(decoAhorcado);

        cardLayoutDer = new CardLayout();
        contenedorDer = new JPanel(cardLayoutDer);

        JPanel vistaLogin = crearVistaLogin();
        JPanel vistaRegistro = crearVistaRegistro();

        contenedorDer.add(vistaLogin, "VistaLogin");
        contenedorDer.add(vistaRegistro, "VistaRegistro");

        this.add(panelIzq);
        this.add(contenedorDer);
    }

    public JPanel crearVistaLogin(){
        JPanel vistaLogin = new JPanel();
        vistaLogin.setLayout(null);
        vistaLogin.setBackground(Color.WHITE);
        JLabel labelTitulo = new JLabel("BIENVENIDO DE NUEVO");
        labelTitulo.setBounds(60, 160, 380, 20);
        labelTitulo.setForeground(Color.PINK);
        vistaLogin.add(labelTitulo);

        JLabel labelIniciarSesion = new JLabel("Iniciar Sesion");
        labelIniciarSesion.setFont(labelIniciarSesion.getFont().deriveFont(Font.BOLD));
        labelIniciarSesion.setBounds(60, 190, 380, 40);
        vistaLogin.add(labelIniciarSesion);

        JLabel labelDescripcion = new JLabel("<html>Introduce tus credenciales para acceder a tus<br>partidas.</html>");
        labelDescripcion.setBounds(60, 240, 380, 40);
        vistaLogin.add(labelDescripcion);

        JLabel labelUsuario = new JLabel("USUARIO");
        labelUsuario.setBounds(60, 310, 380, 20);
        labelUsuario.setForeground(Color.GRAY);
        vistaLogin.add(labelUsuario);

        textUsuarioLogin = new JTextField();
        textUsuarioLogin.setBounds(60, 340, 380, 45);
        vistaLogin.add(textUsuarioLogin);

        JLabel labelContraseña = new JLabel("CONTRASEÑA");
        labelContraseña.setBounds(60, 410, 380, 20);
        labelContraseña.setForeground(Color.GRAY);
        vistaLogin.add(labelContraseña);

        textContraseñaLogin = new JPasswordField();
        textContraseñaLogin.setBounds(60, 440, 380, 45);
        vistaLogin.add(textContraseñaLogin);

        botonInicioSesion = new JButton("Iniciar Sesion");
        botonInicioSesion.setBounds(60, 530, 380, 50);
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

        //Hago que el cursor se ponga como una manito
        labelRegistrate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Le añado la accion del clic solo a la palabra "Registrate"
        labelRegistrate.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                cardLayoutDer.show(contenedorDer, "VistaRegistro");
            }
        });
        vistaLogin.add(labelRegistrate);

        return vistaLogin;
    }
    public JPanel crearVistaRegistro(){
        JPanel vistaRegistro = new JPanel();
        vistaRegistro.setLayout(null);
        vistaRegistro.setBackground(Color.WHITE);
        JLabel labelTitulo = new JLabel("CREAR CUENTA");
        labelTitulo.setBounds(60, 90, 380, 20);
        labelTitulo.setForeground(Color.PINK);
        vistaRegistro.add(labelTitulo);

        JLabel labelCrearCuenta = new JLabel("Crear una cuenta");
        labelCrearCuenta.setFont(labelCrearCuenta.getFont().deriveFont(Font.BOLD));
        labelCrearCuenta.setBounds(60, 120, 380, 40);
        vistaRegistro.add(labelCrearCuenta);

        JLabel labelDescripcion = new JLabel("<html>Elige un usuario y una contraseña para empezar a<br>jugar.</html>");
        labelDescripcion.setBounds(60, 170, 380, 40);
        vistaRegistro.add(labelDescripcion);

        JLabel labelUsuario = new JLabel("USUARIO");
        labelUsuario.setBounds(60, 240, 380, 20);
        labelUsuario.setForeground(Color.GRAY);
        vistaRegistro.add(labelUsuario);

        textUsuarioRegistro = new JTextField();
        textUsuarioRegistro.setBounds(60, 270, 380, 45);
        vistaRegistro.add(textUsuarioRegistro);

        JLabel labelContraseña = new JLabel("CONTRASEÑA");
        labelContraseña.setBounds(60, 340, 380, 20);
        labelContraseña.setForeground(Color.GRAY);
        vistaRegistro.add(labelContraseña);

        regPass1 = new JPasswordField();
        regPass1.setBounds(60, 370, 380, 45);
        vistaRegistro.add(regPass1);

        JLabel labelContraseña2 = new JLabel("CONFIRMAR CONTRASEÑA");
        labelContraseña2.setBounds(60, 440, 380, 20);
        labelContraseña2.setForeground(Color.GRAY);
        vistaRegistro.add(labelContraseña2);

        regPass2 = new JPasswordField();
        regPass2.setBounds(60, 470, 380, 45);
        vistaRegistro.add(regPass2);

        botonCrearCuenta = new JButton("Crear Cuenta");
        botonCrearCuenta.setBounds(60, 560, 380, 50);
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

        //Hago que el cursor se ponga como una manito
        labelIniciaSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //Le añado la accion del clic solo a la palabra "Registrate"
        labelIniciaSesion.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                cardLayoutDer.show(contenedorDer, "VistaLogin");
            }
        });
        vistaRegistro.add(labelIniciaSesion);

        return vistaRegistro;
    }

    public void actionPerformed(ActionEvent event){
        if(event.getSource() == botonInicioSesion){
            String username = textUsuarioLogin.getText();
            String password = new String(textContraseñaLogin.getPassword());
            if(!cp.login(username, password)){
                labelMensajeLogin.setText("Usuario o contraseña incorrectos.");
            }
            
        }
        if(event.getSource() == botonCrearCuenta){
            String username = textUsuarioRegistro.getText();
            String regPass1 = new String(this.regPass1.getPassword());
            String regPass2 = new String(this.regPass2.getPassword());
            String usuario = cp.registrar(username, regPass1, regPass2);
            if(usuario.equals("No coinciden")){
                labelMensajeRegistro.setText("Las contraseñas no coinciden.");
            }
            if(usuario.equals("Usuario existente")){
                labelMensajeRegistro.setText("El usuario ya existe.");
            }
        }
    }
}
