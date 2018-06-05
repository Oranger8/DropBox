package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.*;

public class FilesTask extends IOTask {

    public FilesTask(Message message) {
        super(message);
    }

    public FilesTask(Message message, File file) {
        super(message, file);
    }

    @Override
    public Object call() {
        Message answer = null;
        try {
            sendMessage();

            switch (message.getCommand()) {

                case LIST:
                    objectInput = new ObjectInputStream(socket.getInputStream());
                    answer = (Message) objectInput.readObject();
                    break;

                case DELETE:
                    objectInput = new ObjectInputStream(socket.getInputStream());
                    answer = (Message) objectInput.readObject();
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

    private void download() throws IOException {
        bufferedInput = new BufferedInputStream(socket.getInputStream());
        fileOutput = new FileOutputStream(file);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = bufferedInput.read(buffer)) > 0) {
            fileOutput.write(buffer, 0, count);
        }
        fileOutput.flush();
    }

    private void upload() throws IOException {
        bufferedOutput = new BufferedOutputStream(socket.getOutputStream());
        fileInput = new FileInputStream(file);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = fileInput.read(buffer)) > 0) {
            bufferedOutput.write(buffer, 0, count);
        }
        bufferedOutput.flush();
    }
}
