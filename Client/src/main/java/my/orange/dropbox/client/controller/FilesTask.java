package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

public class FilesTask extends IOTask {

    public FilesTask(Message message) {
        super(message);
    }

    @Override
    public Object call() {
        Message answer = null;
        try {
            socket = new Socket(HOST, PORT);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(message);
            input = new ObjectInputStream(socket.getInputStream());

            switch (message.getCommand()) {

                case LIST:
                    answer = (Message) input.readObject();
                    return answer.getFileList();

            }

            return input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return null;
    }
}
