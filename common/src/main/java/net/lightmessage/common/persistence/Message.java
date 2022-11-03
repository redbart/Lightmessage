package net.lightmessage.common.persistence;

public class Message {
    public Message(int conversationId, String text) {
        this.conversationId = conversationId;
        this.text = text;
    }

    public Message(Message other){
        this.conversationId = other.conversationId;
        this.text = other.text;
    }

    int universalId;

    int conversationId;
    int sequenceId;

    String text;

    public int getUniversalId() {
        return universalId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public int getSequenceId() {
        return sequenceId;
    }

    public String getText() {
        return text;
    }
}
