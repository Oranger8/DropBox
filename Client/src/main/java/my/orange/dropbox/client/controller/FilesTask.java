package my.orange.dropbox.client.controller;

import my.orange.dropbox.client.Client;
import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.SavedFile;
import my.orange.dropbox.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

public class FilesTask implements Callable {

    private User user;
    private SavedFile file;
    private Command command;

    public FilesTask(User user, SavedFile file, Command command) {
        this.user = user;
        this.file = file;
        this.command = command;
    }

    @Override
    public Object call() {
        /*try {
            ObjectInputStream input = new ObjectInputStream(Client.socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(Client.socket.getOutputStream());
            output.writeObject(command);
            output.writeObject(user);
            switch (command) {

                case PUT:
                    output.writeObject(file);
                    break;

                case DELETE:
                    output.writeObject(file);
                    break;

            }
            return input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return e;
        }*/
        return null;
    }
}
