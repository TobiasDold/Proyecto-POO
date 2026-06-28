package plataformajuegos.controlador;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

import plataformajuegos.modelo.partidas.Puntuacion;
import plataformajuegos.modelo.usuarios.*;

public class ControladorFicheros {
    private static final Path DIRECTORIO_DATOS = Paths.get("plataformajuegos", "datos");
    private static final Path RUTA_USUARIOS = DIRECTORIO_DATOS.resolve("usuarios.txt");
    private static final Path RUTA_HISTORIAL = DIRECTORIO_DATOS.resolve("historial.txt");
    private static final Path RUTA_GUARDADAS = DIRECTORIO_DATOS.resolve("partidasGuardadas.txt");

    public ControladorFicheros() {
        asegurarFicheros();
    }

    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        for (String linea : leerLineas(RUTA_USUARIOS)) {
            String[] partes = linea.split("\\|", 3);
            if (partes.length == 3) {
                usuarios.add(crearUsuario(partes[0], partes[1], partes[2]));
            }
        }
        return usuarios;
    }

    public String[] obtenerUsuario(String username) {
        if (username == null) {
            return null;
        }
        for (String linea : leerLineas(RUTA_USUARIOS)) {
            String[] usuario = linea.split("\\|", 3);
            if (usuario.length == 3 && usuario[0].equalsIgnoreCase(username.trim())) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario validarCredenciales(String username, String password) {
        String[] datos = obtenerUsuario(username);
        if (datos == null || password == null || !datos[1].equals(password)) {
            return null;
        }
        return crearUsuario(datos[0], datos[1], datos[2]);
    }

    public void guardarUsuarios(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null.");
        }
        appendLinea(RUTA_USUARIOS, usuario.toString());
    }

    public boolean existeUsuario(String username) {
        return obtenerUsuario(username) != null;
    }

    public boolean actualizarRolUsuario(String username, String nuevoRol) {
        if (username == null || nuevoRol == null) {
            return false;
        }

        String rolNormalizado = nuevoRol.trim().toUpperCase();
        if (!"ADMIN".equals(rolNormalizado) && !"JUGADOR".equals(rolNormalizado)) {
            return false;
        }

        List<String> lineas = leerLineas(RUTA_USUARIOS);
        boolean actualizado = false;
        for (int i = 0; i < lineas.size(); i++) {
            String[] partes = lineas.get(i).split("\\|", 3);
            if (partes.length == 3 && partes[0].equalsIgnoreCase(username.trim())) {
                lineas.set(i, partes[0] + "|" + partes[1] + "|" + rolNormalizado);
                actualizado = true;
                break;
            }
        }
        if (actualizado) {
            escribirLineas(RUTA_USUARIOS, lineas);
        }
        return actualizado;
    }

    public void registrarPartida(RegistroPartida partida) {
        if (partida == null) {
            throw new IllegalArgumentException("El registro de partida no puede ser null.");
        }
        appendLinea(RUTA_HISTORIAL, partida.toString());
    }

    public List<RegistroPartida> cargarHistorial() {
        List<RegistroPartida> partidas = new ArrayList<>();
        for (String linea : leerLineas(RUTA_HISTORIAL)) {
            RegistroPartida partida = parsearRegistro(linea);
            if (partida != null) {
                partidas.add(partida);
            }
        }
        return partidas;
    }

    public List<RegistroPartida> cargarPartidasDe(String username) {
        List<RegistroPartida> partidas = new ArrayList<>();
        if (username == null) {
            return partidas;
        }
        for (RegistroPartida partida : cargarHistorial()) {
            if (partida.getNombreJugador().equalsIgnoreCase(username.trim())) {
                partidas.add(partida);
            }
        }
        Collections.sort(partidas, new Comparator<RegistroPartida>() {
            @Override
            public int compare(RegistroPartida primera, RegistroPartida segunda) {
                return segunda.getFecha().compareTo(primera.getFecha());
            }
        });
        return partidas;
    }

    public List<Puntuacion> cargarRanking(String nombreJuego) {
        Map<String, Integer> mejoresPuntuaciones = new LinkedHashMap<>();
        for (RegistroPartida partida : cargarHistorial()) {
            if (nombreJuego == null
                    || partida.getNombreJuego().equalsIgnoreCase(nombreJuego.trim())) {
                Integer anterior = mejoresPuntuaciones.get(partida.getNombreJugador());
                if (anterior == null || partida.getPuntuacion() > anterior.intValue()) {
                    mejoresPuntuaciones.put(partida.getNombreJugador(),
                            partida.getPuntuacion());
                }
            }
        }

        List<Puntuacion> ranking = new ArrayList<>();
        for (Map.Entry<String, Integer> entrada : mejoresPuntuaciones.entrySet()) {
            ranking.add(new Puntuacion(entrada.getKey(), entrada.getValue()));
        }
        Collections.sort(ranking);
        return ranking;
    }

    public void guardarEstado(String username, String juego, String estado) {
        if (username == null || juego == null || estado == null) {
            throw new IllegalArgumentException("No se puede guardar un estado incompleto.");
        }
        eliminarGuardada(username, juego);
        String estadoCodificado = Base64.getEncoder().encodeToString(
                estado.getBytes(StandardCharsets.UTF_8));
        appendLinea(RUTA_GUARDADAS,
                username.trim() + "|" + juego.trim() + "|" + estadoCodificado);
    }

    public String cargarEstado(String username, String juego) {
        if (username == null || juego == null) {
            return null;
        }
        for (String linea : leerLineas(RUTA_GUARDADAS)) {
            String[] partes = linea.split("\\|", 3);
            if (partes.length == 3
                    && partes[0].equalsIgnoreCase(username.trim())
                    && partes[1].equalsIgnoreCase(juego.trim())) {
                try {
                    return new String(Base64.getDecoder().decode(partes[2]),
                            StandardCharsets.UTF_8);
                } catch (IllegalArgumentException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public boolean tieneEstadoGuardado(String username, String juego) {
        return cargarEstado(username, juego) != null;
    }

    public void eliminarGuardada(String username, String juego) {
        if (username == null || juego == null) {
            return;
        }
        List<String> restantes = new ArrayList<>();
        for (String linea : leerLineas(RUTA_GUARDADAS)) {
            String[] partes = linea.split("\\|", 3);
            boolean eliminar = partes.length == 3
                    && partes[0].equalsIgnoreCase(username.trim())
                    && partes[1].equalsIgnoreCase(juego.trim());
            if (!eliminar) {
                restantes.add(linea);
            }
        }
        escribirLineas(RUTA_GUARDADAS, restantes);
    }

    private RegistroPartida parsearRegistro(String linea) {
        String[] partes = linea.split("\\|", 4);
        if (partes.length != 4) {
            return null;
        }
        try {
            return new RegistroPartida(partes[0].trim(), partes[1].trim(),
                    LocalDateTime.parse(partes[2].trim()),
                    Integer.parseInt(partes[3].trim()));
        } catch (RuntimeException e) {
            return null;
        }
    }

    private Usuario crearUsuario(String username, String password, String rol) {
        if ("ADMIN".equalsIgnoreCase(rol.trim())) {
            return new Administrador(username, password);
        }
        return new Usuario(username, password);
    }

    private void asegurarFicheros() {
        try {
            Files.createDirectories(DIRECTORIO_DATOS);
            crearSiNoExiste(RUTA_USUARIOS);
            crearSiNoExiste(RUTA_HISTORIAL);
            crearSiNoExiste(RUTA_GUARDADAS);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo preparar la carpeta de datos.", e);
        }
    }

    private void crearSiNoExiste(Path ruta) throws IOException {
        if (!Files.exists(ruta)) {
            Files.createFile(ruta);
        }
    }

    private List<String> leerLineas(Path ruta) {
        try {
            return Files.readAllLines(ruta, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer " + ruta + ".", e);
        }
    }

    private void escribirLineas(Path ruta, List<String> lineas) {
        try {
            Files.write(ruta, lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo escribir " + ruta + ".", e);
        }
    }

    private void appendLinea(Path ruta, String linea) {
        try {
            Files.write(ruta, Collections.singletonList(linea), StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo escribir " + ruta + ".", e);
        }
    }
}
