import java.awt.*;

/**
 * Clase heredada de player: humano, el cual representa a los jugadores que deseen intentar ganar
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class Human extends Player{

    /**
     * Constructor for objects of class Human
     * @param newName, nombre que se desee colocar el jugador
     * @param newColor color que desee para la ficha
     * @param nWalls, cantidad maxima de muros que puede llegar a usar
     * @param xPosition indica la posicion en x dentro de la matriz
     * @param yPosition indica la posicion en y dentro de la matriz
     * @param newTurn indica cual va a ser el turno de este jugador (si juega en el turno 1 o en el turno 2)
     */
    public Human(String newName, Color newColor, int nWalls, int xPosition, int yPosition, int newTurn) {
        super(newName, newColor, nWalls, xPosition, yPosition, newTurn);
    }
}
