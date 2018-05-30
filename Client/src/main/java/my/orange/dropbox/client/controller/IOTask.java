package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

abstract class IOTask implements Callable {

    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;

    Message message;

    IOTask(Message message) {
        this.message = message;
    }

    void close() {
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
