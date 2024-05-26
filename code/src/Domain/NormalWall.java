import java.awt.*;
import java.util.ArrayList;

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
    public NormalWall(Color newColor, ArrayList<Integer> newPositions, Player newPlayer, int longTable) throws QuoriPoobException {
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
        return false;
    }
}
