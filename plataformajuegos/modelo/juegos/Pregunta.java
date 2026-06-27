package plataformajuegos.modelo.juegos;

public class Pregunta {
    private String letra;
    private String tipo;
    private String definicion;
    private String respuesta;

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
        if (!respuestaUsuario.equals(respuesta))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return letra + "|" + tipo + "|" + definicion + "|" + respuesta;
    }
}
