package my.orange.dropbox.common;

import java.io.Serializable;

public class Message implements Serializable {

    private User user;
    private Command command;
    private SavedFile file;

    public User getUser() {
        return user;
    }

    public Message setUser(User user) {
        this.user = user;
        return this;
    }

    public Command getCommand() {
        return command;
    }

    public Message setCommand(Command command) {
        this.command = command;
        return this;
    }

    public SavedFile getFile() {
        return file;
    }

    public Message setFile(SavedFile file) {
        this.file = file;
        return this;
    }
}
