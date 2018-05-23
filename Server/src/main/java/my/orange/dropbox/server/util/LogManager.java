package my.orange.dropbox.server.util;

import my.orange.dropbox.server.Server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogManager {

    private static LogManager INSTANCE;

    private Logger logger;

    private LogManager() throws IOException {
        logger = Logger.getLogger(Server.class.getName());
        Path path = checkFolder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        FileHandler handler = new FileHandler(
                path.toAbsolutePath().toString() +
                        "/" +
                        dateFormat.format(Calendar.getInstance().getTime()) +
                        ".log",
                true);
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    public static synchronized LogManager getLogger() throws IOException {
        if (INSTANCE == null) INSTANCE = new LogManager();
        return INSTANCE;
    }

    private Path checkFolder() throws IOException {
        Path path = Paths.get("logs");
        if (!Files.exists(path))
            Files.createDirectory(path);
        return path;
    }

    public void log(String message, Throwable throwable) {
        logger.log(Level.SEVERE, throwable.getMessage(), throwable);
    }

    public void log(String message) {
        logger.log(Level.SEVERE, message);
    }
}
