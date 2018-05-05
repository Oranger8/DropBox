package my.orange.dropbox.common;

public class SavedFile {
    
    private String name;
    private String size;

    public SavedFile(String name, long size) {
        this.name = name;
        if (size > 1024 * 1024 * 1024) this.size = size / 1024 / 1024 / 1024 + "GB";
        if (size > 1024 * 1024) this.size = size / 1024 / 1024 + "MB";
        if (size > 1024) this.size = size / 1024 + "KB";
        this.size = size + "B";
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
}
