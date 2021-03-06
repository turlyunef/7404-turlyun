package view;

import controller.Controllers;
import controller.field.ButtonMouseListener;
import controller.field.GameFieldController;
import model.game.statistic.Winner;
import model.game.DifficultyLevel;
import model.game.GameProperties;
import model.game.GameState;
import view.restart.button.RestartButton;
import view.scoreboard.BombsCounterScoreboard;
import view.scoreboard.TimerScoreboard;
import view.statistics.StatisticsFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Viewer class.
 * Defines the game window consisting of the playing field, the reset button, the counter of non-mine bombs and the timer.
 * In the menu, user can select game modes, see the statistics of wins.
 * The restart button displays the state of the game: play, lose or win with the corresponding icon.
 * This class configures all connections of controllers and viewers.
 */
public class GameFrame {
    private static final int RESTART_BUTTON_IMAGE_HEIGHT = 50;
    private static final int RESTART_BUTTON_IMAGE_WIDTH = 50;
    private static final int SCOREBOARDS_DIGITS_COUNT = 3;
    private final GameProperties gameProperties = new GameProperties();
    private final StatisticsFrame statisticsFrame = new StatisticsFrame();
    private JPanel mainPanel;
    private JPanel gameField;
    private JFrame gameFrame;
    private RestartButton restartButton;
    private Controllers controllers;

    /**
     * Initializes the main game window.
     */
    public void initFrame() {
        this.gameFrame = new JFrame();
        this.gameFrame.setTitle("Minesweeper");
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createMenu(this.gameFrame);
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        this.controllers = new Controllers(this.gameProperties);
        createRestartButton(northPanel);
        createBombsCounter(northPanel);
        createTimer(northPanel);
        createGame(this.mainPanel);
        this.mainPanel.add(northPanel, BorderLayout.NORTH);
        this.gameFrame.add(this.mainPanel);
        this.gameFrame.setResizable(true);
        this.gameFrame.pack();
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setVisible(true);
    }

    /**
     * Creates a scoreboard of bombs that are not cleared by flags.
     *
     * @param usedPanel panel to which this scoreboard is added
     * @see BombsCounterScoreboard
     */
    private void createBombsCounter(JPanel usedPanel) {
        BombsCounterScoreboard numberPanel = new BombsCounterScoreboard(SCOREBOARDS_DIGITS_COUNT);
        JPanel bombCounterPanel = new JPanel();
        bombCounterPanel.add(numberPanel);
        usedPanel.add(bombCounterPanel, BorderLayout.WEST);
        this.controllers.addObserver(numberPanel);
    }

    /**
     * Creates a scoreboard timer.
     * The timer starts when the user presses the right or left mouse button on the playing field.
     * The timer stops when losing or winning.
     * The timer resets when the user clicks the restart button.
     *
     * @param usedPanel panel to which this scoreboard is added
     * @see TimerScoreboard
     */
    private void createTimer(JPanel usedPanel) {
        TimerScoreboard timerScoreboard = new TimerScoreboard(SCOREBOARDS_DIGITS_COUNT);
        JPanel timerPanel = new JPanel();
        timerPanel.add(timerScoreboard);
        usedPanel.add(timerPanel, BorderLayout.EAST);
        this.controllers.addObserver(timerScoreboard);
    }

    /**
     * Creates a game restart button.
     * The button reacts to the user pressing the right or left mouse button.
     * The button displays the current state of the game: play, lose or win.
     *
     * @param usedPanel panel to which this button is added
     * @see RestartButton
     */
    private void createRestartButton(JPanel usedPanel) {
        JButton restartButton = new JButton();
        Dimension buttonPreferredSize = new Dimension(RESTART_BUTTON_IMAGE_WIDTH, RESTART_BUTTON_IMAGE_HEIGHT);
        restartButton.setPreferredSize(buttonPreferredSize);
        restartButton.addActionListener(e -> restartGame());
        this.restartButton = new RestartButton(restartButton);
        JPanel restartButtonPanel = new JPanel();
        restartButtonPanel.add(restartButton);
        usedPanel.add(restartButtonPanel);
        this.controllers.setRestartButton(this.restartButton);
    }

    /**
     * Creates a new game.
     *
     * @param usedPanel panel to which this game field is added
     * @see GameFieldController
     */
    private void createGame(JPanel usedPanel) {
        this.controllers.createGame();
        this.gameField = new JPanel();
        int rows = this.gameProperties.getRows();
        int cols = this.gameProperties.getCols();
        this.gameField.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ButtonMouseListener buttonMouseListener = createButtonMouseListener(this.controllers, i, j);
                CellButton cellButton = new CellButton(buttonMouseListener);
                this.controllers.setCellButtonController(cellButton, i, j);
                this.gameField.add(cellButton);
            }
        }
        usedPanel.add(this.gameField, BorderLayout.SOUTH);
    }

    /**
     * Restarts the game.
     *
     * @see RestartButton
     */
    private void restartGame() {
        this.mainPanel.remove(this.gameField);
        this.restartButton.setGameState(GameState.PLAY);
        this.restartButton.setPlayedButton();
        this.controllers.setGameProperties(this.gameProperties);
        createGame(this.mainPanel);
        this.gameFrame.pack();
    }

    /**
     * Creates button mouse listener of the cell.
     * Puts a link to all controllers and restart button controller in a field.
     *
     * @param controllers a link to all controllers
     * @param rowIndex    index on the rows of this button
     * @param colIndex    index on the columns of this button
     * @return button mouse listener of the cell
     */
    private ButtonMouseListener createButtonMouseListener(Controllers controllers, int rowIndex, int colIndex) {
        ButtonMouseListener buttonMouseListener = new ButtonMouseListener(controllers, rowIndex, colIndex);
        buttonMouseListener.addObserver(this.restartButton);

        return buttonMouseListener;
    }

    /**
     * Creates main menu.
     *
     * @param gameFrame frame to which this menu is added
     */
    private void createMenu(JFrame gameFrame) {
        JMenu jMenu = new JMenu("File");
        addStatisticsSubmenu(jMenu);

        addNewGameSubmenu("New game (beginner)", jMenu, DifficultyLevel.BEGINNER);
        addNewGameSubmenu("New game (experienced)", jMenu, DifficultyLevel.EXPERIENCED);
        addNewGameSubmenu("New game (expert)", jMenu, DifficultyLevel.EXPERT);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        jMenu.add(exitItem);
        gameFrame.setJMenuBar(jMenuBar);
    }

    /**
     * Creates a submenu for creating a new game with the given parameters.
     *
     * @param nameSubmenu     display name of the submenu
     * @param menu            menu to which this submenu is added
     * @param difficultyLevel level of difficulty, that contains parameters of the game mode
     */
    private void addNewGameSubmenu(String nameSubmenu, JMenu menu, DifficultyLevel difficultyLevel) {
        JMenuItem restartItem = new JMenuItem(nameSubmenu);
        restartItem.addActionListener(e -> {
            this.gameProperties.setProperties(difficultyLevel);
            restartGame();
            this.gameFrame.setLocationRelativeTo(null);
        });
        menu.add(restartItem);
    }

    /**
     * Creates a submenu for viewing statistics.
     *
     * @param menu menu to which this submenu is added
     */
    private void addStatisticsSubmenu(JMenu menu) {
        JMenuItem item = new JMenuItem("Statistics");
        item.addActionListener(e -> {
            StringBuilder statisticsData = new StringBuilder();
            for (Winner winner : this.controllers.getWinners()) {
                statisticsData.append(String.format("Game mode: %s, time: %s sec\n", winner.getPlayMode(), winner.getPlayTime()));
            }
            if (statisticsData.toString().isEmpty()) {
                this.statisticsFrame.initFrame("Statistics is empty.");
            } else {
                this.statisticsFrame.initFrame(statisticsData.toString());
            }
        });
        menu.add(item);
    }
}