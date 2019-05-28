package model.game;

/**
 * Exception that throws when the number of bombs and the number of cells of the playing field are not compatible
 */
public class TableGenerationException extends Exception {
    TableGenerationException(String message) {
        super(message);
    }
}