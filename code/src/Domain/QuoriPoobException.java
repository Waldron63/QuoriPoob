/**
 * Clase de Excepciones de el proyecto, el generador de errores del proyecto
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class QuoriPoobException extends Exception{
    //CONSTRUCTOR
    public static final String SMALLEST_BOARD_LENGTH = "Longitud del tablero es demasiado pequena";
    public static final String DIFFICULTY_NOT_FOUND = "tipo de dificultad no posible";
    //PLAYERS
    public static final String NAME_NOT_VALID = "Este nombre no es valido";
    public static final String DIFFICULTY_MACHINE_NOT_VALID = "La dificultad de la maquina no es valida";
    public static final String TURN_OUT_OF_RANGE = "Turno no valido para un tablero de 2 jugadores";
    //MUROS
    public static final String TYPE_WALL_NOT_IN_CONFIGURATIONS = "el tipo de muro seleccionado no es valido" +
            " para esta configuracion de tablero";
    public static final String WRONG_TOTAL_WALLS = "cantidad total de muros no valida";
    public static final String WRONG_SIDE_WALL = "El muro no puede ser colocado en esta posicion";
    public static final String WRONG_WALL_LENGHT = "El muro seleccionado no tiene la longitud adecuada";
    public static final String WALL_NOT_FOUND = "El muro seleccionado no se encuentra disponible";
    //MOVIMIENTOS
    public static final String GRAPH_EXCEED_SIZE_TABLE = "La casilla seleccionada esta fuera del tablero";
    public static final String MOVEMENT_NOT_POSSIBLE = "El movimiento del jugador no es posible";
    //CASILLAS
    public static final String BOX_NOT_FOUND = "Esta casilla no esta disponible";
    //public static final String  = "";
    //public static final String  = "";
    //public static final String  = "";

    /**
     * Constructor de QuoriPoobException
     */
    public QuoriPoobException(String message) {
        super(message);
    }
}
