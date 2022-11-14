package net.lightmessage.common.packets;

import java.io.*;

public abstract class Packet {
    public static final int MAGIC_BYTES = 0x4c6d7367;

    abstract Packet read(DataInputStream inputStream) throws IOException;

    public abstract void write(DataOutputStream outputStream) throws IOException;

    public static Packet readPacket(DataInputStream inputStream) throws IOException {
        int magic = inputStream.readInt();
        if (magic != MAGIC_BYTES) {
            throw new IOException("Failed to read packet: Invalid magic bytes");
        }
        int packetID = inputStream.readInt();

        switch (packetID) {
            case SendMessageRequest.ID:
                return new SendMessageRequest().read(inputStream);
            case DisconnectRequest.ID:
                return new DisconnectRequest().read(inputStream);
            case ConversationUpdateRequest.ID:
                return new ConversationUpdateRequest().read(inputStream);
            case NewMessageResponse.ID:
                return new NewMessageResponse().read(inputStream);
            case NewConversationResponse.ID:
                return new NewConversationResponse().read(inputStream);
            case LoginRequest.ID:
                return new LoginRequest().read(inputStream);

        }
        throw new IOException("Failed to read packet: Invalid packet ID");
    }
}
