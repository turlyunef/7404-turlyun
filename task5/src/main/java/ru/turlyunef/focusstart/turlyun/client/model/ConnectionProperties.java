package ru.turlyunef.focusstart.turlyun.client.model;

public class ConnectionProperties {
    private ServerProperties serverProperties;
    private String clientName;

    ServerProperties getServerProperties() {
        return serverProperties;
    }

    public void setServerProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "ConnectionProperties{" +
                "serverProperties=" + serverProperties +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}