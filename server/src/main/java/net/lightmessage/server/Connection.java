package net.lightmessage.server;

import net.lightmessage.common.packets.DisconnectRequest;
import net.lightmessage.common.packets.Packet;
import net.lightmessage.common.packets.SendMessageRequest;
import net.lightmessage.common.persistence.Message;
import net.lightmessage.common.persistence.PersistenceStore;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection extends Thread {
    private static final Logger LOGGER = Logger.getLogger(Connection.class.getName());

    private final Socket socket;
    private final PersistenceStore persistenceStore;

    public Connection(Socket socket, PersistenceStore persistenceStore) {
        this.socket = socket;
        this.persistenceStore = persistenceStore;
    }

    @Override
    public void run() {
        DataInputStream inputStream;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Input stream creation failure", e);
            return;
        }

        while (true) {
            try {
                Packet packet = Packet.readPacket(inputStream);

                if (packet instanceof SendMessageRequest sendMessageRequest) {
                    LOGGER.info("SendMessageRequest packet: " + sendMessageRequest);

                    persistenceStore.addMessage(new Message(sendMessageRequest.getConversation(),sendMessageRequest.getMessage()));
                } else if (packet instanceof DisconnectRequest disconnectRequest) {
                    LOGGER.info("DisconnectRequest packet: " + disconnectRequest);

                    socket.close();
                    return;
                } else {
                    LOGGER.warning("UNKNOWN packet: " + packet);
                }
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Packet read failure", e);
                return;
            }
        }
    }
}
