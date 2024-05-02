import java.awt.*;
import java.awt.Color;

public class Wall {
    private int size;
    private String type;
    private Color color;

    public Wall(Color newColor){
        size = 2;
        type = "normal";
        color = newColor;
    }
}
