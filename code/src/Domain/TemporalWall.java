import java.awt.*;

/**
 * Clase heredada de muro, la cual indica el muro mas largo (3 casillas) que tiene el juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class TemporalWall extends Wall{
    private int times;

    /**
     * Constructor for objects of class LargaWall
     * @param newColor, color que va a obtener el muro
     * @param newPositions, posiciones de las celdas que va a ocupar el muro.
     */
    public TemporalWall(Color newColor, int[] newPositions, Player newPlayer) {
        super(newColor, newPositions, newPlayer);
        times = 4;
    }

    public int getTimes() {
        return times;
    }

    public int changeTimes() {
        times -= 1;
        if (times == 0){
            int t = player.getTurn();
            return t;
        }else{
            return 0;
        }
    }
}
