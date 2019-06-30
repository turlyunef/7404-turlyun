package ru.turlyunef.focusstart.turlyun.client.controller.event;

import ru.turlyunef.focusstart.turlyun.client.model.ConnectStatus;

public class ConnectStatusChangeEvent implements Event {
    private final ConnectStatus connectStatus;

    public ConnectStatusChangeEvent(ConnectStatus connectStatus) {
        this.connectStatus = connectStatus;
    }

    public ConnectStatus getConnectStatus() {

        return this.connectStatus;
    }
}