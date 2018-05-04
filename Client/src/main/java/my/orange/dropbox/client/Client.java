package my.orange.dropbox.client;

import my.orange.dropbox.client.gui.MainFrame;

import java.io.IOException;
import java.net.Socket;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

public class Client {

    private Socket socket;

    public Client() {
        MainFrame frame = new MainFrame();
        try {
            socket = new Socket(HOST, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }

}
