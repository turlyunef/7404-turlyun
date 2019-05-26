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

public class Controllers {
    private static final Logger log = LoggerFactory.getLogger(Controllers.class);
    private GameField gameField;
    private RestartButtonController restartButtonController;
    private GameProperties gameProperties;
    private WinnersManager winnersManager = new WinnersManager();

    public Controllers(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    public void setGameProperties(GameProperties gameProperties) {
        this.gameProperties = gameProperties;
    }

    public void setRestartButtonController(RestartButtonController restartButtonController) {
        this.restartButtonController = restartButtonController;
        restartButtonController.setPlayButton();
    }

    public void createCellFieldControllers() {
        AbstractButtonCellController[][] controllers =
                new ButtonCellController[gameProperties.getRows()][gameProperties.getCols()];
        MainModel gameMainModel = createNewModel(gameProperties);
        this.gameField = new ButtonGameField(gameMainModel, controllers);
    }

    public void setCellButtonController(ButtonCellController buttonCellController, int rowIndex, int columnIndex) {
        gameField.setController(buttonCellController, rowIndex, columnIndex);
    }

    public void releasedButton1(int rowIndex, int columnIndex) {
        if (checkGameNotEnded()) {
            this.winnersManager.startTimer();
            gameField.tryOpenCell(rowIndex, columnIndex);
            checkGameState();
        }
    }

    public void releasedButton2(int rowIndex, int colIndex) {
        if (checkGameNotEnded()) {
            gameField.openCellsAround(rowIndex, colIndex);
            checkGameState();
        }
    }

    public void pressedButton3(int rowIndex, int columnIndex) {
        if (checkGameNotEnded()) {
            gameField.changeCellStatus(rowIndex, columnIndex);
            checkGameState();
        }
    }

    public List<Winner> getWinners() {

        return this.winnersManager.getWinners();
    }

    private boolean checkGameNotEnded() {

        return gameField.getGameState().equals(GameState.PLAY);
    }

    private void checkGameState() {
        switch (gameField.getGameState()) {
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
    }

    private void setWin() {
        this.restartButtonController.setWinButton();
        this.restartButtonController.setGameState(GameState.WIN);
        this.winnersManager.createWinner(gameProperties);
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