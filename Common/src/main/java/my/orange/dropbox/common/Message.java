package my.orange.dropbox.common;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

    private User user;
    private Command command;
    private SavedFile file;

    public List<SavedFile> getFileList() {
        return fileList;
    }

    public Message setFileList(List<SavedFile> fileList) {
        this.fileList = fileList;
        return this;
    }

    private List<SavedFile> fileList;

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
