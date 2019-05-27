package controller.restart.button;

import model.game.GameState;
import view.Constants;
import controller.Observer;

import javax.swing.*;

public class RestartButtonController implements Observer {

    private final Icon lostIcon = new ImageIcon(Constants.class.getResource(Constants.LOST_ICON));
    private final Icon playIcon = new ImageIcon(Constants.class.getResource(Constants.PLAY_ICON));
    private final Icon winIcon = new ImageIcon(Constants.class.getResource(Constants.WIN_ICON));
    private final Icon clickIcon = new ImageIcon(Constants.class.getResource(Constants.CLICK_ICON));

    private final JButton jButton;
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

    private void setClickButton() {

        jButton.setIcon(clickIcon);
    }

    @Override
    public void handleEvent() {
        if (gameState.equals(GameState.PLAY)) {
            if (jButton.getIcon().equals(playIcon)) {
                setClickButton();
            } else {
                setPlayButton();
            }
        }
    }

    @Override
    public void handleEvent(int number, String observerName) {
        /*NOP*/
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}