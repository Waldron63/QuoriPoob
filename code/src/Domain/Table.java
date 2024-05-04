import java.awt.*;
import java.awt.Color;

public class Table {
    public static final String[] moves = new String[] {"n", "s", "e", "w", "ne", "nw", "se", "sw"};
    private int longitud;
    private Box[][] casillas;
    private TableAdyacence adyacence;

    public Table(int newLong){
        longitud = newLong;
        casillas = new Box[newLong][newLong];
        for (int i = 0; i < casillas.length; i++){
            for (int j = 0; j < casillas.length; j++){
                casillas[i][j] = new NormalBox();
            }
        }
        adyacence = new TableAdyacence(newLong);
    }

    public Box[][] getCasillas(){
        return casillas;
    }
}
