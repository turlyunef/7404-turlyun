package controller.restart.button;

import controller.Observer;
import model.game.GameState;
import view.Constants;

import javax.swing.*;

/**
 * Controller restart button. Also reflects the current status of the game: play, lose, win, hold the mouse button.
 */
public class RestartButtonController implements Observer {

    private final Icon lostIcon = new ImageIcon(Constants.class.getResource(Constants.LOST_ICON));
    private final Icon playIcon = new ImageIcon(Constants.class.getResource(Constants.PLAY_ICON));
    private final Icon winIcon = new ImageIcon(Constants.class.getResource(Constants.WIN_ICON));
    private final Icon clickIcon = new ImageIcon(Constants.class.getResource(Constants.CLICK_ICON));

    private final JButton jButton;
    private GameState gameState = GameState.PLAY;

    /**
     * The constructor that creates the controller corresponding to the button.
     *
     * @param jButton button controlled by the controller
     */
    public RestartButtonController(JButton jButton) {
        this.jButton = jButton;
    }

    /**
     * Sets the lost icon on the restart button.
     */
    public void setLostButton() {
        this.jButton.setIcon(this.lostIcon);
    }

    /**
     * Sets the play icon on the restart button.
     */
    public void setPlayedButton() {
        this.jButton.setIcon(this.playIcon);
    }

    /**
     * Sets the win icon on the restart button.
     */
    public void setWinButton() {
        this.jButton.setIcon(this.winIcon);
    }

    /**
     * Sets the clicked icon on the restart button.
     */
    private void setClickedButton() {
        this.jButton.setIcon(this.clickIcon);
    }


    /**
     * Sets the icon on the restart button by the observed's call.
     */
    @Override
    public void handleEvent() {
        if (this.gameState.equals(GameState.PLAY)) {
            changeButtonIcon();
        }
    }

    /**
     * Changes the icon of the button in accordance with the current icon
     */
    private void changeButtonIcon() {
        if (this.jButton.getIcon().equals(this.playIcon)) {
            setClickedButton();
        } else {
            setPlayedButton();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleEvent(int number, String observerName) {
        /*NOP*/
    }

    /**
     * Sets game state
     * @param gameState game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}