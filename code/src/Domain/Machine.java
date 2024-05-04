import java.awt.*;

public class Machine extends Player{
    private String difficulty;

    public Machine(String newDifficulty, int nWalls) {
        super("Robot", Color.BLACK, nWalls);
        difficulty = newDifficulty;
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
