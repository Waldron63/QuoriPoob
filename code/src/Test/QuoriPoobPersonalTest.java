//package Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * The test class Quopripoobv1.
 *
 * @author  POOB
 * @version 2024-1
 */
public class QuoriPoobPersonalTest {
    /**
     * Default constructor for test class Qupripoobv1
     */
    public QuoriPoobPersonalTest(){
    }

    @Test
    public void shouldThrowCreateBoardsOfDifferentSizes() throws QuoriPoobException {
        try {
            new QuoriPoob(0, "Normal");
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.SMALLEST_BOARD_LENGTH, e0.getMessage());
        }
        try {
            new QuoriPoob(9, "no normal");
        }catch (QuoriPoobException e1){
            assertEquals(QuoriPoobException.DIFFICULTY_NOT_FOUND, e1.getMessage());

        }
    }


    @Test
    public void shouldThrowAssignBarriersToPlayers() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        try {
            quoriPoob.setTypeWalls(new int[] {4,3,2});
        }catch (QuoriPoobException e0) {
            assertEquals(QuoriPoobException.WRONG_TOTAL_WALLS, e0.getMessage());
        }
        try {
            quoriPoob.setTypeWalls(new int[] {4,3,2, 7, 10});
        }catch (QuoriPoobException e1){
            assertEquals(QuoriPoobException.WRONG_TOTAL_WALLS, e1.getMessage());
        }
    }


    @Test
    public void shouldThrowMoveOrthogonallyAPawn() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        try {
            quoriPoob.move("s");
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.GRAPH_EXCEED_SIZE_TABLE, e0.getMessage());
        }
        quoriPoob.move("n");
        try {
            quoriPoob.move("s");
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.GRAPH_EXCEED_SIZE_TABLE, e0.getMessage());
        }
        try {
            quoriPoob.move("ne");
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
        try {
            quoriPoob.move("se");
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
        try {
            quoriPoob.move("nw");
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
        try {
            quoriPoob.move("sw");
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
    }


    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown(){

    }
}
