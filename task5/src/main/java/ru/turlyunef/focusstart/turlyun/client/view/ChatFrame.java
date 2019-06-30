package ru.turlyunef.focusstart.turlyun.client.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.turlyunef.focusstart.turlyun.client.controller.Controllers;
import ru.turlyunef.focusstart.turlyun.client.controller.event.Event;
import ru.turlyunef.focusstart.turlyun.client.controller.event.SendMessageEvent;
import ru.turlyunef.focusstart.turlyun.client.model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatFrame extends JFrame implements Observer {
    private static final String HINT_STRING_IN_INPUT_FIELD = "write a message";
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 400;
    private static final String FRAME_TITLE = "Chat";
    private static final String SEND_BUTTON_TITLE = "Send";
    private Controllers controllers;
    private JTextArea textInput;
    private JTextArea chatHistory;

    ChatFrame(Controllers controllers) throws HeadlessException {
        this.controllers = controllers;
    }

    void initFrame() {
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
        this.setPreferredSize(dimension);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 1));

        createChatField(northPanel);
        createInputField(southPanel);
        createSendButton(southPanel);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createChatField(JPanel panel) {
        chatHistory = new JTextArea(15, 1);
        chatHistory.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatHistory);
        panel.add(scroll);
        controllers.addObserver(this);
    }

    private void createInputField(JPanel panel) {
        textInput = new JTextArea(3, 1);

        textInput.setForeground(Color.LIGHT_GRAY);
        textInput.setText(HINT_STRING_IN_INPUT_FIELD);
        FieldFocusAdapter focusAdapter = new FieldFocusAdapter(textInput, HINT_STRING_IN_INPUT_FIELD);
        textInput.addFocusListener(focusAdapter);
        JScrollPane scroll = new JScrollPane(textInput);
        panel.add(scroll);
    }

    private void createSendButton(JPanel panel) {
        JButton sendButton = new JButton(SEND_BUTTON_TITLE);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panel.add(sendButton);
    }

    private void sendMessage() {
        try {
            controllers.sendMessage(textInput.getText());
        } catch (JsonProcessingException e) {
            ErrorFrame errorFrame = new ErrorFrame(e.getMessage());
            errorFrame.initFrame();
        }
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof SendMessageEvent){
            addMessageToChatField(((SendMessageEvent) event).getMessageText());
        }
    }

    private void addMessageToChatField(String messageText) {
        chatHistory.append(messageText);
    }
}