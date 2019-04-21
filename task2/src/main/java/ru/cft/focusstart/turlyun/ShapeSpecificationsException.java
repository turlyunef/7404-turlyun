package ru.cft.focusstart.turlyun;

public class ShapeSpecificationsException extends Exception {
    private String message;
    public ShapeSpecificationsException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
