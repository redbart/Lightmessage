package net.lightmessage.client;

import net.lightmessage.common.Utils;
import net.lightmessage.common.packets.*;

import java.io.DataInputStream;
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
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            new LoginRequest("userA","passwordA").write(outputStream);
            new SendMessageRequest("test message", 0).write(outputStream);
            new ConversationUpdateRequest(new ConversationStatus[]{}).write(outputStream);
            while(true){
                System.out.println(Packet.readPacket(inputStream));
            }
//            new DisconnectRequest().write(outputStream);
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
