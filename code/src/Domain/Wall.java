import java.awt.*;
import java.awt.Color;

/**
 * Clase abstracta del muro, donde mostrara los comportamientos basicos de
 * los muros y sus diferentes subclases
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public abstract class Wall {
    protected int size; //tamano que va a tener el muro
    private Color color; //color que va a tener el muro
    private int[] positions; //posiciones en las celdas que va a ocupar el muro

    /**
     * Constructor for objects of class Wall
     * @param newColor, color que va a tener el muro
     * @param newPositions, posiciones en las celdas que va a ocupar el muro
     */
    public Wall(Color newColor, int[] newPositions){
        size = 2;
        positions= newPositions;
        color = newColor;
    }
}
