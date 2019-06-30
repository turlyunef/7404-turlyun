package ru.turlyunef.focusstart.turlyun.client.controller;

import ru.turlyunef.focusstart.turlyun.client.controller.event.Event;
import ru.turlyunef.focusstart.turlyun.client.view.Observer;

interface Observable {

    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers(Event event);
}