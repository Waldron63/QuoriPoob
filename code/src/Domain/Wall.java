import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase abstracta del muro, donde mostrara los comportamientos basicos de
 * los muros y sus diferentes subclases
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public abstract class Wall implements Serializable {
    //Los tipos de muros validos para QuoriPoob
    public static final String[] typesWalls = new String[] {"Muro Normal", "Muro Temporal", "Muro Largo", "Muro Aliado"};
    protected int size; //tamano que va a tener el muro
    private Color color; //color que va a tener el muro
    private ArrayList<Integer> positions; //posiciones en las celdas en grafos que va a ocupar el muro
    protected Player player; //jugador que coloco este muro
    protected int sizeTable; //longitud actual del tablero

    /**
     * Constructor for objects of class Wall
     * @param newColor, color que va a tener el muro
     * @param newPositions, posiciones en las celdas que va a ocupar el muro
     * @param newPlayer indica que jugador puso este muro
     */
    public Wall(Color newColor, ArrayList<Integer> newPositions, Player newPlayer, int longTable) throws QuoriPoobException{
        sizeTable = longTable;
        boolean confirmSize = confirmLenghtPositions(newPositions);
        if (!confirmSize){
            throw new QuoriPoobException(QuoriPoobException.WRONG_WALL_LENGHT);
        }
        boolean confirmSquential = confirmSequentialPositions(newPositions);
        if (!confirmSquential){
            throw new QuoriPoobException(QuoriPoobException.WRONG_SIDE_WALL);
        }
        size = 2;
        positions= newPositions;
        color = newColor;
        player = newPlayer;
    }

    abstract boolean confirmLenghtPositions(ArrayList<Integer> newPositions);

    abstract boolean confirmSequentialPositions(ArrayList<Integer> newPositions);

    abstract boolean samePlayerPass(int mainTurn);

    /**
     * @return el jugador que coloco el muro
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return las posiciones en grafos de las posiciones de el muro
     */
    public  ArrayList<Integer> getPositions() {
        return positions;
    }

    /**
     * cambia el contador de los cambios de turnos
     * (para el muro temporal)
     *
     * @return verdadero si el muro aun debe estar en el juego, false en cuyo caso deba borrarse
     */
    public boolean changeTimes(){
        return true;
    }
}
