package controller;

import controller.cell.AbstractButtonCellController;
import controller.cell.ButtonCellController;
import controller.cell.ButtonGameField;
import controller.cell.GameField;
import controller.event.BombsCounterChangeEvent;
import controller.event.Event;
import controller.restart.button.RestartButtonController;
import controller.statistic.Winner;
import controller.statistic.WinnersManager;
import controller.timer.GameTimer;
import model.game.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The class contains connections between game controllers and the model.
 */
public class Controllers implements Observable {
    private static final Logger log = LoggerFactory.getLogger(Controllers.class);
    private IModel gameModel;
    private GameField gameField;
    private RestartButtonController restartButtonController;
    private GameProperties gameProperties;
    private final WinnersManager winnersManager = new WinnersManager();
    private final List<Observer> observers = new ArrayList<>();
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
     * @param restartButtonController restart button controller
     * @see RestartButtonController
     */
    public void setRestartButtonController(RestartButtonController restartButtonController) {
        this.restartButtonController = restartButtonController;
        restartButtonController.setPlayedButton();
    }

    /**
     * Creates a new game.
     */
    public void createGame() {
        createCellsFieldControllers();
        setBombsCountToBombsScoreboard();
        this.gameTimer.restartTimer();
    }

    /**
     * Creates controllers of the game field cells with reference to the game model.
     */
    private void createCellsFieldControllers() {
        AbstractButtonCellController[][] controllers =
                new ButtonCellController[this.gameProperties.getRows()][this.gameProperties.getCols()];
        this.gameModel = createNewModel(this.gameProperties);
        this.gameField = new ButtonGameField(gameModel, controllers);
    }

    /**
     * Sets a specific controller for a cell to the game board object.
     *
     * @param buttonCellController controller for a cell
     * @param rowIndex             index on the rows of this cell
     * @param colIndex             index on the columns of this cell
     */
    public void setCellButtonController(ButtonCellController buttonCellController, int rowIndex, int colIndex) {
        this.gameField.setController(buttonCellController, rowIndex, colIndex);
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
            this.gameField.tryOpenCell(rowIndex, colIndex);
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
            this.gameField.openCellsAround(rowIndex, colIndex);
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
            this.gameField.changeCellStatus(rowIndex, colIndex);
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

        return this.winnersManager.getWinners();
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
     * @return true if the current game is over
     */
    private boolean checkGameNotEnded() {

        return this.gameField.getGameState().equals(GameState.PLAY);
    }

    /**
     * Checks current game state.
     */
    private void checkGameState() {
        switch (this.gameField.getGameState()) {
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
        this.restartButtonController.setLostButton();
        this.restartButtonController.setGameState(GameState.LOSE);
        this.gameTimer.stopTimer();
    }

    /**
     * Sets the end of the game by winning.
     */
    private void setWin() {
        this.restartButtonController.setWinButton();
        this.restartButtonController.setGameState(GameState.WIN);
        this.winnersManager.addWinner(this.gameProperties, this.gameTimer.getTime());
        this.gameTimer.stopTimer();
    }

    /**
     * Creates a new main model by game properties
     *
     * @param gameProperties game properties containing number of bombs of the game, number of rows and columns game field
     * @return new main model
     */
    private IModel createNewModel(GameProperties gameProperties) {
        IModel gameModel = null;
        try {
            gameModel = new GameModel(gameProperties);
        } catch (TableGenerationException e) {
            gameProperties = new GameProperties();
            try {
                gameModel = new GameModel(gameProperties);
            } catch (TableGenerationException ex) {
                ex.printStackTrace();
            }
        }

        return gameModel;
    }
}