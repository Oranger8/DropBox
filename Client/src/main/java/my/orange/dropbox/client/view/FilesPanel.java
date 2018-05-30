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
    private Model model;
    private JButton downloadButton, uploadButton, deleteButton;

    public FilesPanel(MainFrame frame) {
        super(frame);
        model = new Model(getFiles());
        table = new JTable(model);
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
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadButton) {

        }

        if (e.getSource() == uploadButton) {

        }

        if (e.getSource() == deleteButton) {
            SavedFile file = model.getSavedFile(table.getSelectedRow());
            if (file != null) {
                Message answer = (Message) new FilesTask(
                        new Message()
                                .setUser(frame.getUser())
                                .setCommand(Command.DELETE)
                                .setFile(file)
                ).call();
                if (answer.getCommand() == Command.AUTH_SUCCESS) {
                    model = new Model(answer.getFileList());
                    table.setModel(model);
                    table.getColumn("Size").setPreferredWidth(350);
                } else {
                    frame.notAuthorized();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<SavedFile> getFiles() {
        Message message = (Message) new FilesTask(
                new Message()
                        .setUser(frame.getUser())
                        .setCommand(Command.LIST)
        ).call();
        return message.getFileList();
    }
}
