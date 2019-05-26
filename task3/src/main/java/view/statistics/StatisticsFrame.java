package view.statistics;

import javax.swing.*;
import java.awt.*;

public class StatisticsFrame {

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