package net.lightmessage.common.packets;

import java.io.*;

public abstract class Packet {
    abstract Packet read(ObjectInputStream inputStream) throws IOException;

    public abstract void write(ObjectOutputStream outputStream) throws IOException;

    public static Packet readPacket(ObjectInputStream inputStream) throws IOException {
        int packetID = inputStream.readInt();

        switch (packetID) {
            case SendMessageRequest.ID:
                return new SendMessageRequest().read(inputStream);
            case DisconnectRequest.ID:
                return new DisconnectRequest().read(inputStream);
        }
        throw new IOException("Failed to read packet");
    }
}
