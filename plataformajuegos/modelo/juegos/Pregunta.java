package plataformajuegos.modelo.juegos;

import plataformajuegos.util.Normalizador;

public class Pregunta {
    private final String letra;
    private final String tipo;
    private final String definicion;
    private final String respuesta;

    public Pregunta(String letra, String tipo, String definicion, String respuesta) {
        if (letra == null || letra.trim().isEmpty()) {
            throw new IllegalArgumentException("La letra es obligatoria.");
        }
        if (definicion == null || definicion.trim().isEmpty()) {
            throw new IllegalArgumentException("La definicion es obligatoria.");
        }
        if (respuesta == null || respuesta.trim().isEmpty()) {
            throw new IllegalArgumentException("La respuesta es obligatoria.");
        }
        this.letra = letra.trim().toUpperCase();
        this.tipo = tipo == null ? "EMPIEZA" : tipo.trim().toUpperCase();
        this.definicion = definicion.trim();
        this.respuesta = respuesta.trim();
    }

    public String getLetra() {
        return letra;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDefinicion() {
        return definicion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public boolean verificarRespuesta(String respuestaUsuario) {
        return Normalizador.normalizar(respuesta)
                .equals(Normalizador.normalizar(respuestaUsuario));
    }

    @Override
    public String toString() {
        return letra + "|" + tipo + "|" + definicion + "|" + respuesta;
    }
}
