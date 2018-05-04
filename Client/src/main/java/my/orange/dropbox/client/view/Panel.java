package my.orange.dropbox.client.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Panel extends JPanel implements ActionListener {

    protected GridBagConstraints constraints;

    protected static Dimension FIELD_DIMENSION;

    public Panel() {
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        FIELD_DIMENSION = new Dimension(150, 27);
    }
}
