package view;

import controller.ButtonController;
import controller.Controllers;
import controller.RestartButtonController;
import model.game.GameProperties;
import model.game.GameState;
import model.game.Model;
import model.game.TableGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class GameFrame {
    private JPanel gameField;
    private JFrame gameFrame;
    private Model gameModel;
    private GameProperties gameProperties = new GameProperties();
    private RestartButtonController restartButtonController;
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
        restartButton.addActionListener(e -> restartGame());
        restartButtonController = new RestartButtonController(restartButton);

        return restartButton;
    }

    private void restartGame() {
        this.gameFrame.remove(gameField);
        restartButtonController.setGameState(GameState.PLAY);
        createGame();
        this.gameFrame.pack();
    }

    private void createGame() {
        this.gameField = new JPanel();
        createNewModel();
        int rows = gameProperties.getRows();
        int cols = gameProperties.getCols();
        Controllers controllers = new Controllers(new ButtonController[rows][cols], gameModel, restartButtonController);
        gameField.setLayout(new GridLayout(rows, cols));

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                ButtonMouseListener buttonMouseListener = createButtonMouseListener(controllers, i, j);
                JButton cellButton = createCellButton(buttonMouseListener);


                ButtonController buttonController = new ButtonController(cellButton);
                controllers.setCellButtonController(buttonController, i, j);
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

    private void createNewModel() {
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