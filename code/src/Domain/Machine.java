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
    public static final String[] typesMachines = new String[] {"Principiante", "Intermedio", "Avanzado"};
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
    public Machine(String newDifficulty, int nWalls, int xPosition, int yPosition) throws QuoriPoobException{
        super("Robot", Color.BLACK, nWalls, xPosition, yPosition, 2);
        if (newDifficulty.isEmpty() || !Arrays.asList(typesMachines).contains(newDifficulty)){
            throw new QuoriPoobException(QuoriPoobException.DIFFICULTY_MACHINE_NOT_VALID);
        }
        difficulty = newDifficulty;
    }

    @Override
    public void setCantDifferentWalls(int[] newCantWalls) throws QuoriPoobException {
        if (newCantWalls.length < 4){
            throw new QuoriPoobException(QuoriPoobException.WRONG_TOTAL_WALLS);
        }
        cantDifferentsWalls = new int[] {100, 0,0,0};
    }

    public void setSelection() throws QuoriPoobException{
        switch (difficulty){
            case "Principiante":
                selectBeginner();
                break;
            case "Intermedio":
                selectMedium();
                break;
            case "Avanzado":
                selectHardCore();
                break;
            default:
                break;
        }
    }

    private void selectBeginner() throws QuoriPoobException{
        Random random = new Random();
        int randomNumber = random.nextInt(2) + 1;
        if (randomNumber == 1){
            int randomIndex = random.nextInt(Table.basicMoves.length);
            String randomElement = Table.basicMoves[randomIndex];
            try{
                tablero.move(new int[] {xPosition, yPosition}, randomElement, mainTurn);
            }catch (QuoriPoobException ignore){}
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
