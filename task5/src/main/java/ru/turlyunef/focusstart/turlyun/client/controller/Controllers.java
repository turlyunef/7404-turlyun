package ru.turlyunef.focusstart.turlyun.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.turlyunef.focusstart.turlyun.client.controller.event.ConnectStatusChangeEvent;
import ru.turlyunef.focusstart.turlyun.client.controller.event.Event;
import ru.turlyunef.focusstart.turlyun.client.controller.event.SendMessageEvent;
import ru.turlyunef.focusstart.turlyun.client.controller.event.UpdateUserNamesEvent;
import ru.turlyunef.focusstart.turlyun.client.model.ClientModel;
import ru.turlyunef.focusstart.turlyun.client.model.ConnectStatus;
import ru.turlyunef.focusstart.turlyun.client.model.ConnectionProperties;
import ru.turlyunef.focusstart.turlyun.client.view.Observer;
import ru.turlyunef.focusstart.turlyun.common.Message;
import ru.turlyunef.focusstart.turlyun.common.MessageType;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Controllers implements Observable {
    private static final String REPLACE_NAME = "You";
    private final List<Observer> observers = new ArrayList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private ClientModel clientModel;

    public void connect(ConnectionProperties connectionProperties) throws IOException {
        clientModel = new ClientModel();
        clientModel.connectToServer(connectionProperties);
        startMessageListener();
    }

    public void sendDataMessage(String messageText) throws JsonProcessingException {
        Message message = new Message(MessageType.CLIENT_MESSAGE, messageText, clientModel.getUserName());
        String jsonMessage = objectMapper.writeValueAsString(message);
        clientModel.writeMessage(jsonMessage);
    }

    public void sendServiceStatus(MessageType messageType) throws JsonProcessingException {
        Message message = new Message(messageType, null, null);
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
                    e.printStackTrace();
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
                notifyObservers(new UpdateUserNamesEvent(names));
                break;
            }
        }
    }

    private void displayMessage(Message message) {
        String name = message.getUserName();
        String messageText = message.getMessageText();
        DateFormat df = new SimpleDateFormat("dd.MM.yy k:mm:ss");
        String sendDate = df.format(message.getSendDate());

        if (name.equals(clientModel.getUserName())) {
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