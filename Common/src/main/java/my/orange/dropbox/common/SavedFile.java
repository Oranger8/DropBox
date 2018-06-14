package my.orange.dropbox.common;

import java.io.File;
import java.io.Serializable;

public class SavedFile implements Serializable {
    
    private String size;
    private String name;

    public SavedFile(File file) {
        name = file.getName();
        if (file.length() > 1024 * 1024 * 1024) {
            size = file.length() / 1024 / 1024 / 1024 + " GB";
        } else if (file.length() > 1024 * 1024) {
            size = file.length() / 1024 / 1024 + " MB";
        } else if (file.length() > 1024) {
            size = file.length() / 1024 + " KB";
        } else {
            size = file.length() + " B";
        }
    }

    public String getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
