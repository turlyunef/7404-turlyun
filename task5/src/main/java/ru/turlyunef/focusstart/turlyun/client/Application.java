package ru.turlyunef.focusstart.turlyun.client;

import ru.turlyunef.focusstart.turlyun.client.controller.Controller;
import ru.turlyunef.focusstart.turlyun.client.view.ConnectFrame;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller();
        ConnectFrame connectFrame = new ConnectFrame(controller);
        connectFrame.initFrame();
    }
}