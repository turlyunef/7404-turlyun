package ru.turlyunef.focusstart.turlyun.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

class ChatClient {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String name;
    private ClientStatus clientStatus;

    BufferedReader getReader() {
        return reader;
    }

    void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    Socket getSocket() {
        return socket;
    }

    void setSocket(Socket socket) {
        this.socket = socket;
    }

    PrintWriter getWriter() {
        return writer;
    }

    void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    ClientStatus getClientStatus() {
        return clientStatus;
    }

    void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }
}