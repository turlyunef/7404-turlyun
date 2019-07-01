package ru.turlyunef.focusstart.turlyun.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.focusstart.turlyun.client.controller.event.ConnectStatusChangeEvent;
import ru.turlyunef.focusstart.turlyun.client.controller.event.Event;
import ru.turlyunef.focusstart.turlyun.client.controller.event.SendMessageEvent;
import ru.turlyunef.focusstart.turlyun.client.controller.event.UpdateClientNamesEvent;
import ru.turlyunef.focusstart.turlyun.client.model.ClientModel;
import ru.turlyunef.focusstart.turlyun.client.model.ConnectStatus;
import ru.turlyunef.focusstart.turlyun.client.model.ConnectionProperties;
import ru.turlyunef.focusstart.turlyun.client.view.ErrorFrame;
import ru.turlyunef.focusstart.turlyun.client.view.Observer;
import ru.turlyunef.focusstart.turlyun.common.Message;
import ru.turlyunef.focusstart.turlyun.common.MessageType;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Controller implements Observable {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final String REPLACE_NAME = "You";
    private final List<Observer> observers = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ClientModel clientModel;

    public void connect(ConnectionProperties connectionProperties) throws IOException {
        clientModel = new ClientModel();
        clientModel.connectToServer(connectionProperties);
        startMessageListener();
        startCloser();
    }

    public void sendDataMessage(String messageText) throws JsonProcessingException {
        Message message = new Message(MessageType.CLIENT_MESSAGE, messageText, clientModel.getClientName());
        String jsonMessage = objectMapper.writeValueAsString(message);
        clientModel.writeMessage(jsonMessage);
    }

    private void startMessageListener() {
        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    if (clientModel.getReader().ready()) {
                        String jsonMessage = clientModel.getReader().readLine();
                        Message message = objectMapper.readValue(jsonMessage, Message.class);
                        processMessage(message);
                    }
                } catch (IOException e) {
                    logger.warn("Request client name was failed. Error occurred when parsing JSON content");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        });
        messageListenerThread.start();
    }

    private void startCloser() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                sendDisconnectedStatus();
            } catch (JsonProcessingException e) {
                ErrorFrame errorFrame = new ErrorFrame("Error closing client socket, cause" + e.getMessage());
                errorFrame.initFrame();
            }
        }));
    }

    private void sendDisconnectedStatus() throws JsonProcessingException {
        Message message = new Message(MessageType.CLIENT_DISCONNECTED, null, null);
        String jsonMessage = objectMapper.writeValueAsString(message);
        clientModel.writeMessage(jsonMessage);
    }

    private void processMessage(Message message) throws IOException {
        switch (message.getMessageType()) {
            case CLIENT_MESSAGE:
            case CLIENT_DISCONNECTED:
            case NEW_CLIENT_CONNECTED: {
                displayMessage(message);
                break;
            }
            case CLIENT_NAME_REQUEST: {
                clientModel.sendNameToServer();
                break;
            }
            case SUCCESS_CONNECT: {
                notifyObservers(new ConnectStatusChangeEvent(ConnectStatus.CONNECTED));
                break;
            }
            case WRONG_CLIENT_NAME: {
                notifyObservers(new ConnectStatusChangeEvent(ConnectStatus.WRONG_NAME));
                break;
            }
            case CLIENT_NAMES_RESPONSE: {
                List<String> names = objectMapper.readValue(message.getMessageText(), new TypeReference<List<String>>() {
                });
                notifyObservers(new UpdateClientNamesEvent(names));
                break;
            }
        }
    }

    private void displayMessage(Message message) {
        String name = message.getClientName();
        String messageText = message.getMessageText();
        DateFormat df = new SimpleDateFormat("dd.MM.yy k:mm:ss");
        String sendDate = df.format(message.getSendDate());
        if (name.equals(clientModel.getClientName())) {
            name = REPLACE_NAME;
        }
        notifyObservers(new SendMessageEvent(
                sendDate + "\n" + name + ": " + messageText + "\n"));
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Event event) {
        for (Observer observer : observers) {
            observer.handleEvent(event);
        }
    }
}