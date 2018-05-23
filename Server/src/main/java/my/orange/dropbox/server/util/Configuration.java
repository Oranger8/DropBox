package my.orange.dropbox.server.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {

    private LogManager logger;

    protected int PORT;
    protected String FOLDER;

    protected Configuration() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            logger = LogManager.getLogger();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            checkFile();
            input = new FileInputStream("config.properties");
            properties.load(input);
            PORT = Integer.valueOf(properties.getProperty("port"));
            FOLDER = properties.getProperty("folder");
        } catch (IOException e) {
            logger.log("Failed to load configuration", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.log(e.getMessage(), e);
                }
            }
        }
    }

    private void checkFile() throws IOException {
        Path path = Paths.get("config.properties");
        if (!Files.exists(path))
            Files.copy(Configuration.class.getResourceAsStream("/config.properties"), path);
    }
}
