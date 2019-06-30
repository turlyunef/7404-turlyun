package ru.turlyunef.focusstart.turlyun.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientModel {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public ConnectStatus connectToServer(ConnectionProperties properties) throws IOException {
        socket = new Socket(properties.getServerProperties().getServerHost(), properties.getServerProperties().getServerPort());
        writer = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        userName = properties.getUserName();

        return ConnectStatus.CONNECTED;
    }

    public void writeMessage(String message) {
        writer.println(message);
        writer.flush();
    }



}
