package ru.turlyunef.focusstart.turlyun.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.focusstart.turlyun.common.Message;
import ru.turlyunef.focusstart.turlyun.common.MessageType;
import ru.turlyunef.focusstart.turlyun.server.exception.CreateServerSocketException;
import ru.turlyunef.focusstart.turlyun.server.exception.ReadPropertiesFileException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<ChatClient> clients = new CopyOnWriteArrayList<>();
    private ServerSocket serverSocket;

    void serverInit() throws ReadPropertiesFileException, CreateServerSocketException {
        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        } catch (IOException e) {
            throw new ReadPropertiesFileException("error occurs when reading from the input stream");
        }
        try {
            serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("server.port")));
            createMessageListenerThread();
            createClientsListener();
        } catch (IllegalArgumentException e) {
            throw new ReadPropertiesFileException("property \"server.port\" or file \"server.properties\" not found");
        } catch (IOException e) {
            throw new CreateServerSocketException("I/O error occurs when opening the socket");
        }
    }

    private void createMessageListenerThread() {
        Thread messageListenerThread = new Thread(() -> {
            boolean interrupted = false;
            while (!interrupted) {
                try {
                    String jsonMessage;
                    for (ChatClient client : clients) {
                        BufferedReader reader = client.getReader();
                        if (reader.ready()) {
                            jsonMessage = reader.readLine();
                            Message message = objectMapper.readValue(jsonMessage, Message.class);
                            processMessage(client, message);
                        }
                    }
                } catch (IOException e) {
                    logger.warn("Error reading message, cause " + e.getMessage());
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        });
        messageListenerThread.start();
        createCloserThread(messageListenerThread);
    }

    private void createCloserThread(Thread interruptedThread) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                interruptedThread.interrupt();
                serverSocket.close();
                for (ChatClient client : clients) {
                    client.getSocket().close();
                }
            } catch (IOException e) {
                logger.error("Error closing client socket, cause " + e.getMessage());
            }
        }));
    }

    private void processMessage(ChatClient client, Message message) throws IOException {
        switch (message.getMessageType()) {
            case CLIENT_MESSAGE: {
                sendAll(message);
                break;
            }
            case CLIENT_NAME_RESPONSE: {
                validateClientName(client, message.getClientName());
                break;
            }
            case CLIENT_DISCONNECTED: {
                clients.remove(client);
                sendAllClientNames();
                sendAllThatClientDisconnected(client);
                client.getSocket().close();
                break;
            }
        }
    }

    private void validateClientName(ChatClient client, String clientName) throws JsonProcessingException {
        if (checkName(clientName)) {
            client.setName(clientName);
            client.setClientStatus(ClientStatus.VALIDATED);
            logger.info(String.format("Client %s validated with name %s.",
                    client.getSocket().toString(), client.getName()));
            sendServiceMessage(client, MessageType.SUCCESS_CONNECT);
            sendAllClientNames();
            sendAllThatClientConnected(client);
        } else {
            sendServiceMessage(client, MessageType.WRONG_CLIENT_NAME);
            logger.info(String.format("Client %s did not validate with name %s, cause this name is used.",
                    client.getSocket().toString(), client.getName()));
        }
    }

    private void sendAllThatClientConnected(ChatClient client) throws JsonProcessingException {
        Message message = new Message(MessageType.NEW_CLIENT_CONNECTED, "Connected", client.getName());
        sendAll(message);
        logger.info(String.format("Client %s connected.", client.getName()));
    }

    private void sendAllThatClientDisconnected(ChatClient client) throws JsonProcessingException {
        Message message = new Message(MessageType.CLIENT_DISCONNECTED, "Disconnected", client.getName());
        sendAll(message);
        logger.info(String.format("Client %s disconnected.", client.getName()));
    }

    private void sendAllClientNames() throws JsonProcessingException {
        List<String> names = getClientNames();
        String jsonNames = objectMapper.writeValueAsString(names);
        Message message = new Message(MessageType.CLIENT_NAMES_RESPONSE, jsonNames, null);
        sendAll(message);
    }

    private List<String> getClientNames() {
        List<String> clientNames = new ArrayList<>();
        for (ChatClient client : clients) {
            if (client.getClientStatus().equals(ClientStatus.VALIDATED)) {
                clientNames.add(client.getName());
            }
        }

        return clientNames;
    }

    private void sendServiceMessage(ChatClient client, MessageType messageType) throws JsonProcessingException {
        Message message = new Message(messageType, null, null);
        String jsonMessage = objectMapper.writeValueAsString(message);
        sendMessage(client, jsonMessage);
        logger.info(String.format("The server sent to the client with %s message %s.", client.getSocket(), messageType));
    }

    private boolean checkName(String clientName) {
        for (ChatClient client : clients) {
            if ((client.getName() != null) && (client.getName().equals(clientName))) {

                return false;
            }
        }

        return true;
    }

    private void sendAll(Message message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        for (ChatClient client : clients) {
            if (client.getClientStatus().equals(ClientStatus.VALIDATED)) {
                sendMessage(client, jsonMessage);
            }
        }
        logger.info(String.format("The server sent all clients message: %s", message.toString()));
    }

    private void createClientsListener() {
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ChatClient client = new ChatClient();
                client.setSocket(clientSocket);
                client.setWriter(new PrintWriter(clientSocket.getOutputStream()));
                client.setReader(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
                requestName(client);
                clients.add(client);
            } catch (JsonProcessingException e) {
                logger.warn("Request client name was failed. Error occurred when parsing JSON content");
            } catch (IOException e) {
                logger.warn("I/O error occurs when waiting for a client connection");
            }
        }
    }

    private void requestName(ChatClient client) throws JsonProcessingException {
        Message message = new Message(MessageType.CLIENT_NAME_REQUEST, null, null);
        String jsonMessage;
        jsonMessage = objectMapper.writeValueAsString(message);
        sendMessage(client, jsonMessage);
        client.setClientStatus(ClientStatus.REQUESTED_NAME);
    }

    private void sendMessage(ChatClient client, String message) {
        PrintWriter writer = client.getWriter();
        writer.println(message);
        writer.flush();
    }
}