import java.awt.*;

public class Human extends Player{

    public Human(String newName, Color newColor, int nWalls) {
        super(newName, newColor, nWalls);
    }

    @Override
    public int[] move() {
        return new int[0];
    }

    @Override
    public void addWall() {
        return;
    }
}
