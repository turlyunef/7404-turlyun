package ru.turlyunef.focusstart.turlyun.client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.turlyunef.focusstart.turlyun.common.Message;
import ru.turlyunef.focusstart.turlyun.common.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientModel {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private PrintWriter writer;
    private BufferedReader reader;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void connectToServer(ConnectionProperties properties) throws IOException {
        Socket socket = new Socket(properties.getServerProperties().getServerHost(), properties.getServerProperties().getServerPort());
        writer = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        userName = properties.getUserName();
    }

    public void sendNameToServer() throws JsonProcessingException {
        Message message = new Message(MessageType.CLIENT_NAME_RESPONSE, null, userName);
        String jsonMessage = objectMapper.writeValueAsString(message);
        writeMessage(jsonMessage);
    }

    public void writeMessage(String message) {
        writer.println(message);
        writer.flush();
    }
}