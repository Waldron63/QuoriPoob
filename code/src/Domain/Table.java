import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * Clase Tablero la cual almacena todos los sucesos que cambian dentro del tablero del juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class Table implements Serializable {
    public static final String[] basicMoves = new String[] {"n", "e", "w", "s"}; //posiciones basicas de movimiento
    public static final String[] diagonalMoves = new String[] {"ne", "nw", "se", "sw"}; //posiciones diagonales de movimiento
    private HashMap<String, Integer> graphs; //posiciones y el numero respectivo del "grafo" de la matriz
    private HashMap<Integer, int[]> posGraphs; //numero del grafo y sus posiciones respectivas
    private int longitud; //longitud que va a tener el tablero
    private Box[][] casillas; //matriz con todas las casillas y jugadores
    private String[][] typeCasillas; //matriz con los tipos de casillas que tiene "casillas"
    private ArrayList<Wall> muros; //arreglo de todos los muros que se pueden llegar a colocar
    private TableAdyacence adyacence; //matriz de adyacencia para representar el grafo
    private int[][] previousBoxP1;
    private int[][] previousBoxP2;

    /**
     * Constructor for objects of class Table
     * @param newLong, longitud del tablero
     */
    public Table(int newLong) throws QuoriPoobException {
        if (newLong <= 3){
            throw new QuoriPoobException(QuoriPoobException.SMALLEST_BOARD_LENGTH);
        }
        longitud = newLong;
        muros = new ArrayList<>();
        casillas = new Box[newLong][newLong];
        typeCasillas = new String[newLong][newLong];
        graphs = new HashMap<>();
        posGraphs = new HashMap<>();
        int contador = 0;
        previousBoxP1 = new int[2][2];
        previousBoxP1[0] = null;
        previousBoxP1[1] = null;
        previousBoxP2 = new int[2][2];
        previousBoxP2[0] = null;
        previousBoxP2[1] = null;
        //genera la matriz de casillas para la inicializacion del juego, ademas de nombras los "grafos"
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; j < casillas.length; j++){
                casillas[i][j] = new NormalBox();
                typeCasillas[i][j] = "Normal";
                String pos = i + "," + j;
                graphs.put(pos, contador);
                posGraphs.put(contador, new int[] {i, j});
                contador += 1;
            }
        }
        adyacence = new TableAdyacence(newLong);
    }

    /**
     * anade el jugador a la matriz de adyacencia
     * @param numPlayer el numero que tiene el jugador en la partida
     * @param graphPos la posicion en el tablero en grafo que tiene el usaurio
     */
    public void addPlayer(int numPlayer, int graphPos){
        adyacence.addPlayer(numPlayer, graphPos);
    }

    /**
     * anade a la matriz los tipos de casillas diferentes, en lugares aleatorios
     * @param cantTypeBoxes, dicta el numero de casillas especiales que quiere de cada tipo
     */
    public void addRandomBox(int[] cantTypeBoxes){
        Random random = new Random();
        int posX = random.nextInt(casillas.length);
        int posY = random.nextInt(casillas.length);
        //recorre el array de los nuevos tipos de casillas a agregar
        for (int i = 0; i < cantTypeBoxes.length; i++){
            //recorre la cantidad de casilla especial que quere de este tipo
            for (int j = 0; j < cantTypeBoxes[i]; j++){
                Box box;
                String typeBox;
                switch (i){
                    //si es tipo Teletransportador
                    case 0:
                        box = new TeleportBox();
                        typeBox = "Teletransportador";
                        break;
                    //si es tipo Regresar
                    case 1:
                        box = new ReturnBox();
                        typeBox = "Regresar";
                        break;
                    case 2:
                    //si es tipo Doble
                        box = new DoubleBox();
                        typeBox = "Doble";
                        break;
                    default:
                        box = new NormalBox();
                        typeBox = "Normal";
                        break;
                }
                while (!typeCasillas[posX][posY].equals("Normal")){
                    posX = random.nextInt(casillas.length);
                    posY = random.nextInt(casillas.length);
                }
                casillas[posX][posY] = box;
                typeCasillas[posX][posY] = typeBox;
            }
        }
    }

    /**
     * anade los muros al tablero
     * @param newWall el nuevo muro que se va a colocar
    //* @return true si el muro fue anadido correctamente, false en caso contrario
     */
    public void addWall(Wall newWall, Player playerOne, Player playerTwo) throws QuoriPoobException{
        adyacence.addWall(newWall, playerOne, playerTwo);
        muros.add(newWall);
    }

    /**
     * mueve cualquier jugador en su respectivo turno teniendo en cuenta el lado que decidio moverse
     * @param positionsP, las posiciones actuales del jugador
     * @param side, lado hacia el que quiere ir el jugador
     * @param turn el turno actual de la partida
     * @return las nuevas posiciones en las que va a estar el jugador
     */
    public int[] move(int[] positionsP, String side, int turn) throws QuoriPoobException {
        //revisa que si sea un lado valido para moverse
        if (! Arrays.asList(basicMoves).contains(side) && ! Arrays.asList(diagonalMoves).contains(side)){
            throw new QuoriPoobException(QuoriPoobException.MOVEMENT_NOT_POSSIBLE);
        }
        int[] positionsTp = isTeleportBox(positionsP[0], positionsP[1], side);
        if (positionsTp != null){
            addPreviousSide(positionsP, turn);
            return positionsTp;
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
                secondPositions = new int[] {positionsP[0] + 1,positionsP[1] - 1};
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
            addPreviousSide(positionsP, turn);
            int[] thirdPosition = isReturnBox(secondPositions[0], secondPositions[1], turn);
            if (thirdPosition == null){
                return secondPositions;
            }else{
                return thirdPosition;
            }
        }else{
            throw new QuoriPoobException(QuoriPoobException.MOVEMENT_NOT_POSSIBLE);
        }
    }

    /**
     * metodo privado que crea un "historial" de los ultimos 2 pasos que ha usado el jugador
     * @param positionsP posiciones de las casillas en donde estyvo el usuario
     * @param turn turno actual del jugador
     */
    private void addPreviousSide(int[] positionsP,int turn){
        if (turn == 1){
            if (previousBoxP1[0] == null){
                previousBoxP1[0] = positionsP;
            } else if (previousBoxP1[1] == null) {
                previousBoxP1[1] = positionsP;
            }else{
                previousBoxP1[0] = previousBoxP1[1];
                previousBoxP1[1] = positionsP;
            }
        }else{
            if (previousBoxP2[0] == null){
                previousBoxP2[0] = positionsP;
            } else if (previousBoxP2[1] == null) {
                previousBoxP2[1] = positionsP;
            }else{
                previousBoxP2[0] = previousBoxP2[1];
                previousBoxP2[1] = positionsP;
            }
        }
    }

    /**
     * revisa que el usuario este en una casilla teletransportadora
     * @param xPosition posicion x de la casilla
     * @param yPosition posicion y de la casilla
     * @param side sitio hacia donde se quiere mover el jugador
     * @return arreglo con las nuevas posiciones del usuario, nulo si no es casilla teleport
     */
    private int[] isTeleportBox(int xPosition, int yPosition, String side) throws QuoriPoobException{
        String typeBox = typeCasillas[xPosition][yPosition];
        if (Objects.equals(typeBox, "Teletransportador")){
            String pos = xPosition + "," + yPosition;
            int initialG = graphs.get(pos);
            String newPos;
            int finalG;
            int [] finalPositions;
            switch (side){
                // CASOS BASICOS
                case "n": //moverse hacia el norte
                    newPos = (xPosition - 1) + "," + yPosition;
                    finalG = graphs.get(newPos);
                    adyacence.movePlayer(initialG, finalG);
                    finalPositions = new int[] {xPosition - 1, yPosition};
                    break;
                case "s": //moverse hacia el sur
                    newPos = (xPosition + 1) + "," + yPosition;
                    finalG = graphs.get(newPos);
                    adyacence.movePlayer(initialG, finalG);
                    finalPositions = new int[] {xPosition + 1, yPosition};
                    break;
                case "e": //moverse hacia el este
                    newPos = xPosition + "," + (yPosition + 1);
                    finalG = graphs.get(newPos);
                    adyacence.movePlayer(initialG, finalG);
                    finalPositions = new int[] {xPosition, yPosition + 1};
                    break;
                case "w": //moverse hacia el oeste
                    newPos = xPosition + "," + (yPosition - 1);
                    finalG = graphs.get(newPos);
                    adyacence.movePlayer(initialG, finalG);
                    finalPositions = new int[] {xPosition, yPosition - 1};
                    break;

                //CASOS DIAGONALES
                case "ne": //moverse hacia el nor este
                    newPos = (xPosition - 1) + "," + (yPosition + 1);
                    finalG = graphs.get(newPos);
                    adyacence.movePlayer(initialG, finalG);
                    finalPositions = new int[] {xPosition - 1, yPosition + 1};
                    break;
                case "nw": //moverse hacia el nor oeste
                    newPos = (xPosition - 1) + "," + (yPosition - 1);
                    finalG = graphs.get(newPos);
                    adyacence.movePlayer(initialG, finalG);
                    finalPositions = new int[] {xPosition - 1, yPosition - 1};
                    break;
                case "se": //moverse hacia el sur este
                    newPos = (xPosition + 1) + "," + (yPosition + 1);
                    finalG = graphs.get(newPos);
                    adyacence.movePlayer(initialG, finalG);
                    finalPositions = new int[] {xPosition + 1, yPosition + 1};
                    break;
                case "sw": //moverse hacia el sur oeste
                    newPos = (xPosition + 1) + "," + (yPosition - 1);
                    finalG = graphs.get(newPos);
                    adyacence.movePlayer(initialG, finalG);
                    finalPositions = new int[] {xPosition + 1, yPosition - 1};
                    break;
                default:
                    finalPositions = new int[] {xPosition, yPosition};
                    break;
            }
            return finalPositions;
        }else{
            return null;
        }
    }

    /**
     * revisa si la casilla que avanzo es una de retorno, en cuyo caso si, devuelve al jugador
     * @param xPosition posicion x del jugador
     * @param yPosition posicion y del jugador
     * @param turn turno actual del jugador
     * @return arreglo con las nuevas posiciones del usuario, nulo si no es casilla retorno
     */
    private int[] isReturnBox(int xPosition, int yPosition, int turn) throws QuoriPoobException {
        String typeBox = typeCasillas[xPosition][yPosition];
        String pos = xPosition + "," + yPosition;
        int initialG = graphs.get(pos);
        String pos2;
        int finalG;
        boolean comproveReturn1;
        if (Objects.equals(typeBox, "Regresar")){
            int[] newPositions = new int[] {xPosition, yPosition};
            if (turn == 1){
                if (previousBoxP1[0] != null) {
                    pos2 = previousBoxP1[0][0] + "," + previousBoxP1[0][1];
                    finalG = graphs.get(pos2);
                    comproveReturn1 = adyacence.bfs(finalG, turn);
                    if (comproveReturn1) {
                        newPositions = previousBoxP1[0];
                        adyacence.movePlayer(initialG, finalG);
                    }else if (previousBoxP1[1] != null){
                        pos2 = previousBoxP1[1][0] + "," + previousBoxP1[1][1];
                        finalG = graphs.get(pos2);
                        comproveReturn1 = adyacence.bfs(finalG, turn);
                        if (comproveReturn1){
                            newPositions = previousBoxP1[1];
                            adyacence.movePlayer(initialG, finalG);
                        }
                    }
                }
            }else{
                if (previousBoxP2[0] != null) {
                    pos2 = previousBoxP2[0][0] + "," + previousBoxP2[0][1];
                    finalG = graphs.get(pos2);
                    comproveReturn1 = adyacence.bfs(finalG, turn);
                    if (comproveReturn1) {
                        newPositions = previousBoxP2[0];
                        adyacence.movePlayer(initialG, finalG);
                    }else if (previousBoxP1[1] != null){
                        pos2 = previousBoxP2[1][0] + "," + previousBoxP2[1][1];
                        finalG = graphs.get(pos2);
                        comproveReturn1 = adyacence.bfs(finalG, turn);
                        if (comproveReturn1){
                            newPositions = previousBoxP2[1];
                            adyacence.movePlayer(initialG, finalG);
                        }
                    }
                }
            }
            return newPositions;
        }else{
            return null;
        }
    }

    /**
     * @return el tablero completo con todas las casillas
     */
    public String[][] getCasillas(){
        return typeCasillas;
    }

    /**
     * @param xPosition posicion en x de el grafo a buscar en la matriz
     * @param yPosition posicion en y de el grafo a buscar en la matriz
     * @return el grafo que tiene esas 2 posiciones
     */
    public int getGraphPosition(int xPosition, int yPosition) throws QuoriPoobException {
        String pos = xPosition + "," + yPosition;
        if (graphs.containsKey(pos)) {
            return graphs.get(pos);
        }else{
            throw new QuoriPoobException(QuoriPoobException.GRAPH_EXCEED_SIZE_TABLE);
        }
    }

    public ArrayList<Integer>[] getWallsPositions(){
        ArrayList<Integer>[] posicionesMuros = new ArrayList[muros.size()];
        int cont = 0;
        for (Wall wall : muros){
            ArrayList<Integer> addPos = new ArrayList<>();
            ArrayList<Integer> murosP = wall.getPositions();
            for (int i = 0; i < murosP.size(); i++){
                int[] posGraph = posGraphs.get(murosP.get(i));
                addPos.add(posGraph[0]);
                addPos.add(posGraph[1]);
            }
            posicionesMuros[cont] = addPos;
            cont += 1;
        }
        return posicionesMuros;
    }

    public int[] getGraphPosition(int graph){
        return  posGraphs.get(graph);
    }

    /**
     * cambia el contador de los muros si es que tienen contador.
     */
    public ArrayList<Integer> changeWallCount() throws QuoriPoobException {
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
            player.addCantWalls("Muro Temporal");
            muros.remove(temporal);
            adyacence.delWall(temporal);
            return temporal.getPositions();
        }
        return null;
    }

    public void changeBox(int xPosition, int yPosition){
        casillas[xPosition][yPosition] = new NormalBox();
        typeCasillas[xPosition][yPosition] = "Normal";
    }
}
