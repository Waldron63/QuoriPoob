import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Clase abstracta del muro, donde mostrara los comportamientos basicos de
 * los muros y sus diferentes subclases
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class AllyWall extends Wall{

    /**
     * Constructor for objects of class Wall
     * @param newColor, color que va a tener el muro
     * @param newPositions, posiciones en las celdas que va a ocupar el muro
     * @param newPlayer indica ue jugador coloco el muro
     */
    public AllyWall(Color newColor, ArrayList<Integer> newPositions, Player newPlayer, int longTable) throws QuoriPoobException {
        super(newColor, newPositions, newPlayer, longTable);
        for (int i = 0; i < 4; i++){
            positions.add(newPositions.get(i));
        }
    }

    boolean confirmLenghtPositions(ArrayList<Integer> newPositions){
        if (newPositions.size() == 6){
            return true;
        }else{
            return false;
        }
    }

    @Override
    boolean confirmSequentialPositions(ArrayList<Integer> newPositions){
        if (Math.abs(newPositions.get(0) - newPositions.get(2)) != 1 &&
                Math.abs(newPositions.get(0) - newPositions.get(2)) != sizeTable){
            return false;
        }else{
            return true;
        }
    }

    @Override
    boolean confirmSequentialButton(ArrayList<Integer> newPositions) {
        if (Math.abs(newPositions.get(4) - newPositions.get(5)) != 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * @param mainTurn
     * @return
     */
    @Override
    boolean samePlayerPass(int mainTurn) {
        return  mainTurn == player.getMainTurn();
    }
}
