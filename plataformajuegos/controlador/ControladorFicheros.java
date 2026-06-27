package plataformajuegos.controlador;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public void actualizarRolUsuario(String username, String nuevoRol) {
    }

    // ===SECCION PARTIDAS===\\
    public void registrarPartida(RegistroPartida partida) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("plataformajuegos/datos/historial.txt", true))) {
            pw.println(partida.toString());
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RegistroPartida> cargarHistorial() {
        List<RegistroPartida> partida = new ArrayList<>();
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader("plataformajuegos/datos/historial.txt"))) {
            while ((linea = br.readLine()) != null) {
                String partesPartida[] = linea.split("\\|");
                if (partesPartida.length == 4) {
                    try {
                        LocalDateTime fecha = LocalDateTime.parse(partesPartida[2].trim());
                        int puntuacion = Integer.parseInt(partesPartida[3].trim());
                        partida.add(new RegistroPartida(partesPartida[0].trim(), partesPartida[1].trim(), fecha, puntuacion));
                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("El formato de la fecha es incorrecto.");
                    } catch (NumberFormatException e) {
                        System.out.println("La puntuacion no es un numero valido.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partida;
    }

    public List<RegistroPartida> cargarPartidasDe(String username) {
        List<RegistroPartida> partida = new ArrayList<>();
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader("plataformajuegos/datos/historial.txt"))) {
            while ((linea = br.readLine()) != null) {
                String partesPartida[] = linea.split("\\|");
                if (partesPartida.length == 4) {
                    if ((partesPartida[0].trim()).equals(username.trim()))
                        try {
                            LocalDateTime fecha = LocalDateTime.parse(partesPartida[2].trim());
                            int puntuacion = Integer.parseInt(partesPartida[3].trim());
                            partida.add(new RegistroPartida(partesPartida[0].trim(), partesPartida[1].trim(), fecha, puntuacion));
                        } catch (java.time.format.DateTimeParseException e) {
                            System.out.println("El formato de la fecha es incorrecto.");
                        } catch (NumberFormatException e) {
                            System.out.println("La puntuacion no es un numero valido.");
                        }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partida;
    }

    // ===SECCION PARTIDAS GUARDADAS===\\
    public void guardarEstado(String username, String juego, String estado) {
        eliminarGuardada(username, juego);
        try (PrintWriter pw = new PrintWriter(new FileWriter("plataformajuegos/datos/partidasGuardadas.txt"), true)) {
            pw.println(username + "\\|" + juego + "|" + estado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String cargarEstado(String username, String juego) {
        String userABuscar = username.trim();
        String juegoABuscar = juego.trim();
        try (BufferedReader br = new BufferedReader(new FileReader("plataformajuegos/datos/partidasGuardadas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partesPartida = linea.split("\\|");
                if (partesPartida.length == 3) {
                    if ((partesPartida[0].trim()).equals(userABuscar)
                            && (partesPartida[1].trim()).equals(juegoABuscar)) {
                        return partesPartida[2];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void eliminarGuardada(String username, String juego) {
        String ruta = "plataformajuegos/datos/partidasGuardadas.txt";
        String userAEliminar = username.trim();
        String juegoAEliminar = juego.trim();
        List<String> partidaExistentes = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = bf.readLine()) != null) {
                String[] partesLineaActual = linea.split("\\|");
                boolean partidaAEliminar = (partesLineaActual[0].trim().equals(userAEliminar)
                        && (partesLineaActual[1].trim()).equals(juegoAEliminar));
                if (!partidaAEliminar) {
                    partidaExistentes.add(linea);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            for(String linea : partidaExistentes){
                pw.println(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
