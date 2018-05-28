package my.orange.dropbox.server;

import my.orange.dropbox.server.handler.ClientTask;
import my.orange.dropbox.server.util.Configuration;
import my.orange.dropbox.server.util.LogManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.channels.SelectionKey.OP_CONNECT;
import static java.nio.channels.SelectionKey.OP_READ;

public class Server extends Configuration implements Runnable {

    private static final int CLIENT_OPS = OP_READ | OP_CONNECT;

    private LogManager logger;

    private ServerSocketChannel serverChannel;
    private Selector selector;
    private ExecutorService executor;

    private Server() {
        try {
            logger = LogManager.getLogger();
            serverChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverChannel.bind(new InetSocketAddress(PORT));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, serverChannel.validOps());
            executor = Executors.newCachedThreadPool();
            Runtime.getRuntime().addShutdownHook(new Thread(this));
        } catch (IOException e) {
            logger.log("Failed to start server", e);
        }
    }

    private void start() {
        while (serverChannel.isOpen()) {
            try {
                if (selector.select() < 0) continue;
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {
                        accept();
                        iterator.remove();
                    }

                    if (key.isReadable()) {
                        executor.submit(new ClientTask(key));
                        iterator.remove();
                    }

                    if (key.isConnectable()) {
                        disconnect(key);
                        iterator.remove();
                    }

                }
            } catch (IOException e) {
                logger.log("Failed to handle connections", e);
            }
        }
    }

    private void accept() {
        try {
            SocketChannel channel = serverChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, CLIENT_OPS);
        } catch (IOException e) {
            logger.log("Failed to accept client connection", e);
        }
    }

    private void disconnect(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.keyFor(selector).cancel();
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new Server().start();
    }
}
