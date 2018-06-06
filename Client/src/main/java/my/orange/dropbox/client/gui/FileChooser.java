package my.orange.dropbox.client.gui;

import my.orange.dropbox.client.view.Panel;

import javax.swing.*;
import java.io.File;

public class FileChooser {

    private JFileChooser chooser;
    private Panel panel;

    public FileChooser(Panel panel) {
        this.panel = panel;
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public File choose() {
        int state = chooser.showDialog(panel, "123");
        if (state != JFileChooser.APPROVE_OPTION) return null;
        return chooser.getSelectedFile();
    }
}
