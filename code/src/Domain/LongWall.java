import java.awt.*;

/**
 * Clase heredada de muro, la cual indica el muro mas largo (3 casillas) que tiene el juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class LongWall extends Wall{

    /**
     * Constructor for objects of class LargaWall
     * @param newColor, color que va a obtener el muro
     * @param newPositions, posiciones de las celdas que va a ocupar el muro.
     * @param newPlayer indica que jugador puso este muro
     */
    public LongWall(Color newColor, int[] newPositions, Player newPlayer) throws QuoriPoobException {
        super(newColor, newPositions, newPlayer);
        size = 3;
    }

    boolean confirmPositions(int[] newPositions){
        if (newPositions.length == 3){
            return true;
        }else{
            return false;
        }
    }
}
