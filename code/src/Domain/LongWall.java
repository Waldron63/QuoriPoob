import java.awt.*;
import java.util.ArrayList;

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
    public LongWall(Color newColor, ArrayList<Integer> newPositions, Player newPlayer, int longTable) throws QuoriPoobException {
        super(newColor, newPositions, newPlayer, longTable);
        size = 3;
        for (int i = 0; i < 6; i++){
            positions.add(newPositions.get(i));
        }
    }

    boolean confirmLenghtPositions(ArrayList<Integer> newPositions){
        if (newPositions.size() == 9){
            return true;
        }else{
            return false;
        }
    }

    @Override
    boolean confirmSequentialPositions(ArrayList<Integer> newPositions) {
        if (Math.abs(newPositions.get(0) - newPositions.get(2)) == 1 &&
                Math.abs(newPositions.get(2) - newPositions.get(4)) == 1){
            return true;
        }else if (Math.abs(newPositions.get(0) - newPositions.get(2)) == sizeTable &&
                Math.abs(newPositions.get(2) - newPositions.get(4)) == sizeTable){
            return true;
        }else{
            return false;
        }
    }

    @Override
    boolean confirmSequentialButton(ArrayList<Integer> newPositions) {
        if (Math.abs(newPositions.get(6) - newPositions.get(7)) != 0){
            return false;
        }else if (Math.abs(newPositions.get(7) - newPositions.get(8)) != 0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * @param mainTurn
     * @return
     */
    @Override
    boolean samePlayerPass(int mainTurn) {
        return false;
    }
}
