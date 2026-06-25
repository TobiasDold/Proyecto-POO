package plataformajuegos.modelo.sistema;

import plataformajuegos.controlador.*;
import plataformajuegos.modelo.usuarios.*;

public class SistemaJuegos {

    public boolean registrar(String username, String password) {
        ControladorFicheros cf = new ControladorFicheros();
        if (cf.existeUsuario(username)) {
            System.out.println("El usuario ya existe.");
            return false;
        }

        Usuario nuevoUsuario = new Usuario(username, password);
        cf.guardarUsuarios(nuevoUsuario);

        System.out.println("Usuario creado correctamente.");
        return true;
    }

    public Usuario login(String username, String password) {
        ControladorFicheros cf = new ControladorFicheros();
        String[] partesUsuario = cf.obtenerUsuario(username);

        if (partesUsuario == null) {
            System.out.println("El usuario no existe.");
            return null;
        }

        if (!partesUsuario[1].equals(password)) {
            System.out.println("Contraseña incorrecta.");
            return null;
        }

        System.out.println("Se ha loggeado correctamente.");
        if (partesUsuario[2].equals("ADMIN")){
            return new Administrador(partesUsuario[0], partesUsuario[1]);
        }else{
            return new Usuario(partesUsuario[0], partesUsuario[1]);
        }
    }
}
