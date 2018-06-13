package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.Message;

import java.io.File;
import java.io.IOException;

public class FilesTask extends IOTask {

    public FilesTask(Message request) throws IOException {
        super(request);
    }

    public FilesTask(Message request, File file) throws IOException {
        super(request, file);
    }

    @Override
    public Object call() {
        try {

            switch (request.getCommand()) {

                case DELETE:
                    send();
                    receive();
                    break;

                case GET:
                    if (file == null) break;
                    send();
                    receive();
                    if (answer.getCommand() == Command.AUTH_SUCCESS) {
                        download();
                    }
                    break;

                case PUT:
                    if (request.getFile() == null) break;
                    send();
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
