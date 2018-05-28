package my.orange.dropbox.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static Properties properties;

    public static String HOST;
    public static int PORT;

    static {
        properties = new Properties();
        InputStream in = null;
        try {
            in = Configuration.class.getResourceAsStream("/config.properties");
            properties.load(in);
            HOST = properties.getProperty("host");
            PORT = Integer.valueOf(properties.getProperty("port"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
