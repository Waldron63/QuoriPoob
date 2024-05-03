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
    private int sizeTable; //longitud total del tablero
    private String difficult; //tipo de dificultad escogido por los jugadores
    private int turn;

    public QuoriPoob(int n, String newDifficult){
        tablero = new Table(n);
        sizeTable = n;
        difficult = newDifficult;
        turn = 1;
    }

    public void addPlayer(String name, Color color){
        if (playerOne == null){
            playerOne = new Player(name, color, sizeTable);
        }else{
            playerTwo = new Player(name, color, sizeTable);
        }
    }

    public int changeTurn(){
        if (turn == 1){
            turn = 2;
            return turn;
        }else{
            turn = 1;
            return turn;
        }
    }

    public int getTurn(){
        return turn;
    }

    public void addWall(){

        changeTurn();
    }

    public void move(String movement){
        changeTurn();
    }

    public Box[][] board(){
        return tablero.getCasillas();
    }
}