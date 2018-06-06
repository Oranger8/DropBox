package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

abstract class IOTask implements Callable {

    private Socket socket;
    private ObjectInputStream objectInput;
    private ObjectOutputStream objectOutput;
    private BufferedInputStream bufferedInput;
    private BufferedOutputStream bufferedOutput;
    private FileInputStream fileInput;
    private FileOutputStream fileOutput;

    Message request;
    Message answer;
    File file;

    IOTask(Message request) {
        this(request, null);
    }

    IOTask(Message request, File file) {
        this.request = request;
        this.file = file;
    }

    void send() throws IOException {
        socket = new Socket(HOST, PORT);
        objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(request);
    }

    void receive() throws IOException, ClassNotFoundException {
        socket = new Socket(HOST, PORT);
        objectInput = new ObjectInputStream(socket.getInputStream());
        answer = (Message) objectInput.readObject();
    }

    void download() throws IOException {
        bufferedInput = new BufferedInputStream(socket.getInputStream());
        fileOutput = new FileOutputStream(file);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = bufferedInput.read(buffer)) > 0) {
            fileOutput.write(buffer, 0, count);
        }
        fileOutput.flush();
    }

    void upload() throws IOException {
        bufferedOutput = new BufferedOutputStream(socket.getOutputStream());
        fileInput = new FileInputStream(file);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = fileInput.read(buffer)) > 0) {
            bufferedOutput.write(buffer, 0, count);
        }
        bufferedOutput.flush();
    }

    void close() {
        Stream.of(objectInput,
                objectOutput,
                bufferedInput,
                bufferedOutput,
                fileInput,
                fileOutput)
                .forEach(closeable -> {
                    if (closeable != null) {
                        try {
                            closeable.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
