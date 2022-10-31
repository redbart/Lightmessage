package net.lightmessage.common.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class NewConversationResponse extends Packet {
    public static final int ID = 3;

    private int conversationId;

    public NewConversationResponse() {
    }

    public NewConversationResponse(int conversationId) {
        this.conversationId = conversationId;
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
        outputStream.writeInt(hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewConversationResponse that = (NewConversationResponse) o;
        return conversationId == that.conversationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationId);
    }

    @Override
    public String toString() {
        return "NewConversationResponse{" +
                "conversationId=" + conversationId +
                '}';
    }
}
