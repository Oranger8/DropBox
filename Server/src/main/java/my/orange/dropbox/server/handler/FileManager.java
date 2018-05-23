package my.orange.dropbox.server.handler;

import my.orange.dropbox.common.SavedFile;
import my.orange.dropbox.common.User;

import java.io.File;

import static my.orange.dropbox.server.util.LogManager.log;

public class FileManager {

    public void add(User user, SavedFile savedFile) {

    }

    public void delete(User user, SavedFile savedFile) {
        File file = new File(FOLDER + user.getLogin() + "/" + savedFile.getName());
        boolean deleted = file.delete();
        if (!deleted) log("File " + file.getAbsolutePath() + " not deleted");
    }

    public void addFolder(User user) {
        File userFolder = new File(FOLDER + user.getLogin());
        if (!userFolder.exists()) {
            boolean made = userFolder.mkdir();
            if (!made) log("Home folder for " + user.getLogin() + " not created");
        }
    }
}
