import java.util.LinkedList;
import java.util.Queue;

/**
 * Clase de calcular la matriz adyacente de los grafos del tablero
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class TableAdyacence {
    private final int[] movements; //los movimientos que puede hacer el jugador (arriba, abajo, izquierda, derecha)
    private int longitudNormal; //longitud principal del tablero
    private int longitudAdyacencia; //longitud de la matriz de adyacencia
    private int[][] matrix; //matriz de adyacencia

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
        //ciclo para llenar la matriz de adyacencia de 0
        for (int i = 0; i < newLong; i++){
            for (int j = 0; j < newLong; j++){
                matrix[i][j]= 0;
            }
        }
        makeRelations();
    }

    /**
     * comprueba que se pueda pasar de un grafo a otro
     * @param initialG, grafo inicial o donde esta el usuario
     * @param finalG, grafo final o a donde quiere ir el usuario
     * @return true si si puede pasar de una celda a la otra, false en caso contrario
     */
    public boolean comproveSide(int initialG, int finalG) {
        //revisa que la celda a donde desee pasarse este en el rango
        if (finalG < 0 || finalG >= longitudNormal){
            return false;
        }
        //revisa si se puede pasar de una celda a otra
        if (matrix[initialG][finalG] == 1){
            return true;
        }
        return false;
    }

    /**
     * Algoritmo de busqueda, encuentra los caminos posibles que hay entre la primera celda a alguna del otro extremo
     * donde el jugador puede ganar
     * @param initialPos, posicion de grafo inicial
     * @param nPlayer, turno actual de la partida
     * @return true si existe algun camino para llegar al final, false en caso contrario
     */
    public boolean bfs(int initialPos, int nPlayer){
        int[][] matrizTemp = matrix;
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
