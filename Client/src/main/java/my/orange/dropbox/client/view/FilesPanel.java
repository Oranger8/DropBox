package my.orange.dropbox.client.view;

import my.orange.dropbox.client.controller.FilesTask;
import my.orange.dropbox.client.gui.MainFrame;
import my.orange.dropbox.client.model.Model;
import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.Message;
import my.orange.dropbox.common.SavedFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FilesPanel extends Panel implements ActionListener {

    private JTable table;
    private JButton downloadButton, uploadButton, deleteButton;

    public FilesPanel(MainFrame frame) {
        super(frame);
        table = new JTable(new Model(getFiles()));
        table.setPreferredSize(new Dimension(500, 400));
        table.getColumn("Name").setPreferredWidth(350);
        constraints.gridwidth = 3;
        add(table, constraints);

        downloadButton = new JButton("Download");
        downloadButton.addActionListener(this);
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        add(downloadButton, constraints);

        uploadButton = new JButton("Upload");
        uploadButton.addActionListener(this);
        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.gridx = 1;
        add(uploadButton, constraints);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(this);
        constraints.insets = new Insets(10, 0, 10, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 2;
        add(deleteButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadButton) {

        }

        if (e.getSource() == uploadButton) {

        }

        if (e.getSource() == deleteButton) {

        }
    }

    @SuppressWarnings("unchecked")
    private List<SavedFile> getFiles() {
        return  (List<SavedFile>) new FilesTask(
                new Message()
                        .setUser(frame.getUser())
                        .setCommand(Command.LIST)
        ).call();
    }
}
