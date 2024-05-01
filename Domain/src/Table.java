import java.awt.*;
import java.awt.Color;

public class Table {
    private int longitud;
    private Color color;
    private Box[][] casillas;
    private String[][] typeCasillas;

    public Table(int newLong, Color newColor){
        longitud = newLong;
        color = newColor;
        casillas = new Box[newLong][newLong];
        typeCasillas = new String[newLong][newLong];
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; j < casillas.length; j++){
                casillas[i][j] = new Box();
                typeCasillas[i][j] = "normal";
            }
        }
    }
}
