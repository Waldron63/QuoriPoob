import java.awt.*;
import java.awt.Color;

public abstract class Wall {
    private int size;
    private Color color;
    private int[] positions;

    public Wall(Color newColor, int[] newPositions){
        size = 2;
        positions= newPositions;
        color = newColor;
    }
}
