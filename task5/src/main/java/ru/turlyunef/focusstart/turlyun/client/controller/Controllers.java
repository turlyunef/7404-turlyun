package ru.turlyunef.focusstart.turlyun.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ru.turlyunef.focusstart.turlyun.client.controller.event.ConnectStatusChangeEvent;
import ru.turlyunef.focusstart.turlyun.client.controller.event.Event;
import ru.turlyunef.focusstart.turlyun.client.controller.event.SendMessageEvent;
import ru.turlyunef.focusstart.turlyun.client.model.ClientModel;
import ru.turlyunef.focusstart.turlyun.client.model.ConnectStatus;
import ru.turlyunef.focusstart.turlyun.client.model.ConnectionProperties;
import ru.turlyunef.focusstart.turlyun.client.model.Message;
import ru.turlyunef.focusstart.turlyun.client.view.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controllers implements Observable {
    private List<Observer> observers = new ArrayList<>();

    private final ObjectWriter messageWriter;
    private final ObjectMapper objectMapper;
    private ClientModel clientModel;


    public Controllers() {
        objectMapper = new ObjectMapper();
        messageWriter = objectMapper.writerFor(Message.class);
    }

    public void connect(ConnectionProperties connectionProperties) throws IOException {
        clientModel = new ClientModel();
        clientModel.connectToServer(connectionProperties);
        notifyObservers(new ConnectStatusChangeEvent(ConnectStatus.CONNECTED));
        startMessageListener();
    }

    public void sendMessage(String messageText) throws JsonProcessingException {
        Message message = new Message(messageText, clientModel.getUserName());
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
                        if (message.getUserName().equals(clientModel.getUserName())) {
                            sendMessageAs("You", message.getMessageText());
                        } else {
                            sendMessageAs(message.getUserName(), message.getMessageText());
                        }


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

    private void sendMessageAs(String name, String messageText) {
        notifyObservers(new SendMessageEvent(
                name + ": " + messageText + "\n"));
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
