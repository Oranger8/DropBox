package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class AuthorizationTask extends IOTask {

    public AuthorizationTask(Message message) {
        super(message);
    }

    @Override
    public Message call() {
        Message answer = null;
        try {
            sendMessage();
            objectInput = new ObjectInputStream(socket.getInputStream());
            answer = (Message) objectInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return answer;
    }
}
