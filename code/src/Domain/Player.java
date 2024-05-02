import java.awt.*;
import java.awt.Color;

public class Player {
    private String name;
    private int cantWalls;
    private Color color;

    public Player(int nWalls, Color newColor, String newName){
        name = newName;
        cantWalls = nWalls;
        color = newColor;
    }
}
