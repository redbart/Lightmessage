package net.lightmessage.common.persistence;

import java.util.ArrayList;
import java.util.HashMap;

public class PersistenceStore {
    ArrayList<Conversation> conversations;
    ArrayList<Message> messages;
    HashMap<Integer, ArrayList<Message>> messagesIndexedByConversationIdSequenced;

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

    public void addMessage(Message message) {
        Message messageCopy = new Message(message);
        ArrayList<Message> conversationMessageList = messagesIndexedByConversationIdSequenced.get(messageCopy.conversationId);

        messageCopy.sequenceId = conversationMessageList.size();
        messageCopy.universalId = messages.size();

        messages.add(messageCopy);
        conversationMessageList.add(messageCopy);
    }
}
