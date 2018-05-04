package my.orange.dropbox.client.view;

import my.orange.dropbox.client.controller.FilesTask;

import javax.swing.*;
import java.awt.*;

public class FilesPanel extends Panel {

    public FilesPanel() {
        super(new FilesTask());

        JTable table = new JTable();
        table.setPreferredSize(new Dimension(500, 400));
        constraints.gridwidth = 3;
        add(table, constraints);

        JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(listener);
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        add(downloadButton, constraints);

        JButton uploadButton = new JButton("Upload");
        uploadButton.addActionListener(listener);
        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.gridx = 1;
        add(uploadButton, constraints);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(listener);
        constraints.insets = new Insets(10, 0, 10, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 2;
        add(deleteButton, constraints);

    }
}
