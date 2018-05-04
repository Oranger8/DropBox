package my.orange.dropbox.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Panel extends JPanel {

    protected ActionListener listener;
    protected GridBagConstraints constraints;

    protected static Dimension FIELD_DIMENSION;

    public Panel(ActionListener listener) {
        this.listener = listener;
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        FIELD_DIMENSION = new Dimension(150, 27);
    }
}
