package plataformajuegos.controlador;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import plataformajuegos.modelo.partidas.*;
import plataformajuegos.modelo.usuarios.*;

public class ControladorFicheros {

    // ===SECCION USUARIOS===\\

    // Para leer usuarios de la BBDD/fichero txt.
    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader("plataformajuegos/datos/usuarios.txt"))) {
            while ((linea = br.readLine()) != null) {
                String[] partesUsuario = linea.split("\\|");
                if (partesUsuario.length == 3) {
                    if (partesUsuario[2].equals("ADMIN")) {
                        Usuario nuevoUsuario = new Administrador(partesUsuario[0], partesUsuario[1]);
                        usuarios.add(nuevoUsuario);
                    } else {
                        Usuario nuevoUsuario = new Usuario(partesUsuario[0], partesUsuario[1]);
                        usuarios.add(nuevoUsuario);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Busca al usuario y si existe devuelve el usuario del tipo que debe y sino
    // devuelve null
    public String[] obtenerUsuario(String username) {
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader("plataformajuegos/datos/usuarios.txt"))) {
            while ((linea = br.readLine()) != null) {
                String[] usuario = linea.split("\\|");
                if (usuario.length == 3 && usuario[0].equals(username)) {
                    return usuario;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Para guardar usuarios en BBDD/fichero txt.
    public void guardarUsuarios(Usuario usuario) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("plataformajuegos/datos/usuarios.txt", true))) {
            pw.println(usuario.toString());
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Para verificar si el usuario existe.
    public boolean existeUsuario(String username) {
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader("plataformajuegos/datos/usuarios.txt"))) {
            while ((linea = br.readLine()) != null) {
                int sep = linea.indexOf('|');
                if (sep == -1) {
                    continue;
                }

                String nombreUsuario = linea.substring(0, sep);
                if (nombreUsuario.equals(username)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace() + "\n" + e.getMessage());
        }
        return false;
    }

    // ===SECCION PARTIDAS===\\
    public void registrarPartida(Partida partida) {
    }

    public List<Partida> cargarPartidas() {
        List<Partida> partida = new ArrayList<>();
        return partida;
    }

    public List<Partida> cargarPartidasDe(String username) {
        List<Partida> partida = new ArrayList<>();
        return partida;
    }

    // ===SECCION PARTIDAS GUARDADAS===\\
    public void guardarEstado(String username, String juego, String estado) {
    }

    public String cargarEstado(String username, String juego) {
        return null;
    }

    public void eliminarGuardada(String username, String juego) {
    }
}
