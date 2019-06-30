package ru.turlyunef.focusstart.turlyun.client.controller.event;

public class SendMessageEvent implements Event {
    private final String messageText;

    public SendMessageEvent(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {

        return messageText;
    }
}