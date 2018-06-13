package my.orange.dropbox.client.view;

import my.orange.dropbox.client.controller.AuthorizationTask;
import my.orange.dropbox.client.gui.MainFrame;
import my.orange.dropbox.common.Command;
import my.orange.dropbox.common.Message;
import my.orange.dropbox.common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class AuthorizationPanel extends Panel {

    private JTextField loginField, passwordField;
    private JButton loginButton, registerButton;

    public AuthorizationPanel(MainFrame frame) {
        super(frame);
        loginField = new JTextField();
        loginField.setPreferredSize(FIELD_DIMENSION);
        constraints.insets = new Insets(10, 10, 0, 10);
        constraints.gridwidth = 2;
        add(loginField, constraints);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(FIELD_DIMENSION);
        constraints.insets = new Insets(0, 10, 0, 10);
        constraints.gridy = 1;
        add(passwordField, constraints);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        constraints.insets = new Insets(0, 10, 10, 10);
        constraints.gridwidth = 1;
        constraints.gridy = 2;
        add(registerButton, constraints);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        constraints.insets = new Insets(0, 10, 10, 10);
        constraints.anchor = GridBagConstraints.EAST;
        constraints.gridx = 1;
        add(loginButton, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user = getUser();
        if (user == null) return;

        Message answer = null;

        if (e.getSource() == loginButton) {
            try {
                answer = new AuthorizationTask(new Message()
                        .setUser(user)
                        .setCommand(Command.LOGIN)).call();
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
        }

        if (e.getSource() == registerButton) {
            try {
                answer = new AuthorizationTask(new Message()
                        .setUser(user)
                        .setCommand(Command.REGISTER)).call();
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }
        }

        if (answer != null) {
            switch (answer.getCommand()) {

                case AUTH_SUCCESS:
                    frame.authorized(user, answer.getFileList());
                    break;

                    default:
                        JOptionPane.showMessageDialog(this, answer.getCommand().getTitle());

            }
        }
    }

    private User getUser() {
        if (loginField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Login is empty");
            return null;
        }
        if (passwordField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password is empty");
            return null;
        }
        return new User(loginField.getText(), passwordField.getText());
    }
}
