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
     */
    public Machine(String newDifficulty, int nWalls) {
        super("Robot", Color.BLACK, nWalls);
        difficulty = newDifficulty;
    }

    /**
     * movimiento que va a usar la maquina para intentar  ganar
     *
     * @param positions, ...
     * @return, ...
     */
    @Override
    public boolean move(int[] positions) {
        return false;
    }

    /**
     * ayuda a colocar los muros que la maquina desea poner
     */
    @Override
    public void addWall() {
        return;
    }
}
