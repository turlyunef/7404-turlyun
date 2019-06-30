package ru.turlyunef.focusstart.turlyun.client.model;

public class Message {
    private String messageText;
    private String userName;

    public Message() {
    }

    public Message(String messageText, String userName) {
        this.messageText = messageText;
        this.userName = userName;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getUserName() {
        return userName;
    }
}