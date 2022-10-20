package net.lightmessage.server;

import net.lightmessage.common.packets.DisconnectRequest;
import net.lightmessage.common.packets.Packet;
import net.lightmessage.common.packets.SendMessageRequest;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection extends Thread {
    private static final Logger LOGGER = Logger.getLogger(Connection.class.getName());

    private final Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream inputStream;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Input stream creation failure", e);
            return;
        }

        while (true) {
            try {
                Packet packet = Packet.readPacket(inputStream);

                if (packet instanceof SendMessageRequest) {
                    LOGGER.info("SendMessageRequest packet: " + packet);
                } else if (packet instanceof DisconnectRequest) {
                    LOGGER.info("DisconnectRequest packet: " + packet);

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
