package my.orange.dropbox.common;

import java.io.File;

public class SavedFile extends File {
    
    private String size;

    public SavedFile(String filePath) {
        super(filePath);
        if (length() > 1024 * 1024 * 1024) size = length() / 1024 / 1024 / 1024 + "GB";
        if (length() > 1024 * 1024) size = length() / 1024 / 1024 + "MB";
        if (length() > 1024) size = length() / 1024 + "KB";
        size = length() + "B";
    }

    public String getSize() {
        return size;
    }
}
