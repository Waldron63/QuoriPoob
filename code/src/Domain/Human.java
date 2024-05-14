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

    /**
     * movimientos que puede hacer el jugador para intentar ganar
     * @param positions, ...
     * @return true si el jugador se puede mover, falso en cuyo caso no
     */
    @Override
    public boolean move(int[] positions) {
        int distX = Math.abs(positions[0] - xPosition);
        int distY = Math.abs(positions[1] - xPosition);
        //revisa que las distancias entre las celdas sean unitarias o 0
        if (distX <= 1 && distY <= 1 && (distY + distX) == 1){
            return true;
        }
        return false;
    }

    /**
     * ayuda a colocar los muros que el jugador obstaculice a su contrincante
     */
    @Override
    public void addWall() {
        return;
    }
}
