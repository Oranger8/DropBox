package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.Message;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

public class FilesTask extends IOTask {

    public FilesTask(Message request) {
        super(request);
    }

    public FilesTask(Message request, File file) {
        super(request, file);
    }

    @Override
    public Object call() {
        try {
            socket = new Socket(HOST, PORT);
            send();

            switch (request.getCommand()) {

                case DELETE:
                    receive();
                    break;

                case GET:
                    if (file == null) break;
                    receive();
                    if (answer.getCommand() == Command.AUTH_SUCCESS) {
                        download();
                    }
                    break;

                case PUT:
                    if (file == null) break;
                    receive();
                    if (answer.getCommand() == Command.AUTH_SUCCESS) {
                        upload();
                        receive();
                    }
                    break;

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return answer;
    }
}
