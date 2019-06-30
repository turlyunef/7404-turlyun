package ru.turlyunef.focusstart.turlyun.client.controller.event;

import java.util.List;

public class UpdateUserNamesEvent implements Event {
    private final List<String> names;

    public UpdateUserNamesEvent(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {

        return names;
    }
}