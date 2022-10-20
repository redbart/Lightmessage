package net.lightmessage.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager extends Thread {
    private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());

    private final int port;

    public ConnectionManager(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Server socket creation failure", e);
            return;
        }

        while (true) {
            try {
                Socket newClient = serverSocket.accept();

                LOGGER.info("New client " + newClient.getInetAddress().toString());

                new Connection(newClient).start();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Client accept failure", e);
            }
        }
    }
}
