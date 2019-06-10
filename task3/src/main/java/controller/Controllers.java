package controller;

import controller.event.BombsCounterChangeEvent;
import controller.event.Event;
import controller.field.ButtonGameFieldController;
import controller.field.GameFieldController;
import model.game.statistic.Winner;
import controller.timer.GameTimer;
import model.game.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.CellButton;
import view.Observer;
import view.restart.button.RestartButton;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains connections between game controllers and the model.
 */
public class Controllers implements Observable {
    private static final Logger log = LoggerFactory.getLogger(Controllers.class);
    private final List<Observer> observers = new ArrayList<>();
    private final Model gameModel = new GameModel();
    private GameFieldController gameFieldController;
    private RestartButton restartButton;
    private GameProperties gameProperties;
    private final GameTimer gameTimer = new GameTimer();

    /**
     * Constructor based on the game properties.
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     */
    public Controllers(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    /**
     * Sets new game properties.
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     */
    public void setGameProperties(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    /**
     * Sets the restart button controller.
     *
     * @param restartButton restart button controller
     * @see RestartButton
     */
    public void setRestartButton(RestartButton restartButton) {
        this.restartButton = restartButton;
        restartButton.setPlayedButton();
    }

    /**
     * Creates a new game.
     */
    public void createGame() {
        createCellsFieldControllers();
        setBombsCountToBombsScoreboard();
        this.gameTimer.setGameModel(gameModel);
        this.gameTimer.restartTimer();
    }

    /**
     * Creates controllers of the game field cells with reference to the game model.
     */
    private void createCellsFieldControllers() {
        CellButton[][] controllers =
                new CellButton[this.gameProperties.getRows()][this.gameProperties.getCols()];
        restartGameModel(this.gameProperties);
        this.gameFieldController = new ButtonGameFieldController(gameModel, controllers);
    }

    /**
     * Sets a specific controller for a cell to the game board object.
     *
     * @param cellButton controller for a cell
     * @param rowIndex   index on the rows of this cell
     * @param colIndex   index on the columns of this cell
     */
    public void setCellButtonController(CellButton cellButton, int rowIndex, int colIndex) {
        this.gameFieldController.setController(cellButton, rowIndex, colIndex);
    }

    /**
     * Responds to clicking the left mouse button on the cell with the specified coordinates.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     */
    public void releasedButton1(int rowIndex, int colIndex) {
        if (checkGameNotEnded()) {
            this.gameTimer.runTimer();
            this.gameFieldController.tryOpenCell(rowIndex, colIndex);
            checkGameState();
        }
    }

    /**
     * Responds to pressing the middle mouse button on the cell with the specified coordinates.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     */
    public void releasedButton2(int rowIndex, int colIndex) {
        if (checkGameNotEnded()) {
            this.gameFieldController.openCellsAround(rowIndex, colIndex);
            checkGameState();
        }
    }

    /**
     * Responds to pressing the middle mouse button on the cell with the specified coordinates.
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     */
    public void pressedButton3(int rowIndex, int colIndex) {
        if (checkGameNotEnded()) {
            this.gameTimer.runTimer();
            this.gameFieldController.changeCellStatus(rowIndex, colIndex);
            checkGameState();
            setBombsCountToBombsScoreboard();
        }
    }

    /**
     * Returns the list of winners with their results.
     *
     * @return list of winners
     */
    public List<Winner> getWinners() {

        return this.gameModel.getWinners();
    }

    /**
     * Adds the observer object to the observers list for this controller.
     *
     * @param o observer object
     */
    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
        this.gameTimer.addObserver(o);
    }

    /**
     * Removes the observer object from the observers list for this controller.
     *
     * @param o observer object
     */
    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
        this.gameTimer.removeObserver(o);
    }

    /**
     * Notify all observer objects from the observers list for this controller.
     */
    @Override
    public void notifyObservers(Event event) {
        for (Observer o : this.observers) {
            o.handleEvent(event);
        }
    }

    /**
     * Sets the number of bombs on the scoreboard.
     */
    private void setBombsCountToBombsScoreboard() {
        int bombsCount = this.gameProperties.getBombsCount() - this.gameModel.getFlagCount();
        notifyObservers(new BombsCounterChangeEvent(bombsCount));
    }

    /**
     * Checks if the current game is over.
     *
     * @return true if the current game is over else false
     */
    private boolean checkGameNotEnded() {

        return this.gameModel.getGameState().equals(GameState.PLAY);
    }

    /**
     * Checks current game state.
     */
    private void checkGameState() {
        switch (this.gameModel.getGameState()) {
            case LOSE: {
                setLost();
                log.debug("Game over, set lost");
                break;
            }
            case WIN: {
                setWin();
                log.debug("Victory, set win");
                break;
            }
            case PLAY: {
                /*NOP*/
                break;
            }
        }
    }

    /**
     * Sets the end of the game by losing.
     */
    private void setLost() {
        this.restartButton.setLostButton();
        this.restartButton.setGameState(GameState.LOSE);
        this.gameTimer.stopTimer();
    }

    /**
     * Sets the end of the game by winning.
     */
    private void setWin() {
        this.restartButton.setWinButton();
        this.restartButton.setGameState(GameState.WIN);
        this.gameTimer.stopTimer();
    }

    /**
     * Restarts the main game model by game properties.
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     */
    private void restartGameModel(GameProperties gameProperties) {
        try {
            gameModel.restartGameField(gameProperties);
        } catch (TableGenerationException e) {
            gameProperties = new GameProperties();
            try {
                gameModel.restartGameField(gameProperties);
            } catch (TableGenerationException ex) {
                ex.printStackTrace();
            }
        }
    }
}