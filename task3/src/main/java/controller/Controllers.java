package controller;

import controller.cell.AbstractButtonCellController;
import controller.cell.ButtonCellController;
import controller.cell.ButtonGameField;
import controller.cell.GameField;
import controller.restart.button.RestartButtonController;
import controller.statistic.Winner;
import controller.statistic.WinnersManager;
import model.game.GameProperties;
import model.game.GameState;
import model.game.MainModel;
import model.game.TableGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Controllers implements Observed {
    private static final Logger log = LoggerFactory.getLogger(Controllers.class);
    private MainModel gameMainModel;
    private GameField gameField;
    private RestartButtonController restartButtonController;
    private GameProperties gameProperties;
    private WinnersManager winnersManager = new WinnersManager();
    private List<Observer> observers = new ArrayList<>();
    private GameTimer gameTimer = new GameTimer();

    public Controllers(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    public void setGameProperties(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    public void setRestartButtonController(RestartButtonController restartButtonController) {
        this.restartButtonController = restartButtonController;
        restartButtonController.setPlayedButton();
    }

    public void createCellFieldControllers() {
        AbstractButtonCellController[][] controllers =
                new ButtonCellController[this.gameProperties.getRows()][this.gameProperties.getCols()];
        MainModel gameMainModel = createNewModel(this.gameProperties);
        this.gameField = new ButtonGameField(gameMainModel, controllers);
        this.gameMainModel = gameMainModel;
        setBombsCountToBombsScoreboard();
        this.gameTimer.restartTimer();
    }

    public void setCellButtonController(ButtonCellController buttonCellController, int rowIndex, int colIndex) {
        this.gameField.setController(buttonCellController, rowIndex, colIndex);
    }

    public void releasedButton1(int rowIndex, int colIndex) {
        if (checkGameNotEnded()) {
            this.gameTimer.runTimer();
            this.gameField.tryOpenCell(rowIndex, colIndex);
            checkGameState();
        }
    }

    public void releasedButton2(int rowIndex, int colIndex) {
        if (checkGameNotEnded()) {
            this.gameField.openCellsAround(rowIndex, colIndex);
            checkGameState();
        }
    }

    public void pressedButton3(int rowIndex, int colIndex) {
        if (checkGameNotEnded()) {
            this.gameTimer.runTimer();
            this.gameField.changeCellStatus(rowIndex, colIndex);
            checkGameState();
            setBombsCountToBombsScoreboard();
        }
    }

    public List<Winner> getWinners() {

        return this.winnersManager.getWinners();
    }

    @Override
    public void addObserver(Observer o) {
        this.observers.add(o);
        this.gameTimer.addObserver(o);
    }

    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
        this.gameTimer.removeObserver(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : this.observers) {
            o.handleEvent();
        }
    }

    @Override
    public void notifyObservers(int number, String observerName) {
        for (Observer o : this.observers) {
            o.handleEvent(number, observerName);
        }
    }

    private void setBombsCountToBombsScoreboard() {
        int bombsCount = this.gameProperties.getBombsCount() - this.gameMainModel.getFlagCount();
        notifyObservers(bombsCount, "bombsCounterPanel");
    }

    private boolean checkGameNotEnded() {

        return this.gameField.getGameState().equals(GameState.PLAY);
    }

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

    private void setLost() {
        this.restartButtonController.setLostButton();
        this.restartButtonController.setGameState(GameState.LOSE);
        this.gameTimer.stopTimer();
    }

    private void setWin() {
        this.restartButtonController.setWinButton();
        this.restartButtonController.setGameState(GameState.WIN);
        this.winnersManager.createWinner(this.gameProperties, this.gameTimer.getTime());
        this.gameTimer.stopTimer();
    }

    private MainModel createNewModel(GameProperties gameProperties) {
        MainModel gameMainModel = null;
        try {
            gameMainModel = new MainModel(gameProperties);
        } catch (TableGenerationException e) {
            gameProperties = new GameProperties();
            try {
                gameMainModel = new MainModel(gameProperties);
            } catch (TableGenerationException ex) {
                ex.printStackTrace();
            }
        }

        return gameMainModel;
    }
}