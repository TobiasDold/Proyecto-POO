package plataformajuegos.modelo.partidas;

public class Puntuacion implements Comparable<Puntuacion> {
    private final String username;
    private int puntos;

    public Puntuacion(String username) {
        this(username, 0);
    }

    public Puntuacion(String username, int puntos) {
        this.username = username;
        this.puntos = puntos;
    }

    public String getUsername() {
        return username;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumar(int cantidad) {
        puntos += cantidad;
    }

    @Override
    public int compareTo(Puntuacion otra) {
        return Integer.compare(otra.puntos, puntos);
    }
}
