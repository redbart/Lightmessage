package net.lightmessage.common.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class NewMessageResponse extends Packet{
    public static final int ID = 4;

    private int universalId;

    private int conversationId;
    private int sequenceId;

    private String text;

    public NewMessageResponse() {
    }

    public NewMessageResponse(int universalId, int conversationId, int sequenceId, String text) {
        this.universalId = universalId;
        this.conversationId = conversationId;
        this.sequenceId = sequenceId;
        this.text = text;
    }

    @Override
    Packet read(DataInputStream inputStream) throws IOException {
        universalId = inputStream.readInt();
        conversationId = inputStream.readInt();
        sequenceId = inputStream.readInt();
        int textLength = inputStream.readInt();
        text = new String(inputStream.readNBytes(textLength));
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
        outputStream.writeInt(universalId);
        outputStream.writeInt(conversationId);
        outputStream.writeInt(sequenceId);
        outputStream.writeInt(text.length());
        outputStream.write(text.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewMessageResponse that = (NewMessageResponse) o;
        return universalId == that.universalId && conversationId == that.conversationId && sequenceId == that.sequenceId && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(universalId, conversationId, sequenceId, text);
    }

    @Override
    public String toString() {
        return "NewMessageResponse{" +
                "universalId=" + universalId +
                ", conversationId=" + conversationId +
                ", sequenceId=" + sequenceId +
                ", text='" + text + '\'' +
                '}';
    }
}
