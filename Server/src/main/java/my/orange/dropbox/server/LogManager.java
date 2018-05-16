package my.orange.dropbox.server;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {

    private static Logger LOGGER;

    static {
        LOGGER = Logger.getLogger(Server.class.getName());
        File logsDir = new File("logs");
        if (!logsDir.exists()) logsDir.mkdir();
        try {
            LOGGER.addHandler(new FileHandler("logs/log", 50000, 10, true));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void log(Throwable throwable) {
        LOGGER.log(Level.SEVERE, throwable.getMessage(), throwable);
    }

    public static void log(String entry) {
        LOGGER.log(Level.SEVERE, entry);
    }
}
