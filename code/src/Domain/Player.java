import java.awt.*;
import java.awt.Color;

public abstract class Player {
    private String name;
    private int cantWalls;
    private Color color;
    private int xPosition;
    private int yPosition;


    public Player(String newName, Color newColor, int nWalls) {
        name = newName;
        cantWalls = nWalls + 1;
        color = newColor;
    }

    public int getCantWalls() {
        return cantWalls;
    }

    public abstract int[] move();
    public abstract void addWall();
}