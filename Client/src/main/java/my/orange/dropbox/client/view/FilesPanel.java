package my.orange.dropbox.client.view;

import my.orange.dropbox.client.controller.FilesTask;
import my.orange.dropbox.client.gui.FileChooser;
import my.orange.dropbox.client.gui.MainFrame;
import my.orange.dropbox.client.model.Model;
import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.Message;
import my.orange.dropbox.common.SavedFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static my.orange.dropbox.common.Command.*;

public class FilesPanel extends Panel implements ActionListener {

    private JTable table;
    private Model model;
    private JButton downloadButton, uploadButton, deleteButton;

    public FilesPanel(MainFrame frame, List<SavedFile> files) {
        super(frame);
        table = new JTable();
        table.setPreferredSize(new Dimension(500, 400));
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

        updateModel(files);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == downloadButton) {
            SavedFile file = model.getSavedFile(table.getSelectedRow());
            if (file != null) {
                Message answer;
                try {
                    answer = (Message) new FilesTask(
                            new Message()
                                    .setUser(frame.getUser())
                                    .setCommand(Command.GET)
                                    .setFile(file),
                            new FileChooser(this).choose()
                    ).call();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    frame.notAuthorized();
                    return;
                }

                if (answer.getCommand() == LOGIN_INCORRECT) {
                    frame.notAuthorized();
                }
            }
        }

        if (e.getSource() == uploadButton) {
            Message answer;
            try {
                File file = new FileChooser(this).choose();
                answer = (Message) new FilesTask(
                        new Message()
                                .setUser(frame.getUser())
                                .setCommand(PUT)
                                .setFile(new SavedFile(file)),
                        file
                ).call();
            } catch (IOException ex) {
                ex.printStackTrace();
                frame.notAuthorized();
                return;
            }

            if (answer.getCommand() == AUTH_SUCCESS) {
                updateModel(answer.getFileList());
            } else {
                frame.notAuthorized();
            }
        }

        if (e.getSource() == deleteButton) {
            SavedFile file = model.getSavedFile(table.getSelectedRow());
            if (file != null) {
                Message answer;
                try {
                    answer = (Message) new FilesTask(
                            new Message()
                                    .setUser(frame.getUser())
                                    .setCommand(DELETE)
                                    .setFile(file)
                    ).call();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    frame.notAuthorized();
                    return;
                }

                if (answer.getCommand() == AUTH_SUCCESS) {
                    updateModel(answer.getFileList());
                } else {
                    frame.notAuthorized();
                }
            }
        }
    }

    private void updateModel(List<SavedFile> files) {
        model = new Model(files);
        table.setModel(model);
        table.getColumn("Name").setPreferredWidth(350);
    }
}
