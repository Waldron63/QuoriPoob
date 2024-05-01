import java.awt.*;
import java.awt.Color;

public class Player {
    private int cantWalls;
    private Color color;
    private Box[][] casillas;
    private String[][] typeCasillas;

    public Player(int nWalls, Color newColor){
        cantWalls = nWalls;
        color = newColor;
        casillas = new Box[nWalls][nWalls];
        typeCasillas = new String[nWalls][nWalls];
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; j < casillas.length; j++){
                casillas[i][j] = new Box();
                typeCasillas[i][j] = "normal";
            }
        }
    }
}
