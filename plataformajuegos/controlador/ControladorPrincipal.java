package plataformajuegos.controlador;

import plataformajuegos.modelo.sistema.SistemaJuegos;
import plataformajuegos.modelo.usuarios.*;
import plataformajuegos.vista.VentanaPrincipal;

public class ControladorPrincipal {
    private SistemaJuegos sj = new SistemaJuegos();
    private Usuario usuarioActual;
    private VentanaPrincipal ventana;

    public void iniciar(){
        ventana = new VentanaPrincipal(this);
        ventana.setVisible(true);
    }
    public boolean login(String username, String password){
        Usuario usuario = sj.login(username, password);

        if(usuario == null){return false;}

        this.usuarioActual = usuario;
        if(usuarioActual instanceof Administrador){
            ventana.mostrarPanel("ADMIN");
        }else {
            ventana.mostrarPanel("MENU");
        }
        return true;
    }
    public String registrar(String username, String regPass1, String regPass2){
        if(!regPass1.equals(regPass2)) return "No coinciden";
        
        if(!sj.registrar(username, regPass1)){return "Usuario existente";}
        return "Usuario creado";
    }
    public void mostrarMenu(){}

}
