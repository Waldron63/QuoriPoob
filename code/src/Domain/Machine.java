import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Clase heredada de player, simulara el comportamiento de la maquina al jugar
 * depende de la dificultad que coloque el usuario
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class Machine extends Player{
    public static final String[] typesMachines = new String[] {"principiante","intermedio","avanzado"};
    //los tipos de maquina que existen en el juego

    private String difficulty; //dificultad que va a tener la maquina
    private Table tablero; //tablero actual de la partida para que la maquina sepa que puede hacer.

    /**
     * Constructor for objects of class Machine
     *
     * @param newDifficulty, el grado de dificultad que va a tener la maquina
     * @param nWalls, cantidad de muros que puede llegar a colocar la maquina
     * @param xPosition indica la posicion en x dentro de la matriz
     * @param yPosition indica la posicion en y dentro de la matriz
     */
    public Machine(String newDifficulty, int nWalls, int xPosition, int yPosition) {
        super("Robot", Color.BLACK, nWalls, xPosition, yPosition, 2);
        if (! Arrays.asList(typesMachines).contains(newDifficulty)){
            return ;
        }
        difficulty = newDifficulty;
    }

    public void setSelection(){
        switch (difficulty){
            case "principiante":
                selectBeginner();
                break;
            case "intermedio":
                selectMedium();
                break;
            case "avanzado":
                selectHardCore();
                break;
            default:
                return;
        }
    }

    private void selectBeginner(){
        Random random = new Random();
        int randomNumber = random.nextInt(2) + 1;
        if (randomNumber == 1){
            int randomIndex = random.nextInt(Table.basicMoves.length);
            String randomElement = Table.basicMoves[randomIndex];
            tablero.move(new int[] {xPosition, yPosition}, randomElement, mainTurn);
        }else{

        }
    }

    private void selectMedium(){
        return;
    }

    private void selectHardCore(){
        return;
    }
}
