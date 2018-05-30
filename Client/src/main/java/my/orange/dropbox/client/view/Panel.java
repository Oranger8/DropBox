package my.orange.dropbox.client.view;

import my.orange.dropbox.client.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Panel extends JPanel implements ActionListener {

    protected MainFrame frame;
    protected GridBagConstraints constraints;

    protected static Dimension FIELD_DIMENSION;

    public Panel(MainFrame frame) {
        this.frame = frame;
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        FIELD_DIMENSION = new Dimension(150, 27);
    }
}
