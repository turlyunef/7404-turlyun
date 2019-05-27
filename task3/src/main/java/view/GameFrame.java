package view;

import controller.cell.ButtonCellController;
import controller.Controllers;
import controller.restart.button.RestartButtonController;
import controller.scoreboard.BombsCounterNumberPanel;
import controller.scoreboard.NumberPanel;
import controller.scoreboard.TimerNumberPanel;
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
        gameFrame = new JFrame();
        gameFrame.setTitle("Minesweeper");
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setJMenuBar(createMenu());
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        this.controllers = new Controllers(gameProperties);
        createRestartButton();
        createBombsCounter();
        createTimer();
        createGame();
        mainPanel.add(northPanel, BorderLayout.NORTH);
        gameFrame.add(mainPanel);
        gameFrame.setResizable(true);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    private void createBombsCounter(){
        BombsCounterNumberPanel numberPanel = new BombsCounterNumberPanel(3);
        numberPanel.setNumber(0);
        JPanel bombCounterPanel = new JPanel();
        bombCounterPanel.add(numberPanel);
        northPanel.add(bombCounterPanel, BorderLayout.WEST);
        controllers.addObserver(numberPanel);
    }

    private void createTimer(){
        TimerNumberPanel timerNumberPanel = new TimerNumberPanel(3);
        timerNumberPanel.setNumber(0);
        JPanel timerPanel = new JPanel();
        timerPanel.add(timerNumberPanel);
        northPanel.add(timerPanel, BorderLayout.EAST);
        controllers.addObserver(timerNumberPanel);
    }


    private void createRestartButton() {
        JButton restartButton = new JButton();
        Dimension buttonPreferredSize = new Dimension(50, 50);
        restartButton.setPreferredSize(buttonPreferredSize);
        restartButton.addActionListener(e -> restartGame());
        restartButtonController = new RestartButtonController(restartButton);
        JPanel restartButtonPanel = new JPanel();
        restartButtonPanel.add(restartButton);
        northPanel.add(restartButtonPanel);
        controllers.setRestartButtonController(this.restartButtonController);
    }

    private void restartGame() {
        this.mainPanel.remove(gameField);
        restartButtonController.setGameState(GameState.PLAY);
        restartButtonController.setPlayButton();
        controllers.setGameProperties(gameProperties);
        createGame();
        this.gameFrame.pack();
    }

    private void createGame() {
        controllers.createCellFieldControllers();
        this.gameField = new JPanel();
        int rows = gameProperties.getRows();
        int cols = gameProperties.getCols();

        gameField.setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ButtonMouseListener buttonMouseListener = createButtonMouseListener(controllers, i, j);
                JButton cellButton = createCellButton(buttonMouseListener);
                ButtonCellController buttonCellController = new ButtonCellController(cellButton);
                controllers.setCellButtonController(buttonCellController, i, j);
                gameField.add(cellButton);
            }
        }
        mainPanel.add(this.gameField, BorderLayout.SOUTH);
    }

    private JButton createCellButton(ButtonMouseListener buttonMouseListener){
        JButton cellButton = new JButton();
        cellButton.addMouseListener(buttonMouseListener);
        Dimension buttonPreferredSize = new Dimension(40, 40);
        cellButton.setPreferredSize(buttonPreferredSize);
        closeButton(cellButton);

        return cellButton;
    }

    private ButtonMouseListener createButtonMouseListener(Controllers controllers, int rowIndex, int colIndex){
        ButtonMouseListener buttonMouseListener = new ButtonMouseListener(controllers, rowIndex, colIndex);
        buttonMouseListener.addObserver(restartButtonController);

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
            gameProperties.setProperties(bombsCount, rows, cols, gameModeName);
            restartGame();
            gameFrame.setLocationRelativeTo(null);
        });
        menu.add(restartItem);
    }

    private void addStatisticsSubmenu(JMenu menu){
        JMenuItem item = new JMenuItem("Statistics");
        item.addActionListener(e -> {
            StringBuilder statisticsData = new StringBuilder();
            for (Winner winner: this.controllers.getWinners()
                 ) {
            statisticsData.append(String.format("Game mode: %s, time: %s sec\n", winner.getPlayMode(), winner.getPlayTime()));
            }
            statisticsFrame.initFrame(statisticsData.toString());
        });
        menu.add(item);
    }
}