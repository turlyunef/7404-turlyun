package ru.turlyunef.focusstart.turlyun.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.turlyunef.focusstart.turlyun.common.Message;
import ru.turlyunef.focusstart.turlyun.common.MessageType;

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

    void serverInit() {
        Properties properties = new Properties();
        try (InputStream propertiesStream = Server.class.getClassLoader().getResourceAsStream("server.properties")) {
            if (propertiesStream != null) {
                properties.load(propertiesStream);
            }
        } catch (IOException e) {
            logger.error("Error reading server configuration file, cause " + e.getMessage());
        }
        try {
            serverSocket = new ServerSocket(Integer.valueOf(properties.getProperty("server.port")));
            createMessageListenerThread();
            createClientsListener();
        } catch (IOException e) {
            logger.error("Socket not open, cause " + e.getMessage());
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
                    logger.error("Error reading message, cause " + e.getMessage());
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

    private void processMessage(ChatClient client, Message message) throws JsonProcessingException {
        switch (message.getMessageType()) {
            case DATA: {
                sendAll(message);
                break;
            }
            case USER_NAME_RESPONSE: {
                validateClientName(client, message.getUserName());
                break;
            }
            case REQUEST_USERS_NAME: {
                sendAllUserNames();
                break;
            }
            case DISCONNECTED: {
                clients.remove(client);
                sendAllUserNames();
                sendAllThatUserDisconnected(client);
                break;
            }
        }
    }

    private void validateClientName(ChatClient client, String clientName) throws JsonProcessingException {
        for (ChatClient chatClient : clients) {
            if (chatClient.getSocket().equals(client.getSocket())) {
                if (checkName(clientName)) {
                    chatClient.setName(clientName);
                    chatClient.setClientStatus(ClientStatus.VALIDATED);
                    logger.info(String.format("Client %s validated with name %s.",
                            client.getSocket().toString(), chatClient.getName()));
                    sendServiceMessage(client, MessageType.CONNECTED);
                    sendAllUserNames();
                    sendAllThatUserConnected(client);
                    break;
                } else {
                    sendServiceMessage(client, MessageType.WRONG_NAME);
                    logger.info(String.format("Client %s did not validate with name %s, cause this name is used.",
                            client.getSocket().toString(), chatClient.getName()));
                }
            }
        }
    }

    private void sendAllThatUserConnected(ChatClient client) throws JsonProcessingException {
        Message message = new Message(MessageType.NEW_USER_CONNECTED, "Connected", client.getName());
        sendAll(message);
        logger.info(String.format("Client %s connected.", client.getName()));
    }

    private void sendAllThatUserDisconnected(ChatClient client) throws JsonProcessingException {
        Message message = new Message(MessageType.USER_DISCONNECTED, "Disconnected", client.getName());
        sendAll(message);
        logger.info(String.format("Client %s disconnected.", client.getName()));
    }

    private void sendAllUserNames() throws JsonProcessingException {
        List<String> names = getClientNames();
        String jsonNames = objectMapper.writeValueAsString(names);
        Message message = new Message(MessageType.USERS_NAME_RESPONSE, jsonNames, null);
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

    private boolean checkName(String userName) {
        for (ChatClient client : clients) {
            if ((client.getName() != null) && (client.getName().equals(userName))) {

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

    private void createClientsListener() throws IOException {
        //noinspection InfiniteLoopStatement
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ChatClient client = new ChatClient();

            client.setSocket(clientSocket);
            client.setWriter(new PrintWriter(clientSocket.getOutputStream()));
            client.setReader(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));

            clients.add(client);
            requestName(client);
        }
    }

    private void requestName(ChatClient client) throws JsonProcessingException {
        Message message = new Message(MessageType.REQUEST_NAME, null, null);
        String jsonMessage = objectMapper.writeValueAsString(message);
        sendMessage(client, jsonMessage);
        client.setClientStatus(ClientStatus.REQUESTED_NAME);
    }

    private void sendMessage(ChatClient client, String message) {
        PrintWriter writer = client.getWriter();
        writer.println(message);
        writer.flush();
    }
}