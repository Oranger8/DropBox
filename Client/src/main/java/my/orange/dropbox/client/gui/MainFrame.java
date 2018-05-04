package my.orange.dropbox.client.gui;

import my.orange.dropbox.client.view.AuthorizationPanel;
import my.orange.dropbox.client.view.FilesPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("DropBox");
        setLocationRelativeTo(null);
        setVisible(true);
        setAuthorized(false);
    }

    public void setAuthorized(boolean authorized) {
        if (authorized) {
            setContentPane(new FilesPanel());
            pack();
        } else {
            setContentPane(new AuthorizationPanel());
            pack();
        }
    }
}
