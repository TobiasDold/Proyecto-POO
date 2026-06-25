package plataformajuegos.modelo.usuarios;

public class Administrador extends Usuario{
    private String rol;

    public Administrador(String username, String password){
        super(username, password);
        this.rol = "ADMIN";
    }

    public Administrador(String username, String password, String rol){
        super(username, password);
        this.rol = rol;
    }
}
