package net.lightmessage.common.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EndResponse extends Packet{
    public static final int ID = 6;

    public EndResponse() {
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
        return "EndResponse{}";
    }
}
