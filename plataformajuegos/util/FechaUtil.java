package plataformajuegos.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FechaUtil {
    private static final DateTimeFormatter FORMATO_VISUAL = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private FechaUtil() {
    }

    public static String formatear(LocalDateTime fecha) {
        return fecha == null ? "" : fecha.format(FORMATO_VISUAL);
    }

    public static LocalDateTime parsear(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.parse(fecha.trim());
        } catch (DateTimeParseException e) {
            return LocalDateTime.parse(fecha.trim(), FORMATO_VISUAL);
        }
    }
}
