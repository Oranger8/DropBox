package my.orange.dropbox.server.handler;

import my.orange.dropbox.common.SavedFile;
import my.orange.dropbox.common.User;
import my.orange.dropbox.server.util.Configuration;
import my.orange.dropbox.server.util.LogManager;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileManager extends Configuration {

    private static LogManager logger;

    static {
        try {
            logger = LogManager.getLogger();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(User user, SavedFile savedFile) {

    }

    public void delete(User user, SavedFile savedFile) {
        Path file = Paths.get(FOLDER + user.getLogin() + "/" + savedFile.getName());
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            logger.log("Failed to delete file", e);
        }
    }

    public void addFolder(User user) {
        Path files = Paths.get(FOLDER);
        if (!Files.exists(files) || !Files.isDirectory(files)) {
            try {
                Files.createDirectory(files);
            } catch (IOException e) {
                logger.log("Failed to create main folder", e);
            }
        }
        Path folder = Paths.get(FOLDER + user.getLogin());
        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            try {
                Files.createDirectory(folder);
            } catch (IOException e) {
                logger.log("Failed to create user folder", e);
            }
        }
    }

    public List<SavedFile> getFileList(User user) {
        List<SavedFile> list = new LinkedList<>();
        Path folder = Paths.get(FOLDER + user.getLogin());
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(folder);
            for (Path path : stream) {
                list.add(new SavedFile(path.toFile()));
            }
        } catch (IOException e) {
            logger.log("Failed to get list of files", e);
        }
        return list;
    }
}
