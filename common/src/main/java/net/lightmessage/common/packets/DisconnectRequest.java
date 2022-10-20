package net.lightmessage.common.packets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DisconnectRequest extends Packet {
    public static final int ID = 1;

    public DisconnectRequest() {
    }

    @Override
    Packet read(ObjectInputStream inputStream) throws IOException {
        return this;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeInt(ID);
        outputStream.flush();
    }

    @Override
    public String toString() {
        return "DisconnectRequest{}";
    }
}
