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

public class GameFrame {
    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel gameField;
    private JFrame gameFrame;
    private GameProperties gameProperties = new GameProperties();
    private RestartButtonController restartButtonController;
    private Controllers controllers;
    private StatisticsFrame statisticsFrame = new StatisticsFrame();

    public void initFrame() {
        this.gameFrame = new JFrame();
        this.gameFrame.setTitle("Minesweeper");
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setJMenuBar(createMenu());
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.northPanel = new JPanel();
        this.northPanel.setLayout(new BorderLayout());
        this.controllers = new Controllers(this.gameProperties);
        createRestartButton(this.northPanel);
        createBombsCounter(this.northPanel);
        createTimer(this.northPanel);
        createGame(this.mainPanel);
        this.mainPanel.add(this.northPanel, BorderLayout.NORTH);
        this.gameFrame.add(this.mainPanel);
        this.gameFrame.setResizable(true);
        this.gameFrame.pack();
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setVisible(true);
    }

    private void createBombsCounter(JPanel usedPanel) {
        BombsCounterScoreboard numberPanel = new BombsCounterScoreboard(Constants.SCOREBOARDS_DIGITS_COUNT);
        JPanel bombCounterPanel = new JPanel();
        bombCounterPanel.add(numberPanel);
        usedPanel.add(bombCounterPanel, BorderLayout.WEST);
        this.controllers.addObserver(numberPanel);
    }

    private void createTimer(JPanel usedPanel) {
        TimerScoreboard timerScoreboard = new TimerScoreboard(Constants.SCOREBOARDS_DIGITS_COUNT);
        JPanel timerPanel = new JPanel();
        timerPanel.add(timerScoreboard);
        usedPanel.add(timerPanel, BorderLayout.EAST);
        this.controllers.addObserver(timerScoreboard);
    }


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

    private void restartGame() {
        this.mainPanel.remove(this.gameField);
        this.restartButtonController.setGameState(GameState.PLAY);
        this.restartButtonController.setPlayedButton();
        this.controllers.setGameProperties(this.gameProperties);
        createGame(this.mainPanel);
        this.gameFrame.pack();
    }

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

    private JButton createCellButton(ButtonMouseListener buttonMouseListener) {
        JButton cellButton = new JButton();
        cellButton.addMouseListener(buttonMouseListener);
        Dimension buttonPreferredSize = new Dimension(Constants.CELL_IMAGE_WIDTH, Constants.CELL_IMAGE_HEIGHT);
        cellButton.setPreferredSize(buttonPreferredSize);
        closeButton(cellButton);

        return cellButton;
    }

    private ButtonMouseListener createButtonMouseListener(Controllers controllers, int rowIndex, int colIndex) {
        ButtonMouseListener buttonMouseListener = new ButtonMouseListener(controllers, rowIndex, colIndex);
        buttonMouseListener.addObserver(this.restartButtonController);

        return buttonMouseListener;
    }

    private void closeButton(JButton button) {
        Icon closedIcon = new ImageIcon(Constants.class.getResource(Constants.CLOSED_ICON));
        button.setIcon(closedIcon);
    }

    private JMenuBar createMenu() {
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

        return jMenuBar;
    }

    private void addNewGameSubmenu(String nameSubmenu, JMenu menu, int bombsCount, int rows, int cols, String gameModeName) {
        JMenuItem restartItem = new JMenuItem(nameSubmenu);
        restartItem.addActionListener(e -> {
            this.gameProperties.setProperties(bombsCount, rows, cols, gameModeName);
            restartGame();
            this.gameFrame.setLocationRelativeTo(null);
        });
        menu.add(restartItem);
    }

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