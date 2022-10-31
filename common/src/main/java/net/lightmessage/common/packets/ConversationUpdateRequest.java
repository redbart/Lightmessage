package net.lightmessage.common.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ConversationUpdateRequest extends Packet {
    public static final int ID = 2;

    private ConversationStatus[] conversationStatuses;

    public ConversationUpdateRequest() {
    }

    public ConversationUpdateRequest(ConversationStatus[] conversationStatuses) {
        this.conversationStatuses = conversationStatuses;
    }

    public ConversationStatus[] getConversationStatuses() {
        return conversationStatuses;
    }

    public void setConversationStatuses(ConversationStatus[] conversationStatuses) {
        this.conversationStatuses = conversationStatuses;
    }


    @Override
    Packet read(DataInputStream inputStream) throws IOException {
        int arrayLength = inputStream.readInt();
        conversationStatuses = new ConversationStatus[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            int conversationId = inputStream.readInt();
            int sequenceId = inputStream.readInt();
            conversationStatuses[i] = new ConversationStatus(conversationId, sequenceId);
        }
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
        outputStream.writeInt(conversationStatuses.length);
        for (ConversationStatus conversationStatus : conversationStatuses) {
            outputStream.writeInt(conversationStatus.conversationId());
            outputStream.writeInt(conversationStatus.sequenceId());
        }
        outputStream.writeInt(hashCode());
        outputStream.flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationUpdateRequest that = (ConversationUpdateRequest) o;
        return Arrays.equals(conversationStatuses, that.conversationStatuses);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(conversationStatuses);
    }

    @Override
    public String toString() {
        return "ConversationUpdateRequest{" +
                "conversationStatuses=" + Arrays.toString(conversationStatuses) +
                '}';
    }
}
