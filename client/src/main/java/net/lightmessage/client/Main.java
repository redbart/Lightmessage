package net.lightmessage.client;

import net.lightmessage.common.Utils;
import net.lightmessage.common.packets.DisconnectRequest;
import net.lightmessage.common.packets.SendMessageRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Utils.printInfo("net.lightmessage.client.Main");

        try {
            Socket socket = new Socket("127.0.0.1", 5432);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            new SendMessageRequest("test message on conversation 1234", 1234).write(outputStream);
            new DisconnectRequest().write(outputStream);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
