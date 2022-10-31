package net.lightmessage.common.packets;

import java.io.*;

public class DisconnectRequest extends Packet {
    public static final int ID = 1;

    public DisconnectRequest() {
    }

    @Override
    Packet read(DataInputStream inputStream) throws IOException {
        return this;
    }

    @Override
    public void write(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(Packet.MAGIC_BYTES);
        outputStream.writeInt(ID);
        outputStream.flush();
    }

    @Override
    public String toString() {
        return "DisconnectRequest{}";
    }
}
