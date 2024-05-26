import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * Clase Domain de el proyecto, el principal auditor del paquete presentation
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class QuoriPoob implements Serializable {
    public static final String[] typesDifficults = new String[] {"Normal", "Contrarreloj", "Cronometrado"};
    public static final int players = 2; // cantidad total de jugadores en el tablero
    private Player playerOne; //primer jugador de la partida
    private Player playerTwo; //segundo jugador de la partida
    private Table tablero; //tablero de nxn para la partida
    private int sizeTable; //longitud total del tablero
    private String difficult; //tipo de dificultad escogido por los jugadores
    private int turn; //indica de quien es el turno actual en el juego
    private boolean isDoubleTurn; //indica si el usuario cayo en una casilla doble
    public int[] totalCantWalls; //cantidad de cada muro que el usuario selecciono inicialmente
    public int[] totalCantBoxes; //cantidad de cada casilla que el usuario selecciono inicialmente

    /**
     * metodo principal para comenzar el dominio
     * @param args
     */
    public static void main(String[] args){
        try {
            QuoriPoob q = new QuoriPoob(9, "Normal");
        }catch (QuoriPoobException ignore){}
    }

    /**
     * Constructor for objects of class QuoriPoob
     * @param n, la longitud nXn del tablero para jugar
     * @param newDifficult, la dificultad que va a tener el juego
     */
    public QuoriPoob(int n, String newDifficult) throws QuoriPoobException{
        if(newDifficult.isEmpty() || !Arrays.asList(typesDifficults).contains(newDifficult)){
            throw new QuoriPoobException(QuoriPoobException.DIFFICULTY_NOT_FOUND);
        }
        tablero = new Table(n);
        sizeTable = n;
        difficult = newDifficult;
        turn = 1;
        isDoubleTurn = false;
    }

    /**
     * Anade a los jugadores humanos a el tablero para empezar a jugar
     * @param name, el nombre que decidio el usuario
     * @param color, color que desea su ficha
     */
    public void addPlayer(String name, Color color) throws QuoriPoobException{
        //revisa si es el primero o segundo jugador
        int yPosition = (int) (sizeTable - 1)/2;
        //revisa que ya este el primer jugador
        if (playerOne == null) {
            playerOne = new Human(name, color, sizeTable, sizeTable - 1, yPosition, 1);
            int graphPos = tablero.getGraphPosition(sizeTable - 1, yPosition);
            playerOne.setPositionGraph(graphPos);
            tablero.addPlayer(2, graphPos);
        } else { //inserta los datos del segundo jugador
            playerTwo = new Human(name, color, sizeTable, 0, yPosition, 2);
            int graphPos = tablero.getGraphPosition(0, yPosition);
            playerTwo.setPositionGraph(graphPos);
            tablero.addPlayer(3, graphPos);
        }
    }

    /**
     * Anade a los jugadores humanos a el tablero para empezar a jugar
     * @param difficult, la dificultad que va a tener la maquina
     */
    public void addMachine(String difficult) throws QuoriPoobException{
        Machine m = new Machine(difficult, sizeTable, 0, (int) (sizeTable + 1)/2);
        playerTwo = m;
        int graphPos = tablero.getGraphPosition(0, (int) (sizeTable + 1)/2);
        playerTwo.setPositionGraph(graphPos);
        tablero.addPlayer(3, graphPos);
    }

    /**
     * inidica cual es la configuracion de los tipos de muro que desea el usuario
     * @param newTypes los tipos de muros seleccionados
     */
    public void setTypeWalls(int[] newTypes) throws QuoriPoobException {
        int[] newTypes2 = new int[newTypes.length];
        for (int i = 0; i < newTypes.length; i++){
            newTypes2[i] = newTypes[i];
        }
        playerOne.setCantDifferentWalls(newTypes);
        playerTwo.setCantDifferentWalls(newTypes2);
        totalCantWalls = newTypes;
    }

    /**
     * coloca los tipos de casillas diferentes en en zonas aleatorias del tablero
     * @param cantTypeBoxes, cuantos tipos diferentes de casillas quiere de cada tipo
     */
    public void setRandomBox(int[] cantTypeBoxes){
        tablero.addRandomBox(cantTypeBoxes);
        totalCantBoxes = cantTypeBoxes;
    }

    /**
     * ayuda a anadir nuevos muros al tablero, mientras no se cierren todos
     * los caminos de un lado a otro.
     */
    public void addWall(String type, ArrayList<Integer> iniPositions) throws QuoriPoobException{
        //revisa que el tipo de muro sea el indicado previamente por el usuario
        if (!Arrays.asList(Wall.typesWalls).contains(type)){
            throw new QuoriPoobException(QuoriPoobException.TYPE_WALL_NOT_IN_CONFIGURATIONS);
        }
        ArrayList<Integer> positions = new ArrayList<>();
        int posButton1;
        int posButton2;
        int posButton3 = -1;
        if (iniPositions.size() == 10){
            posButton1 = iniPositions.remove(4);
            posButton2 = iniPositions.remove(8);
        }else{
            posButton1 = iniPositions.remove(4);
            posButton2 = iniPositions.remove(8);
            posButton3 = iniPositions.remove(12);

        }
        for (int k = 0; k < iniPositions.size(); k = k + 2){
            positions.add(tablero.getGraphPosition(iniPositions.get(k), iniPositions.get(k + 1)));
        }
        positions.add(posButton1);
        positions.add(posButton2);
        if (posButton3 != -1) {
            positions.add(posButton3);
        }

        //indica el jugador actual del turno
        Player actPlayer = (turn == 1) ? playerOne : playerTwo;
        Wall newWall;
        int arrayPosition;
        //revisa que tipo de muro es
        switch (type){
            //si el muro es aliado
            case "Muro Aliado":
                newWall = new AllyWall(actPlayer.getColor(),positions,actPlayer, sizeTable);
                arrayPosition = 3;
                break;
            //si el muro es largo
            case "Muro Largo":
                newWall = new LongWall(Color.BLACK, positions, actPlayer, sizeTable);
                arrayPosition = 2;
                break;
            //si el muro es normal
            case "Muro Normal":
                newWall = new NormalWall(Color.black, positions, actPlayer, sizeTable);
                arrayPosition = 0;
                break;
            //si el muro es temporal
            case "Muro Temporal":
                newWall = new TemporalWall(Color.black, positions, actPlayer, sizeTable);
                arrayPosition = 1;
                break;
            //cualquier otro caso
            default:
                throw new QuoriPoobException(QuoriPoobException.TYPE_WALL_NOT_IN_CONFIGURATIONS);
        }
        if (actPlayer.getCantDifferentWalls()[arrayPosition] > 0) {
            tablero.addWall(newWall, playerOne, playerTwo);
            actPlayer.delCantWalls(type);
        }else{
            actPlayer.delCantWalls(type);
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
        //comprueba si se puede mover hacia la casilla que el usuario desea
        int[] comp = tablero.move(positionsP, side, actualTurn);
        // revisa el turno actual y cambia la posicion del jugador
        String typeCasilla = tablero.getCasillas()[comp[0]][comp[1]];
        int graphPosition = tablero.getGraphPosition(comp[0], comp[1]);
        if(turn == 1){
            playerOne.changePositions(comp);
            playerOne.setPositionGraph(graphPosition);
            playerOne.addCantBoxes(typeCasilla);
            changeTurn();
            return comp;
        }else {
            playerTwo.changePositions(comp);
            playerTwo.setPositionGraph(graphPosition);
            playerTwo.addCantBoxes(typeCasilla);
            changeTurn();
            return comp;
        }
    }

    public ArrayList<Integer> changeWallCount() throws QuoriPoobException {
        ArrayList<Integer> possibleDelWall = tablero.changeWallCount();
        ArrayList<Integer> posiciones = new ArrayList<>();
        if (possibleDelWall != null) {
            for (int i = 0; i < possibleDelWall.size(); i++) {
                int[] graph = tablero.getGraphPosition(possibleDelWall.get(i));
                if (i % 2 == 0) {
                    posiciones.add(graph[0]);
                    posiciones.add(graph[1]);
                } else {
                    int dist = possibleDelWall.get(i - 1) - possibleDelWall.get(i);
                    switch (dist) {
                        case 1:
                            posiciones.add(2);
                            break;
                        case -1:
                            posiciones.add(3);
                            break;
                        case 9:
                            posiciones.add(0);
                            break;
                        case -9:
                            posiciones.add(1);
                            break;
                    }
                }
            }
            return posiciones;
        }
        return null;
    }

    public boolean isDoubleTurn(){
        if (isDoubleTurn){
            isDoubleTurn = false;
            turn = switch (turn) {
                case 1 -> 2;
                case 2 -> 1;
                default -> turn;
            };
            return true;
        }else{
            return false;
        }
    }

    /**
     * ayuda a cambiar el turno de los jugadores
     */
    public void changeTurn() throws QuoriPoobException {
        int[] positions;
        if (turn == 1){
            positions = playerOne.getPositions();
            String typeBox = tablero.getCasillas()[positions[0]][positions[1]];
            if (Objects.equals(typeBox, "Doble")) {
                tablero.changeBox(positions[0], positions[1], "Normal");
                isDoubleTurn = true;
            }else if(Objects.equals(typeBox, "Estrella")){
                isDoubleTurn = true;
            }
        }else{
            positions = playerTwo.getPositions();
            String typeBox = tablero.getCasillas()[positions[0]][positions[1]];
            if (Objects.equals(typeBox, "Doble")) {
                tablero.changeBox(positions[0], positions[1], "Normal");
                isDoubleTurn = true;
            }else if(Objects.equals(typeBox, "Estrella")){
                isDoubleTurn = true;
            }
        }
        setTurn();
    }

    /**
     * cambia el turno de la partida
     * @return el turno al cual se cambio
     */
    private int setTurn(){
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
    public int playerWin(){
        int graphPlayer1 = playerOne.getPositionGraph();
        int graphPlayer2 = playerTwo.getPositionGraph();
        //revisa si el jugador 1 fue el que gano
        if (graphPlayer1 >= 0 && graphPlayer1 < sizeTable){
            return 1;
        //sevisa si el jugador 2 fue el que gano
        }else if(graphPlayer2 >= Math.pow(sizeTable,2) - sizeTable && graphPlayer2 < Math.pow(sizeTable,2)){
            return 2;
        //caso contrario, aun no han ganado ninguno
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

    /**
     * devuelve los colores de el jugador con su turno respectivo
     * @param mainTurn el turno del jugador al que quiere obtener el color
     * @return el color de el jugador
     */
    public Color getPlayerColor(int mainTurn){
        if (mainTurn == 1){
            return playerOne.getColor();
        }else{
            return playerTwo.getColor();
        }
    }

    /**
     * devuelve los nombres de el jugador con su turno respectivo
     * @param mainTurn el turno del jugador al que quiere obtener el nombre
     * @return el nombre de el jugador
     */
    public String getPlayerName(int mainTurn){
        if (mainTurn == 1){
            return playerOne.getName();
        }else{
            return playerTwo.getName();
        }
    }

    public int[] getPlayerPositions(int mainTurn){
        if (mainTurn == 1){
            return playerOne.getPositions();
        }else{
            return playerTwo.getPositions();
        }
    }

    public int[] getPlayerCountWalls(int mainTurn){
        if (mainTurn == 1){
            return playerOne.getCantDifferentWalls();
        }else{
            return playerTwo.getCantDifferentWalls();
        }
    }

    public int[] getPlayerCountBoxes(int mainTurn){
        if (mainTurn == 1){
            return playerOne.getCantDifferentsBoxes();
        }else{
            return playerTwo.getCantDifferentsBoxes();
        }
    }

    /**
     * @return la longitud que tiene el tablero
     */
    public int getSizeTable(){
        return sizeTable;
    }

    /**
     * @return las posiciones de cada muro que han puesto los usuarios
     */
    public ArrayList<Integer>[] getWallsPositions(){
        return tablero.getWallsPositions();
    }

    public String[] getWallsTypes(){
        return tablero.getWallsTypes();
    }

    /**
     * @return el tablero con las casillas actuales.
     */
    public String[][] getBoard(){
        String[][] casillero = tablero.getCasillas();
        return casillero;
    }

    /**
     * @return devuelve la dificultad que tiene la partida
     */
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
            throw new QuoriPoobException("Archivo no encontrado: " + archivo.getName());
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