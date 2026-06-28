package plataformajuegos.util;

import java.text.Normalizer;
import java.util.Locale;

public class Normalizador {
    private Normalizador() {
    }

    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String sinAcentos = Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");
        return sinAcentos.toUpperCase(Locale.ROOT).replaceAll("\\s+", " ");
    }

    public static boolean sonIguales(String respuesta1, String respuesta2) {
        return normalizar(respuesta1).equals(normalizar(respuesta2));
    }
}
