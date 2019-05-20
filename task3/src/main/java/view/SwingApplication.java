package view;

import controller.ButtonController;
import controller.Controllers;
import model.game.GameState;
import model.game.Model;
import model.game.TableGenerationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class SwingApplication {
    private JPanel gameField;
    JLabel jLabel;
    private JFrame gameFrame;
    Model gameModel;
    GameState gameState;
    private static Logger log = LoggerFactory.getLogger(SwingApplication.class);

    public void initFrame() throws TableGenerationException {
        initFrame(Constants.DEFAULT_BOMBS_COUNT, Constants.DEFAULT_ROWS_COUNT, Constants.DEFAULT_COLUMNS_COUNT);
    }

    private void initFrame(int bombsCount, int rows, int cols) throws TableGenerationException {
        gameFrame = new JFrame();
        gameFrame.setTitle("Minesweeper");
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setJMenuBar(createMenu());
        gameFrame.setLayout(new BorderLayout());
        createGameField(bombsCount, rows, cols);

        jLabel = new JLabel(new ImageIcon(SwingApplication.class.getResource(Constants.WIN_ICON)));
        gameFrame.add(jLabel, BorderLayout.NORTH);
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    private void restartGame(int bombsCount, int rows, int cols) throws TableGenerationException {
        this.gameFrame.remove(gameField);
        createGameField(bombsCount, rows, cols);
        this.gameFrame.pack();
    }

    private void createGameField(int bombsCount, int rows, int cols) throws TableGenerationException {
        this.gameState = GameState.PLAY;
        this.gameField = new JPanel();
        this.gameModel = new Model(bombsCount, rows, cols);
        Controllers controllers = new Controllers(new ButtonController[rows][cols], gameModel);
        JButton[][] buttons = new JButton[rows][cols];
        gameField.setLayout(new GridLayout(rows, cols));
        Dimension buttonPreferredSize = new Dimension(40, 40);
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                ButtonController buttonController = new ButtonController(buttons[i][j]);
                controllers.setController(buttonController, i, j);
                buttons[i][j].addMouseListener(new ButtonMouseListener(controllers, i, j));
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
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        JMenuItem restartAsNewbieItem = new JMenuItem("New game (newbie)");
        restartAsNewbieItem.addActionListener(e -> {
            try {
                restartGame(10, 9, 9);
            } catch (TableGenerationException ex) {
                try {
                    restartGame(Constants.DEFAULT_BOMBS_COUNT, Constants.DEFAULT_ROWS_COUNT, Constants.DEFAULT_COLUMNS_COUNT);
                } catch (TableGenerationException exc) {
                    exc.printStackTrace();
                }
            }
        });

        JMenuItem restartAsExperiencedItem = new JMenuItem("New game (experienced)");
        restartAsExperiencedItem.addActionListener(e -> {
            try {
                restartGame(40, 16, 16);
            } catch (TableGenerationException ex) {
                try {
                    restartGame(10, 9, 9);
                } catch (TableGenerationException exc) {
                    exc.printStackTrace();
                }
            }
        });

        JMenuItem restartAsExpertItem = new JMenuItem("New game (expert)");
        restartAsExpertItem.addActionListener(e -> {
            try {
                restartGame(99, 16, 30);
            } catch (TableGenerationException ex) {
                try {
                    restartGame(10, 9, 9);
                } catch (TableGenerationException exc) {
                    exc.printStackTrace();
                }
            }
        });

        JMenu jMenu = new JMenu("File");
        jMenu.add(exitItem);
        jMenu.add(restartAsNewbieItem);
        jMenu.add(restartAsExperiencedItem);
        jMenu.add(restartAsExpertItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        return jMenuBar;
    }
}