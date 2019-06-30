package ru.turlyunef.focusstart.turlyun.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Message {
    private MessageType messageType;
    private String messageText;
    private String userName;
    private Date sendDate;

    public Message() {
    }

    public Message(MessageType messageType, String messageText, String userName) {
        this.messageType = messageType;
        Calendar calendar = new GregorianCalendar();
        this.sendDate = calendar.getTime();
        this.messageText = messageText;
        this.userName = userName;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getUserName() {
        return userName;
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
                ", userName='" + userName + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}