package net.lightmessage.common.packets;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SendMessageRequest extends Packet {
    public static final int ID = 0;

    private String message;
    private int conversation;

    public SendMessageRequest(String message, int conversation) {
        this.message = message;
        this.conversation = conversation;
    }

    SendMessageRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getConversation() {
        return conversation;
    }

    public void setConversation(int conversation) {
        this.conversation = conversation;
    }

    @Override
    Packet read(ObjectInputStream inputStream) throws IOException {
        conversation = inputStream.readInt();
        int messageLength = inputStream.readInt();
        message = new String(inputStream.readNBytes(messageLength));
        int hashCode = inputStream.readInt();
        if (hashCode != hashCode()) {
            throw new IOException("Non-matching hash value when reading packet");
        }
        return this;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeInt(ID);
        outputStream.writeInt(conversation);
        outputStream.writeInt(message.length());
        outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        outputStream.writeInt(hashCode());
        outputStream.flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageRequest that = (SendMessageRequest) o;
        return conversation == that.conversation && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, conversation);
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "message='" + message + '\'' +
                ", conversation=" + conversation +
                '}';
    }
}
