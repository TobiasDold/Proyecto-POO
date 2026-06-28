package plataformajuegos.modelo.usuarios;

public class Jugador extends Usuario {
    public Jugador(String username, String password) {
        super(username, password, "JUGADOR");
    }

    public Jugador(String username, String password, String rol) {
        super(username, password, "JUGADOR");
    }
}
