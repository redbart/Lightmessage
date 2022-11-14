package net.lightmessage.common.persistence;

import java.util.ArrayList;

public class Conversation {
    int conversationId;
    ArrayList<String> users;

    public Conversation(ArrayList<String> users) {
        this.users = users;
    }

    public Conversation(Conversation other) {
        this.users = new ArrayList<>(other.users);
    }

    public int getConversationId() {
        return conversationId;
    }

    public ArrayList<String> getUsers() {
        return users;
    }
}
