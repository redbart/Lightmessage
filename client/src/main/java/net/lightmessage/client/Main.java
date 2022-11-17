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

        System.out.println("UserA");

        try {
            Socket socket = new Socket("127.0.0.1", 5432);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            new LoginRequest("userA", "passwordA").write(outputStream);
            Packet packet1 = Packet.readPacket(inputStream);
            System.out.println(packet1);
            new SendMessageRequest("test message", 0).write(outputStream);
            Packet packet2 = Packet.readPacket(inputStream);
            System.out.println(packet2);
            new ConversationUpdateRequest(new ConversationStatus[]{}).write(outputStream);
            while (true) {
                Packet packet3 = Packet.readPacket(inputStream);
                System.out.println(packet3);
                if (packet3 instanceof EndResponse) {
                    break;
                }
            }
            new DisconnectRequest().write(outputStream);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("UserB");
        try {
            Socket socket = new Socket("127.0.0.1", 5432);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            new LoginRequest("userB", "passwordB").write(outputStream);
            Packet packet1 = Packet.readPacket(inputStream);
            System.out.println(packet1);
            new ConversationUpdateRequest(new ConversationStatus[]{}).write(outputStream);
            while (true) {
                Packet packet3 = Packet.readPacket(inputStream);
                System.out.println(packet3);
                if (packet3 instanceof EndResponse) {
                    break;
                }
            }
            new DisconnectRequest().write(outputStream);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("UserZ");
        try {
            Socket socket = new Socket("127.0.0.1", 5432);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            new LoginRequest("userZ", "passwordB").write(outputStream);
            Packet packet1 = Packet.readPacket(inputStream);
            System.out.println(packet1);
            new ConversationUpdateRequest(new ConversationStatus[]{}).write(outputStream);
            while (true) {
                Packet packet3 = Packet.readPacket(inputStream);
                System.out.println(packet3);
                if (packet3 instanceof EndResponse) {
                    break;
                }
            }
            new DisconnectRequest().write(outputStream);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
