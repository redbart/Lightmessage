package net.lightmessage.server;

import net.lightmessage.common.packets.*;
import net.lightmessage.common.persistence.Conversation;
import net.lightmessage.common.persistence.Message;
import net.lightmessage.common.persistence.PersistenceStore;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
        DataOutputStream outputStream;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Input stream creation failure", e);
            return;
        }

        HashMap<Integer, Integer> conversationStatuses = new HashMap<>();

        while (true) {
            try {
                Packet packet = Packet.readPacket(inputStream);

                if (packet instanceof SendMessageRequest sendMessageRequest) {
                    LOGGER.info("SendMessageRequest packet: " + sendMessageRequest);

                    persistenceStore.addMessage(new Message(sendMessageRequest.getConversationId(), sendMessageRequest.getText()));
                } else if (packet instanceof DisconnectRequest disconnectRequest) {
                    LOGGER.info("DisconnectRequest packet: " + disconnectRequest);

                    socket.close();
                    return;
                } else if (packet instanceof ConversationUpdateRequest conversationUpdateRequest) {
                    LOGGER.info("ConversationUpdateRequest packet: " + conversationUpdateRequest);

                    for (ConversationStatus conversationStatus : conversationUpdateRequest.getConversationStatuses()) {
                        conversationStatuses.put(conversationStatus.conversationId(), conversationStatus.sequenceId());
                    }

                    //TODO PLEASE FUTURE LEO UPDATE THIS HARDCODED USER ID 0
                    for (Conversation conversation : persistenceStore.getConversationsByUserId(0)) {
                        if (!conversationStatuses.containsKey(conversation.getConversationId())) {
                            //New conversation response

                            conversationStatuses.put(conversation.getConversationId(), -1);
                        }

                        ArrayList<Message> messages = persistenceStore.getSequencedMessagesByConversationId(conversation.getConversationId());
                        for (int i = conversationStatuses.get(conversation.getConversationId()) + 1; i < messages.size(); i++) {
                            //New message response

                            conversationStatuses.put(conversation.getConversationId(), i);
                        }
                    }

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
