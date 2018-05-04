package my.orange.dropbox.common;

import java.io.File;

public class SavedFile extends File {

    public SavedFile(String pathname) {
        super(pathname);
    }

    public String getSize() {
        if (length() > 1024 * 1024 * 1024) return length() / 1024 / 1024 / 1024 + "GB";
        if (length() > 1024 * 1024) return length() / 1024 / 1024 + "MB";
        if (length() > 1024) return length() / 1024 + "KB";
        return length() + "B";
    }
}
