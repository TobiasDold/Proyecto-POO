package plataformajuegos.modelo.usuarios;

public class Administrador extends Usuario {
    public Administrador(String username, String password) {
        super(username, password, "ADMIN");
    }

    public Administrador(String username, String password, String rol) {
        super(username, password, "ADMIN");
    }
}
