import java.awt.*;

/**
 * Clase heredada de muro, la cual indica el muro comun que se usa para el juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class NormalWall extends Wall{

    /**
     * Constructor for objects of class NormalWall
     *
     * @param newColor, el color que va a tomar el muro comun
     * @param newPositions, lista de las posiciones que va a ocupar el muro
     */
    public NormalWall(Color newColor, int[] newPositions, Player newPlayer) {
        super(newColor, newPositions, newPlayer);
    }
}
