package my.orange.dropbox.client;

import my.orange.dropbox.client.gui.MainFrame;
import my.orange.dropbox.common.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

public class Client {

    private User user;
    private SocketChannel channel;
    private Selector selector;

    public Client() {
        MainFrame frame = new MainFrame();
        try {
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(HOST, PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
