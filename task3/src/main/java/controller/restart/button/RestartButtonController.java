package controller.restart.button;

import controller.Observer;
import model.game.GameState;

import javax.swing.*;

/**
 * Controller restart button. Also reflects the current status of the game: play, lose, win, hold the mouse button.
 */
public class RestartButtonController implements Observer {
    private static final String CLICK_ICON_PATH = "/icons/click.png";
    private static final String WIN_ICON_PATH = "/icons/win.png";
    private static final String LOST_ICON_PATH = "/icons/lost.png";
    private static final String PLAY_ICON_PATH = "/icons/play.png";
    private final Icon playIcon = new ImageIcon(this.getClass().getResource(PLAY_ICON_PATH));

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
        Icon lostIcon = new ImageIcon(this.getClass().getResource(LOST_ICON_PATH));
        this.jButton.setIcon(lostIcon);
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
        Icon winIcon = new ImageIcon(this.getClass().getResource(WIN_ICON_PATH));
        this.jButton.setIcon(winIcon);
    }

    /**
     * Sets the clicked icon on the restart button.
     */
    private void setClickedButton() {
        Icon clickIcon = new ImageIcon(this.getClass().getResource(CLICK_ICON_PATH));
        this.jButton.setIcon(clickIcon);
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
     *
     * @param gameState game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}