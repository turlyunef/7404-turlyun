package ru.turlyunef.focusstart.turlyun.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Message {
    private MessageType messageType;
    private String messageText;
    private String clientName;
    private Date sendDate;

    public Message() {
    }

    public Message(MessageType messageType, String messageText, String clientName) {
        this.messageType = messageType;
        Calendar calendar = new GregorianCalendar();
        this.sendDate = calendar.getTime();
        this.messageText = messageText;
        this.clientName = clientName;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getClientName() {
        return clientName;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", messageText='" + messageText + '\'' +
                ", clientName='" + clientName + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}