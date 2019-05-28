package view;

import controller.Controllers;
import controller.cell.ButtonCellController;
import controller.cell.ButtonMouseListener;
import controller.restart.button.RestartButtonController;
import controller.scoreboard.BombsCounterScoreboard;
import controller.scoreboard.TimerScoreboard;
import controller.statistic.Winner;
import model.game.GameProperties;
import model.game.GameState;

import javax.swing.*;
import java.awt.*;

/**
 * Viewer class.
 * Defines the game window consisting of the playing field, the reset button, the counter of non-mine bombs, the timer.
 * In the menu, user cans select game modes, see the statistics of wins.
 * The restart button displays the state of the game: play, lose or win with the corresponding icon.
 * This class configures the connection of controllers and viewers.
 *
 * @see Constants
 */
public class GameFrame {
    private JPanel mainPanel;
    private JPanel gameField;
    private JFrame gameFrame;
    private GameProperties gameProperties = new GameProperties();
    private RestartButtonController restartButtonController;
    private Controllers controllers;
    private StatisticsFrame statisticsFrame = new StatisticsFrame();

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
        BombsCounterScoreboard numberPanel = new BombsCounterScoreboard(Constants.SCOREBOARDS_DIGITS_COUNT);
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
        TimerScoreboard timerScoreboard = new TimerScoreboard(Constants.SCOREBOARDS_DIGITS_COUNT);
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
     * @see RestartButtonController
     */
    private void createRestartButton(JPanel usedPanel) {
        JButton restartButton = new JButton();
        Dimension buttonPreferredSize = new Dimension(Constants.RESTART_BUTTON_IMAGE_WIDTH, Constants.RESTART_BUTTON_IMAGE_HEIGHT);
        restartButton.setPreferredSize(buttonPreferredSize);
        restartButton.addActionListener(e -> restartGame());
        this.restartButtonController = new RestartButtonController(restartButton);
        JPanel restartButtonPanel = new JPanel();
        restartButtonPanel.add(restartButton);
        usedPanel.add(restartButtonPanel);
        this.controllers.setRestartButtonController(this.restartButtonController);
    }

    /**
     * Creates a new game
     *
     * @param usedPanel panel to which this game field is added
     * @see controller.cell.GameField
     */
    private void createGame(JPanel usedPanel) {
        this.controllers.createCellFieldControllers();
        this.gameField = new JPanel();
        int rows = this.gameProperties.getRows();
        int cols = this.gameProperties.getCols();

        this.gameField.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ButtonMouseListener buttonMouseListener = createButtonMouseListener(this.controllers, i, j);
                JButton cellButton = createCellButton(buttonMouseListener);
                ButtonCellController buttonCellController = new ButtonCellController(cellButton);
                this.controllers.setCellButtonController(buttonCellController, i, j);
                this.gameField.add(cellButton);
            }
        }
        usedPanel.add(this.gameField, BorderLayout.SOUTH);
    }

    /**
     * Restarts the game
     *
     * @see RestartButtonController
     */
    private void restartGame() {
        this.mainPanel.remove(this.gameField);
        this.restartButtonController.setGameState(GameState.PLAY);
        this.restartButtonController.setPlayedButton();
        this.controllers.setGameProperties(this.gameProperties);
        createGame(this.mainPanel);
        this.gameFrame.pack();
    }

    /**
     * Creates a button that implements the cell of the playing field
     *
     * @param buttonMouseListener mouse listener of the cell of the playing field
     * @return button of the cell of the playing field
     */
    private JButton createCellButton(ButtonMouseListener buttonMouseListener) {
        JButton cellButton = new JButton();
        cellButton.addMouseListener(buttonMouseListener);
        Dimension buttonPreferredSize = new Dimension(Constants.CELL_IMAGE_WIDTH, Constants.CELL_IMAGE_HEIGHT);
        cellButton.setPreferredSize(buttonPreferredSize);
        closeButton(cellButton);

        return cellButton;
    }

    /**
     * Creates button mouse listener of the cell of the playing field.
     * Puts a link to all controllers and restart button controller in a cell
     *
     * @param controllers a link to all controllers
     * @param rowIndex    index on the rows of this button
     * @param colIndex    index on the columns of this button
     * @return button mouse listener of the cell of the playing field
     */
    private ButtonMouseListener createButtonMouseListener(Controllers controllers, int rowIndex, int colIndex) {
        ButtonMouseListener buttonMouseListener = new ButtonMouseListener(controllers, rowIndex, colIndex);
        buttonMouseListener.addObserver(this.restartButtonController);

        return buttonMouseListener;
    }

    /**
     * Sets up the button of the cell as closed
     *
     * @param button close button
     */
    private void closeButton(JButton button) {
        Icon closedIcon = new ImageIcon(Constants.class.getResource(Constants.CLOSED_ICON));
        button.setIcon(closedIcon);
    }

    /**
     * Creates main menu
     *
     * @param gameFrame frame to which this menu is added
     */
    private void createMenu(JFrame gameFrame) {
        JMenu jMenu = new JMenu("File");
        addStatisticsSubmenu(jMenu);

        addNewGameSubmenu("New game (beginner)", jMenu, 10, 9, 9, "Beginner");
        addNewGameSubmenu("New game (experienced)", jMenu, 40, 16, 16, "Experienced");
        addNewGameSubmenu("New game (expert)", jMenu, 99, 16, 30, "Expert");

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        jMenu.add(exitItem);
        gameFrame.setJMenuBar(jMenuBar);
    }

    /**
     * Creates a submenu for creating a new game with the given parameters
     *
     * @param nameSubmenu  display name of the submenu
     * @param menu         menu to which this submenu is added
     * @param bombsCount   number of bombs of the game
     * @param rows         number of rows of the game
     * @param cols         number of columns of the game
     * @param gameModeName name of the game mode, which will be displayed in the statistics
     */
    private void addNewGameSubmenu(String nameSubmenu, JMenu menu, int bombsCount, int rows, int cols, String gameModeName) {
        JMenuItem restartItem = new JMenuItem(nameSubmenu);
        restartItem.addActionListener(e -> {
            this.gameProperties.setProperties(bombsCount, rows, cols, gameModeName);
            restartGame();
            this.gameFrame.setLocationRelativeTo(null);
        });
        menu.add(restartItem);
    }

    /**
     * Creates a submenu for viewing statistics
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