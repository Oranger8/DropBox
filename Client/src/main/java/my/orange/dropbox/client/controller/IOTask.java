package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.FileExchange;
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

    Message request;
    Message answer;
    File file;

    IOTask(Message request) throws IOException {
        this(request, null);
    }

    IOTask(Message request, File file) throws IOException {
        socket = new Socket(HOST, PORT);
        this.request = request;
        this.file = file;
    }

    void send() throws IOException {
        objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(request);
    }

    void receive() throws IOException, ClassNotFoundException {
        if (objectInput == null) objectInput = new ObjectInputStream(socket.getInputStream());
        answer = (Message) objectInput.readObject();
    }

    void download() {
        FileExchange.download(objectInput, file);
    }

    void upload() {
        FileExchange.upload(objectOutput, file);
    }

    void close() {
        Stream.of(objectInput,
                objectOutput)
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
