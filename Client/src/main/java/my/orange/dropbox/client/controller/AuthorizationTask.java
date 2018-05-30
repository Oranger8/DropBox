package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

public class AuthorizationTask implements Callable<Message> {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private Message message;

    public AuthorizationTask(Message message) {
        this.message = message;
    }

    @Override
    public Message call() {
        Message answer = null;
        try {
            socket = new Socket(HOST, PORT);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(message);
            input = new ObjectInputStream(socket.getInputStream());
            answer = (Message) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return answer;
    }

    private void close() {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
