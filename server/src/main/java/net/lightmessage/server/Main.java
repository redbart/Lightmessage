package net.lightmessage.server;

import net.lightmessage.common.Utils;
import net.lightmessage.common.persistence.PersistenceStore;

public class Main {
    public static void main(String[] args) {
        Utils.printInfo("net.lightmessage.server.Main");

        PersistenceStore persistenceStore = new PersistenceStore();

        ConnectionManager connectionManager = new ConnectionManager(5432, persistenceStore);
        connectionManager.start();
    }
}
