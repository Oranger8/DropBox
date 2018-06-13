package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.IOException;

public class AuthorizationTask extends IOTask {

    public AuthorizationTask(Message message) throws IOException {
        super(message);
    }

    @Override
    public Message call() {
        try {
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
