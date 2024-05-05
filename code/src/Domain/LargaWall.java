import java.awt.*;

/**
 * Clase heredada de muro, la cual indica el muro mas largo (3 casillas) que tiene el juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class LargaWall extends Wall{

    /**
     * Constructor for objects of class LargaWall
     * @param newColor, color que va a obtener el muro
     * @param newPositions, posiciones de las celdas que va a ocupar el muro.
     */
    public LargaWall(Color newColor, int[] newPositions) {
        super(newColor, newPositions);
        size = 3;
    }
}
