package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.File;
import java.io.IOException;

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
            send();

            switch (request.getCommand()) {

                case DELETE:
                    receive();
                    break;

                case GET:
                    if (file == null) break;
                    download();
                    break;

                case PUT:
                    if (file == null) break;
                    upload();
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
