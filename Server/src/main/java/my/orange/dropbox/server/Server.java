package my.orange.dropbox.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import static my.orange.dropbox.server.Configuration.PORT;
import static my.orange.dropbox.server.LogManager.log;

public class Server {

    private ServerSocketChannel channel;
    private Selector selector;

    private Server(int port) {
        try {
            channel = ServerSocketChannel.open();
            selector = Selector.open();
            channel.bind(new InetSocketAddress(port));
            channel.configureBlocking(false);
            channel.register(selector, channel.validOps());
        } catch (IOException e) {
            log(e);
        } finally {
            try { channel.close(); } catch (IOException e) { log(e); }
        }
    }

    private void start() {
        while (channel.isOpen()) {
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    if (key.isAcceptable()) {
                        accept(key);
                        keys.iterator().remove();
                    }
                }
            } catch (IOException e) {
                log(e);
            }
        }
    }

    private void accept(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            channel.configureBlocking(false);
            channel.register(selector, channel.validOps());
        } catch (IOException e) {
            log(e);
        } finally {
            try { channel.close(); } catch (IOException e) { log(e); }
        }
    }

    public static void main(String[] args) {
        new Server(PORT).start();
    }
}
