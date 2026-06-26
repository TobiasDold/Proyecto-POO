package plataformajuegos.modelo.usuarios;

public class Usuario {
    private String username;
    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
        this.rol = "JUGADOR";
    }

    public Usuario(String username, String password, String rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        if (password.equals(this.password))
            this.password = password;
    }

    @Override
    public String toString() {
        return this.username + "|" + this.password + "|" + this.rol;
    }
}
