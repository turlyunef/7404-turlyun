package ru.turlyunef.focusstart.turlyun.client.view;

import ru.turlyunef.focusstart.turlyun.client.controller.event.Event;

public interface Observer {

    void handleEvent(Event event);
}