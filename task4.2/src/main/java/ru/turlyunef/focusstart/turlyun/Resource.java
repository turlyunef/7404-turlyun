package ru.turlyunef.focusstart.turlyun;

class Resource {
    private final int id;

    Resource() {
        this.id = Recorder.getResourceId();
    }

    int getId() {

        return id;
    }
}