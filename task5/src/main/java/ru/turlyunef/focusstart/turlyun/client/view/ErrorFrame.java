package ru.turlyunef.focusstart.turlyun.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ErrorFrame extends JFrame {
    private static final String ACCEPT_BUTTON_TITLE = "Ok";
    private static final String FRAME_TITLE = "Error!";
    private final String errorMessage;

    ErrorFrame(String errorMessage) throws HeadlessException {
        this.errorMessage = errorMessage;
    }

    void initFrame() {
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        createErrorMessageField(mainPanel);
        createAcceptButton(mainPanel);
        this.add(mainPanel);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createErrorMessageField(JPanel panel) {
        JLabel errorMessageField = new JLabel(errorMessage);
        panel.add(errorMessageField);
    }

    private void createAcceptButton(JPanel panel) {
        JButton connectButton = new JButton(ACCEPT_BUTTON_TITLE);
        connectButton.addActionListener(e -> doAction());
        panel.add(connectButton);
    }

    private void doAction() {
        this.dispose();
    }
}