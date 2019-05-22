package controller;

import model.game.GameState;
import view.Constants;
import view.Observer;

import javax.swing.*;

public class RestartButtonController implements Observer {

    private Icon lostIcon = new ImageIcon(Constants.class.getResource(Constants.LOST_ICON));
    private Icon playIcon = new ImageIcon(Constants.class.getResource(Constants.PLAY_ICON));
    private Icon winIcon = new ImageIcon(Constants.class.getResource(Constants.WIN_ICON));
    private Icon clickIcon = new ImageIcon(Constants.class.getResource(Constants.CLICK_ICON));
    private JButton jButton;
    private GameState gameState = GameState.PLAY;

    public RestartButtonController(JButton jButton) {
        this.jButton = jButton;
    }

    public void setLostButton() {

        jButton.setIcon(lostIcon);
    }

    public void setPlayButton() {

        jButton.setIcon(playIcon);
    }

    public void setWinButton() {

        jButton.setIcon(winIcon);
    }

    public void setClickButton() {

        jButton.setIcon(clickIcon);
    }

    @Override
    public void handleEvent() {
        if (gameState.equals(GameState.LOSE)) {
            return;
        }
        if (jButton.getIcon().equals(playIcon)){
            setClickButton();
        }
        else {
            setPlayButton();
        }
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}