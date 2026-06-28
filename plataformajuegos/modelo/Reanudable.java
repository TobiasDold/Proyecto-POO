package plataformajuegos.modelo;

public interface Reanudable {
    String serializarEstado();

    void deserializarEstado(String estado);
}
