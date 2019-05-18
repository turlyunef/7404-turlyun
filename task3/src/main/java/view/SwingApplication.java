package view;

import controller.ButtonController;
import controller.Controllers;
import model.game.GameState;
import model.game.Model;
import model.game.TableGenerationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SwingApplication {
    Controllers controllers;
    JButton[][] buttons;
    JPanel jPanel;
    Model model;
    JLabel jLabel;
    JFrame frame;

    public void initFrame() throws TableGenerationException {
        initFrame(Constants.DEFAULT_BOMBS_COUNT, Constants.DEFAULT_ROWS_COUNT, Constants.DEFAULT_COLUMNS_COUNT);
    }

    public void initFrame(int bombsCount, int rows, int cols) throws TableGenerationException {
        frame = new JFrame();
        frame.setTitle("Minesweeper");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(createMenu());
        frame.setLayout(new BorderLayout());
        createGameField(bombsCount, rows, cols);
        frame.add(this.jPanel, BorderLayout.CENTER);
        jLabel = new JLabel(new ImageIcon(SwingApplication.class.getResource(Constants.WIN_ICON)));
        frame.add(jLabel, BorderLayout.NORTH);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public void restartGame(int bombsCount, int rows, int cols) throws TableGenerationException {
//        this.model = new Model(bombsCount, rows, cols);
//        closeButtons();
    }

    public void closeButtons() {
        Icon closedIcon = new ImageIcon(Constants.class.getResource(Constants.CLOSED_ICON));
        for (JButton[] button : buttons) {
            for (JButton jButton : button) {
                jButton.setIcon(closedIcon);
            }
        }
    }

    private void createGameField(int bombsCount, int rows, int cols) throws TableGenerationException {
        this.jPanel = new JPanel();
        this.model = new Model(bombsCount, rows, cols);
        this.controllers = new Controllers(new ButtonController[rows][cols], this.model);
        this.buttons = new JButton[rows][cols];
        jPanel.setLayout(new GridLayout(rows, cols));

        Dimension buttonPreferredSize = new Dimension(40, 40);
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                ButtonController buttonController = new ButtonController(buttons[i][j]);
                controllers.setController(buttonController, i, j);
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            controllers.pressedButton3(finalI, finalJ);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            controllers.releasedButton1(finalI, finalJ);
                        }
                        if (e.getButton() == MouseEvent.BUTTON2) {
                            controllers.releasedButton2(finalI, finalJ);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
                buttons[i][j].setPreferredSize(buttonPreferredSize);
                jPanel.add(buttons[i][j]);
            }
        }
        closeButtons();
    }

    private JMenuBar createMenu() {
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        JMenuItem restartItem = new JMenuItem("New game");
        restartItem.addActionListener(e -> {
            try {
                restartGame(Constants.DEFAULT_BOMBS_COUNT, Constants.DEFAULT_ROWS_COUNT, Constants.DEFAULT_COLUMNS_COUNT);
            } catch (TableGenerationException ex) {
                ex.printStackTrace();
            }
        });

        JMenu jMenu = new JMenu("File");
        jMenu.add(exitItem);
        jMenu.add(restartItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        return jMenuBar;
    }
}