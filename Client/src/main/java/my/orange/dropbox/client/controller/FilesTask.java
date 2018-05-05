package my.orange.dropbox.client.controller;

import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.SavedFile;
import my.orange.dropbox.common.User;

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
    public Object call() throws Exception {
        return null;
    }
}
