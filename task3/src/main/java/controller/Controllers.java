package controller;

import controller.cell.AbstractController;
import controller.cell.ButtonController;
import controller.cell.ButtonGameField;
import controller.cell.GameField;
import controller.restart.button.RestartButtonController;
import controller.statistic.Winner;
import model.game.GameProperties;
import model.game.GameState;
import model.game.Model;
import model.game.TableGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Controllers {
    private GameField gameField;
    private RestartButtonController restartButtonController;
    private GameProperties gameProperties;

    private long startTime;
    private ArrayList<Winner> winners = new ArrayList<>();
    private static Logger log = LoggerFactory.getLogger(Controllers.class);

    public Controllers(AbstractController[][] controllers, GameProperties gameProperties, RestartButtonController restartButtonController) { //TODO реализовать добавление новых контроллеров сеттерами
        startTime = System.currentTimeMillis();
        this.gameProperties = gameProperties;

        Model gameModel = createNewModel(gameProperties);
        this.gameField = new ButtonGameField(gameModel, controllers);
        this.restartButtonController = restartButtonController;

        restartButtonController.setPlayButton();
    }

    public void setCellButtonController(ButtonController buttonController, int rowIndex, int columnIndex) {
        gameField.setController(buttonController, rowIndex, columnIndex);
    }

    public void releasedButton1(int rowIndex, int columnIndex) {
        if (checkGameNotEnded()) {
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

    private boolean checkGameNotEnded() {
        System.out.println("gameField.getGameState() = " + gameField.getGameState());

        return gameField.getGameState() == GameState.PLAY;
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
        winners.add(new Winner(this.gameProperties, getPlayTime()));
    }

    private Model createNewModel(GameProperties gameProperties) {
        Model gameModel = null; //TODO: Переделать
        try {
            gameModel = new Model(gameProperties);
        } catch (TableGenerationException e) {
            gameProperties = new GameProperties();
            try {
                gameModel = new Model(gameProperties);
            } catch (TableGenerationException ex) {
                ex.printStackTrace();
            }
        }

        return gameModel;
    }

    private int getPlayTime() {

        return (int) (System.currentTimeMillis() - startTime) / 1000;
    }

    public ArrayList<Winner> getWinners() {

        return winners;
    }
}