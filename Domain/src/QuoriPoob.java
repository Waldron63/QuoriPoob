import java.awt.*;
import java.awt.Color;


/**
 * Clase Domain de el proyecto, el principal auditor del paquete presentation
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class QuoriPoob {
    private final int players = 2; // cantidad total de jugadores en el tablero
    private Player playerOne; //primer jugador de la partida
    private Player playerTwo; //segundo jugador de la partida
    private Table tablero; //tablero de nxn para la partida

    public QuoriPoob(int n, Color colorTable){
        tablero = new Table(n, colorTable);
    }
}