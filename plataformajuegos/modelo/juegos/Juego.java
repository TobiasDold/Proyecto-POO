package plataformajuegos.modelo.juegos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Juego {
    private String id;
    private String nombre;
    private String descripcion;
    private List<String> reglas;
    private boolean activo;

    public Juego() {
        this.reglas = new ArrayList<>();
        this.activo = true;
    }

    public Juego(String id, String nombre, String descripcion) {
        this();
        setId(id);
        setNombre(nombre);
        setDescripcion(descripcion);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = validarTexto(id, "id");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "nombre");
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? "" : descripcion.trim();
    }

    public List<String> getReglas() {
        return Collections.unmodifiableList(reglas);
    }

    public void setReglas(List<String> reglas) {
        this.reglas.clear();

        if (reglas == null) {
            return;
        }

        for (String regla : reglas) {
            agregarRegla(regla);
        }
    }

    public void agregarRegla(String regla) {
        String reglaValidada = validarTexto(regla, "regla");
        reglas.add(reglaValidada);
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    public boolean estaDisponible() {
        return activo;
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede estar vacio.");
        }

        return texto.trim();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
