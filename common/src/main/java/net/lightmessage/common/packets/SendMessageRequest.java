package net.lightmessage.common.packets;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class SendMessageRequest extends Packet {
    public static final int ID = 0;

    private String text;
    private int conversationId;

    public SendMessageRequest(String message, int conversation) {
        this.text = message;
        this.conversationId = conversation;
    }

    SendMessageRequest() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    Packet read(DataInputStream inputStream) throws IOException {
        conversationId = inputStream.readInt();
        int textLength = inputStream.readInt();
        text = new String(inputStream.readNBytes(textLength), StandardCharsets.UTF_8);
        int hashCode = inputStream.readInt();
        if (hashCode != hashCode()) {
            throw new IOException("Non-matching hash value when reading packet");
        }
        return this;
    }

    @Override
    public void write(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(Packet.MAGIC_BYTES);
        outputStream.writeInt(ID);
        outputStream.writeInt(conversationId);
        outputStream.writeInt(text.length());
        outputStream.write(text.getBytes(StandardCharsets.UTF_8));
        outputStream.writeInt(hashCode());
        outputStream.flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageRequest that = (SendMessageRequest) o;
        return conversationId == that.conversationId && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, conversationId);
    }

    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "message='" + text + '\'' +
                ", conversation=" + conversationId +
                '}';
    }
}
