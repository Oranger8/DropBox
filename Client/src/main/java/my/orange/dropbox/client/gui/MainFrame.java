package my.orange.dropbox.client.gui;

import my.orange.dropbox.client.view.AuthorizationPanel;
import my.orange.dropbox.client.view.FilesPanel;
import my.orange.dropbox.common.SavedFile;
import my.orange.dropbox.common.User;

import javax.swing.*;
import java.util.List;

public class MainFrame extends JFrame {

    private User user;

    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("DropBox");
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(MainFrame.class.getResource("/image/icon.png"));
        setIconImage(icon.getImage());
        setVisible(true);
        notAuthorized();
    }

    public void notAuthorized() {
        setContentPane(new AuthorizationPanel(this));
        pack();
        setLocationRelativeTo(null);
    }

    public void authorized(User user, List<SavedFile> files) {
        this.user = user;
        setContentPane(new FilesPanel(this, files));
        pack();
        setLocationRelativeTo(null);
    }

    public User getUser() {
        return user;
    }
}
