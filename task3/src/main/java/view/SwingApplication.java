package view;

import controller.Controller;
import controller.Controllers;
import model.game.Model;
import model.game.TableGenerationException;

import javax.swing.*;
import java.awt.*;

public class SwingApplication {

    public static void main(String[] args) throws TableGenerationException {

        JFrame frame = new JFrame();
        frame.setTitle("Minesweeper");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        JMenu jMenu = new JMenu("File");
        jMenu.add(exitItem);

        JMenuBar jMenuBar = new JMenuBar();
        jMenuBar.add(jMenu);

        frame.setJMenuBar(jMenuBar);

        JPanel jPanel = new JPanel();
        frame.add(jPanel);

        int rows = Constants.DEFAULT_ROWS_COUNT;
        int cols = Constants.DEFAULT_COLUMNS_COUNT;
        int bombsCount = Constants.DEFAULT_BOMBS_COUNT;

        Model model = new Model(bombsCount, rows, cols);
        Controllers controllers = new Controllers(rows, cols, model);

        JButton[][] buttons = new JButton[rows][cols];

        jPanel.setLayout(new GridLayout(rows, cols));

        Icon closedIcon = new ImageIcon(Constants.class.getResource(Constants.CLOSED_ICON));
        Dimension buttonPreferredSize = new Dimension(40, 40);

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setIcon(closedIcon);

                Controller controller = new Controller(i, j, buttons[i][j], model);
                controllers.setController(controller, i, j);

                buttons[i][j].addActionListener(controller);
                buttons[i][j].addMouseListener(controller);
                buttons[i][j].setPreferredSize(buttonPreferredSize);
                jPanel.add(buttons[i][j]);
            }
        }
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                controllers.addCellsAround(i, j, cols);
            }
        }

        frame.setLayout(new BorderLayout());
        frame.add(jPanel, BorderLayout.CENTER);

        JLabel jLabel = new JLabel(new ImageIcon(SwingApplication.class.getResource("/icons/win.png")));
        frame.add(jLabel, BorderLayout.NORTH);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}