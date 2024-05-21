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
public class Quoripoobvi {
    /**
     * Default constructor for test class Qupripoobv1
     */
    public Quoripoobvi(){
    }

    @Test
    public void shouldCreateBoardsOfDifferentSizes() throws QuoriPoobException {
        QuoriPoob qp4 = new QuoriPoob(4, "Normal");
        assertEquals(4, qp4.getSizeTable());
        assertEquals("Normal", qp4.getDifficult());
        assertEquals(4, qp4.getBoard().length);
        QuoriPoob qp6 = new QuoriPoob(6, "Normal");
        assertEquals(6, qp6.getSizeTable());
        assertEquals("Normal", qp4.getDifficult());
        assertEquals(6, qp6.getBoard().length);
        QuoriPoob qp9 = new QuoriPoob(9, "Normal");
        assertEquals(9, qp9.getSizeTable());
        assertEquals("Normal", qp4.getDifficult());
        assertEquals(9, qp9.getBoard().length);
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
    public void shouldAssignBarriersToPlayers() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.setTypeWalls(new int[] {4,3,2,1});
        int[] wall1 = quoriPoob.getPlayerCountWalls(1);
        int[] wall2 = quoriPoob.getPlayerCountWalls(2);
        assertEquals(4, wall1.length); //muestra que tiene los 4 tipos de barrera
        //muestra que los 2 usuarios tienen la misma cantidad de muros de cada tipo
        assertEquals(wall1[0], wall2[0]);
        assertEquals(wall1[1], wall2[1]);
        assertEquals(wall1[2], wall2[2]);
        assertEquals(wall1[3], wall2[3]);
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
    public void shouldMoveOrthogonallyAPawn() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.move("e");
        int[] sides;
        sides = quoriPoob.getPlayerPositions(1);
        assertEquals(sides[0], 8);
        assertEquals(sides[1], 5);
        quoriPoob.move("w");
        sides = quoriPoob.getPlayerPositions(2);
        assertEquals(sides[0], 0);
        assertEquals(sides[1], 3);
        quoriPoob.move("n");
        sides = quoriPoob.getPlayerPositions(1);
        assertEquals(sides[0], 7);
        assertEquals(sides[1], 5);
        quoriPoob.move("s");
        sides = quoriPoob.getPlayerPositions(2);
        assertEquals(sides[0], 1);
        assertEquals(sides[1], 3);
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


    @Test
    public void shouldMoveDiagonallyAPawn(){
        fail();
    }

    @Test
    public void shouldPlaceANormalBarrier(){
        fail();
    }


    @Test
    public void shouldMoveAPawnOverAPawn() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        int[] sides;
        sides = quoriPoob.getPlayerPositions(1);
        assertEquals(sides[0], 5);
        assertEquals(sides[1], 4);
        sides = quoriPoob.getPlayerPositions(2);
        assertEquals(sides[0], 3);
        assertEquals(sides[1], 4);
        quoriPoob.move("n");
        quoriPoob.move("s");
        sides = quoriPoob.getPlayerPositions(1);
        assertEquals(sides[0], 4);
        assertEquals(sides[1], 4);
        sides = quoriPoob.getPlayerPositions(2);
        assertEquals(sides[0], 5);
        assertEquals(sides[1], 4);
    }


    @Test
    public void shouldNotMoveAPawnOverANonAlliedBarrier(){
        fail();
    }

    @Test
    public void shouldMoveAPawnOverAnAlliedBarrier(){
        fail();
    }


    @Test
    public void shouldKnowWhenSomeoneWonTheGame() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        int winner;
        winner = quoriPoob.playerWin();
        assertEquals(0, winner);
        quoriPoob.move("s");
        winner = quoriPoob.playerWin();
        assertEquals(2, winner);
    }

    @Test
    public void shouldKnowTheBarriersLeftForEachPlayer(){
        fail();
    }

    @Test
    public void shouldNotBlockThePassageOfAPlayer(){
        fail();
    }

    @Test
    public void shouldMeetNormalBarrierConditions(){
        fail();
    }

    @Test
    public void shouldMeetTemporalBarrierConditions(){
        fail();
    }

    @Test
    public void shouldMeetLongBarrierConditions(){
        fail();
    }

    @Test
    public void shouldMeetAlliedBarrierConditions(){
        fail();
    }


    @Test
    public void shouldNotCreateABoardIfItsNotPossible(){
        fail();
    }



    @Test
    public void shouldNotMoveOrthogonallyAPawnIfItsNotPossible(){
        fail();
    }


    @Test
    public void shouldNotMoveDiagonallyAPawnIfItsNotPossible(){
        fail();
    }

    @Test
    public void shouldNotPlaceANormalBarrierIfItsNotPossible(){
        fail();
    }


    @Test
    public void shouldNotMoveAPawnOverAPawnIfItsNotPossible(){
        fail();
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