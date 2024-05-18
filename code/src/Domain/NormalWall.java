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
     * @param newPlayer indica que jugador puso este muro
     */
    public NormalWall(Color newColor, int[] newPositions, Player newPlayer) {
        super(newColor, newPositions, newPlayer);
    }

    boolean confirmPositions(int[] newPositions){
        if (newPositions.length == 2){
            return true;
        }else{
            return false;
        }
    }
}
