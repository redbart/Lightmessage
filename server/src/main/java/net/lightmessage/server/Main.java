package net.lightmessage.server;

import net.lightmessage.common.Utils;
import net.lightmessage.common.persistence.Conversation;
import net.lightmessage.common.persistence.Message;
import net.lightmessage.common.persistence.PersistenceStore;
import net.lightmessage.common.persistence.User;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Utils.printInfo("net.lightmessage.server.Main");

        PersistenceStore persistenceStore = new PersistenceStore();

        persistenceStore.addUser(new User("userA"));
        persistenceStore.addUser(new User("userB"));
        persistenceStore.addUser(new User("userZ"));

        Conversation conv1 = new Conversation(new ArrayList<>());
        conv1.getUsers().add("userA");
        conv1.getUsers().add("userB");
        persistenceStore.addConversation(conv1);

        Message message1 = new Message(conv1.getConversationId(), "Message1");
        persistenceStore.addMessage(message1);

        ConnectionManager connectionManager = new ConnectionManager(5432, persistenceStore);
        connectionManager.start();
    }
}
