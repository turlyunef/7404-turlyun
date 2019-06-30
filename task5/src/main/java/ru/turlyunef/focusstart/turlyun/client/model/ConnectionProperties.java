package ru.turlyunef.focusstart.turlyun.client.model;

public class ConnectionProperties {
    private ServerProperties serverProperties;
    private String userName;

    public void setServerProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ServerProperties getServerProperties() {
        return serverProperties;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "ConnectionProperties{" +
                "serverProperties=" + serverProperties +
                ", userName='" + userName + '\'' +
                '}';
    }
}