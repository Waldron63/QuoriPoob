import java.awt.Color;

/**
 * Clase abstracta Player del juego, mostrara los comportamientos basicos de los jugadores en el juego
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public abstract class Player {
    private String name; //nombre del jugador
    private int cantWalls; //cantidad maxima de muros que puede llegar a colocar
    private Color color; //color de la ficha del jugador
    protected int xPosition; //posicion en X dentro del tablero
    protected int yPosition; //posicion en Y dentro del tablero
    protected final int mainTurn; //dicta cual es el turno de este jugador
    protected  int positionGraph; //grafo en el que esta posicionado el jugador

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
    public Player(String newName, Color newColor, int nWalls, int xPosition, int yPosition, int newTurn) {
        name = newName;
        cantWalls = nWalls + 1;
        color = newColor;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        mainTurn = newTurn;
    }

    /**
     * @return cantidad de muros que le falta por colocar al jugador
     */
    public int getCantWalls() {
        return cantWalls;
    }

    /**
     * añade en 1 la cantidad de muros que el usuario puede llegar a colocar
     */
    public void addCantWalls(){
        cantWalls ++;
    }

    /**
     * remueve en 1 la cantidad de muros que el usuario puede llegar a colocar
     */
    public void delCantWalls(){
        cantWalls --;
    }

    /**
     * @return turno de este jugador
     */
    public int getMainTurn(){
        return mainTurn;
    }

    /**
     * actualiza cual es la posicion del jugador en terminos de grafo
     * @param newPositionGraph nueva posicion en grafos del jugador
     */
    public void setPositionGraph(int newPositionGraph){
        positionGraph = newPositionGraph;
    }

    /**
     * @return retorna la posicion en el grafo de el jugador
     */
    public int getPositionGraph(){
        return positionGraph;
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
     * @return las posiciones actuales en donde esta el jugador
     */
    public int[] getPositions(){
        return new int[] {xPosition, yPosition};
    }

    public Color getColor(){
        return color;
    }
}