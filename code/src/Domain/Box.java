import java.io.Serializable;

/**
 * Clase casilla para conocer los tipos de celdas del tablero
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public abstract class Box implements Serializable {
    public static final String[] typesBoxes = new String[] {"Normal", "Teletransportador", "Regresar", "Doble", "Estrella"};

    /**
     * Constructor for objects of class Box
     */
    public Box(){

    }
}