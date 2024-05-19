import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;


/**
 * Clase Domain de el proyecto, el principal auditor del paquete presentation
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class QuoriPoob implements Serializable {
    public static final int players = 2; // cantidad total de jugadores en el tablero
    private Player playerOne; //primer jugador de la partida
    private Player playerTwo; //segundo jugador de la partida
    private Table tablero; //tablero de nxn para la partida
    private int sizeTable; //longitud total del tablero
    private String difficult; //tipo de dificultad escogido por los jugadores
    private int turn; //indica de quien es el turno actual en el juego
    private String[] typeWalls; //indica los muros que el usuario puede colocar

    public static void main(String[] args){
        QuoriPoob q = new QuoriPoob(9, "Normal");
    }
    /**
     * Constructor for objects of class QuoriPoob
     *
     * @param n, la longitud nXn del tablero para jugar
     * @param newDifficult, la dificultad que va a tener el juego
     */
    public QuoriPoob(int n, String newDifficult){
        tablero = new Table(n);
        sizeTable = n;
        difficult = newDifficult;
        turn = 1;
    }

    public void setTypeWalls(String[] newTypes){
        typeWalls = newTypes;
    }

    /**
     * Anade a los jugadores humanos a el tablero para empezar a jugar
     * @param name, el nombre que decidio el usuario
     * @param color, color que desea su ficha
     */
    public void addPlayer(String name, Color color) {
        //revisa si es el primero o segundo jugador
        int yPosition = (int) (sizeTable - 1)/2;
        //revisa que ya este el primer jugador
        if (playerOne == null) {
            playerOne = new Human(name, color, sizeTable + 1, sizeTable - 1, yPosition, 1);
            int graphPos = tablero.getGraphPosition(sizeTable - 1, yPosition);
            playerOne.setPositionGraph(graphPos);
            tablero.addPlayer(2, graphPos);
        } else { //inserta los datos del segundo jugador
            playerTwo = new Human(name, color, sizeTable + 1, 0, yPosition, 2);
            int graphPos = tablero.getGraphPosition(0, yPosition);
            playerTwo.setPositionGraph(graphPos);
            tablero.addPlayer(3, graphPos);
        }
    }

    /**
     * Anade a los jugadores humanos a el tablero para empezar a jugar
     * @param difficult, la dificultad que va a tener la maquina
     */
    public void addMachine(String difficult) {
        Machine m = new Machine(difficult, sizeTable, 0, (int) (sizeTable + 1)/2);
        playerTwo = m;
        int graphPos = tablero.getGraphPosition(0, (int) (sizeTable + 1)/2);
        playerTwo.setPositionGraph(graphPos);
        tablero.addPlayer(3, graphPos);
    }


    public void addRandomBox(int[] cantTypeBoxes){
        tablero.addRandomBox(cantTypeBoxes);
    }

    /**
     * ayuda a anadir nuevos muros al tablero, mientras no se cierren todos
     * los caminos de un lado a otro.
     */
    public void addWall(String type, int[] positions){
        if (! Arrays.asList(typeWalls).contains(type)){
            return ;
        }
        Player actPlayer = (turn == 1) ? playerOne : playerTwo;
        Wall newWall;
        switch (type){
            case "AllyWall":
                newWall = new AllyWall(actPlayer.getColor(),positions,actPlayer);
                break;
            case "LongWall":
                newWall = new LongWall(Color.BLACK, positions, actPlayer);
                break;
            case "NormalWall":
                newWall = new NormalWall(Color.black, positions, actPlayer);
                break;
            case "TemporalWall":
                newWall = new TemporalWall(Color.black, positions, actPlayer);
                break;
            default:
                return ;
        }
        boolean comp = tablero.addWall(newWall, playerOne, playerTwo);
        if (comp){
            actPlayer.delCantWalls();
        }else{
            return;
        }
        changeTurn();
    }

    /**
     * mueve al jugador que esta en turno actual, hacia la casilla que desea
     * @param side el lado hacia el cual va a caminar la ficha
     * @return int[], las posiciones de la casilla a la que el jugador se va a mover si puede
     * @throws QuoriPoobException, indica si no se puede generar el movimiento pedido por el usuario
     */
    public int[] move(String side) throws QuoriPoobException{
        int[] positionsP;
        int actualTurn;
        //revisa cual es el turno actual y recoge la posicion del jugador
        if(turn == 1){
            positionsP = playerOne.getPositions();
            actualTurn = playerOne.getMainTurn();
        }else {
            positionsP = playerTwo.getPositions();
            actualTurn = playerTwo.getMainTurn();
        }
        int[] comp = tablero.move(positionsP, side, actualTurn);
        //comprueba si se puede mover hacia la casilla que el usuario desea
        if (Arrays.asList(comp).isEmpty()){
            throw new QuoriPoobException(QuoriPoobException.MOVEMENT_NOT_POSSIBLE);
        }else{
            // revisa el turno actual y cambia la posicion del jugador
            int graphPosition = tablero.getGraphPosition(comp[0], comp[1]);
            if(turn == 1){
                playerOne.changePositions(comp);
                playerOne.setPositionGraph(graphPosition);
                changeTurn();
                return comp;
            }else {
                playerTwo.changePositions(comp);
                playerTwo.setPositionGraph(graphPosition);
                changeTurn();
                return comp;
            }
        }
    }

    /**
     * ayuda a cambiar el turno de los jugadores
     * @return mainTurn, devuelve el turno del nuevo jugador
     */
    private void changeTurn(){
        tablero.changeWallCount();
        int winner = playerWin();
        
        //TakeBox();
        setTurn();
    }

    public int setTurn(){
        //genera un swap (cambio) en los turnos
        if (turn == 1){
            turn = 2;
            return turn;
        }else{
            turn = 1;
            return turn;
        }
    }

    /**
     * indica si el jugador que se movio gano la partida o continua jugando
     * @return true si el jugador gano la partida, false en caso contrario
     */
    private int playerWin(){
        int graphPlayer1 = playerOne.getPositionGraph();
        int graphPlayer2 = playerTwo.getPositionGraph();
        if (graphPlayer1 >= 0 && graphPlayer1 > sizeTable){
            return 1;
        }else if(graphPlayer2 >= Math.pow(sizeTable,2) - sizeTable && graphPlayer2 < Math.pow(sizeTable,2)){
            return 2;
        }else{
            return 0;
        }
    }

    /**
     * @return el turno actual
     */
    public int getTurn(){
        return turn;
    }

    public Color getPlayerColor(int mainTurn){
        if (mainTurn == 1){
            return playerOne.getColor();
        }else{
            return playerTwo.getColor();
        }
    }

    public String getPlayerName(int mainTurn){
        if (mainTurn == 1){
            return playerOne.getName();
        }else{
            return playerTwo.getName();
        }
    }

    /**
     * @return el tablero con las casillas actuales.
     */
    public Box[][] getBoard(){
        return tablero.getCasillas();
    }

    public int getSizeTable(){
        return sizeTable;
    }

    public String getDifficult() {
        return difficult;
    }

    /**
     * apertura de un nuevo archivo Garden
     * @param archivo, archivo que contiene todo el QuoriPoob que se desea abrir
     * @throws QuoriPoobException
     */
    public static QuoriPoob openArchivo(File archivo) throws QuoriPoobException{
        if (archivo ==null){
            //throw new QuoriPoobException(QuoriPoobException.ARCHIVE_NOT_NULL);
        }
        try{
            ObjectInputStream open = new ObjectInputStream(new FileInputStream(archivo));
            QuoriPoob qp = (QuoriPoob) open.readObject();
            open.close();
            return qp;
        } catch(FileNotFoundException e){
            throw new QuoriPoobException("Archivo no encontrado: " + archivo.getName());
        } catch(IOException e1){
            throw new QuoriPoobException("Error al abrir el archivo: " + archivo.getName());
        } catch(Exception e2){
            throw new QuoriPoobException("Opcion open no se abrio. Archivo: "+archivo.getName());
        }
    }

    /**
     * salvar el actual archivo Garden 
     * @param archivo, archivo que va a contener todo el QuoriPoob actual
     * @throws QuoriPoobException
     */
    public void saveArchivo(File archivo) throws QuoriPoobException{
        if (archivo ==null){
            //throw new QuoriPoobException(QuoriPoobException.ARCHIVE_NOT_NULL);
        }
        try{
            ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(archivo));
            save.writeObject(this);
            save.close();
        } catch(FileNotFoundException e){
            throw new QuoriPoobException("Archivo no guardado: " + archivo.getName());
        } catch(IOException e1){
            throw new QuoriPoobException("Error al salvar el archivo: " + archivo.getName());
        } catch(Exception e2){
            throw new QuoriPoobException("Opcion save no permite guardar. Archivo: "+archivo.getName());
        }
    }
}