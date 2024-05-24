//package Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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
    public void shouldMoveDiagonallyAPawn() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.setTypeWalls(new int[] {2,8,0,0});
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("n");
        quoriPoob.move("s");
        quoriPoob.move("w");
        quoriPoob.move("s");
        quoriPoob.move("n");
        ArrayList<Integer> posiciones1 = new ArrayList<>(Arrays.asList(4, 4, 5, 4, 4, 5, 5, 5));
        quoriPoob.addWall("Muro Normal", posiciones1);
        ArrayList<Integer> posiciones2 = new ArrayList<>(Arrays.asList(4, 4, 4, 5, 3, 4, 3, 5));
        quoriPoob.addWall("Muro Normal", posiciones2);
        ArrayList<Integer> posiciones3 = new ArrayList<>(Arrays.asList(4, 4, 3, 4, 4, 3, 3, 3));
        quoriPoob.addWall("Muro Normal", posiciones3);
        ArrayList<Integer> posiciones4 = new ArrayList<>(Arrays.asList(4, 3, 4, 2, 5, 3, 5, 2));
        quoriPoob.addWall("Muro Normal", posiciones4);
        quoriPoob.move("sw");
        assertEquals(5, quoriPoob.getPlayerPositions(2)[0]);
        assertEquals(3, quoriPoob.getPlayerPositions(2)[1]);
    }

    @Test
    public void shouldPlaceANormalBarrier() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.setTypeWalls(new int[] {1,9,0,0});
        ArrayList<Integer> posiciones = new ArrayList<>(Arrays.asList(0, 0, 1, 0, 1, 0, 1, 1));
        quoriPoob.addWall("Muro Normal", posiciones);
        assertEquals(0,quoriPoob.getPlayerCountWalls(1)[0]);
        assertEquals(1, quoriPoob.getWallsPositions().length);
        for (int i = 0; i < 8; i++){
            assertEquals(posiciones.get(i), quoriPoob.getWallsPositions()[0].get(i));
        }
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
    public void shouldNotMoveAPawnOverANonAlliedBarrier() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.setTypeWalls(new int[] {3,2,2,3});
        ArrayList<Integer> posiciones = new ArrayList<>(Arrays.asList(0, 4, 1, 4, 0, 5, 1, 5));
        quoriPoob.addWall("Muro Aliado", posiciones);
        try {
            quoriPoob.move("s");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
    }

    @Test
    public void shouldMoveAPawnOverAnAlliedBarrier() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.setTypeWalls(new int[] {3,2,2,3});
        ArrayList<Integer> posiciones = new ArrayList<>(Arrays.asList(8, 4, 7, 4, 8, 5, 7, 5));
        quoriPoob.addWall("Muro Aliado", posiciones);
        ArrayList<Integer> posiciones2 = new ArrayList<>(Arrays.asList(0, 4, 1, 4, 0, 5, 1, 5));
        quoriPoob.addWall("Muro Aliado", posiciones2);
        quoriPoob.move("n");
        assertEquals(7, quoriPoob.getPlayerPositions(1)[0]);
        assertEquals(4, quoriPoob.getPlayerPositions(1)[1]);
        quoriPoob.move("s");
        assertEquals(1, quoriPoob.getPlayerPositions(2)[0]);
        assertEquals(4, quoriPoob.getPlayerPositions(2)[1]);
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
    public void shouldKnowTheBarriersLeftForEachPlayer() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.setTypeWalls(new int[] {3,2,2,3});
        ArrayList<Integer> posiciones = new ArrayList<>(Arrays.asList(0, 0, 1, 0, 0, 1, 1, 1));
        quoriPoob.addWall("Muro Normal", posiciones);
        ArrayList<Integer> posiciones3 = new ArrayList<>(Arrays.asList(8, 4, 7, 4, 8, 5, 7, 5));
        quoriPoob.addWall("Muro Normal", posiciones3);
        ArrayList<Integer> posiciones2 = new ArrayList<>(Arrays.asList(0, 2, 1, 2, 0, 3, 1, 3, 0, 4, 1, 4));
        quoriPoob.addWall("Muro Largo", posiciones2);
        ArrayList<Integer> posiciones4 = new ArrayList<>(Arrays.asList(5, 6, 6, 6, 5, 5, 6, 5));
        quoriPoob.addWall("Muro Aliado", posiciones4);
        ArrayList<Integer> posiciones5 = new ArrayList<>(Arrays.asList(4, 4, 3, 4, 4, 3, 3, 3));
        quoriPoob.addWall("Muro Normal", posiciones5);
        int[] cantWalls1 = quoriPoob.getPlayerCountWalls(1);
        int[] cantWalls2 = quoriPoob.getPlayerCountWalls(2);
        assertEquals(1, cantWalls1[0]);
        assertEquals(1, cantWalls1[2]);
        assertEquals(2, cantWalls2[0]);
        assertEquals(2, cantWalls2[3]);
        int cont1 = 0;
        int cont2 = 0;
        for (int i = 0; i < 4; i++){
            cont1 += cantWalls1[i];
            cont2 += cantWalls2[i];
        }
        assertEquals(7, cont1);
        assertEquals(8, cont2);
    }

    @Test
    public void shouldNotBlockThePassageOfAPlayer() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.setTypeWalls(new int[] {3,2,2,3});
        ArrayList<Integer> posiciones = new ArrayList<>(Arrays.asList(0, 0, 1, 0, 0, 1, 1, 1));
        quoriPoob.addWall("Muro Normal", posiciones);
        ArrayList<Integer> posiciones2 = new ArrayList<>(Arrays.asList(0, 2, 1, 2, 0, 3, 1, 3, 0, 4, 1, 4));
        quoriPoob.addWall("Muro Largo", posiciones2);
        ArrayList<Integer> posiciones3 = new ArrayList<>(Arrays.asList(0, 5, 1, 5, 0, 6, 1, 6));
        quoriPoob.addWall("Muro Normal", posiciones3);
        try {
            ArrayList<Integer> posiciones4 = new ArrayList<>(Arrays.asList(0, 7, 1, 7, 0, 8, 1, 8));
            quoriPoob.addWall("Muro Normal", posiciones4);
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.WRONG_SIDE_WALL, e0.getMessage());
        }
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
        try {
            new QuoriPoob(0, "Normal");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.SMALLEST_BOARD_LENGTH, e0.getMessage());
        }
        try {
            new QuoriPoob(9, "no normal");
            fail();
        }catch (QuoriPoobException e1){
            assertEquals(QuoriPoobException.DIFFICULTY_NOT_FOUND, e1.getMessage());
        }
    }



    @Test
    public void shouldNotMoveOrthogonallyAPawnIfItsNotPossible() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        try {
            quoriPoob.move("s");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.GRAPH_EXCEED_SIZE_TABLE, e0.getMessage());
        }
        quoriPoob.move("n");
        try {
            quoriPoob.move("n");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.GRAPH_EXCEED_SIZE_TABLE, e0.getMessage());
        }
        quoriPoob.move("s");
        quoriPoob.move("e");
        quoriPoob.move("w");
        quoriPoob.move("e");
        quoriPoob.move("w");
        quoriPoob.move("e");
        quoriPoob.move("w");
        quoriPoob.move("e");
        quoriPoob.move("w");
        try {
            quoriPoob.move("e");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
        quoriPoob.move("n");
        try {
            quoriPoob.move("w");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
    }


    @Test
    public void shouldNotMoveDiagonallyAPawnIfItsNotPossible() throws QuoriPoobException {
        QuoriPoob quoriPoob = new QuoriPoob(9, "Normal");
        quoriPoob.addPlayer("jugador1", Color.BLACK);
        quoriPoob.addPlayer("jugador2", Color.RED);
        quoriPoob.move("n");
        quoriPoob.move("s");
        try {
            quoriPoob.move("ne");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
        try {
            quoriPoob.move("se");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
        try {
            quoriPoob.move("nw");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
        try {
            quoriPoob.move("sw");
            fail();
        }catch (QuoriPoobException e0){
            assertEquals(QuoriPoobException.MOVEMENT_NOT_POSSIBLE, e0.getMessage());
        }
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