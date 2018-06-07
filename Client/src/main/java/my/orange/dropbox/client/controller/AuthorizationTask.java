package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.IOException;
import java.net.Socket;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

public class AuthorizationTask extends IOTask {

    public AuthorizationTask(Message message) {
        super(message);
    }

    @Override
    public Message call() {
        try {
            socket = new Socket(HOST, PORT);
            send();
            receive();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return answer;
    }
}
