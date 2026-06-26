package plataformajuegos.controlador;

import plataformajuegos.modelo.sistema.SistemaJuegos;
import plataformajuegos.modelo.usuarios.*;
import plataformajuegos.vista.PanelMenu;
import plataformajuegos.vista.VentanaPrincipal;

public class ControladorPrincipal {
    private SistemaJuegos sj = new SistemaJuegos();
    private Usuario usuarioActual;
    private VentanaPrincipal ventana;
    private PanelMenu panelMenu;

    public void iniciar() {
        ventana = new VentanaPrincipal(this);
        ventana.setVisible(true);
    }

    public boolean login(String username, String password) {
        Usuario usuario = sj.login(username.trim(), password);

        if (usuario == null) {
            return false;
        }

        this.usuarioActual = usuario;
        if (panelMenu == null) {
            panelMenu = new PanelMenu(this);
            ventana.agregarPanel(panelMenu, "MENU");
        }

        panelMenu.mostrarVistaSegunRol();
        ventana.mostrarPanel("MENU");

        return true;
    }

    public String registrar(String username, String regPass1, String regPass2) {
        if (!regPass1.equals(regPass2))
            return "No coinciden";

        if (!sj.registrar(username.trim(), regPass1)) {
            return "Usuario existente";
        }
        return "Usuario creado";
    }

    public void mostrarLogin() {
        ventana.mostrarPanel("LOGIN");
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void logout() {
        usuarioActual = null;
        mostrarLogin();
    }

}
