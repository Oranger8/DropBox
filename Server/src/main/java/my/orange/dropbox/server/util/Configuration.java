package my.orange.dropbox.server.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import static my.orange.dropbox.server.util.LogManager.log;

public class Configuration extends TimerTask {

    public int PORT;
    public String FOLDER;

    public Configuration() {
        Timer timer = new Timer(true);
        timer.schedule(this, 5 * 60 * 1000, 5 * 60 * 1000);
        new Thread(this).start();
    }

    private void checkFile() throws IOException {
        Path path = Paths.get("config.properties");
        if (!Files.exists(path))
            Files.copy(Configuration.class.getResourceAsStream("/config.properties"), path);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Properties properties = new Properties();
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
            try {
                Thread.sleep(5 * 1000 * 60);
            } catch (InterruptedException e) {
                log(e);
            }
        }
    }

    protected void load() throws IOException {
        checkFile();

    }
}
