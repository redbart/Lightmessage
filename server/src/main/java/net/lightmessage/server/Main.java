package net.lightmessage.server;

import net.lightmessage.common.Utils;

public class Main {
    public static void main(String[] args) {
        Utils.printInfo("net.lightmessage.server.Main");

        ConnectionManager connectionManager = new ConnectionManager(5432);
        connectionManager.start();
    }
}
