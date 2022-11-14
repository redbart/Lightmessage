package net.lightmessage.common.persistence;

import java.util.ArrayList;
import java.util.HashMap;

//TODO add error handling if ID does not exist
public class PersistenceStore {
    ArrayList<Conversation> conversations = new ArrayList<>();
    ArrayList<Message> messages = new ArrayList<>();
    HashMap<Integer, ArrayList<Message>> messagesIndexedByConversationIdSequenced = new HashMap<>();
    HashMap<String, User> usersIndexedByUsername = new HashMap<>();
    HashMap<String, ArrayList<Conversation>> conversationsIndexedByUsername = new HashMap<>();

    public PersistenceStore() {

    }

    public ArrayList<Message> getSequencedMessagesByConversationId(int conversationId) {
        return messagesIndexedByConversationIdSequenced.get(conversationId);
    }

    public Conversation getConversation(int conversationId) {
        return conversations.get(conversationId);
    }

    public ArrayList<Conversation> getConversationsByUsername(String username) {
        return conversationsIndexedByUsername.get(username);
    }

    public User getUser(String username) {
        return usersIndexedByUsername.get(username);
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

    public Conversation addConversation(Conversation conversation) {
        Conversation conversationCopy = new Conversation(conversation);
        conversationCopy.conversationId = conversations.size();
        ArrayList<Message> messageList = new ArrayList<>();

        conversations.add(conversationCopy);
        messagesIndexedByConversationIdSequenced.put(conversationCopy.getConversationId(), messageList);
        for (String u : conversationCopy.users) {
            ArrayList<Conversation> conversationList = conversationsIndexedByUsername.get(u);
            conversationList.add(conversationCopy);
        }
        return conversationCopy;
    }

    public User addUser(User user) {
        User userCopy = new User(user);
        ArrayList<Conversation> conversationList = new ArrayList<>();

        usersIndexedByUsername.put(userCopy.username, userCopy);
        conversationsIndexedByUsername.put(userCopy.username, conversationList);
        return userCopy;
    }
}
