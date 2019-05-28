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
        this.jButton.setIcon(this.lostIcon);
    }

    public void setPlayedButton() {
        this.jButton.setIcon(this.playIcon);
    }

    public void setWinButton() {
        this.jButton.setIcon(this.winIcon);
    }

    private void setClickedButton() {
        this.jButton.setIcon(this.clickIcon);
    }

    @Override
    public void handleEvent() {
        if (this.gameState.equals(GameState.PLAY)) {
            changeButtonIcon();
        }
    }

    private void changeButtonIcon() {
        if (this.jButton.getIcon().equals(this.playIcon)) {
            setClickedButton();
        } else {
            setPlayedButton();
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