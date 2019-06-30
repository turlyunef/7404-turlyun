package ru.turlyunef.focusstart.turlyun.client.model;

public class ServerProperties {
    private final String serverHost;
    private final int serverPort;

    public ServerProperties(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    String getServerHost() {
        return serverHost;
    }

    int getServerPort() {
        return serverPort;
    }

    @Override
    public String toString() {
        return "ServerProperties{" +
                "serverHost='" + serverHost + '\'' +
                ", serverPort=" + serverPort +
                '}';
    }
}