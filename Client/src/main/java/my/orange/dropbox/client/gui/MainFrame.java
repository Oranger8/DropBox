package my.orange.dropbox.client.gui;

import my.orange.dropbox.client.view.AuthorizationPanel;
import my.orange.dropbox.client.view.FilesPanel;
import my.orange.dropbox.common.SavedFile;
import my.orange.dropbox.common.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private User user;

    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        try {
            new Tray(this).build();
            setDefaultCloseOperation(HIDE_ON_CLOSE);
        } catch (AWTException | UnsupportedOperationException e) {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        setTitle("DropBox");
        ImageIcon icon = new ImageIcon(MainFrame.class.getResource("/image/icon.png"));
        setIconImage(icon.getImage());
        notAuthorized();
        setVisible(true);
        setResizable(false);
    }

    public void notAuthorized() {
        setContentPane(new AuthorizationPanel(this));
        pack();
    }

    public void authorized(User user, List<SavedFile> files) {
        this.user = user;
        setContentPane(new FilesPanel(this, files));
        pack();
    }

    public User getUser() {
        return user;
    }
}
