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

import static my.orange.dropbox.common.Command.*;

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

        Message answer;

        try {
            answer = new AuthorizationTask(new Message()
                    .setUser(user)
                    .setCommand(LOGIN)).call();
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        if (e.getSource() == loginButton) {
            try {
                answer = authorization(user, LOGIN);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == registerButton) {
            try {
                answer = authorization(user, REGISTER);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (answer != null) {
            if (answer.getCommand() == AUTH_SUCCESS) {
                frame.authorized(user, answer.getFileList());
            } else {
                JOptionPane.showMessageDialog(this, answer.getCommand().getTitle());
                passwordField.setText("");
                passwordField.requestFocusInWindow();
            }
        }
    }

    private Message authorization(User user, Command command) throws IOException {
        return new AuthorizationTask(new Message()
                .setUser(user)
                .setCommand(command)).call();
    }

    private User getUser() {
        if (loginField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Login is empty");
            loginField.requestFocusInWindow();
            return null;
        }
        if (passwordField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password is empty");
            passwordField.requestFocusInWindow();
            return null;
        }
        return new User(loginField.getText(), passwordField.getText());
    }
}
