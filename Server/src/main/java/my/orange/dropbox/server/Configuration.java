package my.orange.dropbox.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static my.orange.dropbox.server.LogManager.log;

public class Configuration {

    private static Properties properties;

    public static int PORT;
    public static String FOLDER;

    static {
        properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            properties.load(input);
            PORT = Integer.valueOf(properties.getProperty("port"));
            FOLDER = properties.getProperty("folder");
        } catch (IOException e) {
            log(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log(e);
                }
            }
        }
    }
}
