import java.awt.*;
import java.awt.Color;

/**
 * Clase abstracta Player del juego, mostrara los comportamientos basicos de los jugadores en el juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public abstract class Player {
    private String name; //nombre del jugador
    private int cantWalls; //cantidad maxima de muros que puede llegar a colocar
    private Color color; //color de la ficha del jugador
    protected int xPosition; //posicion en X dentro del tablero
    protected int yPosition; //posicion en Y dentro del tablero

    /**
     * Constructor for objects of class Player
     *
     * @param newName, nombre que decide el jugador
     * @param newColor, color que decide el jugador
     * @param nWalls, cantidad de muros que puede llegar a poner
     */
    public Player(String newName, Color newColor, int nWalls) {
        name = newName;
        cantWalls = nWalls + 1;
        color = newColor;
    }

    /**
     * @return cantidad de muros que le falta por colocar al jugador
     */
    public int getCantWalls() {
        return cantWalls;
    }

    /**
     * cambia las posiciones dentro del tablero de el jugador
     * @param positions, las nuevas posiciones por las que se va a reemplazar
     */
    public void changePositions(int[] positions){
        xPosition = positions[0];
        yPosition = positions[1];
    }

    /**
     * @return las posiciones actuales en donde esta el jugador
     */
    public int[] getPositions(){
        return new int[] {xPosition, yPosition};
    }

    public abstract boolean move(int[] positions); //el movimiento que va a hacer el jugador
    public abstract void addWall(); //los muros que va a anadir el jugador
}