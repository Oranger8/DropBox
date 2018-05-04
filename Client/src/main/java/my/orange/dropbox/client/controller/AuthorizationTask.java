package my.orange.dropbox.client.controller;

import my.orange.dropbox.client.Client;
import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

public class AuthorizationTask implements Callable<Command> {

    private User user;
    private Command command;

    public AuthorizationTask(User user, Command command) {
        this.user = user;
        this.command = command;
    }

    @Override
    public Command call() {
        try {
            ObjectInputStream input = new ObjectInputStream(Client.socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(Client.socket.getOutputStream());
            output.writeObject(command);
            output.writeObject(user);
            return  (Command) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
