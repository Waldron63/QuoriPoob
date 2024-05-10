import java.awt.*;
import java.awt.Color;

/**
 * Clase abstracta del muro, donde mostrara los comportamientos basicos de
 * los muros y sus diferentes subclases
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public abstract class AllyWall extends Wall{

    /**
     * Constructor for objects of class Wall
     * @param newColor, color que va a tener el muro
     * @param newPositions, posiciones en las celdas que va a ocupar el muro
     */
    public AllyWall(Color newColor, int[] newPositions, Player newPlayer){
        super(newColor, newPositions, newPlayer);
    }
}
