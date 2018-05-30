package my.orange.dropbox.server;

import my.orange.dropbox.server.handler.ClientTask;
import my.orange.dropbox.server.util.Configuration;
import my.orange.dropbox.server.util.LogManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Configuration implements Runnable {

    private LogManager logger;

    private ServerSocket server;
    private ExecutorService executor;

    private Server() {
        try {
            logger = LogManager.getLogger();
            server = new ServerSocket(PORT);
            executor = Executors.newCachedThreadPool();
            Runtime.getRuntime().addShutdownHook(new Thread(this));
        } catch (IOException e) {
            logger.log("Failed to start server", e);
        }
    }

    private void start() {
        while (true) {
            try {
                executor.submit(new ClientTask(server.accept()));
            } catch (IOException e) {
                logger.log("Failed to handle connections", e);
            }
        }
    }

    @Override
    public void run() {
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                logger.log("", e);
            }
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
