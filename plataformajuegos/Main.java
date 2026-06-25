package plataformajuegos;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import plataformajuegos.modelo.sistema.*;
import plataformajuegos.controlador.*;
import plataformajuegos.modelo.usuarios.*;

public class Main {
    
    public static void main(String[] args) {
        ControladorPrincipal cp = new ControladorPrincipal();
        cp.iniciar();
    }
}
