package my.orange.dropbox.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

import static java.nio.channels.SelectionKey.OP_CONNECT;
import static java.nio.channels.SelectionKey.OP_READ;
import static my.orange.dropbox.server.LogManager.log;

public class ClientHandler implements Runnable {

    private static final int CLIENT_OPS = OP_READ | OP_CONNECT;

    private Selector selector;

    public ClientHandler() throws IOException {
        selector = Selector.open();
        new Thread(this).start();
    }

    void accept(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            channel.configureBlocking(false);
            channel.register(selector, CLIENT_OPS);
        } catch (IOException e) {
            log(e);
        }
    }

    void read(SelectionKey key) {

    }

    void disconnect(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.keyFor(selector).cancel();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                if (selector.select() < 0) continue;
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {

                    if (key.isReadable()) {
                        read(key);
                        keys.iterator().remove();
                    }

                    if (key.isConnectable()) {
                        disconnect(key);
                        keys.iterator().remove();
                    }

                }
            } catch (IOException e) {
                log(e);
            }
        }
    }
}
