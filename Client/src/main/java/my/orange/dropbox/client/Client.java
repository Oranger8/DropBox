package my.orange.dropbox.client;

import my.orange.dropbox.client.gui.MainFrame;
import my.orange.dropbox.common.User;

import java.io.IOException;
import java.net.Socket;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

public class Client {

    public static User user;
    public static Socket socket = null;

    public Client() {
        MainFrame frame = new MainFrame();
        try {
            if (socket != null) socket = new Socket(HOST, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }

}
