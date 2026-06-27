package plataformajuegos.modelo;

import java.io.*;

public interface Reanudable{
    public String serializarEstado();
    public void deserializarEstado(String estado);
}
