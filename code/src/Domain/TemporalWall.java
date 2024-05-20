import java.awt.*;

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
    public TemporalWall(Color newColor, int[] newPositions, Player newPlayer) throws QuoriPoobException {
        super(newColor, newPositions, newPlayer);
        times = 4;
    }

    boolean confirmPositions(int[] newPositions){
        if (newPositions.length == 2){
            return true;
        }else{
            return false;
        }
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
