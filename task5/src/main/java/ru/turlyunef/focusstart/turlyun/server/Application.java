package ru.turlyunef.focusstart.turlyun.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.focusstart.turlyun.server.exception.CreateServerSocketException;
import ru.turlyunef.focusstart.turlyun.server.exception.ReadPropertiesFileException;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.serverInit();
            logger.info("The chat server is running");
        } catch (ReadPropertiesFileException e) {
            logger.error("The chat server is not running. Error reading server properties file, cause " + e.getMessage());
        } catch (CreateServerSocketException e) {
            logger.error("The chat server is not running. Socket did not create, cause " + e.getMessage());
        }
    }
}