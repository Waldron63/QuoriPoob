import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase de calcular la matriz adyacente de los grafos del tablero
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class TableAdyacence implements Serializable {
    private final int[] movements; //los movimientos que puede hacer el jugador (arriba, abajo, izquierda, derecha)
    private int longitudNormal; //longitud principal del tablero
    private int longitudAdyacencia; //longitud de la matriz de adyacencia
    private int[][] matrix; //matriz de adyacencia
    private HashMap<Integer, Wall>[] arrayAdyacence; //arreglo de adyacencia que guarda las relaciones entre las celdas y si hay un muro

    /**
     * Constructor for objects of class TableAdyacence
     *
     * @param newLong longitud que tiene el tablero
     */
    public TableAdyacence(int newLong) {
        longitudNormal = newLong;
        movements = new int[] {-1,-longitudNormal, 1,longitudNormal};
        longitudAdyacencia = newLong * newLong;
        matrix = new int[longitudAdyacencia][longitudAdyacencia];
        arrayAdyacence = (HashMap<Integer,Wall>[]) new HashMap[longitudAdyacencia];
        //ciclo para llenar la matriz de adyacencia de 0
        for (int i = 0; i < newLong; i++){
            for (int j = 0; j < newLong; j++){
                matrix[i][j]= 0;
            }
        }
        //rellena de hashmaps vacios la lista de adyacencia
        for (int k = 0; k < longitudAdyacencia; k++) {
            arrayAdyacence[k] = new HashMap<>();
        }
        makeRelations();
    }

    /**
     * anade el jugador a la matriz de adyacencia
     * @param numPlayer numero de el jugador que quiere anadir
     * @param numGraph la posicion en grafo de el tablero
     */
    public void addPlayer(int numPlayer, int numGraph){
        matrix[numGraph][numGraph] = numPlayer;
    }

    /**
     * anade los muros a la lista de adyacencia y cambia los valores en la matriz
     * @param newWall el nuevo muro que se va a anadir
     * @param fPlayer primero jugador de la partida
     * @param sPlayer segundo jugador de la partida
     * @return true si el muro si se puede colocar, false en caso contrario
     */
    public void addWall(Wall newWall, Player fPlayer, Player sPlayer) throws QuoriPoobException {
        ArrayList<Integer> selectGraph = newWall.getPositions();

        int turn1 = fPlayer.getMainTurn();
        int posGraph1 = fPlayer.getPositionGraph();

        int turn2 = sPlayer.getMainTurn();
        int posGraph2 = sPlayer.getPositionGraph();

        boolean isWallPosible1 = true;
        boolean isWallPosible2 = true;
        int k = selectGraph.size();
        //revisa que se puedan colocar todos los elementos del muro sin cerrar todos los caminos
        for (int i = 0; i < selectGraph.size(); i = i +2){
            if (matrix[selectGraph.get(i)][selectGraph.get(i + 1)] == -1){
                k = i;
                isWallPosible1 = false;
                isWallPosible2 = false;
                break;
            }else {
                changeRelationMatrix(selectGraph.get(i), selectGraph.get(i + 1), -1);
                isWallPosible1 = bfs(posGraph1, turn1);
                isWallPosible2 = bfs(posGraph2, turn2);
                //si se cierra todos los caminos, rompe el ciclo
                if (!isWallPosible1 || !isWallPosible2) {
                    k = i + 1;
                    break;
                }
            }
        }
        //si no se cerraron todos los caminos, anade el puente a la lista
        if (isWallPosible1 && isWallPosible2){
            for (int j = 0; j < selectGraph.size(); j = j + 2){
                arrayAdyacence[selectGraph.get(j)].put(selectGraph.get(j + 1), newWall);
                arrayAdyacence[selectGraph.get(j + 1)].put(selectGraph.get(j), newWall);
            }
            //return true;
        }else{ //en cuyo caso si halla cerrado todos los caminos, devuelve la matriz a como estaba inicialmente
            for (int j = 0; j < k; j = j + 2) {
                changeRelationMatrix(selectGraph.get(j), selectGraph.get(j + 1), 1);
            }
            throw new QuoriPoobException(QuoriPoobException.WRONG_SIDE_WALL);
            //return false;
        }
    }

    /**
     * elimina los muros que ya no son necesarios en el tablero.
     * @param oldWall muro que va a ser eliminado
     */
    public void delWall(Wall oldWall){
        ArrayList<Integer> selectGraph = oldWall.getPositions();
        //elimina todas las relaciones de el grafo que tengan este muro
        for (int i = 0; i < selectGraph.size(); i = i +2){
            changeRelationMatrix(selectGraph.get(i), selectGraph.get(i + 1), 1);
            arrayAdyacence[selectGraph.get(i)].remove(selectGraph.get(i + 1));
            arrayAdyacence[selectGraph.get(i + 1)].remove(selectGraph.get(i));
        }
    }

    /**
     * cambia la relacion de la matriz por el valor que le digamos
     * @param graph1 primera relacion de grafo
     * @param graph2 segunda relacion de grafo
     * @param value valor que se va a cambiar
     */
    private void changeRelationMatrix(int graph1, int graph2, int value){
        matrix[graph1][graph2] = value;
        matrix[graph2][graph1] = value;
    }

    /**
     * indica a donde se va a mover el usuario en la matriz de adyacencia
     * @param initialG grafo donde esta ubicado el usuario
     * @param finalG grafo a donde se va a mover el usuario
     */
    public void movePlayer(int initialG, int finalG) throws QuoriPoobException{
        if (finalG < 0 || finalG >= longitudAdyacencia){
            throw new QuoriPoobException(QuoriPoobException.GRAPH_EXCEED_SIZE_TABLE);
        }else if (matrix[finalG][finalG] != 0){
            throw new QuoriPoobException(QuoriPoobException.MOVEMENT_NOT_POSSIBLE);
        }
        int value = matrix[initialG][initialG];
        matrix[initialG][initialG] = 0;
        matrix[finalG][finalG] = value;
    }

    /**
     * comprueba que se pueda pasar de un grafo a otro
     * @param initialG, grafo inicial o donde esta el usuario
     * @param finalG, grafo final o a donde quiere ir el usuario
     * @param turn, indica el turno atual en la partida
     * @return true si si puede pasar de una celda a la otra, false en caso contrario
     */
    public Boolean comproveBasicSide(int initialG, int finalG, int turn) throws QuoriPoobException {
        //revisa que la celda a donde desee pasarse este en el rango
        if (finalG < 0 || finalG >= longitudAdyacencia){
            throw new QuoriPoobException(QuoriPoobException.GRAPH_EXCEED_SIZE_TABLE);
        }
        //revisa si se puede pasar de una celda a otra
        if (matrix[initialG][finalG] == 1){
            if (matrix[finalG][finalG] == 0){
                return true;
            }else {
                return null;
            }
        }else if (matrix[initialG][finalG] == -1){ //si hay algun muro en esta relacion de grafos y es muro aliado al jugador actual
            Wall putWall = arrayAdyacence[initialG].get(finalG);
            boolean confirmTurn = putWall.samePlayerPass(turn);
            //revisa que sea el mismo jugador y el muro sea aliado
            if (confirmTurn){
                if (matrix[finalG][finalG] == 0){
                    return true;
                } else {
                    return null;
                }
            }
        }
        return false;
    }

    /**
     * Algoritmo de busqueda, encuentra los caminos posibles que hay entre la primera celda a alguna del otro extremo
     * donde el jugador puede ganar
     * @param initialPos, posicion de grafo inicial
     * @param nPlayer, turno del jugador
     * @return true si existe algun camino para llegar al final, false en caso contrario
     */
    public boolean bfs(int initialPos, int nPlayer){
        int[][] matrizTemp = new int[matrix.length][matrix.length];
        for (int m = 0; m < matrix.length; m++){
            for(int n = 0; n < matrix.length; n++){
                matrizTemp[m][n] = matrix[m][n];
            }
        }
        matrizTemp[initialPos][initialPos] = -1;
        Queue<Integer> q = new LinkedList<>();
        q.add(initialPos);
        //ciclo que dice si aun queda algun camino por recorrer o no
        while (!q.isEmpty()) {
            int ini = q.poll();
            //recorre los movimientos que puede hacer el jugador, para saber si
            // tiene alguna celda disponible que no halla mirado
            for (int movement : movements) {
                int x = ini + movement;
                //revisa el rango de el grafo
                if (x >= 0 && x < matrizTemp.length){
                    //revisa si hay un 0
                    if (matrizTemp[ini][x] == 0) {
                        matrizTemp[ini][x] = -1;
                        //caso contrario revisa si hay un 1 o un -1
                    }else if (matrizTemp[ini][x] == 1 && matrizTemp[x][x] != -1){
                        q.add(x);
                        matrizTemp[ini][x] = -1;
                        matrizTemp[x][ini] = -1;
                        matrizTemp[x][x] = -1;
                    }
                    //revisa el turno del jugador 1 y el rango de la meta de este jugador
                    if (nPlayer == 1 && (x >= 0 && x < longitudNormal)){
                        return true;
                        //revisa el turno del jugador 2 y el rango de la meta de este jugador
                    }else if (nPlayer == 2 && (x >= (longitudAdyacencia - longitudNormal) && x < longitudAdyacencia)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * rellena la matriz de adyacencia con 1 donde se relacionan las celdas
     */
    private void makeRelations(){
        //recorre la cantidad total de grafos que hay para obtener sus relaciones
        for (int i = 0; i < matrix.length; i++){
            //revisa el grafo de la derecha
            int x1 = i +1;
            if (x1 < matrix.length && x1 % longitudNormal != 0){
                matrix[i][x1] = 1;
            }
            //revisa el grafo de la izquierda
            int x2 = i -1;
            if(x2 >= 0 && i % longitudNormal != 0){
                matrix[i][x2] = 1;
            }
            //revisa el grafo de abajo
            int y1 = i + longitudNormal;
            if(y1 < matrix.length){
                matrix[i][y1] = 1;
            }
            //revisa el grafo de arriba
            int y2 = i - longitudNormal;
            if(y2 >= 0){
                matrix[i][y2] = 1;
            }
        }
    }
}
