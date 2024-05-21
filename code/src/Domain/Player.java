import java.awt.Color;
import java.io.Serializable;

/**
 * Clase abstracta Player del juego, mostrara los comportamientos basicos de los jugadores en el juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public abstract class Player implements Serializable {
    private String name; //nombre del jugador
    private int cantWalls; //cantidad maxima de muros que puede llegar a colocar
    private Color color; //color de la ficha del jugador
    protected int xPosition; //posicion en X dentro del tablero
    protected int yPosition; //posicion en Y dentro del tablero
    protected final int mainTurn; //dicta cual es el turno de este jugador
    protected  int positionGraph; //grafo en el que esta posicionado el jugador
    private int[] cantDifferentsWalls; //contador de cada muro que le quedan al jugador
    private int[] cantDifferentsBoxes; //contador de las casillas que ha pisado el jugador

    /**
     * Constructor for objects of class Player
     *
     * @param newName, nombre que decide el jugador
     * @param newColor, color que decide el jugador
     * @param nWalls, cantidad de muros que puede llegar a poner
     * @param xPosition indica la posicion en x dentro de la matriz
     * @param yPosition indica la posicion en y dentro de la matriz
     * @param newTurn indica cual va a ser el turno de este jugador (si juega en el turno 1 o en el turno 2)
     */
    public Player(String newName, Color newColor, int nWalls, int xPosition, int yPosition, int newTurn) throws QuoriPoobException{
        if (newName.isEmpty()){
            throw new QuoriPoobException(QuoriPoobException.NAME_NOT_VALID);
        }else if(newTurn <= 0 || newTurn >=3){
            throw new QuoriPoobException(QuoriPoobException.TURN_OUT_OF_RANGE);
        }
        name = newName;
        cantWalls = nWalls + 1;
        color = newColor;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        mainTurn = newTurn;
        cantDifferentsBoxes = new int[] {0, 0, 0, 0};
    }

    public void setCantDifferentWalls(int[] newCantWalls) throws QuoriPoobException {
        if (newCantWalls.length < 4){
            throw new QuoriPoobException(QuoriPoobException.WRONG_TOTAL_WALLS);
        }
        int contar = 0;
        for (int i = 0; i < newCantWalls.length; i++){
            contar += newCantWalls[i];
        }
        if (contar == cantWalls){
            cantDifferentsWalls = newCantWalls;
        }else{
            throw new QuoriPoobException(QuoriPoobException.WRONG_TOTAL_WALLS);
        }
    }

    /**
     * actualiza cual es la posicion del jugador en terminos de grafo
     * @param newPositionGraph nueva posicion en grafos del jugador
     */
    public void setPositionGraph(int newPositionGraph){
        positionGraph = newPositionGraph;
    }

    /**
     * añade en 1 la cantidad de casillas que el usuario ha pisado
     * @param type tipo de muro que se le va a sumar
     */
    public void addCantBoxes(String type) throws QuoriPoobException {
        switch (type){
            case "Normal":
                cantDifferentsBoxes[0] += 1;
                break;
            case "Teletransportador":
                cantDifferentsBoxes[1] += 1;
                break;
            case "Regresar":
                cantDifferentsBoxes[2] += 1;
                break;
            case "Doble":
                cantDifferentsBoxes[3] += 1;
                break;
            default:
                throw new QuoriPoobException(QuoriPoobException.BOX_NOT_FOUND);
        }
    }

    /**
     * añade en 1 la cantidad de muros que el usuario puede llegar a colocar
     * @param type tipo de muro que se le va a sumar
     */
    public void addCantWalls(String type) throws QuoriPoobException {
        switch (type){
            case "Normal":
                cantDifferentsWalls[0] += 1;
                break;
            case "Temporal":
                cantDifferentsWalls[1] += 1;
                break;
            case "Larga":
                cantDifferentsWalls[2] += 1;
                break;
            case "Aliada":
                cantDifferentsWalls[3] += 1;
                break;
            default:
                throw new QuoriPoobException(QuoriPoobException.WALL_NOT_FOUND);
        }
    }

    /**
     * remueve en 1 la cantidad de muros que el usuario puede llegar a colocar
     * @param type tipo de muro que se le va a restar
     */
    public void delCantWalls(String type) throws QuoriPoobException {
        switch (type){
            case "Normal":
                if (cantDifferentsWalls[0] == 0){
                    throw new QuoriPoobException(QuoriPoobException.WALL_NOT_FOUND);
                }else{
                    cantDifferentsWalls[0] -= 1;
                }
                break;
            case "Temporal":
                if (cantDifferentsWalls[1] == 0){
                    throw new QuoriPoobException(QuoriPoobException.WALL_NOT_FOUND);
                }else{
                    cantDifferentsWalls[1] -= 1;
                }
                break;
            case "Larga":
                if (cantDifferentsWalls[2] == 0){
                    throw new QuoriPoobException(QuoriPoobException.WALL_NOT_FOUND);
                }else{
                    cantDifferentsWalls[2] -= 1;
                }
                break;
            case "Aliada":
                if (cantDifferentsWalls[3] == 0){
                    throw new QuoriPoobException(QuoriPoobException.WALL_NOT_FOUND);
                }else{
                    cantDifferentsWalls[3] -= 1;
                }
                break;
            default:
                throw new QuoriPoobException(QuoriPoobException.WALL_NOT_FOUND);
        }
    }

    /**
     * cambia las posiciones dentro del tablero de el jugador
     * @param positions, las nuevas posiciones por las que se va a reemplazar
     */
    public void changePositions(int[] positions){
        xPosition = positions[0];
        yPosition = positions[1];
    }

    /**
     * @return turno de este jugador
     */
    public int getMainTurn(){
        return mainTurn;
    }

    /**
     * @return retorna la posicion en el grafo de el jugador
     */
    public int getPositionGraph(){
        return positionGraph;
    }

    /**
     * @return las posiciones actuales en donde esta el jugador
     */
    public int[] getPositions(){
        return new int[] {xPosition, yPosition};
    }

    /**
     * @return cantidad de muros que le falta por colocar al jugador
     */
    public int[] getCantDifferentWalls(){
        return cantDifferentsWalls;
    }

    /**
     * @return la camtidad de casillas que el jugador ha pisado
     */
    public int[] getCantDifferentsBoxes() {
        return cantDifferentsBoxes;
    }

    /**
     * @return devuelve el color del usuario
     */
    public Color getColor(){
        return color;
    }

    /**
     * @return devuelve el nombre del usuario
     */
    public String getName(){
        return name;
    }
}