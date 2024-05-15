import java.awt.*;

/**
 * Clase heredada de player, simulara el comportamiento de la maquina al jugar
 * depende de la dificultad que coloque el usuario
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class Machine extends Player{
    private String difficulty; //dificultad que va a tener la maquina

    /**
     * Constructor for objects of class Machine
     *
     * @param newDifficulty, el grado de dificultad que va a tener la maquina
     * @param nWalls, cantidad de muros que puede llegar a colocar la maquina
     * @param xPosition indica la posicion en x dentro de la matriz
     * @param yPosition indica la posicion en y dentro de la matriz
     */
    public Machine(String newDifficulty, int nWalls, int xPosition, int yPosition) {
        super("Robot", Color.BLACK, nWalls, xPosition, yPosition, 2);
        difficulty = newDifficulty;
    }
}
