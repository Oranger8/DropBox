package my.orange.dropbox.client.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tray extends MouseAdapter {

    private MainFrame frame;
    private TrayIcon icon;

    Tray(MainFrame frame) {
        this.frame = frame;
        PopupMenu menu = new PopupMenu("DropBox");
        MenuItem item = new MenuItem("Exit", new MenuShortcut(KeyEvent.VK_Q));
        menu.add(item);
        icon = new TrayIcon(
                Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/image/icon.png")),
                "DropBox",
                menu);
        icon.setImageAutoSize(true);
        icon.addMouseListener(this);
    }

    void build() throws AWTException {
        SystemTray tray = SystemTray.getSystemTray();
        tray.add(icon);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        frame.setVisible(!frame.isVisible());
    }
}
