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

    public void addMessage(Message message) {
        Message messageCopy = new Message(message);
        ArrayList<Message> conversationMessageList = messagesIndexedByConversationIdSequenced.get(message.conversationId);

        messageCopy.sequenceId = conversationMessageList.size();
        messageCopy.universalId = messages.size();

        messages.add(messageCopy);
        conversationMessageList.add(messageCopy);
    }
}
