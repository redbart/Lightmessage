package net.lightmessage.common.persistence;

import java.util.ArrayList;
import java.util.HashMap;

public class PersistenceStore {
    ArrayList<Conversation> conversations = new ArrayList<>();
    ArrayList<Message> messages = new ArrayList<>();
    HashMap<Integer, ArrayList<Message>> messagesIndexedByConversationIdSequenced = new HashMap<>();

    public PersistenceStore() {
        conversations.add(new Conversation(0));

        Message message1 = new Message(0, "Message1");
        messages.add(message1);
        messagesIndexedByConversationIdSequenced.put(0, new ArrayList<>());
        messagesIndexedByConversationIdSequenced.get(0).add(message1);
    }

    public ArrayList<Message> getSequencedMessagesByConversationId(int conversationId) {
        return messagesIndexedByConversationIdSequenced.get(conversationId);
    }

    public Conversation getConversation(int conversationId) {
        return conversations.get(conversationId);
    }

    public ArrayList<Conversation> getConversationsByUserId(int userId) {
        // TODO THIS IS PLACEHOLDER CODE PLEASE UPDATE IT WHEN A USER SYSTEM IS INTRODUCED
        return conversations;
    }

    public Message addMessage(Message message) {
        Message messageCopy = new Message(message);
        ArrayList<Message> conversationMessageList = messagesIndexedByConversationIdSequenced.get(messageCopy.conversationId);

        messageCopy.sequenceId = conversationMessageList.size();
        messageCopy.universalId = messages.size();

        messages.add(messageCopy);
        conversationMessageList.add(messageCopy);
        return messageCopy;
    }
}
