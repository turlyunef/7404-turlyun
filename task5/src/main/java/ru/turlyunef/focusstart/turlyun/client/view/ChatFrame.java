package ru.turlyunef.focusstart.turlyun.client.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.turlyunef.focusstart.turlyun.client.controller.Controller;
import ru.turlyunef.focusstart.turlyun.client.controller.event.Event;
import ru.turlyunef.focusstart.turlyun.client.controller.event.SendMessageEvent;
import ru.turlyunef.focusstart.turlyun.client.controller.event.UpdateClientNamesEvent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatFrame extends JFrame implements Observer {
    private static final String HINT_STRING_IN_INPUT_FIELD = "Write a message...";
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 400;
    private static final String FRAME_TITLE = "Chat";
    private static final String SEND_BUTTON_TITLE = "Send";
    private final Controller controller;
    private JTextArea textInput;
    private JTextArea chatHistory;
    private JTextArea clients;

    ChatFrame(Controller controller) throws HeadlessException {
        this.controller = controller;
    }

    void initFrame() {
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dimension = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
        this.setPreferredSize(dimension);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel leftHorizontalPanel = new JPanel();
        leftHorizontalPanel.setLayout(new BorderLayout());

        JPanel rightHorizontalPanel = new JPanel();
        rightHorizontalPanel.setLayout(new BorderLayout());

        mainPanel.add(leftHorizontalPanel, BorderLayout.CENTER);
        mainPanel.add(rightHorizontalPanel, BorderLayout.EAST);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(2, 1));

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());

        createChatField(northPanel);
        createInputField(southPanel);
        createSendButton(southPanel);
        createClientsScoreboard(eastPanel);

        leftHorizontalPanel.add(northPanel, BorderLayout.NORTH);
        leftHorizontalPanel.add(southPanel, BorderLayout.SOUTH);
        rightHorizontalPanel.add(eastPanel);

        this.add(mainPanel);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        controller.addObserver(this);
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof SendMessageEvent) {
            addMessageToChatField(((SendMessageEvent) event).getMessageText());
        }
        if (event instanceof UpdateClientNamesEvent) {
            updateClientNamesInClientScoreboard(((UpdateClientNamesEvent) event).getNames());
        }
    }

    private void createSendButton(JPanel panel) {
        JButton sendButton = new JButton(SEND_BUTTON_TITLE);
        sendButton.addActionListener(e -> sendMessage());
        panel.add(sendButton);
    }

    private void createChatField(JPanel panel) {
        chatHistory = new JTextArea(15, 1);
        chatHistory.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatHistory);
        panel.add(scroll);
    }

    private void createInputField(JPanel panel) {
        textInput = new JTextArea(3, 1);
        restartTextInputField();
        FieldFocusAdapter focusAdapter = new FieldFocusAdapter(textInput, HINT_STRING_IN_INPUT_FIELD);
        textInput.addFocusListener(focusAdapter);
        JScrollPane scroll = new JScrollPane(textInput);
        panel.add(scroll);
    }

    private void createClientsScoreboard(JPanel panel) {
        clients = new JTextArea(15, 20);
        clients.setEditable(false);
        JScrollPane scroll = new JScrollPane(clients);
        panel.add(scroll);
    }

    private void restartTextInputField() {
        textInput.setForeground(Color.LIGHT_GRAY);
        textInput.setText(HINT_STRING_IN_INPUT_FIELD);
    }

    private void sendMessage() {
        if (!HINT_STRING_IN_INPUT_FIELD.equals(textInput.getText())) {
            try {
                controller.sendDataMessage(textInput.getText());
                restartTextInputField();
            } catch (JsonProcessingException e) {
                ErrorFrame errorFrame = new ErrorFrame(e.getMessage());
                errorFrame.initFrame();
            }
        }
    }

    private void addMessageToChatField(String messageText) {
        chatHistory.append(messageText);
    }

    private void updateClientNamesInClientScoreboard(List<String> clientNameList) {
        clients.setText("Online:\n\n");
        for (String name : clientNameList) {
            clients.append(name + "\n");
        }
    }
}