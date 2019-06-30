package ru.turlyunef.focusstart.turlyun.client;

import ru.turlyunef.focusstart.turlyun.client.controller.Controllers;
import ru.turlyunef.focusstart.turlyun.client.view.ConnectFrame;

public class Application {
    public static void main(String[] args) {
        Controllers controllers = new Controllers();
        ConnectFrame connectFrame = new ConnectFrame(controllers);
        connectFrame.initFrame();
    }
}