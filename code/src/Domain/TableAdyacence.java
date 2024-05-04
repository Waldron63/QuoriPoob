import java.util.LinkedList;
import java.util.Queue;

public class TableAdyacence {
    private final int[] movements;
    private int longitudNormal;
    private int longitudAdyacencia;
    private int[][] matrix;

    public TableAdyacence(int newLong) {
        longitudNormal = newLong;
        movements = new int[] {-1,-longitudNormal, 1,longitudNormal};
        longitudAdyacencia = newLong * newLong;
        matrix = new int[longitudAdyacencia][longitudAdyacencia];
        for (int i = 0; i < newLong; i++){
            for (int j = 0; j < newLong; j++){
                matrix[i][j]= 0;
            }
        }
        makeRelations();
    }

    public boolean bfs(int initialPos, int nPlayer){
        int[][] matrizTemp = matrix;
        matrizTemp[initialPos][initialPos] = -1;
        Queue<Integer> q = new LinkedList<>();
        q.add(initialPos);
        while (!q.isEmpty()) {
            int ini = q.poll();
            for (int movement : movements) {
                int x = ini + movement;
                if (x >= 0 && x < matrizTemp.length){
                    if (matrizTemp[ini][x] == 1 && matrizTemp[x][x] != -1){
                        q.add(x);
                        matrizTemp[ini][x] = -1;
                        matrizTemp[x][ini] = -1;
                        matrizTemp[x][x] = -1;
                    }
                    if (nPlayer == 1 && (x >= 0 && x < longitudNormal)){
                        return true;
                    }else if (nPlayer == 2 && (x >= (longitudAdyacencia - longitudNormal) && x < longitudAdyacencia)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void makeRelations(){
        for (int i = 0; i < matrix.length; i++){
            int x1 = i +1;
            if (x1 < matrix.length && x1 % longitudNormal != 0){
                matrix[i][x1] = 1;
            }
            int x2 = i -1;
            if(x2 >= 0 && i % longitudNormal != 0){
                matrix[i][x2] = 1;
            }
            int y1 = i + longitudNormal;
            if(y1 < matrix.length){
                matrix[i][y1] = 1;
            }
            int y2 = i - longitudNormal;
            if(y2 >= 0){
                matrix[i][y2] = 1;
            }
        }
    }
}
