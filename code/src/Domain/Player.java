import java.awt.*;
import java.awt.Color;

public class Player {
    private String name;
    private int cantWalls;
    private Color color;

    public Player(String newName, Color newColor, int nWalls) {
        name = newName;
        cantWalls = nWalls + 1;
        color = newColor;
    }

    public int getCantWalls() {
        return cantWalls;
    }
}