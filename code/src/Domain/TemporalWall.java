import java.awt.*;
import java.util.ArrayList;

/**
 * Clase heredada de muro, la cual indica el muro mas largo (3 casillas) que tiene el juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class TemporalWall extends Wall{
    private int times; //idica la cantidad de tiempos que necesita el muro antes de desaparecer

    /**
     * Constructor for objects of class LargaWall
     * @param newColor, color que va a obtener el muro
     * @param newPositions, posiciones de las celdas que va a ocupar el muro.
     */
    public TemporalWall(Color newColor, ArrayList<Integer> newPositions, Player newPlayer, int longTable) throws QuoriPoobException {
        super(newColor, newPositions, newPlayer, longTable);
        times = 4;
    }

    boolean confirmLenghtPositions(ArrayList<Integer> newPositions){
        if (newPositions.size() == 4){
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

    /**
     * @param mainTurn
     * @return
     */
    @Override
    boolean samePlayerPass(int mainTurn) {
        return false;
    }

    /**
     * @return  la cantidad de tiempos que le quedan al muro antes de desaparecer
     */
    public int getTimes() {
        return times;
    }

    /**
     * cambia el contador de los cambios de turnos
     * (para el muro temporal)
     *
     * @return verdadero si el muro aun debe estar en el juego, false en cuyo caso deba borrarse
     */
    @Override
    public boolean changeTimes() {
        times -= 1;
        //condicional para saber si se elimina el muro o se deja
        if (times == 0){
            return false;
        }else{
            return true;
        }
    }
}
