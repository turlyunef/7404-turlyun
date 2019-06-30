package ru.turlyunef.focusstart.turlyun.client.model;

public class ConnectionProperties {
    private ServerProperties serverProperties;
    private String userName;

    ServerProperties getServerProperties() {
        return serverProperties;
    }

    public void setServerProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ConnectionProperties{" +
                "serverProperties=" + serverProperties +
                ", userName='" + userName + '\'' +
                '}';
    }
}