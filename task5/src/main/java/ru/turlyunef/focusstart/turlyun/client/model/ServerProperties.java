package ru.turlyunef.focusstart.turlyun.client.model;

public class ServerProperties {
    private String serverHost;
    private int serverPort;

    public ServerProperties(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public int getServerPort() {
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