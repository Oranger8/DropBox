package my.orange.dropbox.server;

import my.orange.dropbox.server.handler.ClientHandler;
import my.orange.dropbox.server.util.Configuration;
import my.orange.dropbox.server.util.LogManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

public class Server extends Configuration implements Runnable {

    private LogManager logger;

    private ServerSocketChannel channel;
    private Selector selector;
    private ClientHandler clientHandler;

    private Server() {
        try {
            logger = LogManager.getLogger();
            channel = ServerSocketChannel.open();
            selector = Selector.open();
            channel.bind(new InetSocketAddress(PORT));
            channel.configureBlocking(false);
            channel.register(selector, channel.validOps());
            clientHandler = new ClientHandler();
            Runtime.getRuntime().addShutdownHook(new Thread(this));
        } catch (IOException e) {
            logger.log("Failed to start server", e);
        }
    }

    private void start() {
        while (channel.isOpen()) {
            try {
                if (selector.select() < 0) continue;
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    if (key.isAcceptable()) {
                        clientHandler.accept(key);
                        keys.iterator().remove();
                    }
                }
            } catch (IOException e) {
                logger.log("Failed to listen for connections", e);
            }
        }
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new Server().start();
    }
}
