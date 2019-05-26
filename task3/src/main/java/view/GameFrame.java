package view;

import controller.cell.ButtonCellController;
import controller.Controllers;
import controller.restart.button.RestartButtonController;
import controller.statistic.Winner;
import model.game.GameProperties;
import model.game.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class GameFrame {
    private JPanel gameField;
    private JFrame gameFrame;
    private GameProperties gameProperties = new GameProperties();
    private RestartButtonController restartButtonController;
    private Controllers controllers;


    public void initFrame() {
        gameFrame = new JFrame();
        gameFrame.setTitle("Minesweeper");
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setJMenuBar(createMenu());
        gameFrame.setLayout(new BorderLayout());
        this.controllers = new Controllers(gameProperties);
        createRestartButton();
        createGame();
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

    }

    private void createRestartButton() {
        JButton restartButton = new JButton();
        Dimension buttonPreferredSize = new Dimension(50, 50);
        restartButton.setPreferredSize(buttonPreferredSize);
        restartButton.addActionListener(e -> restartGame());
        restartButtonController = new RestartButtonController(restartButton);
        gameFrame.add(restartButton, BorderLayout.NORTH);
        controllers.setRestartButtonController(this.restartButtonController);
    }

    private void restartGame() {
        this.gameFrame.remove(gameField);
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
        gameFrame.add(this.gameField, BorderLayout.CENTER);
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
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        jMenu.add(exitItem);

        addNewGameSubmenu("New game (beginner)", jMenu, 10, 9, 9, "Beginner");
        addNewGameSubmenu("New game (experienced)", jMenu, 40, 16, 16, "Experienced");
        addNewGameSubmenu("New game (expert)", jMenu, 99, 16, 30, "Expert");
        addStatisticsSubmenu(jMenu);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

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
            for (Winner winner: this.controllers.getWinners()
                 ) {
                System.out.printf("Game mode: %s, time: %s sec\n", winner.getPlayMode(), winner.getPlayTime());
            }
        });
        menu.add(item);
    }
}