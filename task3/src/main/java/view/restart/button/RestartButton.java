package view.restart.button;

import controller.event.Event;
import controller.event.MouseActionEvent;
import model.game.GameState;
import view.Observer;

import javax.swing.*;

/**
 * Controller of the restart button.
 * Also reflects the current status of the game: play, lose, win, hold the mouse button.
 */
public class RestartButton implements Observer {
    private static final String CLICK_ICON_PATH = "/icons/click.png";
    private static final String WIN_ICON_PATH = "/icons/win.png";
    private static final String LOST_ICON_PATH = "/icons/lost.png";
    private static final String PLAY_ICON_PATH = "/icons/play.png";
    private final Icon playIcon = new ImageIcon(this.getClass().getResource(PLAY_ICON_PATH));

    private final JButton restartButton;
    private GameState gameState = GameState.PLAY;

    /**
     * The constructor that creates the controller corresponding to the button.
     *
     * @param restartButton button controlled by the controller
     */
    public RestartButton(JButton restartButton) {
        this.restartButton = restartButton;
    }

    /**
     * Sets the lost icon on the restart button.
     */
    public void setLostButton() {
        Icon lostIcon = new ImageIcon(this.getClass().getResource(LOST_ICON_PATH));
        this.restartButton.setIcon(lostIcon);
    }

    /**
     * Sets the play icon on the restart button.
     */
    public void setPlayedButton() {
        this.restartButton.setIcon(this.playIcon);
    }

    /**
     * Sets the win icon on the restart button.
     */
    public void setWinButton() {
        Icon winIcon = new ImageIcon(this.getClass().getResource(WIN_ICON_PATH));
        this.restartButton.setIcon(winIcon);
    }

    /**
     * Sets the clicked icon on the restart button.
     */
    private void setClickedButton() {
        Icon clickIcon = new ImageIcon(this.getClass().getResource(CLICK_ICON_PATH));
        this.restartButton.setIcon(clickIcon);
    }

    /**
     * Sets the icon on the restart button by the observed's call.
     */
    @Override
    public void handleEvent(Event event) {
        if (event instanceof MouseActionEvent) {
            if (this.gameState.equals(GameState.PLAY)) {
                changeButtonIcon();
            }
        }
    }

    /**
     * Changes the icon of the button in accordance with the current icon.
     */
    private void changeButtonIcon() {
        if (this.restartButton.getIcon().equals(this.playIcon)) {
            setClickedButton();
        } else {
            setPlayedButton();
        }
    }

    /**
     * Sets game state.
     *
     * @param gameState game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}