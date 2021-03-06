package ru.turlyunef.focusstart.turlyun.client.view;

import ru.turlyunef.focusstart.turlyun.client.controller.Controller;
import ru.turlyunef.focusstart.turlyun.client.controller.event.ConnectStatusChangeEvent;
import ru.turlyunef.focusstart.turlyun.client.controller.event.Event;
import ru.turlyunef.focusstart.turlyun.client.model.ConnectStatus;
import ru.turlyunef.focusstart.turlyun.client.model.ConnectionProperties;
import ru.turlyunef.focusstart.turlyun.client.model.ServerProperties;
import ru.turlyunef.focusstart.turlyun.client.model.exception.IncorrectConnectionSettings;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ConnectFrame extends JFrame implements Observer {
    private static final String FRAME_TITLE = "Connect to server";
    private static final String HINT_STRING_IN_HOST_FIELD = "localhost";
    private static final String HINT_STRING_IN_PORT_FIELD = "1111";
    private static final String HINT_STRING_IN_CLIENT_NAME_FIELD = "Nickname";
    private static final String CLIENT_NAME_LABEL = "User name:";
    private static final String SERVER_PORT_FIELD_LABEL = "Server port:";
    private static final String SERVER_HOST_FIELD_LABEL = "Server host:";
    private static final String CONNECT_BUTTON_TITLE_BEFORE_CONNECT = "Connect";
    private static final String CONNECT_BUTTON_TITLE_AFTER_CONNECT = "Ok";
    private static final String CONNECTED_STATUS_STRING = "Connected to the server";
    private static final String DISCONNECTED_STATUS_STRING = "Disconnected";
    private final Controller controller;
    private JTextField clientNameField;
    private JTextField serverHostField;
    private JTextField serverPortField;
    private JLabel statusLabel;
    private JButton connectButton;
    private ConnectStatus connectStatus = ConnectStatus.DISCONNECTED;
    private final ChatFrame chatFrame;

    public ConnectFrame(Controller controller) {
        this.controller = controller;
        chatFrame = new ChatFrame(controller);
        chatFrame.initFrame();
    }

    public void initFrame() {
        this.setTitle(FRAME_TITLE);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(3, 2));

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        createServerUrlFields(northPanel);
        createClientNameField(northPanel);
        createConnectButton(centerPanel);
        createConnectStatusField(southPanel);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setResizable(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        controller.addObserver(this);
    }

    private void createServerUrlFields(JPanel panel) {
        JLabel serverHostFieldLabel = new JLabel(SERVER_HOST_FIELD_LABEL);
        panel.add(serverHostFieldLabel);

        serverHostField = createJTextFieldWithHintString(HINT_STRING_IN_HOST_FIELD);
        panel.add(serverHostField);

        JLabel serverPortFieldLabel = new JLabel(SERVER_PORT_FIELD_LABEL);
        panel.add(serverPortFieldLabel);

        serverPortField = createJTextFieldWithHintString(HINT_STRING_IN_PORT_FIELD);
        panel.add(serverPortField);
    }

    private void createClientNameField(JPanel panel) {
        JLabel clientNameLabel = new JLabel(CLIENT_NAME_LABEL);
        panel.add(clientNameLabel);

        clientNameField = createJTextFieldWithHintString(HINT_STRING_IN_CLIENT_NAME_FIELD);
        panel.add(clientNameField);
    }

    private JTextField createJTextFieldWithHintString(String hintString) {
        JTextField component = new JTextField(hintString);
        component.setForeground(Color.LIGHT_GRAY);
        FieldFocusAdapter clientNameFocusAdapter = new FieldFocusAdapter(component, hintString);
        component.addFocusListener(clientNameFocusAdapter);

        return component;
    }

    private void createConnectButton(JPanel panel) {
        connectButton = new JButton(CONNECT_BUTTON_TITLE_BEFORE_CONNECT);
        connectButton.addActionListener(e -> doAction());
        panel.add(connectButton);
    }

    private void doAction() {
        switch (connectStatus) {
            case DISCONNECTED: {
                connectToServer();
                break;
            }
            case WRONG_NAME: {
                /*NOP*/
                break;
            }
            case CONNECTED: {
                createChatFrame();
                break;
            }
        }
    }

    private void connectToServer() {
        ConnectionProperties connectionProperties = new ConnectionProperties();
        try {
            try {
                connectionProperties.setClientName(clientNameField.getText());
            } catch (NullPointerException e) {
                throw new IncorrectConnectionSettings("Username missing.");
            }
            String serverHost;
            try {
                serverHost = serverHostField.getText();
            } catch (NullPointerException e) {
                throw new IncorrectConnectionSettings("Hostname missing.");
            }
            int serverPort;
            try {
                serverPort = Integer.parseInt(serverPortField.getText());
            } catch (NullPointerException e) {
                throw new IncorrectConnectionSettings("Server port missing.");
            } catch (NumberFormatException e) {
                throw new IncorrectConnectionSettings("Incorrect port value for connection to the server.");
            }

            connectionProperties.setServerProperties(new ServerProperties(serverHost, serverPort));
            controller.connect(connectionProperties);
        } catch (IncorrectConnectionSettings | IOException exc) {
            ErrorFrame errorFrame = new ErrorFrame(exc.getMessage());
            errorFrame.initFrame();
        }
    }

    private void createChatFrame() {
        this.setVisible(false);
        chatFrame.setVisible(true);

    }

    private void createConnectStatusField(JPanel panel) {
        statusLabel = new JLabel(DISCONNECTED_STATUS_STRING);
        panel.add(statusLabel);
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof ConnectStatusChangeEvent) {
            this.connectStatus = ((ConnectStatusChangeEvent) event).getConnectStatus();

            if (connectStatus.equals(ConnectStatus.CONNECTED)) {
                statusLabel.setText(CONNECTED_STATUS_STRING);
                connectButton.setText(CONNECT_BUTTON_TITLE_AFTER_CONNECT);

            } else if (connectStatus.equals(ConnectStatus.WRONG_NAME)) {
                connectStatus = ConnectStatus.DISCONNECTED;
                ErrorFrame errorFrame = new ErrorFrame("This name already in use, write another!");
                errorFrame.initFrame();
            }
        }
    }
}