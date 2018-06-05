package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

import static my.orange.dropbox.client.Configuration.HOST;
import static my.orange.dropbox.client.Configuration.PORT;

abstract class IOTask implements Callable {

    Socket socket;
    ObjectInputStream objectInput;
    ObjectOutputStream objectOutput;
    BufferedInputStream bufferedInput;
    BufferedOutputStream bufferedOutput;
    FileInputStream fileInput;
    FileOutputStream fileOutput;

    Message message;
    File file;

    IOTask(Message message) {
        this(message, null);
    }

    IOTask(Message message, File file) {
        this.message = message;
        this.file = file;
    }

    void sendMessage() throws IOException {
        socket = new Socket(HOST, PORT);
        objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(message);
    }

    void close() {
        if (objectInput != null) {
            try {
                objectInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (objectOutput != null) {
            try {
                objectOutput.close();
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
