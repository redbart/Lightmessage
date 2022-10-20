package net.lightmessage.common;

import java.util.logging.Logger;

public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public static void printInfo(String appId) {
        LOGGER.info("Running " + appId + " successfully!");
    }
}
