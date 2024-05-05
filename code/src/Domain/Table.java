import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Clase Tablero la cual almacena todos los sucesos que cambian dentro del tablero del juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class Table {
    public static final String[] moves = new String[] {"n", "s", "e", "w"}; //posiciones basicas de movimiento
    private HashMap<int[], Integer> graphs; //posiciones y el numero respectivo del "grafo" de la matriz
    private int longitud; //longitud que va a tener el tablero
    private Box[][] casillas; //matriz con todas las casillas y jugadores
    private Wall[] muros; //arreglo de todos los muros que se pueden llegar a colocar
    private TableAdyacence adyacence; //matriz de adyacencia para representar el grafo

    /**
     * Constructor for objects of class Table
     * @param newLong, longitud del tablero
     */
    public Table(int newLong){
        longitud = newLong;
        muros = new Wall[2 * (newLong + 1)];
        casillas = new Box[newLong][newLong];
        graphs = new HashMap<>();
        int contador = 0;
        //genera la matriz de casillas para la inicializacion del juego, ademas de nombras los "grafos"
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; j < casillas.length; j++){
                casillas[i][j] = new NormalBox();
                graphs.put(new int[] {i, j}, contador);
                contador += 1;
            }
        }
        adyacence = new TableAdyacence(newLong);
    }

    /**
     * mueve cualquier jugador en su respectivo turno teniendo en cuenta el lado que decidio moverse
     * @param positionsP, las posiciones actuales del jugador
     * @param side, lado hacia el que quiere ir el jugador
     * @return int[] lista de la nueva posicion en la que se encuentra el jugador
     */
    public int[] move(int[] positionsP, String side){
        //revisa que si sea un lado valido para moverse
        if (! Arrays.asList(moves).contains(side)){
            return new int[] {};
        }
        int initialG = graphs.get(positionsP);
        boolean movePosible;
        int[] secondPositions;
        //los 4 casos para donde se quiere mover el jugador
        switch (side){
            case "n": //moverse hacia el norte
                movePosible = adyacence.comproveSide(initialG, initialG - longitud);
                secondPositions = new int[] {positionsP[0], positionsP[1] - 1};
                break;
            case "s": //moverse hacia el sur
                movePosible = adyacence.comproveSide(initialG, initialG + longitud);
                secondPositions = new int[] {positionsP[0], positionsP[1] + 1};
                break;
            case "e": //moverse hacia el este
                movePosible = adyacence.comproveSide(initialG, initialG + 1);
                secondPositions = new int[] {positionsP[0] + 1, positionsP[1]};
                break;
            case "w": //moverse hacia el oeste
                movePosible = adyacence.comproveSide(initialG, initialG - 1);
                secondPositions = new int[] {positionsP[0] - 1, positionsP[1]};
                break;
            default: //si todos los casos anteriores llegan a fallar
                movePosible = false;
                secondPositions = new int[] {};
        }
        //revisa que el usuario si se pueda mover hacia el lado que desea
        if (movePosible){
            return secondPositions;
        }
        return new int[] {};
    }

    /**
     * @return el tablero completo con todas las casillas
     */
    public Box[][] getCasillas(){
        return casillas;
    }
}
