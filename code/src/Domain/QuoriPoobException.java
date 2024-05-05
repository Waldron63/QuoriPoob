/**
 * Clase de Excepciones de el proyecto, el generador de errores del proyecto
 *
 * @author Sofia Gil - Santiago Gualdron
 * @version 1.0
 */
public class QuoriPoobException extends Exception{
    public static final String MOVEMENT_NOT_POSSIBLE = "El movimiento del jugador no es posible";

    /**
     * Constructor de QuoriPoobException
     */
    public QuoriPoobException(String message) {
        super(message);
    }
}
