package my.orange.dropbox.client;

import my.orange.dropbox.client.gui.MainFrame;
import my.orange.dropbox.common.User;

public class Client {

    private User user;

    public Client() {
        MainFrame frame = new MainFrame();
    }

    public static void main(String[] args) {
        new Client();
    }
}
