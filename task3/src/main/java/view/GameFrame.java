package view;

import controller.ButtonController;
import controller.Controllers;
import controller.RestartButtonController;
import model.game.GameState;
import model.game.Model;
import model.game.TableGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame {
    private JPanel gameField;
    private JFrame gameFrame;
    private Model gameModel;
    GameState gameState;
    GameProperties gameProperties = new GameProperties();
    RestartButtonController restartButtonController;
    private Controllers controllers;
    private static Logger log = LoggerFactory.getLogger(GameFrame.class);


    public void initFrame() {
        gameFrame = new JFrame();
        gameFrame.setTitle("Minesweeper");
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setJMenuBar(createMenu());
        gameFrame.setLayout(new BorderLayout());

        gameFrame.add(createRestartButton(), BorderLayout.NORTH);
        createGame();
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    private JButton createRestartButton() {
        JButton restartButton = new JButton();
        Dimension buttonPreferredSize = new Dimension(50, 50);
        restartButton.setPreferredSize(buttonPreferredSize);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        restartButtonController = new RestartButtonController(restartButton);

        return restartButton;
    }

    private void restartGame() {
        this.gameFrame.remove(gameField);
        createGame();
        this.gameFrame.pack();
    }

    private void createGame() {
        int rows = gameProperties.getRows();
        int cols = gameProperties.getCols();
        this.gameState = GameState.PLAY;
        this.gameField = new JPanel();
        try {
            this.gameModel = new Model(gameProperties);
        } catch (TableGenerationException e) {
            gameProperties = new GameProperties();
            try {
                this.gameModel = new Model(gameProperties);
            } catch (TableGenerationException ex) {
                ex.printStackTrace();
            }
        }
        controllers = new Controllers(new ButtonController[rows][cols], gameModel, restartButtonController);
        JButton[][] buttons = new JButton[rows][cols];
        gameField.setLayout(new GridLayout(rows, cols));
        Dimension buttonPreferredSize = new Dimension(40, 40);
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                ButtonController buttonController = new ButtonController(buttons[i][j]);
                controllers.setController(buttonController, i, j);
                ButtonMouseListener buttonMouseListener = new ButtonMouseListener(controllers, i, j);
                buttonMouseListener.addObserver(restartButtonController);
                buttons[i][j].addMouseListener(buttonMouseListener);
                buttons[i][j].setPreferredSize(buttonPreferredSize);
                gameField.add(buttons[i][j]);
            }
        }
        closeButtons(buttons);
        gameFrame.add(this.gameField, BorderLayout.CENTER);
    }

    private void closeButtons(JButton[][] buttons) {
        Icon closedIcon = new ImageIcon(Constants.class.getResource(Constants.CLOSED_ICON));
        for (JButton[] button : buttons) {
            for (JButton jButton : button) {
                jButton.setIcon(closedIcon);
            }
        }
    }

    private JMenuBar createMenu() {
        JMenu jMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        jMenu.add(exitItem);

        addNewGameSubmenu("New game (beginner)", jMenu, 10, 9, 9);
        addNewGameSubmenu("New game (experienced)", jMenu, 40, 16, 16);
        addNewGameSubmenu("New game (expert)", jMenu, 99, 16, 30);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        return jMenuBar;
    }

    private void addNewGameSubmenu(String nameSubmenu, JMenu menu, int bombsCount, int rows, int cols) {
        JMenuItem restartItem = new JMenuItem(nameSubmenu);
        restartItem.addActionListener(e -> {
            gameProperties.setProperties(bombsCount, rows, cols);
            restartGame();
        });
        menu.add(restartItem);

    }
}