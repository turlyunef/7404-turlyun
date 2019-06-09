package view.statistics;

import javax.swing.*;
import java.awt.*;

/**
 * Game statistics display window
 */
public class StatisticsFrame {

    /**
     * Initializes the statistics window.
     *
     * @param data information displayed in the window
     */
    public void initFrame(String data) {
        JFrame statisticsFrame = new JFrame();
        statisticsFrame.setTitle("Statistics");
        statisticsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        statisticsFrame.setLayout(new BorderLayout());
        statisticsFrame.add(new JTextArea(data));
        statisticsFrame.setResizable(true);
        statisticsFrame.pack();
        statisticsFrame.setLocationRelativeTo(null);
        statisticsFrame.setVisible(true);
    }
}