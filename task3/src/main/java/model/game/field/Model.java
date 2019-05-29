package model.game.field;

import model.game.GameState;

/**
 * Interface level abstraction that stores the state of the game model
 */
public interface Model {

    GameState getGameState();
}