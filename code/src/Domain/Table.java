import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Clase Tablero la cual almacena todos los sucesos que cambian dentro del tablero del juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class Table implements Serializable {
    public static final String[] basicMoves = new String[] {"n", "e", "w", "s"}; //posiciones basicas de movimiento
    public static final String[] diagonalMoves = new String[] {"ne", "nw", "se", "sw"}; //posiciones diagonales de movimiento
    private HashMap<String, Integer> graphs; //posiciones y el numero respectivo del "grafo" de la matriz
    private int longitud; //longitud que va a tener el tablero
    private Box[][] casillas; //matriz con todas las casillas y jugadores
    private ArrayList<Wall> muros; //arreglo de todos los muros que se pueden llegar a colocar
    private TableAdyacence adyacence; //matriz de adyacencia para representar el grafo

    /**
     * Constructor for objects of class Table
     * @param newLong, longitud del tablero
     */
    public Table(int newLong){
        longitud = newLong;
        muros = new ArrayList<>();
        casillas = new Box[newLong][newLong];
        graphs = new HashMap<>();
        int contador = 0;
        //genera la matriz de casillas para la inicializacion del juego, ademas de nombras los "grafos"
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; j < casillas.length; j++){
                casillas[i][j] = new NormalBox();
                String pos = i + "," + j;
                graphs.put(pos, contador);
                contador += 1;
            }
        }
        adyacence = new TableAdyacence(newLong);
    }

    public void addPlayer(int numPlayer, int graphPos){
        adyacence.addPlayer(numPlayer, graphPos);
    }

    /**
     * anade los muros al tablero
     * @param newWall el nuevo muro que se va a colocar
     */
    public boolean addWall(Wall newWall, Player playerOne, Player playerTwo){
        boolean isPosible =  adyacence.addWall(newWall, playerOne, playerTwo);
        if (isPosible){
            muros.add(newWall);
            return true;
        }else{
            return false;
        }
    }

    public void addRandomBox(int[] cantTypeBoxes){
        for (int i = 0; i < cantTypeBoxes.length; i++){
            for (int j = 0; j < cantTypeBoxes[i]; j++){
                Box box;
                switch (i){
                    case 0:
                        box = new TeleportBox();
                        break;
                    case 1:
                        box = new ReturnBox();
                        break;
                    case 2:
                        box = new DoubleBox();
                        break;
                }
                
            }

        }
    }

    /**
     * mueve cualquier jugador en su respectivo turno teniendo en cuenta el lado que decidio moverse
     * @param positionsP, las posiciones actuales del jugador
     * @param side, lado hacia el que quiere ir el jugador
     * @param turn el turno actual de la partida
     * @return las nuevas posiciones en las que va a estar el jugador
     */
    public int[] move(int[] positionsP, String side, int turn){
        //revisa que si sea un lado valido para moverse
        if (! Arrays.asList(basicMoves).contains(side) && ! Arrays.asList(diagonalMoves).contains(side)){
            return new int[] {};
        }
        String pos = positionsP[0] + "," + positionsP[1];
        int initialG = graphs.get(pos);
        Boolean movePosible;
        Boolean moveDOne;
        Boolean moveDTwo;
        int[] secondPositions;
        //los 8 casos para donde se quiere mover el jugador
        int finalG;
        switch (side){
            // CASOS BASICOS
            case "n": //moverse hacia el norte
                finalG = initialG - longitud;
                movePosible = adyacence.comproveBasicSide(initialG, finalG, turn);
                secondPositions = new int[] {positionsP[0] - 1, positionsP[1]};
                //si el jugador contrario esta en la casilla a la que quiere mover
                if (movePosible == null){
                    finalG = initialG - longitud - longitud;
                    movePosible = adyacence.comproveBasicSide(initialG - longitud, finalG, turn);
                    secondPositions = new int[] {positionsP[0] - 2, positionsP[1]};
                }
                break;
            case "s": //moverse hacia el sur
                finalG = initialG + longitud;
                movePosible = adyacence.comproveBasicSide(initialG, finalG, turn);
                secondPositions = new int[] {positionsP[0] + 1, positionsP[1]};
                if (movePosible == null){
                    finalG = initialG + longitud + longitud;
                    movePosible = adyacence.comproveBasicSide(initialG + longitud, finalG, turn);
                    secondPositions = new int[] {positionsP[0] + 2, positionsP[1]};
                }
                break;
            case "e": //moverse hacia el este
                finalG = initialG + 1;
                movePosible = adyacence.comproveBasicSide(initialG, finalG, turn);
                secondPositions = new int[] {positionsP[0], positionsP[1] + 1};
                if (movePosible == null){
                    finalG = initialG + 2;
                    movePosible = adyacence.comproveBasicSide(initialG +1, finalG, turn);
                    secondPositions = new int[] {positionsP[0], positionsP[1] + 2};
                }
                break;
            case "w": //moverse hacia el oeste
                finalG = initialG - 1;
                movePosible = adyacence.comproveBasicSide(initialG, finalG, turn);
                secondPositions = new int[] {positionsP[0], positionsP[1] - 1};
                if (movePosible == null){
                    finalG = initialG - 2;
                    movePosible = adyacence.comproveBasicSide(initialG -1, finalG, turn);
                    secondPositions = new int[] {positionsP[0], positionsP[1] - 2};
                }
                break;

            //CASOS DIAGONALES
            case "ne": //moverse hacia el nor este
                moveDOne = adyacence.comproveBasicSide(initialG, initialG - longitud, turn);
                moveDTwo = adyacence.comproveBasicSide(initialG, initialG + 1, turn);
                if (moveDOne == null && !moveDTwo){
                    movePosible = adyacence.comproveBasicSide(initialG - longitud, initialG -longitud +1, turn);
                }else if (moveDTwo == null && !moveDOne){
                    movePosible = adyacence.comproveBasicSide(initialG +1, initialG +1 - longitud, turn);
                }else{
                    movePosible = false;
                }
                finalG = initialG + 1 - longitud;
                secondPositions = new int[] {positionsP[0] -1,positionsP[1] +1};
                break;
            case "nw": //moverse hacia el nor oeste
                moveDOne = adyacence.comproveBasicSide(initialG, initialG - longitud, turn);
                moveDTwo = adyacence.comproveBasicSide(initialG, initialG - 1, turn);
                if (moveDOne == null && !moveDTwo){
                    movePosible = adyacence.comproveBasicSide(initialG - longitud, initialG -longitud -1, turn);
                }else if (moveDTwo == null && !moveDOne){
                    movePosible = adyacence.comproveBasicSide(initialG - 1, initialG -1 -longitud, turn);
                }else{
                    movePosible = false;
                }
                finalG = initialG - 1 - longitud;
                secondPositions = new int[] {positionsP[0] -1,positionsP[1] -1};
                break;
            case "se": //moverse hacia el sur este
                moveDOne = adyacence.comproveBasicSide(initialG, initialG + longitud, turn);
                moveDTwo = adyacence.comproveBasicSide(initialG, initialG + 1, turn);
                if (moveDOne == null && !moveDTwo){
                    movePosible = adyacence.comproveBasicSide(initialG + longitud, initialG + longitud + 1, turn);
                }else if (moveDTwo == null && !moveDOne){
                    movePosible = adyacence.comproveBasicSide(initialG +1, initialG + 1 + longitud, turn);
                }else{
                    movePosible = false;
                }
                finalG = initialG + 1 + longitud;
                secondPositions = new int[] {positionsP[0] + 1,positionsP[1] + 1};
                break;
            case "sw": //moverse hacia el sur oeste
                moveDOne = adyacence.comproveBasicSide(initialG, initialG + longitud, turn);
                moveDTwo = adyacence.comproveBasicSide(initialG, initialG - 1, turn);
                if (moveDOne == null && !moveDTwo){
                    movePosible = adyacence.comproveBasicSide(initialG + longitud, initialG +longitud - 1, turn);
                }else if (moveDTwo == null && !moveDOne){
                    movePosible = adyacence.comproveBasicSide(initialG -1, initialG -1 + longitud, turn);
                }else{
                    movePosible = false;
                }
                finalG = initialG - 1 + longitud;
                secondPositions = new int[] {0,0};
                break;

            //caso diferente
            default: //si todos los casos anteriores llegan a fallar
                finalG = 0;
                movePosible = false;
                secondPositions = new int[] {};
        }
        //revisa que el usuario si se pueda mover hacia el lado que desea
        if (movePosible){
            adyacence.movePlayer(initialG, finalG);
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

    /**
     * @param xPosition posicion en x de el grafo a buscar en la matriz
     * @param yPosition posicion en y de el grafo a buscar en la matriz
     * @return el grafo que tiene esas 2 posiciones
     */
    public int getGraphPosition(int xPosition, int yPosition){
        String pos = xPosition + "," + yPosition;
        return graphs.get(pos);
    }

    /**
     * cambia el contador de los muros si es que tienen contador.
     */
    public void changeWallCount(){
        Wall temporal = null;
        //itera en todos los muros que se han colocado en el tablero
        for (Wall walls : muros){
            boolean comp = walls.changeTimes();
            //comprueba que ningun muro tenga que desaparecer
            if (!comp){
                temporal = walls;
            }
        }
        //si hay algun muro que deba desaparecer, lo elimina
        if (temporal != null){
            Player player = temporal.getPlayer();
            player.addCantWalls();
            muros.remove(temporal);
            adyacence.delWall(temporal);
        }
    }
}
